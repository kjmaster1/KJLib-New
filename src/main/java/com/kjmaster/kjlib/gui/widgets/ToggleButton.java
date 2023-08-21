package com.kjmaster.kjlib.gui.widgets;

import com.kjmaster.kjlib.base.StyleConfig;
import com.kjmaster.kjlib.client.RenderHelper;
import com.kjmaster.kjlib.gui.GuiParser;
import com.kjmaster.kjlib.gui.Window;
import com.kjmaster.kjlib.gui.events.ButtonEvent;
import com.kjmaster.kjlib.typed.Key;
import com.kjmaster.kjlib.typed.Type;
import com.kjmaster.kjlib.typed.TypeConvertors;
import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;
import static com.kjmaster.kjlib.base.StyleConfig.*;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ToggleButton extends AbstractLabel<ToggleButton> {

    public static final String TYPE_TOGGLEBUTTON = "togglebutton";
    public static final Key<Boolean> PARAM_ON = new Key<>("on", Type.BOOLEAN);

    public static final boolean DEFAULT_CHECKMARKER = false;

    private List<ButtonEvent> buttonEvents = null;
    private boolean pressed = false;
    private boolean checkMarker = DEFAULT_CHECKMARKER;

    public boolean isPressed() {
        return pressed;
    }

    public ToggleButton pressed(boolean pressed) {
        if (this.pressed == pressed) {
            return this;
        }
        this.pressed = pressed;
        return this;
    }

    @Override
    public int getDesiredWidth() {
        int w = desiredWidth;
        if (isDynamic()) {
            return w;
        }
        if (w == -1) {
            w = mc.font.width(getText())+6 + (checkMarker ? 10 : 0);
        }
        return w;
    }

    public boolean isCheckMarker() {
        return checkMarker;
    }

    public ToggleButton checkMarker(boolean checkMarker) {
        this.checkMarker = checkMarker;
        return this;
    }

    @Override
    public void draw(Screen gui, GuiGraphics graphics, int x, int y) {
        if (!visible) {
            return;
        }
        int xx = x + bounds.x;
        int yy = y + bounds.y;

        if (isEnabled()) {
            if (pressed) {
                drawStyledBoxSelected(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            } else if (isHovering()) {
                drawStyledBoxHovering(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            } else {
                drawStyledBoxNormal(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            }
            if (checkMarker) {
                RenderHelper.drawBeveledBox(graphics, xx + 2, yy + bounds.height / 2 - 4, xx + 10, yy + bounds.height / 2 + 4, colorToggleNormalBorderTopLeft, colorToggleNormalBorderBottomRight, colorToggleNormalFiller);
                if (pressed) {
                    graphics.drawString(mc.font, "v", xx + 3, yy + bounds.height / 2 - 4, StyleConfig.colorToggleTextNormal, false);
                }
            }
        } else {
            drawStyledBoxDisabled(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            if (checkMarker) {
                RenderHelper.drawBeveledBox(graphics, xx + 2, yy + bounds.height / 2 - 4, xx + 10, yy + bounds.height / 2 + 4, colorToggleDisabledBorderTopLeft, colorToggleDisabledBorderBottomRight, colorToggleDisabledFiller);
                if (pressed) {
                    graphics.drawString(mc.font, "v", xx + 3, yy + bounds.height / 2 - 4, StyleConfig.colorToggleTextDisabled, false);
                }
            }
        }

        super.drawOffset(gui, graphics, x, y, checkMarker ? 6 : 0, 1);
    }

    @Override
    public Widget<?> mouseClick(double x, double y, int button) {
        if (isEnabledAndVisible()) {
            pressed = !pressed;
            fireButtonEvents();
            return this;
        }
        return null;
    }

    public ToggleButton event(ButtonEvent event) {
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
        fireChannelEvents(TypedMap.builder()
                .put(Window.PARAM_ID, "enter")
                .put(PARAM_ON, isPressed())
                .build());
        if (buttonEvents != null) {
            for (ButtonEvent event : buttonEvents) {
                event.buttonClicked();
            }
        }
    }

    @Override
    public void readFromGuiCommand(GuiParser.GuiCommand command) {
        super.readFromGuiCommand(command);
        checkMarker = GuiParser.get(command, "check", DEFAULT_CHECKMARKER);
    }

    @Override
    public void fillGuiCommand(GuiParser.GuiCommand command) {
        super.fillGuiCommand(command);
        GuiParser.put(command, "check", checkMarker, DEFAULT_CHECKMARKER);
    }

    @Override
    public GuiParser.GuiCommand createGuiCommand() {
        return new GuiParser.GuiCommand(TYPE_TOGGLEBUTTON);
    }

    @Override
    public <T> void setGenericValue(T value) {
        pressed(TypeConvertors.toBoolean(value));
    }

    @Override
    public Object getGenericValue(Type<?> type) {
        return isPressed();
    }
}
