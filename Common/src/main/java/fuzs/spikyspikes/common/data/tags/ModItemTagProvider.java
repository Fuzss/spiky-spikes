package fuzs.spikyspikes.common.data.tags;

import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import fuzs.spikyspikes.common.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class ModItemTagProvider extends AbstractTagProvider<Item> {

    public ModItemTagProvider(DataProviderContext context) {
        super(Registries.ITEM, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(ModRegistry.SPIKES_ITEM_TAG)
                .add(ModRegistry.WOODEN_SPIKE_ITEM.value(),
                        ModRegistry.STONE_SPIKE_ITEM.value(),
                        ModRegistry.IRON_SPIKE_ITEM.value(),
                        ModRegistry.GOLDEN_SPIKE_ITEM.value())
                .addTag(ModRegistry.ENCHANTABLE_SPIKES_ITEM_TAG);
        this.tag(ModRegistry.ENCHANTABLE_SPIKES_ITEM_TAG)
                .add(ModRegistry.DIAMOND_SPIKE_ITEM.value(), ModRegistry.NETHERITE_SPIKE_ITEM.value());
        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).addTag(ModRegistry.ENCHANTABLE_SPIKES_ITEM_TAG);
        this.tag(ItemTags.WEAPON_ENCHANTABLE).addTag(ModRegistry.ENCHANTABLE_SPIKES_ITEM_TAG);
        this.tag(ItemTags.MELEE_WEAPON_ENCHANTABLE).addTag(ModRegistry.ENCHANTABLE_SPIKES_ITEM_TAG);
        this.tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).addTag(ModRegistry.ENCHANTABLE_SPIKES_ITEM_TAG);
        this.tag(ItemTags.SWEEPING_ENCHANTABLE).addTag(ModRegistry.ENCHANTABLE_SPIKES_ITEM_TAG);
    }
}
