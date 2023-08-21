package com.kjmaster.kjlib.builder;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class BlockBuilder {

    public static final BlockBehaviour.Properties STANDARD_IRON = BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(2.0f)
            .requiresCorrectToolForDrops()
            .sound(SoundType.METAL);

    private BlockBehaviour.Properties properties = STANDARD_IRON;
    private BlockEntityType.BlockEntitySupplier<BlockEntity> tileEntitySupplier;


    public BlockBehaviour.Properties getProperties() {
        return properties;
    }

    public BlockEntityType.BlockEntitySupplier<BlockEntity> getTileEntitySupplier() {
        return tileEntitySupplier;
    }

    public BlockBuilder properties(BlockBehaviour.Properties properties) {
        this.properties = properties;
        return this;
    }

    public BlockBuilder tileEntitySupplier(BlockEntityType.BlockEntitySupplier<BlockEntity> supplier) {
        this.tileEntitySupplier = supplier;
        return this;
    }
}
