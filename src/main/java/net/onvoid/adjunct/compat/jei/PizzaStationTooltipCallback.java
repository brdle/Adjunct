package net.onvoid.adjunct.compat.jei;

import mezz.jei.api.gui.ingredient.ITooltipCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.onvoid.adjunct.common.item.pizza.Topping;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PizzaStationTooltipCallback implements ITooltipCallback<ItemStack> {
    public PizzaStationTooltipCallback(){
        super();
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<ITextComponent> tooltip) {
        if (slotIndex == 1 && input && ingredient != ItemStack.EMPTY){
            tooltip.add(new StringTextComponent(StringUtils.capitalize(Topping.parse(ingredient).get())).withStyle(TextFormatting.GREEN));
        }
    }
}
