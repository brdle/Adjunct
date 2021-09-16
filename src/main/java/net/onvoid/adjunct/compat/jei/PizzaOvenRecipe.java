package net.onvoid.adjunct.compat.jei;

import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class PizzaOvenRecipe {
    private final ItemStack input;
    private final ItemStack output;

    public PizzaOvenRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }
}
