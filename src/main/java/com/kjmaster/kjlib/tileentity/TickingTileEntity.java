package com.kjmaster.kjlib.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class TickingTileEntity extends GenericTileEntity{

    public TickingTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void tick() {
        if (level != null) {
            if (level.isClientSide()) {
                tickClient();
            } else {
                tickServer();
            }
        }
    }

    protected void tickServer() {

    }

    protected void tickClient() {

    }
}
