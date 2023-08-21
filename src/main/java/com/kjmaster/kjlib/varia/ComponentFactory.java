package com.kjmaster.kjlib.varia;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ComponentFactory {

    public static MutableComponent translatable(String key) {
        return Component.translatable(key);
    }

    public static MutableComponent translatable(String key, Object... objects) {
        return Component.translatable(key, objects);
    }

    public static MutableComponent literal(String text) {
        return Component.literal(text);
    }

    public static MutableComponent empty() {
        return Component.empty();
    }

    public static MutableComponent keybind(String keybind) {
        return Component.keybind(keybind);
    }
}
