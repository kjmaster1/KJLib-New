package com.kjmaster.kjlib.typed;

import com.kjmaster.kjlib.network.NetworkTools;
import com.kjmaster.kjlib.tileentity.GenericTileEntity;
import com.kjmaster.kjlib.tileentity.ValueHolder;
import com.kjmaster.kjlib.varia.LevelTools;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * A Type object represents a given type.
 */

public final class Type<V> {

    // Basic
    public static final Type<Integer> INTEGER = create(Integer.class, (v, buf) -> buf.writeInt(v), FriendlyByteBuf::readInt);
    public static final Type<Float> FLOAT = create(Float.class, (v, buf) -> buf.writeFloat(v), FriendlyByteBuf::readFloat);
    public static final Type<Double> DOUBLE = create(Double.class, (v, buf) -> buf.writeDouble(v), FriendlyByteBuf::readDouble);
    public static final Type<Long> LONG = create(Long.class, (v, buf) -> buf.writeLong(v), FriendlyByteBuf::readLong);
    public static final Type<String> STRING = create(String.class, (v, buf) -> NetworkTools.writeStringUTF8(buf, v), NetworkTools::readStringUTF8);
    public static final Type<UUID> UUID = create(UUID.class, (v, buf) -> {
        if (v != null) {
            buf.writeBoolean(true);
            buf.writeUUID(v);
        } else {
            buf.writeBoolean(false);
        }
    }, buf -> {
        if (buf.readBoolean()) {
            return buf.readUUID();
        } else {
            return null;
        }
    });
    public static final Type<Boolean> BOOLEAN = create(Boolean.class, (v, buf) -> buf.writeBoolean(v), FriendlyByteBuf::readBoolean);
    public static final Type<BlockPos> BLOCKPOS = create(BlockPos.class, (v, buf) -> {
        if (v != null) {
            buf.writeBoolean(true);
            buf.writeBlockPos(v);
        } else {
            buf.writeBoolean(false);
        }
    }, buf -> {
        if (buf.readBoolean()) {
            return buf.readBlockPos();
        } else {
            return null;
        }
    });
    public static final Type<ItemStack> ITEMSTACK = create(ItemStack.class, (v, buf) -> buf.writeItem(v), FriendlyByteBuf::readItem);
    public static final Type<ResourceKey<Level>> DIMENSION_TYPE = create(ResourceKey.class, (v, buf) -> buf.writeResourceLocation(v.location()), buf -> LevelTools.getId(buf.readResourceLocation()));

    public static final Type<List<String>> STRING_LIST = create(List.class, (v, buf) -> NetworkTools.writeStringList(buf, v), NetworkTools::readStringList);
    public static final Type<List<ItemStack>> ITEMSTACK_LIST = create(List.class, (v, buf) -> NetworkTools.writeItemStackList(buf, v), NetworkTools::readItemStackList);
    public static final Type<List<BlockPos>> POS_LIST = create(List.class, (v, buf) -> NetworkTools.writeBlockPosList(buf, v), NetworkTools::readBlockPosList);

    @Nonnull private final Class<V> type;
    @Nullable private final BiConsumer<V, FriendlyByteBuf> serializer;
    @Nullable private final Function<FriendlyByteBuf, V> deserializer;

    private Type(@Nonnull final Class<V> type, @Nullable BiConsumer<V, FriendlyByteBuf> serializer, @Nullable Function<FriendlyByteBuf, V> deserializer) {
        this.type = type;
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Nonnull
    public static <V> Type<V> create(@Nonnull final Class<? super V> type, BiConsumer<V, FriendlyByteBuf> serializer, Function<FriendlyByteBuf, V> deserializer) {
        return new Type<>((Class<V>) type, serializer, deserializer);
    }

    @Nonnull
    public static <V> Type<V> create(@Nonnull final Class<? super V> type) {
        return new Type<>((Class<V>) type, null, null);
    }

    @Nonnull
    public Class<V> getType() {
        return type;
    }

    public void serialize(FriendlyByteBuf buf, Object value) {
        serializer.accept((V) value, buf);
    }

    public <T extends GenericTileEntity> void deserialize(FriendlyByteBuf buf, ValueHolder<T, V> value, T te) {
        V v = deserializer.apply(buf);
        value.setter().accept(te, v);
    }

    @Nullable
    public BiConsumer<V, FriendlyByteBuf> getSerializer() {
        return serializer;
    }

    @Nullable
    public Function<FriendlyByteBuf, V> getDeserializer() {
        return deserializer;
    }

    public boolean isA(Object b) {
        return type.isInstance(b);
    }

    @Nonnull
    public List<V> convert(@Nonnull List<?> list) {
        for(Object o : list) {
            if(o != null && !type.isInstance(o)) {
                throw new ClassCastException("Cannot cast List<? super " + o.getClass().getName() + "> to List<" + type.getName() + ">");
            }
        }
        return (List<V>) list;
    }

    public V convert(Object o) {
        return type.cast(o);
    }

    @Override
    public String toString() {
        return "Type(" + getType().getSimpleName() + ')';
    }
}
