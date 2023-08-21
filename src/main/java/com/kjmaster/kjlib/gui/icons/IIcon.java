package com.kjmaster.kjlib.gui.icons;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.Map;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IIcon {

    /**
     * Draw this icon on the GUI at the specific position
     */
    void draw(Screen gui, GuiGraphics graphics, int x, int y);

    void addOverlay(IIcon icon);

    void removeOverlay(String id);

    void clearOverlays();

    boolean hasOverlay(String id);

    void addData(String name, Object data);

    void removeData(String name);

    void clearData();

    Map<String, Object> getData();

    // Make a copy of this icon
    IIcon clone();

    // Get a unique identifier for this icon
    String getID();
}
