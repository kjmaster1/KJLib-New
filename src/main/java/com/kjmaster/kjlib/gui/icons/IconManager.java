package com.kjmaster.kjlib.gui.icons;

import com.kjmaster.kjlib.gui.WindowManager;
import com.kjmaster.kjlib.gui.widgets.IconHolder;
import com.kjmaster.kjlib.gui.widgets.Widget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.Optional;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class IconManager {

    private final WindowManager windowManager;

    private IIcon draggingIcon;
    private IconHolder origin;
    private int dx;
    private int dy;

    private boolean clickHoldToDrag = false;

    public IconManager(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public void startDragging(IIcon icon, IconHolder origin, int iconX, int iconY) {
        this.draggingIcon = icon;
        this.origin = origin;
        this.dx = iconX - 2;
        this.dy = iconY - 1;
    }

    public boolean isClickHoldToDrag() {
        return clickHoldToDrag;
    }

    public void setClickHoldToDrag(boolean clickHoldToDrag) {
        this.clickHoldToDrag = clickHoldToDrag;
    }

    public void cancelDragging() {
        if (draggingIcon == null) {
            return;
        }

        if (origin != null) {
            // We assume this always works
            origin.setIcon(draggingIcon);
        }
        draggingIcon = null;
        origin = null;
    }

    public void stopDragging(double x, double y) {
        if (draggingIcon == null) {
            return;
        }

        IconHolder iconHolder = findClosestIconHolder(x, y);
        if (iconHolder == null || iconHolder.getIcon() != null) {
            if (origin != null) {
                // We assume this always works
                origin.setIcon(draggingIcon);
            }
        } else {
            if (!iconHolder.setIcon(draggingIcon)) {
                if (origin != null) {
                    // Set it back, it wasn't accepted
                    origin.setIcon(draggingIcon);
                }
            } else {
                if (iconHolder.isSelectable()) {
                    windowManager.setFocus(iconHolder);
                }
            }
        }

        draggingIcon = null;
        origin = null;
    }

    private IconHolder findClosestIconHolder(double x, double y) {
        Optional<Widget<?>> widget = windowManager.findWidgetAtPosition(x, y);
        if (widget.isPresent() && widget.get() instanceof IconHolder) {
            return (IconHolder) widget.get();
        }
        return null;
    }


    public boolean isDragging() {
        return draggingIcon != null;
    }

    public void draw(Screen gui, GuiGraphics graphics) {
        if (draggingIcon != null) {
            draggingIcon.draw(gui, graphics, getRelativeX() - dx, getRelativeY() - dy);
        }
    }

    private int getRelativeX() {
        Screen gui = windowManager.getGui();
        int width = gui.getMinecraft().getWindow().getScreenWidth();
        if (width <= 0) {
            return 0;
        }
        return (int) gui.getMinecraft().mouseHandler.xpos() * gui.width / width;
    }

    private int getRelativeY() {
        Screen gui = windowManager.getGui();
        int height = gui.getMinecraft().getWindow().getScreenHeight();
        if (height <= 0) {
            return 0;
        }
        return (int) gui.getMinecraft().mouseHandler.ypos() * gui.height / height;
    }

}
