package fuzs.spikyspikes.common.client;

import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.BlockEntityRenderersContext;
import fuzs.puzzleslib.common.api.client.core.v1.context.BuiltInBlockModelsContext;
import fuzs.puzzleslib.common.api.client.gui.v2.tooltip.ItemTooltipRegistry;
import fuzs.puzzleslib.common.api.core.v1.ModLoaderEnvironment;
import fuzs.spikyspikes.common.client.renderer.blockentity.SpikeRenderer;
import fuzs.spikyspikes.common.client.renderer.util.SpikeTooltipHelper;
import fuzs.spikyspikes.common.init.ModRegistry;
import fuzs.spikyspikes.common.world.level.block.SpikeBlock;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.BlockStateModelWrapper;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4fc;

import java.util.List;

public class SpikySpikesClient implements ClientModConstructor {

    @Override
    public void onClientSetup() {
        ItemTooltipRegistry.BLOCK.registerItemTooltipLines(SpikeBlock.class, SpikeTooltipHelper::appendHoverText);
    }

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModRegistry.SPIKE_BLOCK_ENTITY_TYPE.value(), SpikeRenderer::new);
    }

    @Override
    public void onRegisterBuiltInBlockModels(BuiltInBlockModelsContext context) {
        if (ModLoaderEnvironment.INSTANCE.getModLoader().isFabricLike()) {
            context.registerModelFactory(ModRegistry.WOODEN_SPIKE_BLOCK.value(), createSpikeModel());
            context.registerModelFactory(ModRegistry.STONE_SPIKE_BLOCK.value(), createSpikeModel());
            context.registerModelFactory(ModRegistry.IRON_SPIKE_BLOCK.value(), createSpikeModel());
            context.registerModelFactory(ModRegistry.GOLDEN_SPIKE_BLOCK.value(), createSpikeModel());
            context.registerModelFactory(ModRegistry.DIAMOND_SPIKE_BLOCK.value(), createSpikeModel());
            context.registerModelFactory(ModRegistry.NETHERITE_SPIKE_BLOCK.value(), createSpikeModel());
        }
    }

    private static BuiltInBlockModels.ModelFactory createSpikeModel() {
        return (BlockColors colors, BlockState state) -> {
            BlockStateModelWrapper.Unbaked unbaked = BuiltInBlockModels.createBlockStateModelWrapper(colors, state);
            return (BlockModel.BakingContext context, Matrix4fc transformation) -> {
                BlockStateModelWrapper blockModel = (BlockStateModelWrapper) unbaked.bake(context, transformation);
                return new BlockStateModelWrapper(blockModel.model, blockModel.tints, blockModel.transformation) {
                    /**
                     * Fabric Api overrides this method, which breaks our implementation as model parts are not properly initialized.
                     * This copies the vanilla method so all that functionality remains intact.
                     */
                    @Override
                    public void update(BlockModelRenderState output, BlockState blockState, BlockDisplayContext displayContext, long seed) {
                        List<BlockStateModelPart> partList = output.setupModel(this.transformation,
                                this.model.hasMaterialFlag(1));
                        this.model.collectParts(output.scratchRandomSource(seed), partList);
                        this.updateTints(output, blockState);
                    }
                };
            };
        };
    }
}
