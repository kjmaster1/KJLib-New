package com.kjmaster.kjlib.blockcommands;

import com.kjmaster.kjlib.tileentity.GenericTileEntity;
import com.kjmaster.kjlib.typed.TypedMap;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

@FunctionalInterface
public interface IRunnableWithList<TE extends GenericTileEntity, T> {
    void run(TE te, Player player, TypedMap params, List<T> list);
}
