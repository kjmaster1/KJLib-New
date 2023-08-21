package com.kjmaster.kjlib.tileentity;

import com.kjmaster.kjlib.typed.Key;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public record ValueHolder<T extends GenericTileEntity, V>(Key<V> key,
                                                          Function<T, V> getter,
                                                          BiConsumer<T, V> setter) {
}
