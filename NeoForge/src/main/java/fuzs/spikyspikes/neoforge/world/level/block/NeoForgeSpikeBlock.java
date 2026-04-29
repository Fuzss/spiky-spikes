package fuzs.spikyspikes.neoforge.world.level.block;

import com.mojang.serialization.MapCodec;
import fuzs.spikyspikes.common.world.level.block.SpikeBlock;
import fuzs.spikyspikes.common.world.level.block.SpikeMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jspecify.annotations.Nullable;

public class NeoForgeSpikeBlock extends SpikeBlock {
    public static final MapCodec<SpikeBlock> CODEC = spikeCodec(NeoForgeSpikeBlock::new);

    public NeoForgeSpikeBlock(SpikeMaterial spikeMaterial, Properties properties) {
        super(spikeMaterial, properties);
    }

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.DAMAGING;
    }

    @Override
    public @Nullable PathType getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, PathType originalType) {
        return PathType.DAMAGING_IN_NEIGHBOR;
    }
}
