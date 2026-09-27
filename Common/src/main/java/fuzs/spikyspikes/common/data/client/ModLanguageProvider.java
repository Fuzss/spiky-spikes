package fuzs.spikyspikes.common.data.client;

import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import fuzs.spikyspikes.common.SpikySpikes;
import fuzs.spikyspikes.common.init.ModRegistry;
import fuzs.spikyspikes.common.world.level.block.SpikeBlock;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.addCreativeModeTab(ModRegistry.CREATIVE_MODE_TAB, SpikySpikes.MOD_NAME);
        this.add(ModRegistry.WOODEN_SPIKE_BLOCK.value(), "Wooden Spike");
        this.add(ModRegistry.STONE_SPIKE_BLOCK.value(), "Stone Spike");
        this.add(ModRegistry.IRON_SPIKE_BLOCK.value(), "Iron Spike");
        this.add(ModRegistry.GOLDEN_SPIKE_BLOCK.value(), "Golden Spike");
        this.add(ModRegistry.DIAMOND_SPIKE_BLOCK.value(), "Diamond Spike");
        this.add(ModRegistry.NETHERITE_SPIKE_BLOCK.value(), "Netherite Spike");
        this.add(((SpikeBlock) ModRegistry.WOODEN_SPIKE_BLOCK.value()).getDescriptionComponent(),
                "Slowly damages mobs, but does not deal a killing blow.");
        this.add(((SpikeBlock) ModRegistry.STONE_SPIKE_BLOCK.value()).getDescriptionComponent(),
                "Killed mobs do not drop any loot or experience.");
        this.add(((SpikeBlock) ModRegistry.IRON_SPIKE_BLOCK.value()).getDescriptionComponent(),
                "Killed mobs only drop normal loot without experience.");
        this.add(((SpikeBlock) ModRegistry.GOLDEN_SPIKE_BLOCK.value()).getDescriptionComponent(),
                "Killed mobs only drop experience without any loot.");
        this.add(((SpikeBlock) ModRegistry.DIAMOND_SPIKE_BLOCK.value()).getDescriptionComponent(),
                "Killed mobs drop all loot like when killed by a player. Accepts most sword enchantments.");
        this.add(((SpikeBlock) ModRegistry.NETHERITE_SPIKE_BLOCK.value()).getDescriptionComponent(),
                "Killed mobs drop all loot like when killed by a player. Accepts most sword enchantments. Resistant to explosions and the wither boss. Does not damage players.");
        this.addGenericDamageType(ModRegistry.SPIKE_DAMAGE_TYPE, "%1$s now rests in a less spiky world");
        this.addPlayerDamageType(ModRegistry.SPIKE_DAMAGE_TYPE, "%1$s oversaw a spike trying to flee from %2$s");
    }
}
