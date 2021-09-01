package net.onvoid.adjunct.common.item.pizza;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.onvoid.adjunct.AdjunctHelper;

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
        if (entity instanceof PlayerEntity && !((PlayerEntity) entity).isCreative() && stack.getItem() instanceof PizzaItem) {
            PlayerEntity player = (PlayerEntity) entity;
            Pizza p = new Pizza(stack);
            player.getFoodData().eat(p.getNutrition(), p.getSaturation());
            stack.shrink(1);
        }
        return stack;
    }

    private Color getNameColor(ItemStack stack){
        Pizza p = new Pizza(stack);
        int toppings = 0;
        for (Topping topping : Topping.values()){
            if (p.has(topping)){
                toppings++;
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
        Pizza p = new Pizza(stack);
        if (p.isCooked()) {
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + ".cooked").withStyle(TextFormatting.GREEN));
        } else {
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + ".uncooked").withStyle(TextFormatting.RED));
        }
        int toppings = 0;
        for (Topping type : Topping.values()) {
            if (p.has(type)) {
                if (type.equals(Topping.TOPPING)){
                    if (p.hasTopping(1)){
                        tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + type.get() + "." + Topping.retrieveTopping(p, 1)).withStyle(TextFormatting.YELLOW));
                    }
                    if (p.hasTopping(2)){
                        tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + type.get() + "." + Topping.retrieveTopping(p, 2)).withStyle(TextFormatting.YELLOW));
                    }
                } else {
                    tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + type.get() + "." + Topping.retrieve(p, type)).withStyle(TextFormatting.YELLOW));
                }
                if (!type.equals(Topping.CRUST)) {
                    toppings++;
                }
            } else if (type.equals(Topping.CRUST)){
                tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + Topping.CRUST.get() + ".none").withStyle(TextFormatting.YELLOW));
            }
        }
        if (toppings == 0){
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + Topping.TOPPING.get() + ".none").withStyle(TextFormatting.YELLOW));
        }
    }
}
