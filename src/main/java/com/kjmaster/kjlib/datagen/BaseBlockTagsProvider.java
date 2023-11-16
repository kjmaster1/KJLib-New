package com.kjmaster.kjlib.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class BaseBlockTagsProvider extends BlockTagsProvider {

    public BaseBlockTagsProvider(DataGenerator pGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator.getPackOutput(), lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }

    protected void ironPickaxe(RegistryObject... blocks) {
        for (RegistryObject b : blocks) {
            Block block = (Block) b.get();
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            tag(BlockTags.NEEDS_IRON_TOOL).add(block);
        }
    }

    protected void diamondPickaxe(RegistryObject... blocks) {
        for (RegistryObject b : blocks) {
            Block block = (Block) b.get();
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            tag(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
        }
    }

    protected void stonePickaxe(RegistryObject... blocks) {
        for (RegistryObject b : blocks) {
            Block block = (Block) b.get();
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
            tag(BlockTags.NEEDS_STONE_TOOL).add(block);
        }
    }
}
