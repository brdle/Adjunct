package net.onvoid.adjunct.compat.jei;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PizzaStationRecipe {
    private final ItemStack base;
    private final List<ItemStack> addons;
    private final ItemStack output;

    public PizzaStationRecipe(ItemStack base, List<ItemStack> addons, ItemStack output) {
        this.base = base;
        this.addons = addons;
        this.output = output;
    }

    public ItemStack getBase() {
        return this.base;
    }

    public List<ItemStack> getAddons(){
        return this.addons;
    }

    public ItemStack getOutput() {
        return this.output;
    }
}
