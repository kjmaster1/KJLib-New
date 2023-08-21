package com.kjmaster.kjlib.tileentity;

import java.lang.annotation.*;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * Annotate a LazyOptional field with this annotation to automatically
 * register it for getCapabilities().
 * This annotation can also be used with the actual capability (not the
 * lazyoptional) in which case it will automatically generate a LazyOptional
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Cap {

    CapType type();
}
