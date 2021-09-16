package net.onvoid.adjunct.compat.jei;

import com.google.common.collect.ImmutableList;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.AdjunctItems;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PizzaStationRecipeCategory implements IRecipeCategory<PizzaStationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Adjunct.MODID, "pizza_station");
    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final IDrawable icon;

    public PizzaStationRecipeCategory(IGuiHelper guiHelper){
        this.background = guiHelper.createDrawable(new ResourceLocation(Adjunct.MODID, "textures/jei/pizza_station.png"), 0, 0, 104, 28);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(AdjunctItems.PIZZA_STATION_ITEM.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends PizzaStationRecipe> getRecipeClass() {
        return PizzaStationRecipe.class;
    }

    @Override
    @Deprecated
    public String getTitle() {
        return new TranslationTextComponent(Adjunct.MODID + ":jei.pizza_station").getString();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(PizzaStationRecipe recipe, IIngredients ingredients) {
        if (recipe.getBase().equals(ItemStack.EMPTY)){
            ingredients.setInputs(VanillaTypes.ITEM, recipe.getAddons());
        } else {
            ingredients.setInputLists(VanillaTypes.ITEM, ImmutableList.of(Collections.singletonList(recipe.getBase()), recipe.getAddons()));
        }
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout layout, PizzaStationRecipe recipe, IIngredients ingredients){
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();

        guiItemStacks.init(0, true, 5, 5);
        guiItemStacks.init(1, true, 29, 5);
        guiItemStacks.init(2, false, 81, 5);

        //guiItemStacks.set(1, new ItemStack(Items.BAKED_POTATO));
        //if (!ingredients.getInputs(VanillaTypes.ITEM).get(0).get(0).equals(ItemStack.EMPTY)) {
        //    guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0).get(0));
        //}
        if (ingredients.getInputs(VanillaTypes.ITEM).size() > 1) {
            guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
            guiItemStacks.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
        } else {
            guiItemStacks.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        }
        guiItemStacks.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0).get(0));
    }
}
