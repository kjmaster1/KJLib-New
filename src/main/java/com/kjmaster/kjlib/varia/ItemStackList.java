package com.kjmaster.kjlib.varia;

import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ItemStackList extends NonNullList<ItemStack> {

    public static final ItemStackList EMPTY = ItemStackList.create(0);

    public static ItemStackList create(int size) {
        Validate.notNull(ItemStack.EMPTY);
        ItemStack[] aobject = new ItemStack[size];
        Arrays.fill(aobject, ItemStack.EMPTY);
        return new ItemStackList(Arrays.asList(aobject), ItemStack.EMPTY);
    }

    public static ItemStackList create() {
        return new ItemStackList(new ArrayList<>(), ItemStack.EMPTY);
    }

    public ItemStackList(List<ItemStack> delegateIn, @Nullable ItemStack stack) {
        super(delegateIn, stack);
    }
}
