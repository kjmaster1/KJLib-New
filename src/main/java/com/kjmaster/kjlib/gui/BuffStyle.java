package com.kjmaster.kjlib.gui;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public enum BuffStyle {
    OFF("off"),
    TOPLEFT("topleft"),
    TOPRIGHT("topright"),
    BOTLEFT("botleft"),
    BOTRIGHT("botright");

    private final String name;

    BuffStyle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BuffStyle getStyle(String name) {
        for (BuffStyle style : values()) {
            if (style.getName().equalsIgnoreCase(name)) {
                return style;
            }
        }
        return null;
    }
}
