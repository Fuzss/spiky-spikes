package fuzs.spikyspikes.common.data.tags;

import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import fuzs.spikyspikes.common.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModRegistry.WOODEN_SPIKE_BLOCK);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModRegistry.STONE_SPIKE_BLOCK,
                        ModRegistry.IRON_SPIKE_BLOCK,
                        ModRegistry.GOLDEN_SPIKE_BLOCK,
                        ModRegistry.DIAMOND_SPIKE_BLOCK,
                        ModRegistry.NETHERITE_SPIKE_BLOCK);
        this.tag(BlockTags.WITHER_IMMUNE).add(ModRegistry.NETHERITE_SPIKE_BLOCK);
    }
}
