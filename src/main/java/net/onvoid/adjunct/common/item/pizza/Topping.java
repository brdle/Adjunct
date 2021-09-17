package net.onvoid.adjunct.common.item.pizza;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.onvoid.adjunct.Adjunct;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Collections;

public enum Topping {
    CRUST,
    SAUCE,
    CHEESE,
    TOPPING;

    private static Tags.IOptionalNamedTag<Item> allTag;
    private static Tags.IOptionalNamedTag<Item> crustTag;
    private static Tags.IOptionalNamedTag<Item> sauceTag;
    private static Tags.IOptionalNamedTag<Item> cheeseTag;
    private static Tags.IOptionalNamedTag<Item> toppingTag;
    public static ArrayList<String> allKeys = new ArrayList<String>();

    public static int size() {
        return allKeys.size();
    }

    public static void registerTags(){
        allTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza"));
        crustTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/crust"));
        sauceTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/sauce"));
        cheeseTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/cheese"));
        toppingTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/topping"));
    }

    public static void initKeys(){
        for (Topping top : Topping.values()){
            String name = top.get();
            if (name.equals("topping")){
                allKeys.add(top.get() + "1");
                allKeys.add(top.get() + "2");
            } else {
                allKeys.add(top.get());
            }
        }
    }

    public static ITag<Item> allTag(){
        return allTag;
    }

    public String get(){
        return super.name().toLowerCase();
    }

    public static ArrayList<String> crusts = new ArrayList<String>() {
        public boolean add(String s) {
            super.add(s);
            // toppings.txt should be sorted in same fashion
            Collections.sort(crusts);
            return true;
        }
    };
    public static ArrayList<String> sauces = new ArrayList<String>() {
        public boolean add(String s) {
            super.add(s);
            // toppings.txt should be sorted in same fashion
            Collections.sort(sauces);
            return true;
        }
    };
    public static ArrayList<String> cheeses = new ArrayList<String>() {
        public boolean add(String s) {
            super.add(s);
            // toppings.txt should be sorted in same fashion
            Collections.sort(cheeses);
            return true;
        }
    };
    public static ArrayList<String> toppings = new ArrayList<String>() {
        public boolean add(String s) {
            super.add(s);
            // toppings.txt should be sorted in same fashion
            Collections.sort(toppings);
            return true;
        }
    };

    public static void register(Topping type, String topping){
        switch (type){
            case CRUST:
                if (crusts.contains(topping)){
                    return;
                }
                crusts.add(topping);
                return;
            case SAUCE:
                if (sauces.contains(topping)){
                    return;
                }
                sauces.add(topping);
                return;
            case CHEESE:
                if (cheeses.contains(topping)){
                    return;
                }
                cheeses.add(topping);
                return;
            case TOPPING:
                if (toppings.contains(topping)){
                    return;
                }
                toppings.add(topping);
                return;
        }
    }

    public static int fromStr(Topping type, String topping){
        switch (type){
            case CRUST:
                if (!crusts.contains(topping)){
                    return -1;
                }
                return crusts.indexOf(topping) + 1;
            case SAUCE:
                if (!sauces.contains(topping)){
                    return -1;
                }
                return sauces.indexOf(topping) + 1;
            case CHEESE:
                if (!cheeses.contains(topping)){
                    return -1;
                }
                return cheeses.indexOf(topping) + 1;
            case TOPPING:
                if (!toppings.contains(topping)){
                    return -1;
                }
                return toppings.indexOf(topping) + 1;
        }
        return -1;
    }

    public static String fromInt(Topping type, int topping){
        switch (type){
            case CRUST:
                if (crusts.size() < topping){
                    return "";
                }
                return crusts.get(topping - 1);
            case SAUCE:
                if (sauces.size() < topping){
                    return "";
                }
                return sauces.get(topping - 1);
            case CHEESE:
                if (cheeses.size() < topping){
                    return "";
                }
                return cheeses.get(topping - 1);
            case TOPPING:
                if (toppings.size() < topping){
                    return "";
                }
                return toppings.get(topping - 1);
        }
        return "";
    }

    public static String retrieve(Pizza p, Topping type){
        if (!p.has(type)){
            return "";
        }
       return Topping.fromInt(type, p.get(type));
    }

    public static String retrieveTopping(Pizza p, int number){
        if (!p.has(Topping.TOPPING)){
            return "";
        }
        if (!p.hasTopping(number)){
            return "";
        }
        return Topping.fromInt(Topping.TOPPING, p.getTopping(number));
    }

    public static ITag<Item> getTag(Topping type){
        // ex: "data/adjunct/tags/items/pizza/cheese"
        // ex:                           ..."/sauce"
        // ex:                           ..."/topping"
        switch (type){
            case CRUST:
                return crustTag;
            case SAUCE:
                return sauceTag;
            case CHEESE:
                return cheeseTag;
            case TOPPING:
                return toppingTag;
        }
        return null;
    }

    public static ITag<Item> getTag(Topping type, String topping){
        // ex: "data/adjunct/tags/items/pizza/cheese/cheese"
        // ex:                           ..."/cheese/vegan"
        // ex:                           ..."/sauce/tomato"
        // PROBABLY NEEDS TO BE FIXED VVV
        return ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(Adjunct.MODID, "pizza/" + type.get() + "/" + topping));
    }

    public static boolean is(ItemStack stack, Topping type){
        return stack.getItem().is(getTag(type));
    }

    public static boolean is(ItemStack stack, Topping type, String topping){
        return stack.getItem().is(getTag(type, topping));
    }

    public static Topping parse(ItemStack stack){
        for (Topping type : Topping.values()){
            if (is(stack, type)){
                return type;
            }
        }
        return Topping.TOPPING;
    }

    public static int getSpecificInt(Topping type, ItemStack topping){
        if (is(topping, CRUST)){
            for (int specific = 0; specific < crusts.size(); specific++){
                if (is(topping, CRUST, crusts.get(specific))){
                    return specific + 1;
                }
            }
        } else if (is(topping, SAUCE)){
            for (int specific = 0; specific < sauces.size(); specific++){
                if (is(topping, SAUCE, sauces.get(specific))){
                    return specific + 1;
                }
            }
        } else if (is(topping, CHEESE)){
            for (int specific = 0; specific < cheeses.size(); specific++){
                if (is(topping, CHEESE, cheeses.get(specific))){
                    return specific + 1;
                }
            }
        } else if (is(topping, TOPPING)){
            for (int specific = 0; specific < toppings.size(); specific++){
                if (is(topping, TOPPING, toppings.get(specific))){
                    return specific + 1;
                }
            }
        }
        return -1;
    }
}
