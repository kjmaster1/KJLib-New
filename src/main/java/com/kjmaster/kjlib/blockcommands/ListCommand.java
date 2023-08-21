package com.kjmaster.kjlib.blockcommands;

import com.kjmaster.kjlib.tileentity.GenericTileEntity;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * A command that gets executed serverside, calculates a list of data and sends that back to the client.
 * Annotate with @ServerCommand to register. Also see McJtyLib.registerListCommandInfo()
 */

public record ListCommand<TE extends GenericTileEntity, T>(String name,
                                                           IRunnableWithListResult<TE, T> cmd,
                                                           IRunnableWithList<TE, T> clientCommand) implements ICommand {

    /// Create a command without a result
    public static <E extends GenericTileEntity, S> ListCommand<E, S> create(String name, IRunnableWithListResult<E, S> command, IRunnableWithList<E, S> clientCommand) {
        return new ListCommand<E, S>(name, command, clientCommand);
    }
}
