package com.kjmaster.kjlib.varia;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface TriConsumer<K, V, S> {
    void accept(K k, V v, S s);
}
