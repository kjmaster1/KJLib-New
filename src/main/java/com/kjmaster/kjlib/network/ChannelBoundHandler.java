package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.varia.TriConsumer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ChannelBoundHandler<T> implements BiConsumer<T, Supplier<NetworkEvent.Context>> {
    private final SimpleChannel channel;
    private final TriConsumer<T, SimpleChannel, Supplier<NetworkEvent.Context>> innerHandler;

    public ChannelBoundHandler(SimpleChannel channel, TriConsumer<T, SimpleChannel, Supplier<NetworkEvent.Context>> innerHandler) {
        this.channel = channel;
        this.innerHandler = innerHandler;
    }

    @Override
    public void accept(T message, Supplier<NetworkEvent.Context> ctx) {
        innerHandler.accept(message, channel, ctx);
    }
}
