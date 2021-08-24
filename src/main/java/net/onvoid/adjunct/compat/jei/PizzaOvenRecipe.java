package net.onvoid.adjunct.compat.jei;

import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class PizzaOvenRecipe {
    private final List<List<ItemStack>> inputs;
    private final List<List<ItemStack>> outputs;

    public PizzaOvenRecipe(List<ItemStack> inputs, List<ItemStack> outputs) {
        this.inputs = Collections.singletonList(inputs);
        this.outputs = Collections.singletonList(outputs);
    }

    public List<List<ItemStack>> getInputs() {
        return inputs;
    }

    public List<List<ItemStack>> getOutputs() {
        return outputs;
    }
}
