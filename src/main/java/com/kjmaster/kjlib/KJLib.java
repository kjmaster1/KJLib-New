package com.kjmaster.kjlib;

import com.ibm.icu.impl.Pair;
import com.kjmaster.kjlib.blockcommands.CommandInfo;
import com.kjmaster.kjlib.network.IServerCommand;
import com.kjmaster.kjlib.preferences.PreferencesProperties;
import com.kjmaster.kjlib.setup.ClientSetup;
import com.kjmaster.kjlib.setup.ModSetup;
import com.kjmaster.kjlib.syncpositional.PositionalDataSyncer;
import com.kjmaster.kjlib.typed.TypedMap;
import com.mojang.logging.LogUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

// The value here should match an entry in the META-INF/mods.toml file
@Mod(KJLib.MODID)
public class KJLib
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "kjlib";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static KJLib instance;

    private static final Map<Pair<String, String>, IServerCommand> serverCommands = new HashMap<>();
    private static final Map<Pair<String, String>, IServerCommand> clientCommands = new HashMap<>();
    private static final Map<String, CommandInfo> commandInfos = new HashMap<>();

    public static SimpleChannel networkHandler;

    public static final ModSetup setup = new ModSetup();

    public static final PositionalDataSyncer SYNCER = new PositionalDataSyncer();

    public KJLib()
    {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(setup::init);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
            modbus.addListener(ClientSetup::init);
        });
    }

    /**
     * This is automatically called by annotated ListCommands (@ServerCommand) if they have
     * an associated type parameter
     */
    public static <T> void registerListCommandInfo(String command, Class<T> type, Function<FriendlyByteBuf, T> deserializer, BiConsumer<FriendlyByteBuf, T> serializer) {
        commandInfos.put(command, new CommandInfo<T>(type, deserializer, serializer));
    }

    public static CommandInfo getCommandInfo(String command) {
        return commandInfos.get(command);
    }

    /**
     * Used in combination with PacketSendServerCommand for a more global command
     */
    public static void registerCommand(String modid, String id, IServerCommand command) {
        serverCommands.put(Pair.of(modid, id), command);
    }

    public static void registerClientCommand(String modid, String id, IServerCommand command) {
        clientCommands.put(Pair.of(modid, id), command);
    }

    public static boolean handleCommand(String modid, String id, Player player, TypedMap arguments) {
        IServerCommand command = serverCommands.get(Pair.of(modid, id));
        if (command == null) {
            return false;
        }
        return command.execute(player, arguments);
    }

    public static boolean handleClientCommand(String modid, String id, Player player, TypedMap arguments) {
        IServerCommand command = clientCommands.get(Pair.of(modid, id));
        if (command == null) {
            return false;
        }
        return command.execute(player, arguments);
    }

    public static LazyOptional<PreferencesProperties> getPreferencesProperties(Player player) {
        return player.getCapability(ModSetup.PREFERENCES_CAPABILITY);
    }
}
