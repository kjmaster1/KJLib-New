package com.kjmaster.kjlib.gui.widgets;

import com.kjmaster.kjlib.gui.layout.HorizontalAlignment;
import com.kjmaster.kjlib.gui.layout.VerticalAlignment;
import com.kjmaster.kjlib.typed.Type;
import com.mojang.blaze3d.systems.RenderSystem;
import com.kjmaster.kjlib.base.StyleConfig;
import com.kjmaster.kjlib.gui.GuiParser;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public abstract class AbstractLabel<P extends AbstractLabel<P>> extends AbstractWidget<P> {

    public static final String TYPE_LABEL = "label";

    public static final HorizontalAlignment DEFAULT_HORIZONTAL_ALIGN = HorizontalAlignment.ALIGN_CENTER;
    public static final VerticalAlignment DEFAULT_VERTICAL_ALIGN = VerticalAlignment.ALIGN_CENTER;
    public static final boolean DEFAULT_DYNAMIC = false;

    private String text;
    private Integer color = null;
    private Integer disabledColor = null;
    private HorizontalAlignment horizontalAlignment = DEFAULT_HORIZONTAL_ALIGN;
    private VerticalAlignment verticalAlignment = DEFAULT_VERTICAL_ALIGN;
    private boolean dynamic = DEFAULT_DYNAMIC;        // The size of this label is dynamic and not based on the contents

    private int txtDx = 0;
    private int txtDy = 0;

    private ResourceLocation image = null;
    private int u;
    private int v;
    private int iw;
    private int ih;


    public ResourceLocation getImage() {
        return image;
    }

    public P image(ResourceLocation image, int u, int v, int iw, int ih) {
        this.image = image;
        this.u = u;
        this.v = v;
        this.iw = iw;
        this.ih = ih;
        return getThis();
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public P dynamic(boolean dynamic) {
        this.dynamic = dynamic;
        return getThis();
    }

    @Override
    public int getDesiredWidth() {
        int w = super.getDesiredWidth();
        if (dynamic) {
            return w;
        }
        if (w == -1) {
            w = mc.font.width(text)+6;
        }
        return w;
    }

    @Override
    public int getDesiredHeight() {
        int h = super.getDesiredHeight();
        if (dynamic) {
            return h;
        }
        if (h == -1) {
            h = mc.font.lineHeight+2;
        }
        return h;
    }

    public String getText() {
        return text;
    }

    public P text(String text) {
        this.text = text;
        return getThis();
    }

    public P textOffset(int ox, int oy) {
        txtDx = ox;
        txtDy = oy;
        return getThis();
    }

    public int getColor() {
        return color == null ? StyleConfig.colorTextNormal : color;
    }

    public P color(int color) {
        this.color = color;
        return getThis();
    }

    public int getDisabledColor() {
        return disabledColor == null ? StyleConfig.colorTextDisabled : disabledColor;
    }

    public P disabledColor(int disabledColor) {
        this.disabledColor = disabledColor;
        return getThis();
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public P horizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return getThis();
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public P verticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return getThis();
    }

    @Override
    public void draw(Screen gui, GuiGraphics graphics, int x, int y) {
        drawOffset(gui, graphics, x, y, 0, 0);
    }

    public void drawOffset(Screen gui, GuiGraphics graphics, int x, int y, int offsetx, int offsety) {
        if (!visible) {
            return;
        }
        super.draw(gui, graphics, x, y);

        int dx = calculateHorizontalOffset() + offsetx + txtDx;
        int dy = calculateVerticalOffset() + offsety + txtDy;

        if (image != null) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int xx = x + bounds.x + (bounds.width-iw) / 2;
            int yy = y + bounds.y + (bounds.height-ih) / 2;
            graphics.blit(image, xx, yy, u, v, iw, ih);
        }

        int col = getColor();
        if (!isEnabled()) {
            col = getDisabledColor();
        }

        if (text == null) {
            graphics.drawString(mc.font, "", x+dx+bounds.x, y+dy+bounds.y, col, false);
        } else {
            graphics.drawString(mc.font, mc.font.plainSubstrByWidth(text, bounds.width), x + dx + bounds.x, y + dy + bounds.y, col, false);
        }
    }

    private int calculateVerticalOffset() {
        if (verticalAlignment != VerticalAlignment.ALIGN_TOP) {
            int h = mc.font.lineHeight;
            if (verticalAlignment == VerticalAlignment.ALIGN_BOTTOM) {
                return bounds.height - h;
            } else {
                return (bounds.height - h)/2;
            }
        } else {
            return 0;
        }
    }

    private int calculateHorizontalOffset() {
        if (horizontalAlignment != HorizontalAlignment.ALIGN_LEFT) {
            int w = mc.font.width(text);
            if (horizontalAlignment == HorizontalAlignment.ALIGN_RIGHT) {
                return bounds.width - w;
            } else {
                return (bounds.width - w)/2;
            }
        } else {
            return 0;
        }
    }


    @Override
    public void readFromGuiCommand(GuiParser.GuiCommand command) {
        super.readFromGuiCommand(command);
        text = command.getOptionalPar(1, "");
        color = GuiParser.get(command, "color", null);
        disabledColor = GuiParser.get(command, "disabledcolor", null);
        horizontalAlignment = HorizontalAlignment.getByName(GuiParser.get(command, "horizalign", DEFAULT_HORIZONTAL_ALIGN.name()));
        verticalAlignment = VerticalAlignment.getByName(GuiParser.get(command, "vertalign", DEFAULT_VERTICAL_ALIGN.name()));
        dynamic = GuiParser.get(command, "dynamic", DEFAULT_DYNAMIC);
    }

    @Override
    public void fillGuiCommand(GuiParser.GuiCommand command) {
        super.fillGuiCommand(command);
        command.parameter(text);
        GuiParser.put(command, "color", color, null);
        GuiParser.put(command, "disabledcolor", disabledColor, null);
        GuiParser.put(command, "horizalign", horizontalAlignment.name(), DEFAULT_HORIZONTAL_ALIGN.name());
        GuiParser.put(command, "vertalign", verticalAlignment.name(), DEFAULT_VERTICAL_ALIGN.name());
        GuiParser.put(command, "dynamic", dynamic, DEFAULT_DYNAMIC);
    }

    @Override
    public GuiParser.GuiCommand createGuiCommand() {
        return new GuiParser.GuiCommand(TYPE_LABEL);
    }

    @Override
    public <T> void setGenericValue(T value) {
        if (value == null) {
            text("");
        } else {
            text(value.toString());
        }
    }

    @Override
    public Object getGenericValue(Type<?> type) {
        return getText();
    }
}