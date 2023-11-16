package com.kjmaster.kjlib.datagen;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public interface ITagFactory {

    void blockTags(Supplier<? extends Block> blockSupplier, List<TagKey> tags);

    void itemTags(Supplier<? extends Item> itemSupplier, List<TagKey> tags);
}
