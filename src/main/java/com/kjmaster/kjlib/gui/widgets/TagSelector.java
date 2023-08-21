package com.kjmaster.kjlib.gui.widgets;

import com.kjmaster.kjlib.base.StyleConfig;
import com.kjmaster.kjlib.client.RenderHelper;
import com.kjmaster.kjlib.gui.GuiParser;
import com.kjmaster.kjlib.gui.TagSelectorWindow;
import com.kjmaster.kjlib.gui.Window;
import com.kjmaster.kjlib.gui.events.TagChoiceEvent;
import com.kjmaster.kjlib.typed.Key;
import com.kjmaster.kjlib.typed.Type;
import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.kjmaster.kjlib.gui.TagSelectorWindow.TYPE_ITEM;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class TagSelector extends AbstractLabel<TagSelector> {

    public static final String TYPE_TAGSELECTOR = "tagselector";
    public static final Key<String> PARAM_TAG = new Key<>("tag", Type.STRING);

    private static final Pattern COMPILE = Pattern.compile("[/:]");

    private String currentTag = null;
    private List<TagChoiceEvent> choiceEvents = null;
    private final TagSelectorWindow selector = new TagSelectorWindow();
    private String type = TYPE_ITEM;

    public TagSelector() {
        text("");
    }

    public TagSelector current(String tag) {
        if (Objects.equals(currentTag, tag)) {
            return this;
        }
        currentTag = tag;
        return this;
    }

    public String getCurrentTag() {
        return currentTag;
    }

    @Override
    public void draw(Screen gui, GuiGraphics graphics, int x, int y) {
        if (!visible) {
            return;
        }
        int xx = x + bounds.x;
        int yy = y + bounds.y;

        if (isEnabled()) {
            if (isHovering()) {
                drawStyledBoxHovering(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            } else {
                drawStyledBoxNormal(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            }
            RenderHelper.drawLeftTriangle(graphics, xx + bounds.width - 10, yy + bounds.height / 2, StyleConfig.colorCycleButtonTriangleNormal);
            RenderHelper.drawRightTriangle(graphics, xx + bounds.width - 4, yy + bounds.height / 2, StyleConfig.colorCycleButtonTriangleNormal);
        } else {
            drawStyledBoxDisabled(window, graphics, xx, yy, xx + bounds.width - 1, yy + bounds.height - 1);
            RenderHelper.drawLeftTriangle(graphics, xx + bounds.width - 10, yy + bounds.height / 2, StyleConfig.colorCycleButtonTriangleDisabled);
            RenderHelper.drawRightTriangle(graphics, xx + bounds.width - 4, yy + bounds.height / 2, StyleConfig.colorCycleButtonTriangleDisabled);
        }

        String tag = getCurrentTagSafe();
        String[] split = COMPILE.split(tag);
        text(split[split.length - 1]); // @todo maybe not very clean like this? Better override getText()

        super.drawOffset(gui, graphics, x, y, 0, 1);
    }

    private String getCurrentTagSafe() {
        String tag = getCurrentTag();
        if (tag == null) {
            tag = "<unset>";
        }
        return tag;
    }

    @Override
    public Widget<?> mouseClick(double x, double y, int button) {
        if (isEnabledAndVisible()) {
            selector.create(window, type, t -> {
                current(t);
                fireChoiceEvents(t);
            }, this::getCurrentTag, false);
        }
        return null;
    }


    public TagSelector event(TagChoiceEvent event) {
        if (choiceEvents == null) {
            choiceEvents = new ArrayList<>();
        }
        choiceEvents.add(event);
        return this;
    }

    public void removeChoiceEvent(TagChoiceEvent event) {
        if (choiceEvents != null) {
            choiceEvents.remove(event);
        }
    }

    private void fireChoiceEvents(String tag) {
        fireChannelEvents(TypedMap.builder()
                .put(Window.PARAM_ID, "choice")
                .put(PARAM_TAG, tag)
                .build());
        if (choiceEvents != null) {
            for (TagChoiceEvent event : choiceEvents) {
                event.tagChanged(tag);
            }
        }
    }

    @Override
    public void readFromGuiCommand(GuiParser.GuiCommand command) {
        super.readFromGuiCommand(command);
        type = GuiParser.get(command, "type", TYPE_ITEM);
    }

    @Override
    public void fillGuiCommand(GuiParser.GuiCommand command) {
        super.fillGuiCommand(command);
        command.removeParameter(1); // We don't need the name as set by the label
    }

    @Override
    public GuiParser.GuiCommand createGuiCommand() {
        return new GuiParser.GuiCommand(TYPE_TAGSELECTOR);
    }

    @Override
    public <T> void setGenericValue(T value) {
        current(value == null ? null : value.toString());
    }

    @Override
    public Object getGenericValue(Type<?> type) {
        return getCurrentTag();
    }
}
