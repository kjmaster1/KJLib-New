package com.kjmaster.kjlib.varia;

import java.util.Objects;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Implement this for an enum with names and descriptions. Used
 * inside gui's for example.
 */

public interface NamedEnum<T extends NamedEnum> {

    /// Used for displaying on the combo button
    String getName();

    /// Used as the tooltip
    String[] getDescription();

    static <T extends NamedEnum<T>> T getEnumByName(String name, T[] values) {
        for (T value : values) {
            if (Objects.equals(name, value.getName())) {
                return value;
            }
        }
        return null;
    }
}
