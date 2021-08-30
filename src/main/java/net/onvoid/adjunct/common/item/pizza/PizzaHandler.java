package net.onvoid.adjunct.common.item.pizza;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class PizzaHandler {

    public static final int COOK_TIME = 10;
    private static ArrayList<ItemStack> allUncookedPizzas = new ArrayList<ItemStack>();
    private static ArrayList<ItemStack> allCookedPizzas = new ArrayList<ItemStack>();


    public static void createTags(){
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

    /*public static void createITags(){
        ingredientsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/ingredients"));
        crustsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/crusts"));
        saucesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/sauces"));
        cheesesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/cheeses"));
        toppingsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/toppings"));
        originalDoughsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/original_doughs"));
        glutenFreeDoughsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/gluten_free_doughs"));
        blazeDoughsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/blaze_doughs"));
        tomatoSaucesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/tomato_sauces"));
        whiteSaucesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/white_sauces"));
        mozzarellaCheesesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/mozzarella_cheeses"));
        veganCheesesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/vegan_cheeses"));
        pepperonisTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/pepperonis"));
        hamsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/hams"));
        pineapplesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/pineapples"));
        olivesTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/olives"));
        peppersTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/peppers"));
        onionsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/onions"));
        mushroomsTag = ItemTags.createOptional(new ResourceLocation(Adjunct.MODID, "pizza/mushrooms"));
    }*/


    /*public static ITag<Item> getCrustsTag(){
        return crustsTag;
    }

    public static ITag<Item> getSaucesTag() { return saucesTag; }

    public static ITag<Item> getCheesesTag(){
        return cheesesTag;
    }

    public static ITag<Item> getToppingsTag(){
        return toppingsTag;
    }

    public static ITag<Item> getOriginalDoughsTag(){
        return originalDoughsTag;
    }

    public static ITag<Item> getGlutenFreeDoughsTag(){
        return glutenFreeDoughsTag;
    }

    public static ITag<Item> getBlazeDoughsTag(){
        return blazeDoughsTag;
    }

    public static ITag<Item> getTomatoSaucesTag(){
        return tomatoSaucesTag;
    }

    public static ITag<Item> getWhiteSaucesTag(){
        return whiteSaucesTag;
    }

    public static ITag<Item> getMozzarellaCheesesTag(){
        return mozzarellaCheesesTag;
    }

    public static ITag<Item> getVeganCheesesTag(){
        return veganCheesesTag;
    }

    public static ITag<Item> getPepperonisTag() { return pepperonisTag; }

    public static ITag<Item> getHamsTag() { return hamsTag; }

    public static ITag<Item> getPineapplesTag() { return pineapplesTag; }

    public static ITag<Item> getOlivesTag() { return olivesTag; }

    public static ITag<Item> getPeppersTag() { return peppersTag; }

    public static ITag<Item> getOnionsTag() { return onionsTag; }

    public static ITag<Item> getMushroomsTag() { return mushroomsTag; }*/

    public static ArrayList<ItemStack> getAllUncookedPizzas() {
        return allUncookedPizzas;
    }

    public static ArrayList<ItemStack> getAllCookedPizzas() {
        return allCookedPizzas;
    }

    /*public static void createPizzaLists(){
        for (int crust = 1; crust < getCrusts().size() + 1; crust++){
            for (int sauce = 1; sauce < getSauces().size() + 1; sauce++){
                for (int cheese = 1; cheese < getCheeses().size() + 1; cheese++){
                    for (int topping1 = 1; topping1 < getToppings().size() + 1; topping1++){
                        for (int topping2 = 1; topping2 < getToppings().size() + 1; topping2++){
                            if (topping1 == topping2){
                                continue;
                            }
                            allUncookedPizzas.add(buildPizza(crust, sauce, cheese, topping1, topping2, false));
                            allCookedPizzas.add(buildPizza(crust, sauce, cheese, topping1, topping2, true));
                        }
                    }
                }
            }
        }
    }*/

    /*public static ItemStack buildPizza(int crust, int sauce, int cheese, int topping1, int topping2, boolean cooked){
        ItemStack pizza = new ItemStack(AdjunctItems.PIZZA_ITEM.get());
        CompoundNBT nbt = pizza.getOrCreateTag();
        nbt.putBoolean("cooked", cooked);
        nbt.putInt("crust", crust);
        nbt.putInt("sauce", sauce);
        nbt.putInt("cheese", cheese);
        nbt.putInt("topping1", topping1);
        nbt.putInt("topping2", topping2);
        return pizza;
    }

    public static int getCrustFromItem(ItemStack stack){
        if (!PizzaHandler.isCrust(stack)){
            return 0;
        }
        if (PizzaHandler.isOriginalDough(stack)){
            return getCrusts().indexOf("original") + 1;
        } else if (PizzaHandler.isGlutenFreeDough(stack)){
            return getCrusts().indexOf("gluten_free") + 1;
        } else if (PizzaHandler.isBlazeDough(stack)){
            return getCrusts().indexOf("blaze") + 1;
         }
        return 0;
    }

    public static int getSauceFromItem(ItemStack stack){
        if (!PizzaHandler.isSauce(stack)){
            return 0;
        }
        if (PizzaHandler.isTomatoSauce(stack)){
            return getSauces().indexOf("tomato") + 1;
        } else if (PizzaHandler.isWhiteSauce(stack)){
            return getSauces().indexOf("white") + 1;
        }
        return 0;
    }

    public static int getCheeseFromItem(ItemStack stack){
        if (!PizzaHandler.isCheese(stack)){
            return 0;
        }
        if (PizzaHandler.isMozzarellaCheese(stack)){
            return getCheeses().indexOf("cheese") + 1;
        } else if (PizzaHandler.isVeganCheese(stack)){
            return getCheeses().indexOf("vegan_cheese") + 1;
        }
        return 0;
    }

    public static int getToppingFromItem(ItemStack stack){
        if (!PizzaHandler.isTopping(stack)){
            return 0;
        }
        if (PizzaHandler.isPepperoni(stack)){
            return getToppings().indexOf("pepperoni") + 1;
        } else if (PizzaHandler.isHam(stack)){
            return getToppings().indexOf("ham") + 1;
        } else if (PizzaHandler.isPineapple(stack)) {
            return getToppings().indexOf("pineapple") + 1;
        } else if (PizzaHandler.isOlives(stack)){
            return getToppings().indexOf("olives") + 1;
        } else if (PizzaHandler.isPeppers(stack)){
            return getToppings().indexOf("peppers") + 1;
        } else if (PizzaHandler.isOnions(stack)){
            return getToppings().indexOf("onions") + 1;
        } else if (PizzaHandler.isMushrooms(stack)) {
            return getToppings().indexOf("mushrooms") + 1;
        }
        return 0;
    }

    public static boolean isIngredient(ItemStack stack) {
        return stack.getItem().is(getIngredientsTag());
    }

    public static boolean isCrust(ItemStack stack) {
        return stack.getItem().is(getCrustsTag());
    }

    public static boolean isSauce(ItemStack stack) {
        return stack.getItem().is(getSaucesTag());
    }

    public static boolean isCheese(ItemStack stack) {
        return stack.getItem().is(getCheesesTag());
    }

    public static boolean isTopping(ItemStack stack) {
        return stack.getItem().is(getToppingsTag());
    }

    public static boolean isOriginalDough(ItemStack stack) {
        return stack.getItem().is(getOriginalDoughsTag());
    }

    public static boolean isGlutenFreeDough(ItemStack stack) {
        return stack.getItem().is(getGlutenFreeDoughsTag());
    }

    public static boolean isBlazeDough(ItemStack stack) {
        return stack.getItem().is(getBlazeDoughsTag());
    }

    public static boolean isTomatoSauce(ItemStack stack) {
        return stack.getItem().is(getTomatoSaucesTag());
    }

    public static boolean isWhiteSauce(ItemStack stack) {
        return stack.getItem().is(getWhiteSaucesTag());
    }

    public static boolean isMozzarellaCheese(ItemStack stack) {
        return stack.getItem().is(getMozzarellaCheesesTag());
    }

    public static boolean isVeganCheese(ItemStack stack) {
        return stack.getItem().is(getVeganCheesesTag());
    }

    public static boolean isPepperoni(ItemStack stack) {
        return stack.getItem().is(getPepperonisTag());
    }

    public static boolean isHam(ItemStack stack) {
        return stack.getItem().is(getHamsTag());
    }

    public static boolean isPineapple(ItemStack stack) {
        return stack.getItem().is(getPineapplesTag());
    }

    public static boolean isOlives(ItemStack stack) {
        return stack.getItem().is(getOlivesTag());
    }

    public static boolean isPeppers(ItemStack stack) {
        return stack.getItem().is(getPeppersTag());
    }

    public static boolean isOnions(ItemStack stack) {
        return stack.getItem().is(getOnionsTag());
    }

    public static boolean isMushrooms(ItemStack stack) {
        return stack.getItem().is(getMushroomsTag());
    }

    public static boolean hasType(CompoundNBT pizza, String type){
        if (pizza.contains(type) && pizza.getInt(type) > 0){
            return true;
        }
        return false;
    }

    public static void addItem(CompoundNBT pizza, String type, ItemStack addition){
        switch (type) {
            case "sauce":
                pizza.putInt(type, getSauceFromItem(addition));
                break;
            case "cheese":
                pizza.putInt(type, getCheeseFromItem(addition));
                break;
            case "topping1":
            case "topping2":
                pizza.putInt(type, getToppingFromItem(addition));
                break;
            default:
                break;
        }
    }

    public static void addItem(CompoundNBT pizza, String type, int addition){
        pizza.putInt(type, addition);
    }

    public static boolean addItemSafe(CompoundNBT pizza, ItemStack addition) {
        if (isSauce(addition) && !hasType(pizza, "sauce")){
            addItem(pizza, "sauce", addition);
            return true;
        } else if (isCheese(addition) && !hasType(pizza, "cheese")){
            addItem(pizza, "cheese", addition);
            return true;
        } else if (isTopping(addition)) {
            int toppingItem = getToppingFromItem(addition);
            //if (!pizza.contains("topping1") || pizza.getInt("topping1") <= 0) {
            if (!hasType(pizza,"topping1")){
                addItem(pizza, "topping1", addition);
                return true;
            } else if (!hasType(pizza, "topping2") ){
                if (toppingItem < pizza.getInt("topping1")) {
                    //Ensure toppings are in correct order
                    addItem(pizza, "topping2", pizza.getInt("topping1"));
                    addItem(pizza, "topping1", addition);
                } else {
                    if (toppingItem == pizza.getInt("topping1")){
                        //Ensure toppings are not the same
                        return false;
                    }
                    addItem(pizza, "topping2", addition);
                }
                return true;
            }
        }
        return false;
    }*/
}
