package com.kjmaster.kjlib.gui.layout;

import com.kjmaster.kjlib.gui.widgets.Widget;
import org.intellij.lang.annotations.JdkConstants;

import java.awt.*;
import java.util.Collection;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public abstract class AbstractLayout<P extends AbstractLayout<P>> implements Layout {
    public static final int DEFAULT_SPACING = 5;
    public static final int DEFAULT_HORIZONTAL_MARGIN = 5;
    public static final int DEFAULT_VERTICAL_MARGIN = 2;
    public static final HorizontalAlignment DEFAULT_HORIZONTAL_ALIGN = HorizontalAlignment.ALIGN_CENTER;
    public static final VerticalAlignment DEFAULT_VERTICAL_ALIGN = VerticalAlignment.ALIGN_CENTER;

    private int spacing = DEFAULT_SPACING;
    private int horizontalMargin = DEFAULT_HORIZONTAL_MARGIN;
    private int verticalMargin = DEFAULT_VERTICAL_MARGIN;

    private HorizontalAlignment horizontalAlignment = DEFAULT_HORIZONTAL_ALIGN;
    private VerticalAlignment verticalAlignment = DEFAULT_VERTICAL_ALIGN;

    /**
     * Calculate the size of the widgets which don't have a fixed size.
     * @return the size of each dynamic widget
     */
    protected int calculateDynamicSize(Collection<Widget<?>> children, int totalSize, Widget.Dimension dimension) {
        // Calculate the total fixed size from all the children that have a fixed size
        int totalFixed = 0;
        int countFixed = 0;
        for (Widget<?> child : children) {
            int s = child.getDesiredSize(dimension);
            if (s != Widget.SIZE_UNKNOWN) {
                totalFixed += s;
                countFixed++;
            }
        }
        totalFixed += getSpacing() * (children.size()-1);
        if (dimension == Widget.Dimension.DIMENSION_WIDTH) {
            totalFixed += getHorizontalMargin() * 2;
        } else {
            totalFixed += getVerticalMargin() * 2;
        }
        int otherSize = 0;
        if (countFixed < children.size()) {
            otherSize = (totalSize - totalFixed) / (children.size() - countFixed);
            if (otherSize <= 0) {
                otherSize = 1;
            }
        }
        return otherSize;
    }

    protected Rectangle align(int x, int y, int width, int height, Widget<?> child) {
        int desiredWidth = child.getDesiredWidth();
        if (desiredWidth == Widget.SIZE_UNKNOWN) {
            desiredWidth = width;
        }
        switch (horizontalAlignment) {
            case ALIGN_LEFT: break;
            case ALIGN_RIGHT: x += width - desiredWidth; break;
            case ALIGN_CENTER: x += (width - desiredWidth) / 2; break;
        }

        int desiredHeight = child.getDesiredHeight();
        if (desiredHeight == Widget.SIZE_UNKNOWN) {
            desiredHeight = height;
        }
        switch (verticalAlignment) {
            case ALIGN_TOP: break;
            case ALIGN_BOTTOM: y += height - desiredHeight; break;
            case ALIGN_CENTER: y += (height - desiredHeight) / 2; break;
        }

        return new Rectangle(x, y, desiredWidth, desiredHeight);
    }


    public int getSpacing() {
        return spacing;
    }

    public P setSpacing(int spacing) {
        this.spacing = spacing;
        return getThis();
    }

    private P getThis() {
        //noinspection unchecked
        return (P) this;
    }

    public int getHorizontalMargin() {
        return horizontalMargin;
    }

    public P setHorizontalMargin(int horizontalMargin) {
        this.horizontalMargin = horizontalMargin;
        return getThis();
    }

    public int getVerticalMargin() {
        return verticalMargin;
    }

    public P setVerticalMargin(int verticalMargin) {
        this.verticalMargin = verticalMargin;
        return getThis();
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public P setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        if (horizontalAlignment == null) {
            throw new RuntimeException("Invalid horizontal alignment!");
        }
        this.horizontalAlignment = horizontalAlignment;
        return getThis();
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    public P setVerticalAlignment(VerticalAlignment verticalAlignment) {
        if (horizontalAlignment == null) {
            throw new RuntimeException("Invalid vertical alignment!");
        }
        this.verticalAlignment = verticalAlignment;
        return getThis();
    }
}
