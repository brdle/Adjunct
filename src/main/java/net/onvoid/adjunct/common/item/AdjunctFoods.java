package net.onvoid.adjunct.common.item;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.onvoid.adjunct.common.block.AdjunctBlocks;

public class AdjunctFoods {
    public static final Food PIZZA = (new Food.Builder()).nutrition(0).saturationMod(0.0F).meat().build();
    public static final Food VEGAN_CHEESE = (new Food.Builder()).nutrition(3).saturationMod(0.3F).build();
    public static final Food PEPPERONI = (new Food.Builder()).nutrition(2).saturationMod(0.2F).meat().build();
    public static final Food PINEAPPLE = (new Food.Builder()).nutrition(2).saturationMod(0.3f).build();
    public static final Food OLIVE = (new Food.Builder()).nutrition(2).saturationMod(0.3f).build();
    public static final Food HAM_SLICE = (new Food.Builder()).nutrition(2).saturationMod(0.2F).meat().build();
    public static final Food BERRY_PIE = (new Food.Builder()).nutrition(8).saturationMod(0.3F).build();
    public static final Food APPLE_PIE = (new Food.Builder()).nutrition(8).saturationMod(0.3F).build();
    public static final Food PEANUT_BUTTER_AND_JELLY_SANDWICH = (new Food.Builder()).nutrition(9).saturationMod(0.4F).build();
    public static final Food CHEESY_BREAD = (new Food.Builder()).nutrition(6).saturationMod(0.3F).build();
    public static final Food NOODLE_BOWL = (new Food.Builder()).nutrition(2).saturationMod(0.3F).build();
    public static final Food SPAGHETTI = (new Food.Builder()).nutrition(6).saturationMod(0.3F).build();
    public static final Food MACARONI_AND_CHEESE = (new Food.Builder()).nutrition(6).saturationMod(0.4F).build();
    public static final Food EGGPLANT_PARMESAN = (new Food.Builder()).nutrition(8).saturationMod(0.6F).build();
    public static final Food HAM_AND_CHEESE_SANDWICH = (new Food.Builder()).nutrition(10).saturationMod(0.6F).build();
}
