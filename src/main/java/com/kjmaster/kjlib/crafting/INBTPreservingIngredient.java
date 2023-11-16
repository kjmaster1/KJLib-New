package com.kjmaster.kjlib.crafting;


import java.util.Collection;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

/**
 * Implement this interface on blocks or items that (when used in a CopyNBT recipe) will preserve
 * the NBT to the output
 */
public interface INBTPreservingIngredient {

    Collection<String> getTagsToPreserve();
}