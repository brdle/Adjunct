package net.onvoid.adjunct.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.AdjunctItems;

import javax.annotation.Nonnull;

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
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getInputs());
        ingredients.setOutputLists(VanillaTypes.ITEM, recipe.getOutputs());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PizzaStationRecipe recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 5, 5);
        recipeLayout.getItemStacks().init(1, true, 29, 5);
        recipeLayout.getItemStacks().init(2, false, 81, 5);
        recipeLayout.getItemStacks().set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        recipeLayout.getItemStacks().set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
        recipeLayout.getItemStacks().set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
    }
}
