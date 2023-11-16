package com.kjmaster.kjlib.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class BaseShapedRecipe extends ShapedRecipe {

    public BaseShapedRecipe(ResourceLocation id, String group, int width, int height, NonNullList<Ingredient> list, ItemStack stack) {
        super(id, group, CraftingBookCategory.MISC, width, height, list, stack);
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess access) {
        return super.assemble(pContainer, access);
    }
}
