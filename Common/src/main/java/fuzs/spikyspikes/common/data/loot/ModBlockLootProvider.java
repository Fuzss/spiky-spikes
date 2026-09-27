package fuzs.spikyspikes.common.data.loot;

import fuzs.puzzleslib.common.api.data.v3.loot.AbstractBlockLootSubProvider;
import fuzs.spikyspikes.common.init.ModRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public class ModBlockLootProvider extends AbstractBlockLootSubProvider {

    public ModBlockLootProvider(LootTableSubProvider.Context context) {
        super(context);
    }

    @Override
    public void generate() {
        this.dropSelf(ModRegistry.WOODEN_SPIKE_BLOCK.value());
        this.dropSelf(ModRegistry.STONE_SPIKE_BLOCK.value());
        this.dropSelf(ModRegistry.IRON_SPIKE_BLOCK.value());
        this.dropSelf(ModRegistry.GOLDEN_SPIKE_BLOCK.value());
        this.dropEnchantableSpike(ModRegistry.DIAMOND_SPIKE_BLOCK.value());
        this.dropEnchantableSpike(ModRegistry.NETHERITE_SPIKE_BLOCK.value());
    }

    public final void dropEnchantableSpike(Block spikeBlock) {
        this.add(spikeBlock, (Block block) -> {
            return LootTable.lootTable()
                    .withPool(this.applyExplosionCondition(block,
                            LootPool.lootPool()
                                    .setRolls(ContextIntProviders.exactly(1))
                                    .add(LootItem.lootTableItem(block)
                                            .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(
                                                            LootContextParams.BLOCK_ENTITY)
                                                    .include(DataComponents.STORED_ENCHANTMENTS)
                                                    .include(DataComponents.REPAIR_COST)))));
        });
    }
}
