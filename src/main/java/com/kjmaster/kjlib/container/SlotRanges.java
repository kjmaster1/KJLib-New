package com.kjmaster.kjlib.container;

import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeSet;

import java.util.Set;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * A collection of ranges of a given slot type.
 */

public class SlotRanges {
    private final TreeRangeSet<Integer> treeRangeSet = TreeRangeSet.create();

    public void addSingle(int index) {
        treeRangeSet.add(Range.singleton(index).canonical(DiscreteDomain.integers()));
    }

    public Set<Range<Integer>> asRanges() {
        return treeRangeSet.asRanges();
    }
}