package com.kjmaster.kjlib.gui.layout;

import com.kjmaster.kjlib.gui.widgets.AbstractWidget;
import com.kjmaster.kjlib.gui.widgets.Widget;

import java.util.Collection;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class HorizontalLayout extends AbstractLayout<HorizontalLayout> {

    @Override
    public void doLayout(Collection<Widget<?>> children, int width, int height) {
        int otherWidth = calculateDynamicSize(children, width, Widget.Dimension.DIMENSION_WIDTH);

        int left = getHorizontalMargin();
        for (Widget<?> child : children) {
            int w = child.getDesiredWidth();
            if (w == Widget.SIZE_UNKNOWN) {
                w = otherWidth;
            }
            ((AbstractWidget<?>)child).setBounds(align(left, getVerticalMargin(), w, height-getVerticalMargin()*2, child));
            left += w;
            left += getSpacing();
        }
    }

}