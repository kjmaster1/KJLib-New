package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.KJLib;
import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Send a packet from the client to the server in order to execute a server side command
 * registered with McJtyLib.registerCommand()
 */

public class PacketSendServerCommand {

    // Package visible for unit tests
    private final String modid;
    private final String command;
    private final TypedMap arguments;

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(modid);
        buf.writeUtf(command);
        TypedMapTools.writeArguments(buf, arguments);
    }

    public PacketSendServerCommand(FriendlyByteBuf buf) {
        modid = buf.readUtf(32767);
        command = buf.readUtf(32767);
        arguments = TypedMapTools.readArguments(buf);
    }

    public PacketSendServerCommand(String modid, String command, @Nonnull TypedMap arguments) {
        this.modid = modid;
        this.command = command;
        this.arguments = arguments;
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            try {
                boolean result = KJLib.handleCommand(modid, command, ctx.getSender(), arguments);
                if (!result) {
                    KJLib.LOGGER.error("Error handling command '" + command + "' for mod '" + modid + "'!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        ctx.setPacketHandled(true);
    }
}
