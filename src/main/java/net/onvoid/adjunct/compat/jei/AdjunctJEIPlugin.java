package net.onvoid.adjunct.compat.jei;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.minecraft.item.ItemStack;
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
        List<ItemStack> bases = new ArrayList<ItemStack>();
        bases.add(new Pizza().add(Topping.CRUST, "original").buildStack());
        bases.add(new Pizza().add(Topping.CRUST, "gluten_free").buildStack());
        bases.add(new Pizza().add(Topping.CRUST, "blaze").buildStack());
        List<ItemStack> toppings = new ArrayList<ItemStack>();
        toppings.add(new ItemStack(AdjunctItems.TOMATO_SAUCE_ITEM.get()));
        toppings.add(new ItemStack(AdjunctItems.TOMATO_SAUCE_ITEM.get()));
        toppings.add(new ItemStack(AdjunctItems.WHITE_SAUCE_ITEM.get()));
        List<ItemStack> pizzas = new ArrayList<ItemStack>();
        pizzas.add(new Pizza().add(Topping.CRUST, "original").add(Topping.SAUCE, "tomato").buildStack());
        pizzas.add(new Pizza().add(Topping.CRUST, "gluten_free").add(Topping.SAUCE, "tomato").buildStack());
        pizzas.add(new Pizza().add(Topping.CRUST, "blaze").add(Topping.SAUCE, "white").buildStack());
        for (int i = 0; i < bases.size(); i++){
            pizzaStationRecipes.add(new PizzaStationRecipe(Collections.singletonList(bases.get(i)), Collections.singletonList(toppings.get(i)), Collections.singletonList(pizzas.get(i))));
        }
        registration.addRecipes(pizzaStationRecipes, PizzaStationRecipeCategory.UID);
        //
        PizzaHandler.createPizzaLists();
        List<ItemStack> uncooked = PizzaHandler.getAllUncookedPizzas();
        List<ItemStack> cooked = PizzaHandler.getAllCookedPizzas();
        for (int i = 0; i < uncooked.size(); i++){
            pizzaOvenRecipes.add(new PizzaOvenRecipe(Collections.singletonList(uncooked.get(i)), Collections.singletonList(cooked.get(i))));
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
