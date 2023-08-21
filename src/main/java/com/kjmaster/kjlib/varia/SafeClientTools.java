package com.kjmaster.kjlib.varia;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class SafeClientTools {

    public static Level getClientWorld() {
        return Minecraft.getInstance().level;
    }

    public static Level getWorld() {
        return Minecraft.getInstance().level;
    }

    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static HitResult getClientMouseOver() {
        return Minecraft.getInstance().hitResult;
    }

    public static boolean isSneaking() {
        return Screen.hasShiftDown();
    }

    public static boolean isCtrlKeyDown() {
        return Screen.hasControlDown();
    }

    public static boolean isJumpKeyDown() {
        return Minecraft.getInstance().options.keyJump.isDown();
    }
}
