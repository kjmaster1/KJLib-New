package com.kjmaster.kjlib.container;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public record SlotFactory(SlotDefinition slotDefinition, String inventoryName, int index,
                          int x, int y) {

    public SlotType getSlotType() {
        return slotDefinition.getType();
    }
}
