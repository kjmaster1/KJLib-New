package com.kjmaster.kjlib.gui.layout;

import com.kjmaster.kjlib.gui.widgets.Widget;

import java.util.Collection;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class PositionalLayout extends AbstractLayout<PositionalLayout> {

    @Override
    public void doLayout(Collection<Widget<?>> children, int width, int height) {
        for (Widget<?> child : children) {
            PositionalHint hint = (PositionalHint) child.getLayoutHint();
            if (hint != null) {
                int w;
                int h;
                if (hint.width == -1) {
                    w = child.getDesiredWidth();
                } else {
                    w = hint.width;
                }
                if (hint.height == -1) {
                    h = child.getDesiredHeight();
                } else {
                    h = hint.height;
                }
                child.bounds(hint.x, hint.y, w, h);
            }
        }
    }

    public static class PositionalHint implements LayoutHint {
        private int x;
        private int y;
        private int width;
        private int height;

        public PositionalHint(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public PositionalHint(int x, int y) {
            this(x, y, -1, -1);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}
