package com.kjmaster.kjlib.syncpositional;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IPositionalData {

    /// Unique Id for this type of positional data
    ResourceLocation getId();

    /**
     * Convert this data to a packet
     */
    void toBytes(FriendlyByteBuf buf);
}
