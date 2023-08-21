package com.kjmaster.kjlib.syncpositional;

import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Package private, this key is not useful outside this package
 */
record PositionalDataKey(ResourceLocation id, GlobalPos pos) {
}
