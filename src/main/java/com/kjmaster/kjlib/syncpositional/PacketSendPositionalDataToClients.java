package com.kjmaster.kjlib.syncpositional;

import com.kjmaster.kjlib.KJLib;
import com.kjmaster.kjlib.varia.LevelTools;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * This packet is used to sync positional data from server to all affected clients
 */
public class PacketSendPositionalDataToClients {

    private final GlobalPos pos;
    private final IPositionalData data;

    public PacketSendPositionalDataToClients(GlobalPos pos, IPositionalData data) {
        this.pos = pos;
        this.data = data;
    }

    public PacketSendPositionalDataToClients(FriendlyByteBuf buf) {
        ResourceKey<Level> dimension = LevelTools.getId(buf.readResourceLocation());
        pos = GlobalPos.of(dimension, buf.readBlockPos());
        ResourceLocation id = buf.readResourceLocation();
        data = KJLib.SYNCER.create(id, buf);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(pos.dimension().location());
        buf.writeBlockPos(pos.pos());
        buf.writeResourceLocation(data.getId());
        data.toBytes(buf);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            KJLib.SYNCER.handle(pos, data);
        });
        ctx.setPacketHandled(true);
    }
}
