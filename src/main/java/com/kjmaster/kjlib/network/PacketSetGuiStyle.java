package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.KJLib;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Change the GUI style.
 */
public class PacketSetGuiStyle {

    // Package visible for unit tests
    private final String style;

    public PacketSetGuiStyle(FriendlyByteBuf buf) {
        style = buf.readUtf(32767);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(style);
    }

    public PacketSetGuiStyle(String style) {
        this.style = style;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> handle(this, ctx.get()));
        ctx.get().setPacketHandled(true);
    }

    private static void handle(PacketSetGuiStyle message, NetworkEvent.Context ctx) {
        ServerPlayer playerEntity = ctx.getSender();
        KJLib.getPreferencesProperties(playerEntity).ifPresent(p -> p.setStyle(message.style));
    }
}
