package com.kjmaster.kjlib.compat.patchouli;

import com.kjmaster.kjlib.setup.ModSetup;
import com.kjmaster.kjlib.varia.ComponentFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class PatchouliCompatibility {

    public static void openBookGUI(ServerPlayer player, ResourceLocation id) {
        if (ModSetup.patchouli) {
            // @todo 1.19.3
//            PatchouliAPI.get().openBookGUI(player, id);
        } else {
            player.sendSystemMessage(ComponentFactory.literal(ChatFormatting.RED + "Patchouli is missing! No manual present"));
        }
    }

    public static void openBookEntry(ServerPlayer player, ResourceLocation id, ResourceLocation entry, int page) {
        if (ModSetup.patchouli) {
            // @todo 1.19.3
//            PatchouliAPI.get().openBookEntry(player, id, entry, page);
        } else {
            player.sendSystemMessage(ComponentFactory.literal(ChatFormatting.RED + "Patchouli is missing! No manual present"));
        }
    }
}
