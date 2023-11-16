package com.kjmaster.kjlib.crafting;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IRecipeBuilder<T extends IRecipeBuilder<T>> {
    T define(Character symbol, TagKey<Item> tagIn);

    T define(Character symbol, ItemLike itemIn);

    T define(Character symbol, Ingredient ingredientIn);

    T patternLine(String patternIn);

    T setGroup(String groupIn);

    void build(Consumer<FinishedRecipe> consumerIn);

    void build(Consumer<FinishedRecipe> consumerIn, String save);

    void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id);
}
