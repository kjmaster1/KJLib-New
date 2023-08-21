package com.kjmaster.kjlib.gui.layout;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public enum HorizontalAlignment {
    ALIGN_LEFT,
    ALIGN_RIGHT,
    ALIGN_CENTER;

    public static HorizontalAlignment getByName(String name) {
        for (HorizontalAlignment alignment : values()) {
            if (name.equals(alignment.name())) {
                return alignment;
            }
        }
        return null;
    }
}
