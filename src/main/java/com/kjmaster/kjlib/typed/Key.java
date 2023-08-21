package com.kjmaster.kjlib.typed;

import javax.annotation.Nonnull;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * A key in a TypedMap
 */

public record Key<T>(@Nonnull String name, @Nonnull Type<T> type) {
}
