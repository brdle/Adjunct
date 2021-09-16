package net.onvoid.adjunct.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.AdjunctItems;

import javax.annotation.Nonnull;

public class PizzaOvenRecipeCategory implements IRecipeCategory<PizzaOvenRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Adjunct.MODID, "pizza_oven");
    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final IDrawable icon;

    public PizzaOvenRecipeCategory(IGuiHelper guiHelper){
        this.background = guiHelper.createDrawable(new ResourceLocation(Adjunct.MODID, "textures/jei/pizza_oven.png"), 0, 0, 80, 28);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(AdjunctItems.PIZZA_OVEN_ITEM.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends PizzaOvenRecipe> getRecipeClass() {
        return PizzaOvenRecipe.class;
    }

    @Override
    @Deprecated
    public String getTitle() {
        return new TranslationTextComponent(Adjunct.MODID + ":jei.pizza_oven").getString();
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
    public void setIngredients(PizzaOvenRecipe recipe, IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipe.getInput());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout layout, PizzaOvenRecipe recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = layout.getItemStacks();
        guiItemStacks.init(0, true, 5, 5);
        guiItemStacks.init(1, false, 57, 5);
        guiItemStacks.set(ingredients);
    }
}
