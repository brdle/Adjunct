package net.onvoid.adjunct.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.AdjunctHelper;
import javax.annotation.Nullable;
import java.util.List;

public class ExperienceContainerItem extends Item {
    private int MAX = -1;

    public ExperienceContainerItem(Properties properties, int max) {
        super(properties);
        this.MAX = max;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide()){
            return ActionResult.consume(stack);
        }
        CompoundNBT tag = stack.getOrCreateTag();
        int exp = tag.getInt("exp");
        if (!player.isCreative()) {
            if (stack.getCount() > 1){
                ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Failed: Invalid stack").withStyle(TextFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
                return ActionResult.fail(stack);
            }
            if (player.isCrouching()){
                //STORE EXP
                int prev = player.totalExperience;
                int total = prev + exp;
                if (total < this.MAX){
                    AdjunctHelper.setPlayerExp(player, 0);
                    tag.putInt("exp", total);
                    stack.setTag(tag);
                    ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Deposited " + prev + " experience").withStyle(TextFormatting.GREEN), ChatType.GAME_INFO, Util.NIL_UUID);
                    world.playSound(player, player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ(), SoundEvents.PLAYER_LEVELUP, player.getSoundSource(), 0.3F, 1.0F);
                    return ActionResult.pass(stack);
                } else if (exp < this.MAX){
                    int diff = this.MAX - exp;
                    AdjunctHelper.addPlayerExp(player, -diff);
                    tag.putInt("exp", this.MAX);
                    stack.setTag(tag);
                    ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Deposited " + diff + " experience").withStyle(TextFormatting.GREEN), ChatType.GAME_INFO, Util.NIL_UUID);
                    world.playSound(player, player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ(), SoundEvents.PLAYER_LEVELUP, player.getSoundSource(), 0.3F, 1.0F);
                    return ActionResult.pass(stack);
                }
                ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Could not deposit experience: Full").withStyle(TextFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
            } else {
                //RETRIEVE EXP
                if (exp > 0) {
                    AdjunctHelper.addPlayerExp(player, exp);
                    tag.putInt("exp", 0);
                    stack.setTag(tag);
                    ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Retrieved " + exp + " experience").withStyle(TextFormatting.GREEN), ChatType.GAME_INFO, Util.NIL_UUID);
                    world.playSound(player, player.blockPosition().getX(), player.blockPosition().getY(), player.blockPosition().getZ(), SoundEvents.PLAYER_LEVELUP, player.getSoundSource(), 0.3F, 1.0F);
                    return ActionResult.pass(stack);
                }
                ((ServerPlayerEntity)player).sendMessage(new StringTextComponent("Could not retrieve experience: Empty").withStyle(TextFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
            }
            return ActionResult.consume(stack);
        }
        return super.use(world, player, hand);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT tag = stack.getOrCreateTag();
        int exp = tag.getInt("exp");
        tooltip.add(new TranslationTextComponent(Adjunct.MODID + ":desc.exp").append(String.valueOf(exp)).append("/" + this.MAX).withStyle(TextFormatting.GREEN));
    }
}
