package com.kjmaster.kjlib.gui;

import net.minecraft.resources.ResourceLocation;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public record ManualEntry(ResourceLocation manual, ResourceLocation entry, int page) {

    public static final ManualEntry EMPTY = new ManualEntry(null, null);

    public ManualEntry(ResourceLocation manual, ResourceLocation entry) {
        this(manual, entry, 0);
    }

    public ManualEntry(ResourceLocation manual, ResourceLocation entry, int page) {
        this.manual = manual;
        this.entry = entry;
        this.page = page;
    }
}
