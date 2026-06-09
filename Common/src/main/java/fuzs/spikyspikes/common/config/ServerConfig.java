package fuzs.spikyspikes.common.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.ConfigCore;

public class ServerConfig implements ConfigCore {
    @Config(description = "Damage dealt by a wooden spike.")
    @Config.DoubleRange(min = 0.0, max = 1024.0)
    public double woodenSpikeDamage = 1.0;
    @Config(description = "Damage dealt by a stone spike.")
    @Config.DoubleRange(min = 0.0, max = 1024.0)
    public double stoneSpikeDamage = 2.0;
    @Config(description = "Damage dealt by a iron spike.")
    @Config.DoubleRange(min = 0.0, max = 1024.0)
    public double ironSpikeDamage = 4.0;
    @Config(description = "Damage dealt by a golden spike.")
    @Config.DoubleRange(min = 0.0, max = 1024.0)
    public double goldenSpikeDamage = 6.0;
    @Config(description = "Damage dealt by a diamond spike.")
    @Config.DoubleRange(min = 0.0, max = 1024.0)
    public double diamondSpikeDamage = 8.0;
    @Config(description = "Damage dealt by a netherite spike.")
    @Config.DoubleRange(min = 0.0, max = 1024.0)
    public double netheriteSpikeDamage = 12.0;
}
