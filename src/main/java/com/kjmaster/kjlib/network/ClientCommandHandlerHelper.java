package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.KJLib;
import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraft.client.Minecraft;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ClientCommandHandlerHelper {

    static void handle(PacketSendClientCommand message) {
        String modid = message.getModid();
        String command = message.getCommand();
        TypedMap arguments = message.getArguments();
        handleClientCommand(modid, command, arguments);
    }

    private static void handleClientCommand(String modid, String command, TypedMap arguments) {
        boolean result = KJLib.handleClientCommand(modid, command, Minecraft.getInstance().player, arguments);
        if (!result) {
            KJLib.LOGGER.error("Error handling client command '" + command + "' for mod '" + modid + "'!");
        }
    }
}
