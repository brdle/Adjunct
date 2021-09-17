package net.onvoid.adjunct.compat.jei;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.AdjunctItems;
import net.onvoid.adjunct.common.item.pizza.Pizza;
import net.onvoid.adjunct.common.item.pizza.PizzaHandler;
import net.onvoid.adjunct.common.item.pizza.Topping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JeiPlugin
public class AdjunctJEIPlugin implements IModPlugin {
    public static IJeiHelpers jeiHelper;
    private static final ResourceLocation UID = new ResourceLocation(Adjunct.MODID, "jei");
    private static ArrayList<PizzaStationRecipe> pizzaStationRecipes = new ArrayList<PizzaStationRecipe>();
    private static ArrayList<PizzaOvenRecipe> pizzaOvenRecipes = new ArrayList<PizzaOvenRecipe>();

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        IModPlugin.super.registerItemSubtypes(registration);
        registration.useNbtForSubtypes(AdjunctItems.PIZZA_ITEM.get());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IModPlugin.super.registerCategories(registration);
        registration.addRecipeCategories(new PizzaStationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PizzaOvenRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IModPlugin.super.registerRecipes(registration);
        if (PizzaHandler.getAllUncookedPizzas().isEmpty()) {
            PizzaHandler.createPizzaLists();
        }
        // Hide error pizza from JEI
        //registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM, Collections.singletonList(new ItemStack(AdjunctItems.PIZZA_ITEM.get())));
        List<ItemStack> uncooked = PizzaHandler.getAllUncookedPizzas();
        List<ItemStack> cooked = PizzaHandler.getAllCookedPizzas();
        // PIZZA STATION
        for (int i = 0; i < uncooked.size(); i++){
            ItemStack outputStack = uncooked.get(i);
            Pizza output = new Pizza(outputStack, true);
            ItemStack lesser = output.getLesserPizza();
            List<ItemStack> addonStacks = new ArrayList<ItemStack>();
            for (Item item : output.getHighestTag().getValues()){
                addonStacks.add(new ItemStack(item));
            }
            //if (lesser.equals(ItemStack.EMPTY)){
            //    pizzaStationRecipes.add(new PizzaStationRecipe(ItemStack.EMPTY, addonStacks, outputStack));
            //} else
                if (!outputStack.equals(ItemStack.EMPTY) && !addonStacks.isEmpty()) {
                pizzaStationRecipes.add(new PizzaStationRecipe(lesser, addonStacks, outputStack));
            }
        }
        registration.addRecipes(pizzaStationRecipes, PizzaStationRecipeCategory.UID);
        // PIZZA OVEN
        for (int i = 0; i < uncooked.size(); i++){
            pizzaOvenRecipes.add(new PizzaOvenRecipe(uncooked.get(i), cooked.get(i)));
        }
        registration.addRecipes(pizzaOvenRecipes, PizzaOvenRecipeCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        IModPlugin.super.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(new ItemStack(AdjunctItems.PIZZA_STATION_ITEM.get()), PizzaStationRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(AdjunctItems.PIZZA_OVEN_ITEM.get()), PizzaOvenRecipeCategory.UID);
    }
}
