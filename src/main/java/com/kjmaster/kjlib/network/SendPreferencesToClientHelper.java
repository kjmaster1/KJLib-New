package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.KJLib;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class SendPreferencesToClientHelper {

    public static void setPreferences(PacketSendPreferencesToClient prefs) {
        Player player = Minecraft.getInstance().player;
        KJLib.getPreferencesProperties(player).ifPresent(properties -> {
            properties.setBuffXY(prefs.getBuffStyle(), prefs.getBuffX(), prefs.getBuffY());
            properties.setStyle(prefs.getStyle().getStyle());
        });
    }
}
