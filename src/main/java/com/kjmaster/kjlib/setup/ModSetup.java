package com.kjmaster.kjlib.setup;

import com.kjmaster.kjlib.KJLib;
import com.kjmaster.kjlib.api.container.CapabilityContainerProvider;
import com.kjmaster.kjlib.network.PacketHandler;
import com.kjmaster.kjlib.preferences.PreferencesDispatcher;
import com.kjmaster.kjlib.preferences.PreferencesProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.ChunkWatchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;

import static com.kjmaster.kjlib.KJLib.MODID;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class ModSetup extends DefaultModSetup {

    public static final ResourceLocation PREFERENCES_CAPABILITY_KEY = new ResourceLocation(MODID, "preferences");

    public static boolean patchouli = false;

    public static Capability<PreferencesProperties> PREFERENCES_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        CapabilityContainerProvider.register(event);
        PreferencesProperties.register(event);
    }

    @Override
    public void init(FMLCommonSetupEvent e) {
        super.init(e);
        KJLib.networkHandler = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> "1.0", s -> true, s -> true);
        PacketHandler.registerMessages(KJLib.networkHandler);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Override
    protected void setupModCompat() {
        patchouli = ModList.get().isLoaded("patchouli");
    }

    public static class EventHandler {

        @SubscribeEvent
        public void onWorldTick(TickEvent.LevelTickEvent event) {
            if (event.phase == TickEvent.Phase.START && event.level.dimension() == Level.OVERWORLD) {
                KJLib.SYNCER.sendOutData(event.level.getServer());
            }
        }

        @SubscribeEvent
        public void onChunkWatch(ChunkWatchEvent.Watch event) {
            KJLib.SYNCER.startWatching(event.getPlayer());
        }

        @SubscribeEvent
        public void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.START && !event.player.getCommandSenderWorld().isClientSide) {
                KJLib.getPreferencesProperties(event.player).ifPresent(handler -> handler.tick((ServerPlayer) event.player));
            }
        }

        @SubscribeEvent
        public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getCapabilities().containsKey(PREFERENCES_CAPABILITY_KEY) && !event.getObject().getCapability(PREFERENCES_CAPABILITY).isPresent()) {
                    event.addCapability(PREFERENCES_CAPABILITY_KEY, new PreferencesDispatcher());
                } else {
                    throw new IllegalStateException(event.getObject().toString());
                }
            }
        }
    }
}
