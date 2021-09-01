package net.onvoid.adjunct.common.item.pizza;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.onvoid.adjunct.common.item.AdjunctItems;

public class Pizza {
    private ItemStack pizzaStack;
    private CompoundNBT nbt;

    public Pizza() {
        this.pizzaStack = new ItemStack(AdjunctItems.PIZZA_ITEM.get(), 1);
        this.nbt = this.pizzaStack.getOrCreateTag();
        this.nbt.putBoolean("cooked", false);
        this.update();
    }

    public Pizza(ItemStack pizzaOrCrustStack){
        if (pizzaOrCrustStack.getItem() instanceof PizzaItem){
            this.pizzaStack = pizzaOrCrustStack;
            this.nbt = this.pizzaStack.getOrCreateTag();
            this.nbt.putBoolean("cooked", false);
        } else if (Topping.is(pizzaOrCrustStack, Topping.CRUST)){
            this.pizzaStack = new ItemStack(AdjunctItems.PIZZA_ITEM.get(), 1);
            this.nbt = this.pizzaStack.getOrCreateTag();
            this.nbt.putBoolean("cooked", false);
            this.nbt.putInt(Topping.CRUST.get(), Topping.getSpecificInt(Topping.CRUST, pizzaOrCrustStack));
        }
        this.update();
    }

    public void update(){
        this.pizzaStack.setTag(this.nbt);
    }

    public Pizza build(){
        this.update();
        return this;
    }

    public ItemStack buildStack(){
        return this.build().getItemStack();
    }

    public ItemStack bakeStack(){
        this.cooked();
        return buildStack();
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
        nbt.putBoolean("cooked", true);
        this.update();
        return this;
    }

    public Pizza uncooked(){
        nbt.putBoolean("cooked", false);
        this.update();
        return this;
    }

    public boolean isCooked(){
        return nbt.getBoolean("cooked");
    }

    public void orderToppings(){
        boolean hasT1 = this.hasTopping(1);
        boolean hasT2 = this.hasTopping(2);
        if (hasT1 && hasT2){
            int t1 = this.getTopping(1);
            int t2 = this.getTopping(2);
            if (t1 > t2){
                this.nbt.putInt(Topping.TOPPING + "1", t2);
                this.nbt.putInt(Topping.TOPPING + "2", t1);
                this.update();
                return;
            }
        } else if (hasT2){
            this.nbt.putInt(Topping.TOPPING + "1", this.getTopping(2));
            this.nbt.remove(Topping.TOPPING + "2");
            this.update();
            return;
        }
    }

    public Pizza add(Topping type, int topping){
        if (type.equals(Topping.TOPPING)){
            boolean hasT1 = this.hasTopping(1);
            boolean hasT2 = this.hasTopping(2);
            if (!hasT1 && !hasT2){
                // No toppings
                nbt.putInt(type.get() + "1", topping);
            } else if (hasT1 && !hasT2){
                // Only topping 1
                nbt.putInt(type.get() + "2", topping);
                this.orderToppings();
            } else if (hasT2 && !hasT1){
                // Only topping 2 (Shouldn't be possible, but...)
                nbt.putInt(type.get() + "1", topping);
                this.orderToppings();
            }
        } else if (!this.has(type)) {
            nbt.putInt(type.get(), topping);
        }
        return this.build();
    }

    public Pizza add(Topping type, String topping){
        return this.add(type, Topping.fromStr(type, topping));
    }

    public Pizza addStack(Topping type, ItemStack topping){
        if (type != Topping.CRUST && topping.getItem().is(Topping.getTag(type))){
            this.add(type, Topping.getSpecificInt(type, topping));
        }
        return this;
    }

    public Pizza addStack(ItemStack topping){
        for (Topping type : Topping.values()){
            if (type != Topping.CRUST && topping.getItem().is(Topping.getTag(type))) {
                this.add(type, Topping.getSpecificInt(type, topping));
            }
        }
        return this;
    }

    public boolean has(Topping type){
        // Check if the Pizza has topping in 1 or 2 slot
        if (type.equals(Topping.TOPPING)){
            if (nbt.contains(Topping.TOPPING.get() + "1")){
                return nbt.getInt(Topping.TOPPING.get() + "1") > 0;
            } else if (nbt.contains(Topping.TOPPING.get() + "2")){
                return nbt.getInt(Topping.TOPPING.get() + "2") > 0;
            }
        }
        // Checks if the Pizza has topping type data at all
        if (!nbt.contains(type.get())){
            return false;
        }
        // Checks if the Pizza has specific topping
        return nbt.getInt(type.get()) > 0;
    }

    public boolean has(Topping type, String topping){
        if (type.equals(Topping.TOPPING)){
            if (nbt.contains(Topping.TOPPING.get() + "1")){
                return Topping.fromInt(type, nbt.getInt(Topping.TOPPING.get() + "1")).equals(topping);
            } else if (nbt.contains(Topping.TOPPING.get() + "2")){
                return Topping.fromInt(type, nbt.getInt(Topping.TOPPING.get() + "2")).equals(topping);
            }
            return false;
        }
        if (!nbt.contains(type.get())){
            return false;
        }
        return topping.equals(Topping.fromInt(type, nbt.getInt(type.get())));
    }

    public boolean hasTopping(int number){
        return this.nbt.contains(Topping.TOPPING.get() + number) && this.nbt.getInt(Topping.TOPPING.get() + number) > 0;
    }

    public int get(Topping type){
        if (!this.has(type)){
            return -1;
        }
        if (type.equals(Topping.TOPPING)){
            return this.getTopping(1);
        }
        return nbt.getInt(type.get());
    }

    public int getTopping(int number){
        if (!nbt.contains(Topping.TOPPING.get() + number)){
            return -1;
        }
        return nbt.getInt(Topping.TOPPING.get() + number);
    }

    public CompoundNBT getNbt() {
        return this.nbt;
    }
}
