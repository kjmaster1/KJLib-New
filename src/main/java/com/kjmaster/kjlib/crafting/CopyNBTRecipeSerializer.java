package com.kjmaster.kjlib.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class CopyNBTRecipeSerializer implements RecipeSerializer<CopyNBTRecipe> {

    private final ShapedRecipe.Serializer serializer = new ShapedRecipe.Serializer();

    @Override
    @Nonnull
    public CopyNBTRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ShapedRecipe recipe = serializer.fromJson(recipeId, json);
        return new CopyNBTRecipe(recipe);
    }

    @Override
    public CopyNBTRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        ShapedRecipe recipe = serializer.fromNetwork(recipeId, buffer);
        return new CopyNBTRecipe(recipe);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, CopyNBTRecipe recipe) {
        serializer.toNetwork(buffer, recipe.getRecipe());
    }
}
