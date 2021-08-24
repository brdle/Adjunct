package net.onvoid.adjunct.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class JumpRocketItem extends Item {

    public JumpRocketItem(Properties properties) {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        //
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Vector3d vector3d = player.getDeltaMovement();
        if (player.isCrouching()){
            player.setDeltaMovement((vector3d.add(0.0D, 1.0D, 0.0D).add(player.getViewVector(1.0F).multiply(1.0D, 0.0D, 1.0D))).multiply(1.4D, 0.55D, 1.4D));
        } else {
            player.setDeltaMovement(vector3d.add(player.getViewVector(1.0F).multiply(0.6D, 0.0D, 0.6D).add(0.0D, 1.0D, 0.0D)));
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
}
