package net.onvoid.adjunct.common.entity.goal;

import net.minecraft.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.onvoid.adjunct.handlers.TagHandler;

public class CatLieOnCatBedGoal extends CatLieOnBedGoal {
    private final CatEntity cat;

    public CatLieOnCatBedGoal(CatEntity cat, double speedModifier, int searchRange) {
        super(cat, speedModifier, searchRange);
        this.cat = cat;
    }

    @Override
    public double acceptedDistance() {
        return 1.4D;
    }

    @Override
    protected boolean isValidTarget(IWorldReader reader, BlockPos pos) {
        return reader.isEmptyBlock(pos.above()) && reader.getBlockState(pos).getBlock().is(TagHandler.CAT_BEDS);
    }
}