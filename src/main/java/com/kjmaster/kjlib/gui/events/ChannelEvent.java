package com.kjmaster.kjlib.gui.events;

import com.kjmaster.kjlib.gui.widgets.Widget;
import com.kjmaster.kjlib.typed.TypedMap;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Generic event on the main window channel
 */
public interface ChannelEvent {

    void fire(@Nonnull Widget<?> source, @Nonnull TypedMap params);
}
