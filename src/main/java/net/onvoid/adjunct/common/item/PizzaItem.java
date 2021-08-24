package net.onvoid.adjunct.common.item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.onvoid.adjunct.AdjunctHelper;
import net.onvoid.adjunct.handlers.PizzaHandler;

import javax.annotation.Nullable;
import java.util.List;

public class PizzaItem extends Item {

    public static final Color ONE_TOPPING = Color.fromRgb(0xb3daff);
    public static final Color TWO_TOPPING = Color.fromRgb(0x7abfff);
    public static final Color THREE_TOPPING = Color.fromRgb(0x339dff);
    public static final Color FOUR_TOPPING = Color.fromRgb(0x006acf);

    public PizzaItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entity) {
        if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative()) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.isCrouching()) {
                player.getFoodData().setFoodLevel(0);
                player.getFoodData().setSaturation(0);
            } else {
                //int foodLevel = player.getFoodData().getFoodLevel() + 2;
                //float saturation = player.getFoodData().getSaturationLevel() + 2;
                int foodLevel = 2;
                float saturation = 0.2F;
                CompoundNBT tag = stack.getOrCreateTag();
                for (String topping : PizzaHandler.getTypes()){
                    if (tag.getBoolean(topping)) {
                        foodLevel += 2;
                        saturation += 0.05F;
                    }
                }
                player.getFoodData().eat(foodLevel, saturation);
                //player.getFoodData().setFoodLevel(foodLevel <= 20 ? foodLevel : 20);
                //player.getFoodData().setSaturation(saturation);
                stack.shrink(1);
            }
        }
        return stack;
    }

    private Color getNameColor(ItemStack stack){
        CompoundNBT tag = stack.getOrCreateTag();
        int toppings = 0;
        for (String topping : PizzaHandler.getTypes()){
            int value = tag.getInt(topping);
            if (value > 0) {
                value -= 1;
                switch (topping){
                    case "crust":
                        break;
                    case "sauce":
                        toppings++;
                        break;
                    case "cheese":
                        toppings++;
                        break;
                    case "topping1":
                    case "topping2":
                        toppings++;
                        break;
                    default:
                        break;
                }
            }
        }
        switch (toppings){
            case 1:
                return ONE_TOPPING;
            case 2:
                return TWO_TOPPING;
            case 3:
                return THREE_TOPPING;
            case 4:
                return FOUR_TOPPING;
            default:
                return null;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ITextComponent getHighlightTip(ItemStack stack, ITextComponent displayName) {
        return AdjunctHelper.styleComponent(displayName, getNameColor(stack));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        AdjunctHelper.setDisplayNameColor(tooltip, getNameColor(stack));
        CompoundNBT tag = stack.getOrCreateTag();
        if (tag.getBoolean("cooked")){
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString()+ ".cooked").withStyle(TextFormatting.GREEN));
        } else {
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString()+ ".uncooked").withStyle(TextFormatting.RED));
        }
        if (tag.getInt("crust") != 0){
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + PizzaHandler.getCrusts().get(tag.getInt("crust") - 1)).withStyle(TextFormatting.YELLOW));
        } else {
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + ".nocrust").withStyle(TextFormatting.YELLOW));
        }
        int toppings = 0;
        for (String topping : PizzaHandler.getTypes()){
            int value = tag.getInt(topping);
            if (value > 0) {
                value -= 1;
                switch (topping){
                    case "crust":
                        break;
                    case "sauce":
                        toppings++;
                        tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + PizzaHandler.getSauces().get(value)).withStyle(TextFormatting.YELLOW));
                        break;
                    case "cheese":
                        toppings++;
                        tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + PizzaHandler.getCheeses().get(value)).withStyle(TextFormatting.YELLOW));
                        break;
                    case "topping1":
                    case "topping2":
                        toppings++;
                        tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + PizzaHandler.getToppings().get(value)).withStyle(TextFormatting.YELLOW));
                        break;
                    default:
                        break;
                }
            }
        }
        if (toppings == 0) {
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString()+ ".notoppings").withStyle(TextFormatting.RED));
        }
    }
}
