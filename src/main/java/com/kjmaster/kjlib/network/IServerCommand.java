package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface IServerCommand {

    /**
     * Execute a command on the server through networking from a client
     * Returns false on failure
     */
    boolean execute(@Nonnull Player player, @Nonnull TypedMap arguments);
}
