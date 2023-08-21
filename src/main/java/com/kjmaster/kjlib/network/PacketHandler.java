package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.syncpositional.PacketSendPositionalDataToClients;
import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraftforge.network.simple.SimpleChannel;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class PacketHandler {

    public static boolean connected = false;

    // Only use client-side!
    private static <MSG> boolean canBeSent(MSG message) {
        return connected;
    }

    // Only use client-side!
    public static void onDisconnect() {
        connected = false;
    }


    public static void registerMessages(SimpleChannel channel) {
        int startIndex = 0;
        channel.registerMessage(startIndex++, PacketSendPreferencesToClient.class, PacketSendPreferencesToClient::toBytes, PacketSendPreferencesToClient::new, PacketSendPreferencesToClient::handle);
        channel.registerMessage(startIndex++, PacketSetGuiStyle.class, PacketSetGuiStyle::toBytes, PacketSetGuiStyle::new, PacketSetGuiStyle::handle);
        channel.registerMessage(startIndex++, PacketOpenManual.class, PacketOpenManual::toBytes, PacketOpenManual::new, PacketOpenManual::handle);
        channel.registerMessage(startIndex++, PacketContainerDataToClient.class, PacketContainerDataToClient::toBytes, PacketContainerDataToClient::new, PacketContainerDataToClient::handle);
        channel.registerMessage(startIndex++, PacketSendPositionalDataToClients.class, PacketSendPositionalDataToClients::toBytes, PacketSendPositionalDataToClients::new, PacketSendPositionalDataToClients::handle);
    }

    public static void registerStandardMessages(int id, SimpleChannel channel) {

        // Server side
        channel.registerMessage(id++, PacketServerCommandTyped.class, PacketServerCommandTyped::toBytes, PacketServerCommandTyped::new, PacketServerCommandTyped::handle);
        channel.registerMessage(id++, PacketSendServerCommand.class, PacketSendServerCommand::toBytes, PacketSendServerCommand::new, PacketSendServerCommand::handle);

        // Client side
        channel.registerMessage(id++, PacketDataFromServer.class, PacketDataFromServer::toBytes, PacketDataFromServer::new, PacketDataFromServer::handle);
    }

    // From client side only: send server command
    public static void sendCommand(SimpleChannel network, String modid, String command, @Nonnull TypedMap arguments) {
        network.sendToServer(new PacketSendServerCommand(modid, command, arguments));
    }
}
