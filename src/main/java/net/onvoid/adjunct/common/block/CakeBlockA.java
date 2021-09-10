package net.onvoid.adjunct.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class CakeBlockA extends CakeBlock {

    private int foodLevel;
    private float saturation;

    public CakeBlockA(Properties properties, int foodLevel, float saturation) {
        super(properties);
        this.foodLevel = foodLevel;
        this.saturation = saturation;
    }

    public int getFoodLevel(){
        return this.foodLevel;
    }

    public float getSaturation(){
        return this.saturation;
    }

    @Override
    public ActionResultType use(BlockState pState, World pLevel, BlockPos pPos, PlayerEntity pPlayer, Hand pHand, BlockRayTraceResult pHit) {
        if (pLevel.isClientSide) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            if (this.eat(pLevel, pPos, pState, pPlayer).consumesAction()) {
                return ActionResultType.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return ActionResultType.CONSUME;
            }
        }

        return this.eat(pLevel, pPos, pState, pPlayer);
    }

    private ActionResultType eat(IWorld pLevel, BlockPos pPos, BlockState pState, PlayerEntity pPlayer) {
        if (!pPlayer.canEat(false)) {
            return ActionResultType.PASS;
        } else {
            pPlayer.awardStat(Stats.EAT_CAKE_SLICE);
            pPlayer.getFoodData().eat(this.getFoodLevel(), this.getSaturation());
            int i = pState.getValue(BITES);
            if (i < 6) {
                pLevel.setBlock(pPos, pState.setValue(BITES, Integer.valueOf(i + 1)), 3);
            } else {
                pLevel.removeBlock(pPos, false);
            }

            return ActionResultType.SUCCESS;
        }
    }

}
