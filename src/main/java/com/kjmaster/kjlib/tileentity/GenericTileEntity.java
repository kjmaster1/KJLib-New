package com.kjmaster.kjlib.tileentity;

import com.kjmaster.kjlib.blockcommands.*;
import com.kjmaster.kjlib.container.AutomationFilterItemHandler;
import com.kjmaster.kjlib.container.GenericItemHandler;
import com.kjmaster.kjlib.network.PacketRequestDataFromServer;
import com.kjmaster.kjlib.typed.Key;
import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class GenericTileEntity extends BlockEntity {

    // This is a generated function (from the annotated capabilities) that is initially (by the TE
    // constructor) set to be a function that looks for the annotations and replaces itself with
    // a function that does the actual testing
    private BiFunction<Capability, Direction, LazyOptional> capSetup;
    private final List<LazyOptional> lazyOptsToClean = new ArrayList<>();

    protected int powerLevel = 0;

    public GenericTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);

        capSetup = (cap,dir) -> {
            List<Pair<Field, Cap>> list = getAnnotationHolder().capabilityList;
            capSetup = generateCapTests(list, 0);
            return capSetup.apply(cap, dir);
        };
        // Make sure the annotation holder exists
        getAnnotationHolder();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
    }

    private BiFunction<Capability, Direction, LazyOptional> generateCapTests(List<Pair<Field, Cap>> caps, int index) {
        if (index >= caps.size()) {
            return super::getCapability;
        } else {
            try {
                Cap annotation = caps.get(index).getRight();
                Object instance = FieldUtils.readField(caps.get(index).getLeft(), this, true);
                LazyOptional lazy;
                if (instance instanceof LazyOptional) {
                    lazy = (LazyOptional) instance;
                } else if (annotation.type() == CapType.ITEMS_AUTOMATION) {
                    lazy = LazyOptional.of(() -> new AutomationFilterItemHandler((GenericItemHandler) instance));
                } else {
                    lazy = LazyOptional.of(() -> instance);
                }
                lazyOptsToClean.add(lazy);
                BiFunction<Capability, Direction, LazyOptional> tail = generateCapTests(caps, index + 1);
                Capability desiredCapability = annotation.type().getCapability();
                return (cap, dir) -> {
                    if (cap == desiredCapability) {
                        return lazy;
                    } else {
                        return tail.apply(cap, dir);
                    }
                };
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return capSetup.apply(cap, side);
    }

    public void markDirtyClient() {
        setChanged();
        if (getLevel() != null) {
            BlockState state = getLevel().getBlockState(getBlockPos());
            getLevel().sendBlockUpdated(getBlockPos(), state, state, Block.UPDATE_CLIENTS + Block.UPDATE_NEIGHBORS);
        }
    }

    public void markDirtyQuick() {
        if (getLevel() != null) {
            getLevel().blockEntityChanged(this.worldPosition);
        }
    }

    public Map<String, ValueHolder<?, ?>> getValueMap() {
        AnnotationHolder holder = getAnnotationHolder();
        return holder.valueMap;
    }

    public void onBlockPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    }

    public void onReplaced(Level world, BlockPos pos, BlockState state, BlockState newstate) {
    }

    public InteractionResult onBlockActivated(BlockState state, Player player, InteractionHand hand, BlockHitResult result) {
        return InteractionResult.PASS;
    }

    protected boolean needsRedstoneMode() {
        return false;
    }

    public void checkRedstone(Level world, BlockPos pos) {
        int powered = world.getBestNeighborSignal(pos); // @todo check
        setPowerInput(powered);
    }


    public void setPowerInput(int powered) {
        if (powerLevel != powered) {
            powerLevel = powered;
            setChanged();
        }
    }

    public int getPowerLevel() {
        return powerLevel;
    }


    // Called when a slot is changed.
    public void onSlotChanged(int index, ItemStack stack) {
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbtTag = new CompoundTag();
        this.saveClientDataToNBT(nbtTag);
        return ClientboundBlockEntityDataPacket.create(this, (BlockEntity entity) -> {return nbtTag;});
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        loadClientDataFromNBT(packet.getTag());
    }

    public boolean canPlayerAccess(Player player) {
        return !isRemoved() && player.distanceToSqr(new Vec3(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ()).add(new Vec3(0.5D, 0.5D, 0.5D))) <= 64D;
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag updateTag = super.getUpdateTag();
        saveClientDataToNBT(updateTag);
        return updateTag;
    }

    /**
     * Override to write only the data you need on the client
     */
    public void saveClientDataToNBT(CompoundTag tagCompound) {
    }

    /**
     * Override to read only the data you need on the client
     */
    public void loadClientDataFromNBT(CompoundTag tagCompound) {
    }

    @Override
    public void load(CompoundTag tagCompound) {
        loadCaps(tagCompound);
    }

    protected void loadCaps(CompoundTag tagCompound) {
        loadItemHandlerCap(tagCompound);
        loadEnergyCap(tagCompound);
    }

    protected void loadItemHandlerCap(CompoundTag tagCompound) {
        getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(h -> h instanceof INBTSerializable)
                .map(h -> (INBTSerializable) h)
                .ifPresent(h -> {
                    // For compatibility with loot tables we check McItems first
                    if (tagCompound.contains("McItems")) {
                        h.deserializeNBT(tagCompound.getList("McItems", Tag.TAG_COMPOUND));
                    } else {
                        h.deserializeNBT(tagCompound.getList("Items", Tag.TAG_COMPOUND));
                    }
                });
    }

    protected void loadEnergyCap(CompoundTag tagCompound) {
        getCapability(ForgeCapabilities.ENERGY)
                .filter(h -> h instanceof INBTSerializable)
                .map(h -> (INBTSerializable) h)
                .ifPresent(h -> {
                    if (tagCompound.contains("Energy")) {
                        h.deserializeNBT(tagCompound.get("Energy"));
                    }
                });
    }

    public void saveAdditional(@Nonnull CompoundTag tagCompound) {
        saveCaps(tagCompound);
    }

    protected void saveCaps(CompoundTag tagCompound) {
        saveItemHandlerCap(tagCompound);
        saveEnergyCap(tagCompound);
    }

    protected void saveItemHandlerCap(CompoundTag tagCompound) {
        getCapability(ForgeCapabilities.ITEM_HANDLER)
                .filter(h -> h instanceof INBTSerializable)
                .map(h -> (INBTSerializable) h)
                .ifPresent(h -> tagCompound.put("Items", h.serializeNBT()));
    }

    protected void saveEnergyCap(CompoundTag tagCompound) {
        getCapability(ForgeCapabilities.ENERGY)
                .filter(h -> h instanceof INBTSerializable)
                .map(h -> (INBTSerializable) h)
                .ifPresent(h -> tagCompound.put("Energy", h.serializeNBT()));
    }

    public int getRedstoneOutput(BlockState state, BlockGetter world, BlockPos pos, Direction side) {
        return -1;
    }

    public void getDrops(NonNullList<ItemStack> drops, BlockGetter world, BlockPos pos, BlockState metadata, int fortune) {
    }

    public void rotateBlock(Rotation axis) {

    }

    private <T extends GenericTileEntity, V> BiConsumer<T, V> findSetter(Key<V> key) {
        // Cache or use Map?
        for (Map.Entry<String, ValueHolder<?, ?>> entry : getValueMap().entrySet()) {
            ValueHolder value = entry.getValue();
            if (key.name().equals(value.key().name())) {
                return value.setter();
            }
        }
        return null;
    }

    /// Override this function if you have a tile entity that needs to be opened remotely and thus has to 'fake' the real dimension
    public ResourceKey<Level> getDimension() {
        return level.dimension();
    }

    /**
     * Call this client-side to this TE to request data from the server
     */
    public void requestDataFromServer(SimpleChannel channel, ICommand command, @Nonnull TypedMap params) {
        channel.sendToServer(new PacketRequestDataFromServer(getDimension(), worldPosition, command.name(), params, false));
    }

    /**
     * Execute a client side command (annotated with @ServerCommand)
     */
    public boolean executeClientCommand(String command, Player player, @Nonnull TypedMap params) {
        AnnotationHolder holder = getAnnotationHolder();
        IRunnable clientCommand = holder.clientCommands.get(command);
        if (clientCommand != null) {
            clientCommand.run(this, player, params);
            return true;
        }
        return false;
    }

    /**
     * Find a server command
     */
    public IRunnable<?> findServerCommand(String command) {
        AnnotationHolder holder = getAnnotationHolder();
        return holder.serverCommands.get(command);
    }

    /**
     * Execute a server side command (annotated with @ServerCommand). Note! Do not call this client-side!
     * This is meant to be called server side. If you want to call this client-side use the
     * PacketServerCommandTyped packet
     */
    public boolean executeServerCommand(String command, Player player, @Nonnull TypedMap params) {
        AnnotationHolder holder = getAnnotationHolder();
        IRunnable serverCommand = holder.serverCommands.get(command);
        if (serverCommand != null) {
            serverCommand.run(this, player, params);
            return true;
        }
        return false;
    }

    /**
     * Execute a server side listcommand (annotated with @ServerCommand). Note! Do not call this client-side!
     * This is meant to be called server side. If you want to call this client-side use the
     * PacketGetListFromServer packet
     */
    public <T> List<T> executeServerCommandList(String command, Player player, @Nonnull TypedMap params, @Nonnull Class<T> type) {
        AnnotationHolder holder = getAnnotationHolder();
        IRunnableWithListResult cmd = holder.serverCommandsWithListResult.get(command);
        if (cmd != null) {
            return cmd.run(this, player, params);
        }
        return Collections.emptyList();
    }

    /**
     * Execute a client side command that handles the list sent by the server side ListCommand
     */
    public <T> boolean handleListFromServer(String command, Player player, @Nonnull TypedMap params, @Nonnull List<T> list) {
        AnnotationHolder holder = getAnnotationHolder();
        IRunnableWithList cmd = holder.clientCommandsWithList.get(command);
        if (cmd != null) {
            cmd.run(this, player, params, list);
            return true;
        }
        return false;
    }

    /**
     * Execute a server side command with a return value (annotated with @ServerCommand). Note! Do not call this client-side!
     * This is meant to be called server side. If you want to call this client-side use the
     * PacketRequestDataFromServer packet
     */
    @Nullable
    public TypedMap executeServerCommandWR(String command, Player player, @Nonnull TypedMap params) {
        AnnotationHolder holder = getAnnotationHolder();
        IRunnableWithResult serverCommand = holder.serverCommandsWithResult.get(command);
        if (serverCommand != null) {
            return serverCommand.run(this, player, params);
        }
        return null;
    }

    private AnnotationHolder getAnnotationHolder() {
        AnnotationHolder holder = AnnotationHolder.annotations.get(getType());
        if (holder == null) {
            holder = AnnotationTools.createAnnotationHolder(getType(), getClass());
        }
        return holder;
    }

    @ServerCommand
    public static final Command<?> COMMAND_SYNC_BINDING = Command.create("generic.syncBinding",
            (te, playerEntity, params) -> te.syncBinding(params));


    private <T> void syncBindingHelper(TypedMap params, Key<T> bkey) {
        T o = params.get(bkey);
        findSetter(bkey).accept(this, o);
    }

    private void syncBinding(TypedMap params) {
        for (Key<?> key : params.getKeys()) {
            syncBindingHelper(params, key);
        }
        markDirtyClient();
    }
}
