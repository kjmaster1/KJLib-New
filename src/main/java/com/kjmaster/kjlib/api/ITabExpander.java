package com.kjmaster.kjlib.api;

import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * For expansion of items in the creative tab (abstraction between 1.19.2 and 1.19.3)
 */

public interface ITabExpander {
    List<ItemStack> getItemsForTab();
}
