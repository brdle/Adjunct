package net.onvoid.adjunct.common.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EggCartonItem extends Item {

    public EggCartonItem(Item.Properties properties) {
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
        if (world.isClientSide()){
            return ActionResult.consume(stack);
        }
        if (player.isCrouching()){
            ItemStack eggs = new ItemStack(Items.EGG, 8);
            if (stack.getCount() == 1){
                stack.shrink(1);
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, eggs);
            } else {
                stack.shrink(1);
                if (!player.inventory.add(eggs)){
                    player.drop(eggs, false);
                }
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        } else {
            return super.use(world, player, hand);
        }
    }
}
