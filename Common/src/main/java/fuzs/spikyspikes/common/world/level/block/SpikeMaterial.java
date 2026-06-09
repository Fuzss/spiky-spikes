package fuzs.spikyspikes.common.world.level.block;

import fuzs.spikyspikes.common.SpikySpikes;
import fuzs.spikyspikes.common.config.ServerConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jspecify.annotations.Nullable;

import java.util.function.DoubleSupplier;

public enum SpikeMaterial implements StringRepresentable {
    WOOD("wood", 0, () -> SpikySpikes.CONFIG.get(ServerConfig.class).woodenSpikeDamage),
    STONE("stone", 1, () -> SpikySpikes.CONFIG.get(ServerConfig.class).stoneSpikeDamage),
    IRON("iron", 2, () -> SpikySpikes.CONFIG.get(ServerConfig.class).ironSpikeDamage),
    GOLD("gold", 3, () -> SpikySpikes.CONFIG.get(ServerConfig.class).goldenSpikeDamage),
    DIAMOND("diamond", 4, () -> SpikySpikes.CONFIG.get(ServerConfig.class).diamondSpikeDamage),
    NETHERITE("netherite", 5, () -> SpikySpikes.CONFIG.get(ServerConfig.class).netheriteSpikeDamage);

    public static final StringRepresentable.StringRepresentableCodec<SpikeMaterial> CODEC = StringRepresentable.fromEnum(
            SpikeMaterial::values);

    private final String materialName;
    private final int materialTier;
    private final DoubleSupplier damageAmount;

    SpikeMaterial(String materialName, int materialTier, DoubleSupplier damageAmount) {
        this.materialName = materialName;
        this.materialTier = materialTier;
        this.damageAmount = damageAmount;
    }

    public float damageAmount() {
        return (float) this.damageAmount.getAsDouble();
    }

    public @Nullable Component getDamageComponent() {
        float damagedAmount = this.damageAmount();
        if (damagedAmount > 0.0F) {
            return Component.translatable("attribute.modifier.plus." + AttributeModifier.Operation.ADD_VALUE.id(),
                            ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(damagedAmount),
                            Component.translatable(Attributes.ATTACK_DAMAGE.value().getDescriptionId()))
                    .withStyle(ChatFormatting.BLUE);
        } else {
            return null;
        }
    }

    public boolean dealsFinalBlow() {
        return this.isAtLeast(STONE);
    }

    public boolean dropsMobLoot() {
        return this.isAtLeast(IRON) && this != GOLD;
    }

    public boolean dropsExperience() {
        return this.isAtLeast(GOLD);
    }

    public boolean dropsPlayerLoot() {
        return this.isAtLeast(DIAMOND);
    }

    public boolean hurtsPlayers() {
        return this != NETHERITE;
    }

    private boolean isAtLeast(SpikeMaterial material) {
        return this.materialTier >= material.materialTier;
    }

    @Override
    public String getSerializedName() {
        return this.materialName;
    }
}
