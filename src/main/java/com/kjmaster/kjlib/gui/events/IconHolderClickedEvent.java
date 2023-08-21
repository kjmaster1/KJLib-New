package com.kjmaster.kjlib.gui.events;

import com.kjmaster.kjlib.gui.icons.IIcon;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IconHolderClickedEvent {

    /// Fires if the holder is clicked (with or without icon)
    void holderClicked(IIcon icon, int dx, int dy);
}
