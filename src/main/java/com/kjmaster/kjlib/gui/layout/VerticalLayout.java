package com.kjmaster.kjlib.gui.layout;

import com.kjmaster.kjlib.gui.widgets.AbstractWidget;
import com.kjmaster.kjlib.gui.widgets.Widget;

import java.util.Collection;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class VerticalLayout extends AbstractLayout<VerticalLayout> {
    @Override
    public void doLayout(Collection<Widget<?>> children, int width, int height) {
        int otherHeight = calculateDynamicSize(children, height, Widget.Dimension.DIMENSION_HEIGHT);

        int top = getVerticalMargin();
        for (Widget<?> child : children) {
            int h = child.getDesiredHeight();
            if (h == Widget.SIZE_UNKNOWN) {
                h = otherHeight;
            }
            ((AbstractWidget<?>)child).setBounds(align(getHorizontalMargin(), top, width-getHorizontalMargin()*2, h, child));
            top += h;
            top += getSpacing();
        }
    }
}
