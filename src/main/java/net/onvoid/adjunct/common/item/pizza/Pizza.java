package net.onvoid.adjunct.common.item.pizza;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.AdjunctItems;

import java.util.List;

public class Pizza {
    private ItemStack pizzaStack = null;

    public Pizza() {
        this.pizzaStack = new ItemStack(AdjunctItems.PIZZA_ITEM.get(), 1);
        this.getNbt().putBoolean("cooked", false);
        this.pizzaStack.setTag(this.getNbt());
    }

    public Pizza(ItemStack stack, boolean pizza){
        this.pizzaStack = new ItemStack(AdjunctItems.PIZZA_ITEM.get(), 1);
        if (pizza && stack.hasTag() && stack.getItem() instanceof PizzaItem) {
            this.pizzaStack.setTag(stack.getOrCreateTag().copy());
        } else if (!pizza && Topping.is(stack, Topping.CRUST)){
            this.getNbt().putInt(Topping.CRUST.get(), Topping.getSpecificInt(Topping.CRUST, stack));
            this.uncooked();
        }
        this.pizzaStack.setTag(this.getNbt());
    }

    public ItemStack buildStack(){
        this.getItemStack().setTag(this.getNbt());
        return this.getItemStack().copy();
    }

    public ItemStack bakeStack(){
        return this.cooked().buildStack();
    }

    public ItemStack getItemStack(){
        return this.pizzaStack;
    }

    public int getNutrition(){
        int nutrition = 2;
        for (Topping type : Topping.values()){
            if (this.has(type)) {
                nutrition += 2;
            }
        }
        return this.isCooked() ? nutrition : nutrition / 2;
    }

    public float getSaturation(){
        float saturation = 0.2f;
        for (Topping type : Topping.values()){
            if (this.has(type)) {
                saturation += 0.05f;
            }
        }
        return this.isCooked() ? saturation : saturation / 2.0f;
    }

    public Pizza cooked(){
        this.getNbt().putBoolean("cooked", true);
        return this;
    }

    public Pizza uncooked(){
        this.getNbt().putBoolean("cooked", false);
        return this;
    }

    public boolean isCooked(){
        return this.getNbt().getBoolean("cooked");
    }

    public void orderToppings(){
        int t1 = this.getTopping(1);
        int t2 = this.getTopping(2);
        if (t1 > 0 && t2 > 0){
            if (t1 > t2){
                this.getNbt().remove(Topping.TOPPING.get() + "1");
                this.getNbt().remove(Topping.TOPPING.get() + "2");
                this.getNbt().putInt(Topping.TOPPING.get() + "1", t2);
                this.getNbt().putInt(Topping.TOPPING.get() + "2", t1);
                return;
            }
        } else if (t2 > 0){
            this.getNbt().putInt(Topping.TOPPING.get() + "1", this.getTopping(2));
            this.getNbt().remove(Topping.TOPPING.get() + "2");
            return;
        }
    }

    public Pizza add(Topping type, int topping){
        if (type.equals(Topping.TOPPING)){
            boolean hasT1 = this.hasTopping(1);
            boolean hasT2 = this.hasTopping(2);
            if (!hasT1 && !hasT2){
                // No toppings
                this.getNbt().putInt(type.get() + "1", topping);
            } else if (hasT1 && !hasT2 && this.getTopping(1) != topping){
                // Only topping 1
                this.getNbt().putInt(type.get() + "2", topping);
                this.orderToppings();
            } else if (hasT2 && !hasT1 && this.getTopping(2) != topping){
                // Only topping 2 (Shouldn't be possible, but...)
                this.getNbt().putInt(type.get() + "1", topping);
                this.orderToppings();
            }
        } else if (!this.has(type)) {
            this.getNbt().putInt(type.get(), topping);
        }
        return this;
    }

    public Pizza add(Topping type, String topping){
        return this.add(type, Topping.fromStr(type, topping));
    }

    public boolean addStack(Topping type, ItemStack topping){
        if (type != Topping.CRUST && topping.getItem().is(Topping.getTag(type))){
            int toppingNum = Topping.getSpecificInt(type, topping);
            if (this.has(type, toppingNum)){
                return false;
            }
            this.add(type, toppingNum);
            return true;
        }
        return false;
    }

    public boolean addStack(ItemStack topping){
        for (Topping type : Topping.values()){
            if (type != Topping.CRUST && Topping.is(topping, type)) {
                int toppingNum = Topping.getSpecificInt(type, topping);
                if (this.has(type, toppingNum)){
                    return false;
                }
                this.add(type, toppingNum);
                return true;
            }
        }
        return false;
    }

    public boolean has(Topping type){
        // Check if the Pizza has topping in 1 or 2 slot
        if (type.equals(Topping.TOPPING)){
            return (this.hasTopping(1) || this.hasTopping(2));
        }
        // Checks if the Pizza has topping type data at all
        if (!this.getNbt().contains(type.get())){
            return false;
        }
        // Checks if the Pizza has specific topping
        return this.getNbt().getInt(type.get()) > 0;
    }

    public boolean has(Topping type, int topping){
        if (type.equals(Topping.TOPPING)){
            if (this.hasTopping(1)){
                return this.getNbt().getInt(Topping.TOPPING.get() + "1") == topping;
            } else if (this.hasTopping(2)){
                return this.getNbt().getInt(Topping.TOPPING.get() + "2") == topping;
            }
            return false;
        }
        if (!this.getNbt().contains(type.get())){
            return false;
        }
        return this.getNbt().getInt(type.get()) == topping;
    }

    public boolean has(Topping type, String topping){
        if (type.equals(Topping.TOPPING)){
            if (this.hasTopping(1)){
                return Topping.fromInt(type, this.getNbt().getInt(Topping.TOPPING.get() + "1")).equals(topping);
            } else if (this.hasTopping(2)){
                return Topping.fromInt(type, this.getNbt().getInt(Topping.TOPPING.get() + "2")).equals(topping);
            }
            return false;
        }
        if (!this.getNbt().contains(type.get())){
            return false;
        }
        return topping.equals(Topping.fromInt(type, this.getNbt().getInt(type.get())));
    }

    public boolean hasTopping(int number){
        return this.getNbt().contains(Topping.TOPPING.get() + number) && this.getNbt().getInt(Topping.TOPPING.get() + number) > 0;
    }

    public int get(Topping type){
        if (!this.has(type)){
            return -1;
        }
        if (type.equals(Topping.TOPPING)){
            return this.getTopping(1);
        }
        return this.getNbt().getInt(type.get());
    }

    public int getTopping(int number){
        if (!this.getNbt().contains(Topping.TOPPING.get() + number)){
            return -1;
        }
        return this.getNbt().getInt(Topping.TOPPING.get() + number);
    }

    public CompoundNBT getNbt() {
        return this.pizzaStack.getOrCreateTag();
    }

    public ITag<Item> getHighestTag(){
        if (this.hasTopping(2)){
            return ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(Adjunct.MODID, "pizza/topping/" + Topping.fromInt(Topping.TOPPING, this.getTopping(2))));
        } else if (this.hasTopping(1)){
            return ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(Adjunct.MODID, "pizza/topping/" + Topping.fromInt(Topping.TOPPING, this.getTopping(1))));
        } else if (this.has(Topping.CHEESE)){
            return ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(Adjunct.MODID, "pizza/cheese/" + Topping.fromInt(Topping.CHEESE, this.get(Topping.CHEESE))));
        } else if (this.has(Topping.SAUCE)){
            return ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(Adjunct.MODID, "pizza/sauce/" + Topping.fromInt(Topping.SAUCE, this.get(Topping.SAUCE))));
        } else if (this.has(Topping.CRUST)){
            return ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation(Adjunct.MODID, "pizza/crust/" + Topping.fromInt(Topping.CRUST, this.get(Topping.CRUST))));
        } else {
            return new ITag<Item>() {
                @Override
                public boolean contains(Item pElement) {
                    return false;
                }

                @Override
                public List<Item> getValues() {
                    return null;
                }
            };
        }
    }

    public ItemStack getLesserPizza(){
        ItemStack stack = this.buildStack();
        if (this.hasTopping(2)){
            stack.getOrCreateTag().remove("topping2");
            return stack;
        } else if (this.hasTopping(1)){
            stack.getOrCreateTag().remove("topping1");
            return stack;
        } else if (this.has(Topping.CHEESE)){
            stack.getOrCreateTag().remove("cheese");
            return stack;
        } else if (this.has(Topping.SAUCE)){
            stack.getOrCreateTag().remove("sauce");
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public int getNumToppings(){
        int num = 0;
        if (this.hasTopping(2)){
            num++;
        }
        if (this.hasTopping(1)){
            num++;
        }
        if (this.has(Topping.CHEESE)){
            num++;
        }
        if (this.has(Topping.SAUCE)){
            num++;
        }
        if (this.has(Topping.CRUST)){
            num++;
        }
        return num;
    }

    public boolean isFull(){
        return this.getNumToppings() == Topping.size();
    }
}
