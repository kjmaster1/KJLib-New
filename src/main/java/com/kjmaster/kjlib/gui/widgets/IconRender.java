package com.kjmaster.kjlib.gui.widgets;

import com.kjmaster.kjlib.client.RenderHelper;
import com.kjmaster.kjlib.gui.GuiParser;
import com.kjmaster.kjlib.typed.Type;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class IconRender extends AbstractWidget<IconRender> {

    public static final String TYPE_ICONRENDER = "iconrender";

    private TextureAtlasSprite icon = null;

    public IconRender() {
        desiredHeight(16);
        desiredWidth(16);
    }

    public TextureAtlasSprite getIcon() {
        return icon;
    }

    public IconRender icon(TextureAtlasSprite icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public void draw(Screen gui, GuiGraphics graphics, int x, int y) {
        if (!visible) {
            return;
        }
        super.draw(gui, graphics, x, y);
        if (icon != null) {
            RenderHelper.renderObject(graphics, x + bounds.x, y + bounds.y, icon, false);
        }
    }


    @Override
    public GuiParser.GuiCommand createGuiCommand() {
        return new GuiParser.GuiCommand(TYPE_ICONRENDER);
    }

    @Override
    public <T> void setGenericValue(T value) {
    }

    @Override
    public Object getGenericValue(Type<?> type) {
        return null;
    }
}
