package com.kjmaster.kjlib.blockcommands;

import net.minecraft.network.FriendlyByteBuf;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Internal class used by McJtyLib (In this instance KJLib) to keep track of things needed for list commands
 */

public record CommandInfo<T>(Class<T> type, Function<FriendlyByteBuf, T> deserializer, BiConsumer<FriendlyByteBuf, T> serializer) {
}
