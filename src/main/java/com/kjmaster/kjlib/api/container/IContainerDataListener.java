package com.kjmaster.kjlib.api.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * This represents generic data that a container can sync from server to client automatically
 */
public interface IContainerDataListener {

    /// A unique ID for this data
    ResourceLocation getId();

    /// Return if the data is dirty and clear the data flag
    boolean isDirtyAndClear();

    /// Write data to buffer
    void toBytes(FriendlyByteBuf buf);

    /// Read data from buffer
    void readBuf(FriendlyByteBuf buf);
}
