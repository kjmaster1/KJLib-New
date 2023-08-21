package com.kjmaster.kjlib.gui.layout;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public enum VerticalAlignment {
    ALIGN_TOP,
    ALIGN_BOTTOM,
    ALIGN_CENTER;


    public static VerticalAlignment getByName(String name) {
        for (VerticalAlignment alignment : values()) {
            if (name.equals(alignment.name())) {
                return alignment;
            }
        }
        return null;
    }

}
