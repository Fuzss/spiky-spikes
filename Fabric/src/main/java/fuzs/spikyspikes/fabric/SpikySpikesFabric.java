package fuzs.spikyspikes.fabric;

import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.spikyspikes.SpikySpikes;
import fuzs.spikyspikes.init.ModRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.LandPathTypeRegistry;
import net.minecraft.world.level.pathfinder.PathType;

public class SpikySpikesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(SpikySpikes.MOD_ID, SpikySpikes::new);
        registerPathTypes();
    }

    private static void registerPathTypes() {
        LandPathTypeRegistry.register(ModRegistry.WOODEN_SPIKE_BLOCK.value(),
                PathType.DAMAGING,
                PathType.DAMAGING_IN_NEIGHBOR);
        LandPathTypeRegistry.register(ModRegistry.STONE_SPIKE_BLOCK.value(),
                PathType.DAMAGING,
                PathType.DAMAGING_IN_NEIGHBOR);
        LandPathTypeRegistry.register(ModRegistry.IRON_SPIKE_BLOCK.value(),
                PathType.DAMAGING,
                PathType.DAMAGING_IN_NEIGHBOR);
        LandPathTypeRegistry.register(ModRegistry.GOLDEN_SPIKE_BLOCK.value(),
                PathType.DAMAGING,
                PathType.DAMAGING_IN_NEIGHBOR);
        LandPathTypeRegistry.register(ModRegistry.DIAMOND_SPIKE_BLOCK.value(),
                PathType.DAMAGING,
                PathType.DAMAGING_IN_NEIGHBOR);
        LandPathTypeRegistry.register(ModRegistry.NETHERITE_SPIKE_BLOCK.value(),
                PathType.DAMAGING,
                PathType.DAMAGING_IN_NEIGHBOR);
    }
}
