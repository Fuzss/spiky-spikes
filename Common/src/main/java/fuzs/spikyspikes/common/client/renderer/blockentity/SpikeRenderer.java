package fuzs.spikyspikes.common.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.spikyspikes.common.client.renderer.blockentity.state.SpikeRenderState;
import fuzs.spikyspikes.common.world.level.block.EnchantmentGlintBlock;
import fuzs.spikyspikes.common.world.level.block.entity.SpikeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.BlockFeatureRenderer;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class SpikeRenderer implements BlockEntityRenderer<SpikeBlockEntity, SpikeRenderState> {
    public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();

    private final BlockModelResolver blockModelResolver;
    private final QuadInstance quadInstance = new QuadInstance();

    public SpikeRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
    }

    @Override
    public SpikeRenderState createRenderState() {
        return new SpikeRenderState();
    }

    @Override
    public void extractRenderState(SpikeBlockEntity blockEntity, SpikeRenderState state, float partialTick, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPosition, crumblingOverlay);
        if (blockEntity.getBlockState().getBlock() instanceof EnchantmentGlintBlock block
                && block.hasFoil(blockEntity.getBlockState())) {
            BlockModel blockStateModel = Minecraft.getInstance()
                    .getModelManager()
                    .getBlockModelSet()
                    .get(blockEntity.getBlockState());
            this.blockModelResolver.update(state.blockModel, blockEntity.getBlockState(), BLOCK_DISPLAY_CONTEXT);
        } else {
            state.blockModel.clear();
        }
    }

    @Override
    public void submit(SpikeRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        // When the spike is enchanted, we set the render shape to invisible and handle everything via this block entity renderer.
        // Overlaying the glint on the block model baked into chunk geometry has issues with z-fighting which couldn't be solved.
        // Having the render shape as invisible has issues though and hides breaking particles and when rendering in a minecart, held by enderman, etc.
        if (!state.blockModel.isEmpty() && state.blockModel.renderType != null) {
            submitNodeCollector.order(1)
                    .submitCustomGeometry(poseStack,
                            state.blockModel.renderType,
                            (PoseStack.Pose pose, VertexConsumer vertexConsumer) -> {
                                this.renderBlockModel(pose, vertexConsumer, state.blockModel, state.lightCoords);
                            });
            submitNodeCollector.order(2)
                    .submitCustomGeometry(poseStack,
                            ItemFeatureRenderer.getFoilRenderType(state.blockModel.renderType, true),
                            (PoseStack.Pose pose, VertexConsumer vertexConsumer) -> {
                                VertexConsumer buffer = new SheetedDecalTextureGenerator(vertexConsumer,
                                        pose,
                                        0.0078125F);
                                this.renderBlockModel(pose, buffer, state.blockModel, state.lightCoords);
                            });
            if (state.breakProgress != null) {
                submitNodeCollector.order(3)
                        .submitCustomGeometry(poseStack,
                                ModelBakery.DESTROY_TYPES.get(state.breakProgress.progress()),
                                (PoseStack.Pose pose, VertexConsumer vertexConsumer) -> {
                                    VertexConsumer buffer = new SheetedDecalTextureGenerator(vertexConsumer,
                                            state.breakProgress.cameraPose(),
                                            1.0F);
                                    this.renderBlockModel(pose, buffer, state.blockModel, LightCoordsUtil.FULL_BRIGHT);
                                });
            }
        }
    }

    /**
     * @see BlockModelRenderState#submitModel(RenderType, PoseStack, SubmitNodeCollector, int, int, int)
     * @see BlockFeatureRenderer#renderBlockModelSubmits(SubmitNodeCollection, MultiBufferSource.BufferSource,
     *         OutlineBufferSource, boolean)
     */
    private void renderBlockModel(PoseStack.Pose pose, VertexConsumer buffer, BlockModelRenderState blockModel, int lightCoords) {
        if (blockModel.modelParts != null && !blockModel.modelParts.isEmpty()) {
            int[] tints =
                    blockModel.tintLayers != null ? blockModel.tintLayers.toArray(BlockModelRenderState.EMPTY_TINTS) :
                            BlockModelRenderState.EMPTY_TINTS;
            this.quadInstance.setLightCoords(lightCoords);
            this.quadInstance.setOverlayCoords(OverlayTexture.NO_OVERLAY);
            for (BlockStateModelPart modelPart : blockModel.modelParts) {
                BlockFeatureRenderer.putPartQuads(modelPart, pose, this.quadInstance, tints, buffer, null);
            }
        }
    }

//    /**
//     * @see ItemBlockRenderTypes#getRenderType(BlockState)
//     */
//    public static RenderType getBlockRenderType(BlockState blockState) {
//        return getBlockRenderType(ItemBlockRenderTypes.getChunkRenderType(blockState));
//    }
//
//    /**
//     * @see ItemBlockRenderTypes#getRenderType(BlockState)
//     */
//    public static RenderType getBlockRenderType(ChunkSectionLayer chunkSectionLayer) {
//        if (chunkSectionLayer == ChunkSectionLayer.TRANSLUCENT) {
//            return Sheets.translucentBlockSheet();
//        } else {
//            return Sheets.cutoutBlockSheet();
//        }
//    }
//
//    public static RenderType getFoilRenderType(BlockState blockState) {
//        return getFoilRenderType(getBlockRenderType(blockState));
//    }
//
//    public static RenderType getFoilRenderType(RenderType renderType) {
//        // just some quirky getter for avoiding the private vanilla method
//        return ItemFeatureRenderer.getFoilRenderType(renderType, true);
//    }

    @Override
    public int getViewDistance() {
        return 256;
    }
}
