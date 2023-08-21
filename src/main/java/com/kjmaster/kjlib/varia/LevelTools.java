package com.kjmaster.kjlib.varia;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.stream.Stream;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class LevelTools {

    public static boolean isLoaded(Level world, BlockPos pos) {
        if (world == null || pos == null) {
            return false;
        }
        return world.hasChunkAt(pos);
    }

    public static ServerLevel getOverworld() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        return server.getLevel(Level.OVERWORLD);
    }

    public static ServerLevel getOverworld(Level world) {
        MinecraftServer server = world.getServer();
        return server.getLevel(Level.OVERWORLD);
    }

    public static ServerLevel getLevel(ResourceKey<Level> type) {
        return ServerLifecycleHooks.getCurrentServer().getLevel(type);
    }

    public static ServerLevel getLevel(Level world, ResourceKey<Level> type) {
        // Worlds in 1.16 are always loaded
        return world.getServer().getLevel(type);
    }

    public static ServerLevel getLevel(Level world, ResourceLocation id) {
        // Worlds in 1.16 are always loaded
        return world.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, id));
    }

    public static ResourceKey<Level> getId(ResourceLocation id) {
        return ResourceKey.create(Registries.DIMENSION, id);
    }

    public static ResourceKey<Level> getId(String id) {
        return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(id));
    }

    /**
     * Gets all players who have the chunk in which the provided (x,z) coordinates are located loaded
     */
    public static Stream<ServerPlayer> getAllPlayersWatchingBlock(Level world, BlockPos pos) {
        if (world instanceof ServerLevel) {
            ChunkHolder.PlayerProvider playerManager = ((ServerLevel)world).getChunkSource().chunkMap;
            return playerManager.getPlayers(new ChunkPos(pos), false).stream();
        }
        return Stream.empty();
    }
}