package fuzs.spikyspikes.client.renderer.block.model;

import com.mojang.math.Quadrant;
import fuzs.puzzleslib.common.api.client.renderer.v1.model.MutableBakedQuad;
import fuzs.spikyspikes.SpikySpikes;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.client.resources.model.cuboid.CuboidFace;
import net.minecraft.client.resources.model.cuboid.CuboidModelElement;
import net.minecraft.client.resources.model.cuboid.UnbakedCuboidGeometry;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.geometry.UnbakedGeometry;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class SpikeModelGenerator implements UnbakedModel {
    public static final Identifier BUILTIN_SPIKE_MODEL = SpikySpikes.id("builtin/spike");
    /**
     * The upper face is only removed after baking and will log a missing texture warning if not present.
     */
    public static final TextureSlots.Data TEXTURE_SLOTS = new TextureSlots.Data.Builder().addTexture(Direction.UP.getSerializedName(),
            new Material(MissingTextureAtlasSprite.getLocation())).build();
    private static final List<CuboidModelElement> ELEMENTS = Collections.singletonList(createCubeElement());

    @Override
    public TextureSlots.Data textureSlots() {
        return TEXTURE_SLOTS;
    }

    @Override
    public UnbakedGeometry geometry() {
        return SpikeModelGenerator::bake;
    }

    private static CuboidModelElement createCubeElement() {
        CuboidFace.UVs blockFaceUV = new CuboidFace.UVs(0.0F, 0.0F, 16.0F, 16.0F);
        Map<Direction, CuboidFace> map = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            map.put(direction, new CuboidFace(direction, -1, direction.getSerializedName(), blockFaceUV, Quadrant.R0));
        }
        return new CuboidModelElement(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), map);
    }

    public static QuadCollection bake(TextureSlots textureSlots, ModelBaker modelBaker, ModelState modelState, ModelDebugName modelDebugName) {
        QuadCollection quadCollection = UnbakedCuboidGeometry.bake(ELEMENTS,
                textureSlots,
                modelBaker,
                modelState,
                modelDebugName);
        return modifyBakedModel(quadCollection, modelState, SpikeModelGenerator::finalizeBakedQuad);
    }

    private static QuadCollection modifyBakedModel(QuadCollection quadCollection, ModelState modelState, BakedQuadFinalizer bakedQuadFinalizer) {
        Map<Direction, BakedQuad> bakedQuadMap = Util.makeEnumMap(Direction.class,
                (Direction direction) -> quadCollection.getQuads(direction).getFirst());
        QuadCollection.Builder builder = new QuadCollection.Builder();
        for (BakedQuad bakedQuad : quadCollection.getQuads(null)) {
            builder.addUnculledFace(bakedQuad);
        }

        Function<Direction, Direction> directionRotator = Util.memoize((Direction direction) -> {
            return Direction.rotate(modelState.transformation().getMatrix(), direction);
        });
        for (Map.Entry<Direction, BakedQuad> entry : bakedQuadMap.entrySet()) {
            bakedQuadFinalizer.finalizeBakedQuad(Direction.rotate(modelState.transformation().getMatrixCopy().invert(),
                            entry.getKey()),
                    entry.getValue(),
                    directionRotator::apply,
                    (@Nullable Direction direction, BakedQuad bakedQuad) -> {
                        if (direction != null) {
                            builder.addCulledFace(direction, bakedQuad);
                        } else {
                            builder.addUnculledFace(bakedQuad);
                        }
                    });
        }

        return builder.build();
    }

    private static void finalizeBakedQuad(Direction direction, BakedQuad bakedQuad, UnaryOperator<Direction> directionRotator, BiConsumer<@Nullable Direction, BakedQuad> bakedQuadConsumer) {
        if (direction != Direction.UP) {
            MutableBakedQuad mutableBakedQuad = MutableBakedQuad.toMutable(bakedQuad);
            if (direction.getAxis().isHorizontal()) {
                int[] maxVertexIndices;
                if (directionRotator.apply(Direction.UP).getAxisDirection() == Direction.UP.getAxisDirection()) {
                    maxVertexIndices = getMaxVertexIndices(mutableBakedQuad,
                            directionRotator.apply(Direction.UP).getAxis());
                } else {
                    maxVertexIndices = getMinVertexIndices(mutableBakedQuad,
                            directionRotator.apply(Direction.UP).getAxis());
                }

                for (int vertexIndex : maxVertexIndices) {
                    setQuadPosition(mutableBakedQuad,
                            vertexIndex,
                            directionRotator.apply(Direction.EAST).getAxis(),
                            0.5F);
                    setQuadPosition(mutableBakedQuad,
                            vertexIndex,
                            directionRotator.apply(Direction.SOUTH).getAxis(),
                            0.5F);
                    float u0 = UVPair.unpackU(mutableBakedQuad.packedUV(maxVertexIndices[0]));
                    float u1 = UVPair.unpackU(mutableBakedQuad.packedUV(maxVertexIndices[1]));
                    float v = UVPair.unpackV(mutableBakedQuad.packedUV(vertexIndex));
                    mutableBakedQuad.packedUV(vertexIndex, UVPair.pack(Mth.lerp(0.5F, u0, u1), v));
                }

                mutableBakedQuad.computeQuadNormals();
                bakedQuadConsumer.accept(null, mutableBakedQuad.toImmutable());
            } else {
                bakedQuadConsumer.accept(directionRotator.apply(direction), mutableBakedQuad.toImmutable());
            }
        }
    }

    public static int[] getMaxVertexIndices(MutableBakedQuad bakedQuad, Direction.Axis axis) {
        IntList maxVertexIndices = new IntArrayList();
        float maxValue = Float.MIN_VALUE;
        for (int i = 0; i < 4; i++) {
            float positionComponent = getQuadPosition(bakedQuad, i, axis);
            if (positionComponent > maxValue) {
                maxVertexIndices.clear();
                maxValue = positionComponent;
            }

            if (positionComponent == maxValue) {
                maxVertexIndices.add(i);
            }
        }

        return maxVertexIndices.toIntArray();
    }

    public static int[] getMinVertexIndices(MutableBakedQuad bakedQuad, Direction.Axis axis) {
        IntList minVertexIndices = new IntArrayList();
        float minValue = Float.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            float positionComponent = getQuadPosition(bakedQuad, i, axis);
            if (positionComponent < minValue) {
                minVertexIndices.clear();
                minValue = positionComponent;
            }

            if (positionComponent == minValue) {
                minVertexIndices.add(i);
            }
        }

        return minVertexIndices.toIntArray();
    }

    public static float getQuadPosition(MutableBakedQuad bakedQuad, int vertexIndex, Direction.Axis axis) {
        Vector3fc vector3fc = bakedQuad.position(vertexIndex);
        return (float) axis.choose(vector3fc.x(), vector3fc.y(), vector3fc.z());
    }

    public static void setQuadPosition(MutableBakedQuad bakedQuad, int vertexIndex, Direction.Axis axis, float positionComponent) {
        Vector3fc inputVector = bakedQuad.position(vertexIndex);
        Vector3f outputVector = new Vector3f();
        inputVector.mul(axis.getPositive().step(), outputVector);
        inputVector.sub(outputVector, outputVector);
        outputVector.add(axis.getPositive().step().mul(positionComponent));
        bakedQuad.position(vertexIndex, outputVector);
    }

    @FunctionalInterface
    public interface BakedQuadFinalizer {
        void finalizeBakedQuad(Direction direction, BakedQuad bakedQuad, UnaryOperator<Direction> directionRotator, BiConsumer<@Nullable Direction, BakedQuad> bakedQuadConsumer);
    }
}
