package com.kjmaster.kjlib.gui.events;

import com.kjmaster.kjlib.gui.icons.IIcon;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IconLeavesEvent {

    /// Return false if you don't want the icon to go away
    boolean iconLeaves(IIcon icon);
}
