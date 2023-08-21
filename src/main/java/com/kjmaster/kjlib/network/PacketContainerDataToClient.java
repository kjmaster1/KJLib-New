package com.kjmaster.kjlib.network;

import com.kjmaster.kjlib.api.container.IContainerDataListener;
import com.kjmaster.kjlib.container.GenericContainer;
import com.kjmaster.kjlib.varia.SafeClientTools;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public class PacketContainerDataToClient {

    private final ResourceLocation id;
    private final FriendlyByteBuf buffer;

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeResourceLocation(id);
        int l = buffer.array().length;
        buf.writeInt(l);
        buf.writeBytes(buffer.array());
    }

    public PacketContainerDataToClient(FriendlyByteBuf buf) {
        id = buf.readResourceLocation();
        int l = buf.readInt();

        ByteBuf newbuf = Unpooled.buffer(l);
        byte[] bytes = new byte[l];
        buf.readBytes(bytes);
        newbuf.writeBytes(bytes);
        buffer = new FriendlyByteBuf(newbuf);
    }

    public PacketContainerDataToClient(ResourceLocation id, FriendlyByteBuf buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            AbstractContainerMenu container = SafeClientTools.getClientPlayer().containerMenu;
            if (container instanceof GenericContainer gc) {
                IContainerDataListener listener = gc.getListener(id);
                if (listener != null) {
                    listener.readBuf(buffer);
                }
            }
        });
        ctx.setPacketHandled(true);
    }


}
