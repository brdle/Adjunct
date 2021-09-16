package net.onvoid.adjunct.common.item.pizza;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PizzaHandler {

    public static final int COOK_TIME = 10;
    private static ArrayList<ItemStack> allUncookedPizzas = new ArrayList<ItemStack>();
    private static ArrayList<ItemStack> allCookedPizzas = new ArrayList<ItemStack>();

    public static void registerToppings(){
        Topping.register(Topping.CRUST, "original");
        Topping.register(Topping.CRUST, "gluten_free");
        Topping.register(Topping.CRUST, "blaze");
        Topping.register(Topping.SAUCE, "tomato");
        Topping.register(Topping.SAUCE, "white");
        Topping.register(Topping.CHEESE, "cheese");
        Topping.register(Topping.CHEESE, "vegan");
        Topping.register(Topping.TOPPING, "pepperoni");
        Topping.register(Topping.TOPPING, "ham");
        Topping.register(Topping.TOPPING, "pineapple");
        Topping.register(Topping.TOPPING, "olives");
        Topping.register(Topping.TOPPING, "peppers");
        Topping.register(Topping.TOPPING, "onions");
        Topping.register(Topping.TOPPING, "mushrooms");
    }

    public static ArrayList<ItemStack> getAllUncookedPizzas() {
        return allUncookedPizzas;
    }

    public static ArrayList<ItemStack> getAllCookedPizzas() {
        return allCookedPizzas;
    }

    public static ArrayList<ArrayList<Integer>> createPairs() {
        ArrayList<ArrayList<Integer>> pairs = new ArrayList<ArrayList<Integer>>();
        for (int i = 1; i < Topping.toppings.size(); i++) {
            for (int j = i + 1; j < Topping.toppings.size() + 1 ; j++) {
                if (i == j){
                    continue;
                }
                pairs.add(new ArrayList<Integer>(Arrays.asList(i, j)));
            }
        }
        return pairs;
    }

    private static void addtoLists(Pizza p){
        allUncookedPizzas.add(p.buildStack());
        allCookedPizzas.add(p.bakeStack());
    }

    public static void createPizzaLists(){
        ArrayList<ArrayList<Integer>> pairs = createPairs();
        for (int crust = 1; crust < Topping.crusts.size() + 1; crust++){
            addtoLists(new Pizza()
                    .add(Topping.CRUST, crust));
            for (int topping = 1; topping < Topping.toppings.size() + 1; topping++){
                addtoLists(new Pizza()
                        .add(Topping.CRUST, crust)
                        .add(Topping.TOPPING, topping));
            }
            for (int sauce = 1; sauce < Topping.sauces.size() + 1; sauce++) {
                addtoLists(new Pizza()
                        .add(Topping.CRUST, crust)
                        .add(Topping.SAUCE, sauce));
            }
            for (int cheese = 1; cheese < Topping.cheeses.size() + 1; cheese++) {
                addtoLists(new Pizza()
                        .add(Topping.CRUST, crust)
                        .add(Topping.CHEESE, cheese));
            }
            for (ArrayList<Integer> pair : pairs){
                addtoLists(new Pizza()
                        .add(Topping.CRUST, crust)
                        .add(Topping.TOPPING, pair.get(0))
                        .add(Topping.TOPPING, pair.get(1)));
            }
            for (int cheese = 1; cheese < Topping.cheeses.size() + 1; cheese++) {
                for (int topping = 1; topping < Topping.toppings.size() + 1; topping++) {
                    addtoLists(new Pizza()
                            .add(Topping.CRUST, crust)
                            .add(Topping.CHEESE, cheese)
                            .add(Topping.TOPPING, topping));
                }
            }
            for (int sauce = 1; sauce < Topping.sauces.size() + 1; sauce++){
                for (int cheese = 1; cheese < Topping.cheeses.size() + 1; cheese++) {
                    addtoLists(new Pizza()
                            .add(Topping.CRUST, crust)
                            .add(Topping.SAUCE, sauce)
                            .add(Topping.CHEESE, cheese));
                }
                for (int topping = 1; topping < Topping.toppings.size() + 1; topping++) {
                    addtoLists(new Pizza()
                            .add(Topping.CRUST, crust)
                            .add(Topping.SAUCE, sauce)
                            .add(Topping.TOPPING, topping));
                }
                for (ArrayList<Integer> pair : pairs){
                    addtoLists(new Pizza()
                                .add(Topping.CRUST, crust)
                                .add(Topping.SAUCE, sauce)
                                .add(Topping.TOPPING, pair.get(0))
                                .add(Topping.TOPPING, pair.get(1)));
                }
            }
            for (int cheese = 1; cheese < Topping.cheeses.size() + 1; cheese++) {
                for (ArrayList<Integer> pair : pairs){
                        addtoLists(new Pizza()
                                .add(Topping.CRUST, crust)
                                .add(Topping.CHEESE, cheese)
                                .add(Topping.TOPPING, pair.get(0))
                                .add(Topping.TOPPING, pair.get(1)));
                }
            }
            for (int sauce = 1; sauce < Topping.sauces.size() + 1; sauce++) {
                for (int cheese = 1; cheese < Topping.cheeses.size() + 1; cheese++) {
                    for (int topping = 1; topping < Topping.toppings.size() + 1; topping++) {
                        addtoLists(new Pizza()
                                .add(Topping.CRUST, crust)
                                .add(Topping.SAUCE, sauce)
                                .add(Topping.CHEESE, cheese)
                                .add(Topping.TOPPING, topping));
                    }
                    for (ArrayList<Integer> pair : pairs){
                        addtoLists(new Pizza()
                                .add(Topping.CRUST, crust)
                                .add(Topping.SAUCE, sauce)
                                .add(Topping.CHEESE, cheese)
                                .add(Topping.TOPPING, pair.get(0))
                                .add(Topping.TOPPING, pair.get(1)));
                    }
                }
            }
        }
    }
}