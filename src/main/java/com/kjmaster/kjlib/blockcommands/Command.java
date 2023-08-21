package com.kjmaster.kjlib.blockcommands;

import com.kjmaster.kjlib.tileentity.GenericTileEntity;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * A command that gets executed serverside. Annotate with @ServerCommand to register
 */
public record Command<TE extends GenericTileEntity>(String name, IRunnable<TE> cmd) implements ICommand {

    /// Create a command without a result
    public static <E extends GenericTileEntity> Command<E> create(String name, IRunnable<E> command) {
        return new Command<>(name, command);
    }
}