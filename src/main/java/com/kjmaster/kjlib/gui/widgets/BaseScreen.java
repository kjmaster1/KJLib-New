package com.kjmaster.kjlib.gui.widgets;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public abstract class BaseScreen extends Screen {

    public BaseScreen(Component pTitle) {
        super(pTitle);
    }

    protected abstract void renderInternal(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick);

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        renderInternal(graphics, pMouseX, pMouseY, pPartialTick);
    }
}
