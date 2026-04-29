package fuzs.spikyspikes.neoforge;

import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.spikyspikes.common.SpikySpikes;
import fuzs.spikyspikes.common.data.loot.ModBlockLootProvider;
import fuzs.spikyspikes.common.data.ModRecipeProvider;
import fuzs.spikyspikes.common.data.tags.ModBlockTagProvider;
import fuzs.spikyspikes.common.data.tags.ModEntityTypeTagProvider;
import fuzs.spikyspikes.common.data.tags.ModItemTagProvider;
import fuzs.spikyspikes.common.init.ModRegistry;
import fuzs.spikyspikes.neoforge.init.NeoForgeModRegistry;
import net.neoforged.fml.common.Mod;

@Mod(SpikySpikes.MOD_ID)
public class SpikySpikesNeoForge {

    public SpikySpikesNeoForge() {
        NeoForgeModRegistry.bootstrap();
        ModConstructor.construct(SpikySpikes.MOD_ID, SpikySpikes::new);
        DataProviderHelper.registerDataProviders(SpikySpikes.MOD_ID,
                ModRegistry.REGISTRY_SET_BUILDER,
                ModBlockLootProvider::new,
                ModBlockTagProvider::new,
                ModItemTagProvider::new,
                ModEntityTypeTagProvider::new,
                ModRecipeProvider::new);
    }
}
