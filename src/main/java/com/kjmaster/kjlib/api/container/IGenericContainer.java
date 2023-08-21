package com.kjmaster.kjlib.api.container;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IGenericContainer {

    void addShortListener(DataSlot holder);

    void addIntegerListener(DataSlot holder);

    void addContainerDataListener(IContainerDataListener dataListener);

    void setupInventories(@Nullable IItemHandler itemHandler, Inventory inventory);

    AbstractContainerMenu getAsContainer();
}
