package com.kjmaster.kjlib.builder;

import com.kjmaster.kjlib.gui.ManualEntry;
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

    private final TooltipBuilder tooltipBuilder = new TooltipBuilder();
    private BlockEntityType.BlockEntitySupplier<BlockEntity> tileEntitySupplier;
    private ManualEntry manualEntry = ManualEntry.EMPTY;


    public BlockBehaviour.Properties getProperties() {
        return properties;
    }

    public ManualEntry getManualEntry() {
        return manualEntry;
    }

    public TooltipBuilder getTooltipBuilder() {
        return tooltipBuilder;
    }

    public BlockEntityType.BlockEntitySupplier<BlockEntity> getTileEntitySupplier() {
        return tileEntitySupplier;
    }

    public BlockBuilder properties(BlockBehaviour.Properties properties) {
        this.properties = properties;
        return this;
    }

    public BlockBuilder manualEntry(ManualEntry manualEntry) {
        this.manualEntry = manualEntry;
        return this;
    }

    public BlockBuilder info(InfoLine... lines) {
        tooltipBuilder.info(lines);
        return this;
    }

    public BlockBuilder infoShift(InfoLine... lines) {
        tooltipBuilder.infoShift(lines);
        return this;
    }

    public BlockBuilder infoAdvanced(InfoLine... lines) {
        tooltipBuilder.infoAdvanced(lines);
        return this;
    }

    public BlockBuilder tileEntitySupplier(BlockEntityType.BlockEntitySupplier<BlockEntity> supplier) {
        this.tileEntitySupplier = supplier;
        return this;
    }
}
