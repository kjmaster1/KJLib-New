package com.kjmaster.kjlib.gui.widgets;

import com.kjmaster.kjlib.gui.GuiParser;
import com.kjmaster.kjlib.gui.events.ButtonEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class Button extends AbstractLabel<Button> {

    public static final String TYPE_BUTTON = "button";

    private List<ButtonEvent> buttonEvents = null;
    private boolean pressed = false;

    @Override
    public void draw(Screen gui, GuiGraphics graphics, int x, int y) {
        if (!visible) {
            return;
        }
        int xx = x + bounds.x;
        int yy = y + bounds.y;

        if (isEnabledAndVisible()) {
            if (pressed) {
                drawStyledBoxSelected(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            } else if (isHovering()) {
                drawStyledBoxHovering(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            } else {
                drawStyledBoxNormal(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            }
        } else {
            drawStyledBoxDisabled(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
        }

        super.drawOffset(gui, graphics, x, y, 0, 1);
    }

    @Override
    public Widget<?> mouseClick(double x, double y, int button) {
        if (isEnabledAndVisible()) {
            pressed = true;
            return this;
        }
        return null;
    }

    @Override
    public void mouseRelease(double x, double y, int button) {
        super.mouseRelease(x, y, button);
        if (pressed) {
            pressed = false;
            if (isEnabledAndVisible()) {
                fireButtonEvents();
            }
        }
    }

    public Button event(ButtonEvent event) {
        if (buttonEvents == null) {
            buttonEvents = new ArrayList<>();
        }
        buttonEvents.add(event);
        return this;
    }

    public void removeButtonEvent(ButtonEvent event) {
        if (buttonEvents != null) {
            buttonEvents.remove(event);
        }
    }

    private void fireButtonEvents() {
        fireChannelEvents();
        if (buttonEvents != null) {
            for (ButtonEvent event : buttonEvents) {
                event.buttonClicked();
            }
        }
    }

    @Override
    public GuiParser.GuiCommand createGuiCommand() {
        return new GuiParser.GuiCommand(TYPE_BUTTON);
    }
}
