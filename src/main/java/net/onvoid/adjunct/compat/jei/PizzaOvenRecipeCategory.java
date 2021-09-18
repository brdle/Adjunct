package net.onvoid.adjunct.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.constants.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.AdjunctHelper;
import net.onvoid.adjunct.common.item.AdjunctItems;
import net.onvoid.adjunct.common.item.pizza.PizzaHandler;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class PizzaOvenRecipeCategory implements IRecipeCategory<PizzaOvenRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Adjunct.MODID, "pizza_oven");
    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final IDrawable icon;
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;

    public PizzaOvenRecipeCategory(IGuiHelper guiHelper){
        this.background = guiHelper.createDrawable(AdjunctJEIPlugin.PIZZA_TEXTURE, 0, 0, 92, 49);
        this.staticFlame = guiHelper.createDrawable(AdjunctJEIPlugin.PIZZA_TEXTURE, 92, 0, 13, 13);
        this.animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 200, IDrawableAnimated.StartDirection.TOP, true);
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
    public void draw(PizzaOvenRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        animatedFlame.draw(matrixStack, 6, 25);
        drawCookTime(recipe, matrixStack, 38);
    }

    protected void drawCookTime(PizzaOvenRecipe recipe, MatrixStack poseStack, int y) {
        IFormattableTextComponent timeString = new StringTextComponent(Integer.toString(PizzaHandler.COOK_TIME)).append(new TranslationTextComponent(Adjunct.MODID + ":desc.seconds_abbreviated"));
        FontRenderer fontRenderer = Minecraft.getInstance().font;
        fontRenderer.draw(poseStack, timeString, background.getWidth() - fontRenderer.width(timeString) - 5, y, 0xFFFFFF);
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
        guiItemStacks.init(1, false, 65, 13);
        guiItemStacks.set(ingredients);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(PizzaOvenRecipe recipe, double mouseX, double mouseY) {
        if (mouseX > 29 && mouseX < 52 && mouseY > 13 && mouseY < 29){
            return AdjunctHelper.tt(TextFormatting.GREEN, "All Pizzas can", "be baked");
        } else if (mouseX > 5 && mouseX < 20 && mouseY > 24 && mouseY < 39){
            return AdjunctHelper.tt(TextFormatting.GREEN, "Oven must", "be fueled!");
        }
        return Collections.emptyList();
    }
}
