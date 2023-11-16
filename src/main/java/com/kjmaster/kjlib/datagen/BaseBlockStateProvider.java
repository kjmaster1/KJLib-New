package com.kjmaster.kjlib.datagen;

import com.kjmaster.kjlib.KJLib;
import com.kjmaster.kjlib.varia.Tools;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * Code from McJtyLib is used in KJLib under the 'MIT License' https://github.com/McJtyMods/McJtyLib/blob/1.20/LICENSE
 *
 */

public abstract class BaseBlockStateProvider extends BlockStateProvider {

    public static final ResourceLocation KJLIBBASE_SIDE = new ResourceLocation(KJLib.MODID, "block/base/machineside");
    public static final ResourceLocation KJLIBBASE_TOP = new ResourceLocation(KJLib.MODID, "block/base/machinetop");
    public static final ResourceLocation KJLIBBASE_BOTTOM = new ResourceLocation(KJLib.MODID, "block/base/machinebottom");

    public String name(Block block) {
        return Tools.getId(block).getPath();
    }

    public BaseBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), modid, exFileHelper);
    }

    public void simpleBlockC(Block block, Consumer<BlockModelBuilder> consumer) {
        BlockModelBuilder blockModelBuilder = models().cubeAll(this.name(block), blockTexture(block));
        consumer.accept(blockModelBuilder);
        simpleBlock(block, blockModelBuilder);
    }

    public ModelFile frontBasedModel(String modelName, ResourceLocation texture) {
        return frontBasedModel(modelName, texture, KJLIBBASE_SIDE, KJLIBBASE_TOP, KJLIBBASE_BOTTOM);
    }

    public ModelFile frontBasedModel(String modelName, ResourceLocation front, ResourceLocation side, ResourceLocation top, ResourceLocation bottom) {
        return models().cube(modelName, bottom, top, front, side, side, side)
                .texture("particle", front);
    }

    public ModelFile topBasedModel(String modelName, ResourceLocation texture) {
        return topBasedModel(modelName, texture, KJLIBBASE_SIDE, KJLIBBASE_BOTTOM);
    }

    public ModelFile topBasedModel(String modelName, ResourceLocation top, ResourceLocation side, ResourceLocation bottom) {
        return models().cube(modelName, bottom, top, side, side, side, side)
                .texture("particle", top);
    }

    public void variantBlock(Block block,
                             Function<BlockState, ModelFile> modelSelector,
                             Function<BlockState, Integer> xRotation,
                             Function<BlockState, Integer> yRotation) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(modelSelector.apply(state))
                        .rotationX(xRotation.apply(state))
                        .rotationY(yRotation.apply(state))
                        .build()
                );
    }

    public void variantBlock(Block block,
                             Function<BlockState, ModelFile> modelSelector) {
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(modelSelector.apply(state))
                        .build()
                );
    }

    public int getXRotation(Direction direction) {
        return switch (direction) {
            case DOWN -> 0;
            case UP -> 180;
            case NORTH -> -90;
            case SOUTH -> 90;
            case WEST -> 90;
            case EAST -> 90;
        };
    }

    public int getYRotation(Direction direction) {
        return switch (direction) {
            case DOWN -> 0;
            case UP -> 0;
            case NORTH -> 0;
            case SOUTH -> 0;
            case WEST -> 90;
            case EAST -> 270;
        };
    }

    public void applyRotation(ConfiguredModel.Builder<?> builder, Direction direction) {
        applyRotationBld(builder, direction);
        builder.addModel();
    }

    private void applyRotationBld(ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case DOWN:
                builder.rotationX(90);
                break;
            case UP:
                builder.rotationX(-90);
                break;
            case NORTH:
                break;
            case SOUTH:
                builder.rotationY(180);
                break;
            case WEST:
                builder.rotationY(270);
                break;
            case EAST:
                builder.rotationY(90);
                break;
        }
    }

    public void applyHorizRotation(ConfiguredModel.Builder<VariantBlockStateBuilder> builder, Direction direction) {
        applyHorizRotationBld(builder, direction);
        builder.addModel();
    }

    private void applyHorizRotationBld(ConfiguredModel.Builder<VariantBlockStateBuilder> builder, Direction direction) {
        switch (direction) {
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }

    public void singleTextureBlock(Block block, String modelName, String textureName) {
        ModelFile model = models().cubeAll(modelName, modLoc(textureName));
        simpleBlock(block, model);
    }

    public void singleTextureBlockC(Block block, String modelName, String textureName, Consumer<BlockModelBuilder> consumer) {
        BlockModelBuilder builder = models().cubeAll(modelName, modLoc(textureName));
        consumer.accept(builder);
        simpleBlock(block, builder);
    }

    public VariantBlockStateBuilder horizontalOrientedBlock(Block block, ModelFile model) {
        return horizontalOrientedBlock(block, (blockState, builder) -> builder.modelFile(model));
    }

    public VariantBlockStateBuilder horizontalOrientedBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        return directionBlock(block, model, BlockStateProperties.HORIZONTAL_FACING);
    }

    public VariantBlockStateBuilder orientedBlock(Block block, ModelFile model) {
        return orientedBlock(block, (blockState, builder) -> builder.modelFile(model));
    }

    public VariantBlockStateBuilder orientedBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        return directionBlock(block, model, BlockStateProperties.FACING);
    }

    private VariantBlockStateBuilder directionBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model, DirectionProperty directionProperty) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(directionProperty));
            return bld.build();
        });
        return builder;
    }

    public void createFrame(BlockModelBuilder dimCellFrame, String txtName, float thick) {
        createFrame(dimCellFrame, txtName, thick, true, true);
    }

    public void createFrame(BlockModelBuilder dimCellFrame, String txtName, float thick, boolean doTop, boolean doBottom) {
        // Vertical bars
        innerCube(dimCellFrame, txtName, 0f, 0f, 0f, thick, 16f, thick);
        innerCube(dimCellFrame, txtName, 16f - thick, 0f, 0f, 16f, 16f, thick);
        innerCube(dimCellFrame, txtName, 0f, 0f, 16f - thick, thick, 16f, 16f);
        innerCube(dimCellFrame, txtName, 16f - thick, 0f, 16f - thick, 16f, 16f, 16f);

        if (doTop) {
            // Top bars
            innerCube(dimCellFrame, txtName, thick, 16f - thick, 0f, 16f - thick, 16f, thick);
            innerCube(dimCellFrame, txtName, thick, 16f - thick, 16f - thick, 16f - thick, 16f, 16f);
            innerCube(dimCellFrame, txtName, 0f, 16f - thick, thick, thick, 16f, 16f - thick);
            innerCube(dimCellFrame, txtName, 16f - thick, 16f - thick, thick, 16f, 16f, 16f - thick);
        }

        if (doBottom) {
            // Bottom bars
            innerCube(dimCellFrame, txtName, thick, 0f, 0f, 16f - thick, thick, thick);
            innerCube(dimCellFrame, txtName, thick, 0f, 16f - thick, 16f - thick, thick, 16f);
            innerCube(dimCellFrame, txtName, 0f, 0f, thick, thick, thick, 16f - thick);
            innerCube(dimCellFrame, txtName, 16f - thick, 0f, thick, 16f, thick, 16f - thick);
        }
    }

    public void innerCube(BlockModelBuilder builder, String txtName, float fx, float fy, float fz, float tx, float ty, float tz) {
        builder.element().from(fx, fy, fz).to(tx, ty, tz).allFaces((direction, faceBuilder) -> faceBuilder.texture(txtName)).end();
    }
}
