package com.kjmaster.kjlib.gui.events;

import com.kjmaster.kjlib.gui.icons.IIcon;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IconClickedEvent {

    /// Fires if the icon is clicked. Return false if you don't want any further processing
    boolean iconClicked(IIcon icon, int dx, int dy);
}
