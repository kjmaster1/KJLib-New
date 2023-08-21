package com.kjmaster.kjlib.client;

import com.kjmaster.kjlib.varia.TriConsumer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 * A delayed render can be used from within a BER (TER) in order to make it possible
 * to render translucent bits on top of everything else
 */

public class DelayedRenderer {

    // Renderers per render type
    private static final Map<RenderType, List<Pair<BlockPos, BiConsumer<PoseStack, VertexConsumer>>>> RENDERS = new HashMap<>();

    // Global renderers
    private static final Map<BlockPos, TriConsumer<PoseStack, Vec3, RenderType>> DELAYED_RENDERS = new HashMap<>();
    private static final Map<BlockPos, BiFunction<Level, BlockPos, Boolean>> RENDER_VALIDATIONS = new HashMap<>();

    public static void render(PoseStack matrixStack) {
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        Set<BlockPos> todelete = new HashSet<>();
        DELAYED_RENDERS.forEach((pos, consumer) -> {
            if (RENDER_VALIDATIONS.getOrDefault(pos, (level, blockPos) -> false).apply(Minecraft.getInstance().level, pos)) {
                consumer.accept(matrixStack, projectedView, null);
            } else {
                todelete.add(pos);
            }
        });
        for (BlockPos pos : todelete) {
            DELAYED_RENDERS.remove(pos);
            RENDER_VALIDATIONS.remove(pos);
        }

        RENDERS.forEach((type, renderlist) -> {
            VertexConsumer consumer = buffer.getBuffer(type);
            renderlist.forEach(r -> {
                RenderSystem.enableDepthTest();
                BlockPos pos = r.getKey();
                matrixStack.pushPose();
                matrixStack.translate(pos.getX() - projectedView.x, pos.getY() - projectedView.y, pos.getZ() - projectedView.z);
                r.getValue().accept(matrixStack, consumer);
                matrixStack.popPose();
            });
            RenderSystem.enableDepthTest();
            buffer.endBatch(type);
        });
        RENDERS.clear();

        buffer.endBatch();
    }

    public static void addRender(BlockPos pos, TriConsumer<PoseStack, Vec3, RenderType> renderer, BiFunction<Level, BlockPos, Boolean> validator) {
        DELAYED_RENDERS.put(pos, renderer);
        RENDER_VALIDATIONS.put(pos, validator);
    }

    public static void removeRender(BlockPos pos) {
        DELAYED_RENDERS.remove(pos);
        RENDER_VALIDATIONS.remove(pos);
    }

    public static void addRender(RenderType type, BlockPos pos, BiConsumer<PoseStack, VertexConsumer> renderer) {
        RENDERS.computeIfAbsent(type, renderType -> new ArrayList<>()).add(Pair.of(pos, renderer));
    }
}