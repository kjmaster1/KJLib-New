package com.kjmaster.kjlib.container;

import com.kjmaster.kjlib.tileentity.GenericTileEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class BaseSlot extends SlotItemHandler {

    private final GenericTileEntity te;

    public BaseSlot(IItemHandler inventory, GenericTileEntity te, int index, int x, int y) {
        super(inventory, index, x, y);
        this.te = te;
    }

    @Override
    public void set(@Nonnull ItemStack stack) {
        if (te != null) {
            te.onSlotChanged(getSlotIndex(), stack);
        }
        super.set(stack);
    }

    public GenericTileEntity getTe() {
        return te;
    }
}
