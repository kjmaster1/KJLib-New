package com.kjmaster.kjlib.datagen;

import com.kjmaster.kjlib.crafting.CopyNBTRecipeBuilder;
import com.kjmaster.kjlib.crafting.IRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IRecipeFactory {

    void recipe(Supplier<IRecipeBuilder> supplier);

    void recipeConsumer(Supplier<Consumer<Consumer<FinishedRecipe>>> consumerSupplier);

    void recipe(String id, Supplier<IRecipeBuilder> supplier);

    void shapedNBT(CopyNBTRecipeBuilder builder, String... pattern);

    void shapedNBT(String id, CopyNBTRecipeBuilder builder, String... pattern);

    void shaped(ShapedRecipeBuilder builder, String... pattern);

    void shaped(String id, ShapedRecipeBuilder builder, String... pattern);

    void shapeless(ShapelessRecipeBuilder builder);

    void shapeless(String id, ShapelessRecipeBuilder builder);

    void smelting(SimpleCookingRecipeBuilder builder);

    void smelting(String id, SimpleCookingRecipeBuilder builder);
}
