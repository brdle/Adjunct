package net.onvoid.adjunct.compat.jei;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class PizzaStationRecipe {
    private final List<List<ItemStack>> inputs;
    private final List<List<ItemStack>> outputs;

    public PizzaStationRecipe(List<ItemStack> leftInput, List<ItemStack> rightInputs, List<ItemStack> outputs) {
        this.inputs = ImmutableList.of(leftInput, rightInputs);
        this.outputs = Collections.singletonList(outputs);
    }

    public List<List<ItemStack>> getInputs() {
        return inputs;
    }

    public List<List<ItemStack>> getOutputs() {
        return outputs;
    }
}
