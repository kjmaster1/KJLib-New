package com.kjmaster.kjlib.blocks;

import com.kjmaster.kjlib.api.container.CapabilityContainerProvider;
import com.kjmaster.kjlib.builder.BlockBuilder;
import com.kjmaster.kjlib.tileentity.GenericTileEntity;
import com.kjmaster.kjlib.tileentity.TickingTileEntity;
import com.kjmaster.kjlib.varia.OrientationTools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class BaseBlock extends Block implements EntityBlock {

    private final BlockEntityType.BlockEntitySupplier<BlockEntity> tileEntitySupplier;

    public static final Property<?>[] HORIZ_PROPERTIES = new Property[]{BlockStateProperties.HORIZONTAL_FACING};
    public static final Property<?>[] ROTATING_PROPERTIES = new Property[]{BlockStateProperties.FACING};
    public static final Property<?>[] NONE_PROPERTIES = new Property[0];

    public BaseBlock(BlockBuilder builder) {
        super(builder.getProperties());
        this.tileEntitySupplier = builder.getTileEntitySupplier();
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof GenericTileEntity genericTileEntity) {
            InteractionResult resultType = genericTileEntity.onBlockActivated(state, player, hand, result);
            if (resultType != InteractionResult.PASS) {
                if (!player.isCrouching()) {
                    openGui(world, pos.getX(), pos.getY(), pos.getZ(), player);
                }
                return resultType;
            }
        }
        return super.use(state, world, pos, player, hand, result);
    }

    protected void breakAndRemember(Level world, Player player, BlockPos pos) {
        if (!world.isClientSide) {
            playerDestroy(world, player, pos, world.getBlockState(pos), world.getBlockEntity(pos), ItemStack.EMPTY);
            world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }

    protected boolean openGui(Level world, int x, int y, int z, Player player) {
        BlockEntity te = world.getBlockEntity(new BlockPos(x, y, z));
        if (te == null) {
            return false;
        }

        return te.getCapability(CapabilityContainerProvider.CONTAINER_PROVIDER_CAPABILITY).map(h -> {
            if (world.isClientSide) {
                return true;
            }
            NetworkHooks.openScreen((ServerPlayer) player, h, te.getBlockPos());
            return true;
        }).orElse(false);
    }

    @Override
    public void setPlacedBy(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nullable LivingEntity placer, @Nonnull ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);

        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof GenericTileEntity genericTileEntity) {
            genericTileEntity.onBlockPlacedBy(world, pos, state, placer, stack);
        }

        checkRedstone(world, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull Block blockIn, @Nonnull BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, blockIn, fromPos, isMoving);
        checkRedstone(world, pos);
    }

    protected void checkRedstone(Level world, BlockPos pos) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof GenericTileEntity genericTileEntity) {
            genericTileEntity.checkRedstone(world, pos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean triggerEvent(@Nonnull BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, int id, int param) {
        if (hasTileEntitySupplier()) {
            super.triggerEvent(state, worldIn, pos, id, param);
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            return tileentity != null && tileentity.triggerEvent(id, param);
        } else {
            return super.triggerEvent(state, worldIn, pos, id, param);
        }
    }

    protected boolean hasTileEntitySupplier() {
        return tileEntitySupplier != null;
    }

    public RotationType getRotationType() {
        return RotationType.ROTATION;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (hasTileEntitySupplier()) {
            return tileEntitySupplier.create(pos, state);
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockState, BlockEntityType<T> blockEntityType) {
        // @todo 1.18 figure out a way to only return a ticker client/server if needed
        return BaseBlock::runTick;
    }

    public static void runTick(Level world, BlockPos blockPos, BlockState state, BlockEntity blockEntity) {
        if (blockEntity instanceof TickingTileEntity ticking) {
            ticking.tick();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState newstate, boolean isMoving) {
        if (!world.isClientSide) {
            BlockEntity te = world.getBlockEntity(pos);
            if (te instanceof GenericTileEntity genericTileEntity) {
                genericTileEntity.onReplaced(world, pos, state, newstate);
            }
        }
        super.onRemove(state, world, pos, newstate, isMoving);
    }

    protected Property<?>[] getProperties() {
        return getProperties(getRotationType());
    }

    public static Property<?>[] getProperties(RotationType rotationType) {
        return switch (rotationType) {
            case HORIZROTATION -> HORIZ_PROPERTIES;
            case ROTATION -> ROTATING_PROPERTIES;
            case NONE -> NONE_PROPERTIES;
        };
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        for (Property<?> property : getProperties()) {
            builder.add(property);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Player placer = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState state = super.getStateForPlacement(context);
        return switch (getRotationType()) {
            case HORIZROTATION -> state.setValue(BlockStateProperties.HORIZONTAL_FACING, placer.getDirection().getOpposite());
            case ROTATION -> state.setValue(BlockStateProperties.FACING, OrientationTools.getFacingFromEntity(pos, placer));
            case NONE -> state;
        };
    }

    protected Direction getOrientation(BlockPos pos, LivingEntity entity) {
        return switch (getRotationType()) {
            case HORIZROTATION -> OrientationTools.determineOrientationHoriz(entity);
            case ROTATION -> OrientationTools.determineOrientation(pos, entity);
            case NONE -> null;
        };
    }

    public Direction getFrontDirection(BlockState state) {
        return switch (getRotationType()) {
            case HORIZROTATION -> state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            case ROTATION -> state.getValue(BlockStateProperties.FACING);
            case NONE -> Direction.NORTH;
        };
    }

    public Direction getRightDirection(BlockState state) {
        return getFrontDirection(state).getCounterClockWise();
    }

    public Direction getLeftDirection(BlockState state) {
        return getFrontDirection(state).getClockWise();
    }

    public static Direction getFrontDirection(RotationType rotationType, BlockState state) {
        return switch (rotationType) {
            case HORIZROTATION -> OrientationTools.getOrientationHoriz(state);
            case ROTATION -> OrientationTools.getOrientation(state);
            case NONE -> Direction.SOUTH;
        };
    }
}
