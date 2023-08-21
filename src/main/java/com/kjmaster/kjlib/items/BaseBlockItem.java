package com.kjmaster.kjlib.items;

import com.kjmaster.kjlib.api.ITabExpander;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Collections;
import java.util.List;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class BaseBlockItem extends BlockItem implements ITabExpander {

    public BaseBlockItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public List<ItemStack> getItemsForTab() {
        return Collections.emptyList();
    }
}
