package com.kjmaster.kjlib.blockcommands;

import java.lang.annotation.*;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Annotate a static Command, ListCommand, or ResultCommand (in your block entity) with this annotation to support sending this command
 * from the client to the server
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ServerCommand {

    /**
     * Optionally specify a class. If this is specified then this command will be registered using McJtyLib.registerListCommandInfo()
     */
    Class type() default void.class;

    /**
     * Specify the serializer to use for McJtyLib.registerListCommandInfo().
     * This is required except for these types: ItemStack, FluidStack, BlockPos, Integer, String
     */
    Class<? extends ISerializer> serializer() default ISerializer.class;
}
