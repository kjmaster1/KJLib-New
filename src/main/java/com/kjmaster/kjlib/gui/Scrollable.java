package com.kjmaster.kjlib.gui;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Implement this interface if you want a widget to be controllable by a slider
 */
public interface Scrollable {
    /**
     * Get the maximum amount of items.
     */
    int getMaximum();

    /**
     * Get the amount of 'selected' items.
     */
    int getCountSelected();

    /**
     * Get the first selected item.
     */
    int getFirstSelected();

    /**
     * Set the first selected item.
     */
    void setFirstSelected(int first);
}
