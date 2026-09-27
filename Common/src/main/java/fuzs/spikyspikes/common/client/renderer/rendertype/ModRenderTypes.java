package fuzs.spikyspikes.common.client.renderer.rendertype;

import fuzs.spikyspikes.common.SpikySpikes;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.rendertype.TextureTransform;

public final class ModRenderTypes {
    /**
     * A block model is rendered here, so {@link TextureTransform#GLINT_TEXTURING} is used for the glint overlay.
     * <p>
     * This matches vanilla for enchanted block items rendered via
     * {@link net.minecraft.client.renderer.Sheets#cutoutBlockItemGlintSheet()} and
     * {@link net.minecraft.client.renderer.Sheets#translucentBlockItemGlintSheet()}.
     * <p>
     * The entity and armor glint texture transforms ({@link TextureTransform#ENTITY_GLINT_TEXTURING} and
     * {@link TextureTransform#ARMOR_ENTITY_GLINT_TEXTURING}) are set up for their respective model UV layouts and are
     * not appropriate here.
     *
     * @see net.minecraft.client.renderer.rendertype.RenderTypes#PATTERNED_SHIELD_GLINT
     */
    private static final RenderType SPIKE_GLINT = RenderType.create(SpikySpikes.id("spike_glint").toString(),
            RenderSetup.builder(RenderPipelines.GLINT)
                    .withTexture("Sampler0", ItemFeatureRenderer.ENCHANTED_GLINT_ITEM)
                    .setTextureTransform(TextureTransform.GLINT_TEXTURING)
                    .createRenderSetup());

    private ModRenderTypes() {
        // NO-OP
    }

    /**
     * @see RenderTypes#patternedShieldGlint()
     */
    public static RenderType spikeGlint() {
        return SPIKE_GLINT;
    }
}
