package com.kjmaster.kjlib.blockcommands;

import com.kjmaster.kjlib.tileentity.GenericTileEntity;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * A command that gets executed serverside, calculates data and sends that back to the client.
 * Annotate with @ServerCommand to register
 */
public class ResultCommand<TE extends GenericTileEntity> implements ICommand {

    private final String name;
    private final IRunnableWithResult<TE> cmd;
    private final IRunnable<TE> clientCommand;

    private ResultCommand(String name, IRunnableWithResult<TE> cmd, IRunnable<TE> clientCommand) {
        this.name = name;
        this.clientCommand = clientCommand;
        this.cmd = cmd;
    }

    @Override
    public String name() {
        return name;
    }

    public IRunnableWithResult<TE> getCmd() {
        return cmd;
    }

    public IRunnable<TE> getClientCommand() {
        return clientCommand;
    }

    /// Create a command with a TypedMap result
    public static <E extends GenericTileEntity> ResultCommand<E> create(String name, IRunnableWithResult<E> command, IRunnable<E> clientCommand) {
        return new ResultCommand<>(name, command, clientCommand);
    }
}
