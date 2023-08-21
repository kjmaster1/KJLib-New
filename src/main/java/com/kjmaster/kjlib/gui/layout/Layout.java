package com.kjmaster.kjlib.gui.layout;

import com.kjmaster.kjlib.gui.widgets.Widget;

import java.util.Collection;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface Layout {
    /**
     * Calculate the layout of the children in the container.
     */
    void doLayout(Collection<Widget<?>> children, int width, int height);
}
