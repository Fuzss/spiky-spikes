package fuzs.spikyspikes;

import fuzs.puzzleslib.common.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.common.api.core.v1.context.GameplayContentContext;
import fuzs.spikyspikes.config.ServerConfig;
import fuzs.spikyspikes.init.ModRegistry;
import net.minecraft.resources.Identifier;
import org.apache.commons.lang3.math.Fraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpikySpikes implements ModConstructor {
    public static final String MOD_ID = "spikyspikes";
    public static final String MOD_NAME = "Spiky Spikes";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
    }

    @Override
    public void onRegisterGameplayContent(GameplayContentContext context) {
        context.registerFuel(ModRegistry.WOODEN_SPIKE_BLOCK, Fraction.getFraction(3, 2));
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
