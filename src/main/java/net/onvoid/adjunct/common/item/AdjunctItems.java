package net.onvoid.adjunct.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.block.BlockItemAddon;
import net.onvoid.adjunct.common.item.pizza.Pizza;
import net.onvoid.adjunct.common.item.pizza.PizzaItem;
import net.onvoid.adjunct.common.item.pizza.PizzaHandler;
import net.onvoid.adjunct.common.block.AdjunctBlocks;
import net.onvoid.adjunct.common.block.BlockItemA;
import net.onvoid.adjunct.common.item.pizza.Topping;

public class AdjunctItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Adjunct.MODID);
    public static final ItemGroup TAB_ADJUNCT = new ItemGroup("adjunct") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new Pizza().add(Topping.CRUST, "original").add(Topping.SAUCE, "tomato").add(Topping.CHEESE, "cheese").add(Topping.TOPPING, "pepperoni").bakeStack();
        }

        @OnlyIn(Dist.CLIENT)
        public void fillItemList(NonNullList<ItemStack> stackList) {
            for (Item item : Registry.ITEM) {
                if (item instanceof PizzaItem) {
                    continue;
                } else if (item instanceof BlockItemAddon && !ModList.get().isLoaded(((BlockItemAddon)item).getAddonMod())){
                    continue;
                } else {
                    item.fillItemCategory(this, stackList);
                }
                if (item instanceof KnowledgeSyringeItem) {
                    ItemStack stack = new ItemStack(item);
                    CompoundNBT tag = stack.getOrCreateTag();
                    tag.putInt("exp", KnowledgeSyringeItem.MAX);
                    stack.setTag(tag);
                    stackList.add(stack);
                } else if (item instanceof KnowledgeScarabItem) {
                    ItemStack stack = new ItemStack(item);
                    CompoundNBT tag = stack.getOrCreateTag();
                    tag.putInt("exp", KnowledgeScarabItem.MAX);
                    stack.setTag(tag);
                    stackList.add(stack);
                }
            }
        }
    };
    public static final RegistryObject<Item> KNOWLEDGE_SYRINGE_ITEM = ITEMS.register("knowledge_syringe", () -> new KnowledgeSyringeItem(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> KNOWLEDGE_SCARAB_ITEM = ITEMS.register("knowledge_scarab", () -> new KnowledgeScarabItem(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> GOLDEN_SCARAB_ITEM = ITEMS.register("golden_scarab", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> DRY_LEAF_ITEM = ITEMS.register("dry_leaf", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> DRY_LEAF_DUST_ITEM = ITEMS.register("dry_leaf_dust", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> EGG_CARTON_ITEM = ITEMS.register("egg_carton", () -> new EggCartonItem(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> PIZZA_ITEM = ITEMS.register("pizza", () -> new PizzaItem(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.PIZZA).stacksTo(1)));
    public static final RegistryObject<Item> ORIGINAL_DOUGH_ITEM = ITEMS.register("original_dough", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> GLUTEN_FREE_DOUGH_ITEM = ITEMS.register("gluten_free_dough", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> BLAZE_DOUGH_ITEM = ITEMS.register("blaze_dough", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> TOMATO_SAUCE_ITEM = ITEMS.register("tomato_sauce_bottle", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> WHITE_SAUCE_ITEM = ITEMS.register("white_sauce_bottle", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> OLIVE_OIL_ITEM = ITEMS.register("olive_oil_bottle", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> VEGAN_CHEESE_ITEM = ITEMS.register("vegan_cheese", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.VEGAN_CHEESE)));
    public static final RegistryObject<Item> PEPPERONI_ITEM = ITEMS.register("pepperoni", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.PEPPERONI)));
    public static final RegistryObject<Item> PINEAPPLE_ITEM = ITEMS.register("pineapple", () -> new BlockNamedItem(AdjunctBlocks.PINEAPPLE_BUSH.get(), new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.PINEAPPLE)));
    public static final RegistryObject<Item> OLIVE_ITEM = ITEMS.register("olive", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.OLIVE)));
    public static final RegistryObject<Item> PEANUT_BUTTER_ITEM = ITEMS.register("peanut_butter_bottle", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> BERRY_PRESERVES_ITEM = ITEMS.register("berry_preserves_bottle", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> BARLEY_FLOUR_ITEM = ITEMS.register("barley_flour", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<Item> APPLE_PIE_ITEM = ITEMS.register("apple_pie", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.APPLE_PIE)));
    public static final RegistryObject<Item> BERRY_PIE_ITEM = ITEMS.register("berry_pie", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.BERRY_PIE)));
    public static final RegistryObject<Item> PEANUT_BUTTER_AND_JELLY_SANDWICH_ITEM = ITEMS.register("peanut_butter_and_jelly_sandwich", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.PEANUT_BUTTER_AND_JELLY_SANDWICH)));
    public static final RegistryObject<Item> CHEESY_BREAD_ITEM = ITEMS.register("cheesy_bread", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.CHEESY_BREAD)));
    public static final RegistryObject<Item> NOODLE_BOWL_ITEM = ITEMS.register("noodle_bowl", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.NOODLE_BOWL).stacksTo(8)));
    public static final RegistryObject<Item> SPAGHETTI_ITEM = ITEMS.register("spaghetti", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.SPAGHETTI).stacksTo(8)));
    public static final RegistryObject<Item> MACARONI_AND_CHEESE_ITEM = ITEMS.register("macaroni_and_cheese", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.MACARONI_AND_CHEESE).stacksTo(8)));
    public static final RegistryObject<Item> EGGPLANT_PARMESAN_ITEM = ITEMS.register("eggplant_parmesan", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.EGGPLANT_PARMESAN)));
    public static final RegistryObject<Item> HAM_SLICE_ITEM = ITEMS.register("ham_slice", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.HAM_SLICE)));
    public static final RegistryObject<Item> HAM_AND_CHEESE_SANDWICH_ITEM = ITEMS.register("ham_and_cheese_sandwich", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.HAM_AND_CHEESE_SANDWICH)));
    public static final RegistryObject<Item> CHICKEN_RAMEN_ITEM = ITEMS.register("chicken_ramen", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).food(AdjunctFoods.CHICKEN_RAMEN).stacksTo(8)));
    public static final RegistryObject<Item> JUMP_ROCKET = ITEMS.register("jump_rocket", () -> new JumpRocketItem(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT).stacksTo(1)));
    public static final RegistryObject<Item> CONDUCTIVE_ALLOY_INGOT = ITEMS.register("conductive_alloy_ingot", () -> new Item(new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));


    public static final RegistryObject<BlockItem> PIZZA_STATION_ITEM = bi(AdjunctBlocks.PIZZA_STATION);
    public static final RegistryObject<BlockItem> PIZZA_OVEN_ITEM = bi(AdjunctBlocks.PIZZA_OVEN);
    public static final RegistryObject<BlockItem> PINEAPPLE_UPSIDE_DOWN_CAKE_ITEM = ITEMS.register("pineapple_upside_down_cake", () -> new BlockItem(AdjunctBlocks.PINEAPPLE_UPSIDE_DOWN_CAKE.get(), (new Item.Properties()).stacksTo(1).tab(AdjunctItems.TAB_ADJUNCT)));
    public static final RegistryObject<BlockItem> OLIVE_LOG_ITEM = ITEMS.register("olive_log", () -> new BlockItemA(AdjunctBlocks.OLIVE_LOG.get()));
    public static final RegistryObject<BlockItem> OLIVE_LEAVES_ITEM = bi(AdjunctBlocks.OLIVE_LEAVES);
    public static final RegistryObject<BlockItem> OLIVE_PLANKS_ITEM = bi(AdjunctBlocks.OLIVE_PLANKS);
    public static final RegistryObject<BlockItem> OLIVE_SAPLING_ITEM = bi(AdjunctBlocks.OLIVE_SAPLING);
    public static final RegistryObject<BlockItem> STRIPPED_OLIVE_LOG = ITEMS.register("stripped_olive_log", () -> new BlockItemA(AdjunctBlocks.STRIPPED_OLIVE_LOG.get()));
    public static final RegistryObject<BlockItem> OLIVE_WOOD = bi(AdjunctBlocks.OLIVE_WOOD);
    public static final RegistryObject<BlockItem> STRIPPED_OLIVE_WOOD = bi(AdjunctBlocks.STRIPPED_OLIVE_WOOD);
    public static final RegistryObject<BlockItem> OLIVE_SIGN = ITEMS.register("olive_sign", () -> new SignItem((new Item.Properties()).stacksTo(16).tab(TAB_ADJUNCT), AdjunctBlocks.OLIVE_SIGN.get(), AdjunctBlocks.OLIVE_WALL_SIGN.get()));
    public static final RegistryObject<BlockItem> OLIVE_PRESSURE_PLATE = bi(AdjunctBlocks.OLIVE_PRESSURE_PLATE);
    public static final RegistryObject<BlockItem> OLIVE_TRAPDOOR = bi(AdjunctBlocks.OLIVE_TRAPDOOR);
    public static final RegistryObject<BlockItem> OLIVE_STAIRS = bi(AdjunctBlocks.OLIVE_STAIRS);
    public static final RegistryObject<BlockItem> OLIVE_BUTTON = bi(AdjunctBlocks.OLIVE_BUTTON);
    public static final RegistryObject<BlockItem> OLIVE_SLAB = bi(AdjunctBlocks.OLIVE_SLAB);
    public static final RegistryObject<BlockItem> OLIVE_FENCE_GATE = bi(AdjunctBlocks.OLIVE_FENCE_GATE);
    public static final RegistryObject<BlockItem> OLIVE_FENCE = bi(AdjunctBlocks.OLIVE_FENCE);
    public static final RegistryObject<BlockItem> OLIVE_DOOR = bi(AdjunctBlocks.OLIVE_DOOR);
    public static final RegistryObject<BlockItem> DECORATIVE_PINK_DOOR = bi(AdjunctBlocks.DECORATIVE_PINK_DOOR);
    public static final RegistryObject<BlockItem> EGG_CRATE = bi(AdjunctBlocks.EGG_CRATE);
    public static final RegistryObject<BlockItem> EGG_CRATE_EGGLESS = bi(AdjunctBlocks.EGG_CRATE_EGGLESS, "desc");
    public static final RegistryObject<BlockItem> ELEMENTAL_LIGHT_ITEM = bi(AdjunctBlocks.ELEMENTAL_LIGHT);
    public static final RegistryObject<BlockItem> DECORATIVE_PINK_GLASS_ITEM = bi(AdjunctBlocks.DECORATIVE_PINK_GLASS);
    public static final RegistryObject<BlockItem> BLACK_CAT_BED_ITEM = bi(AdjunctBlocks.BLACK_CAT_BED);
    public static final RegistryObject<BlockItem> BLUE_CAT_BED_ITEM = bi(AdjunctBlocks.BLUE_CAT_BED);
    public static final RegistryObject<BlockItem> BROWN_CAT_BED_ITEM = bi(AdjunctBlocks.BROWN_CAT_BED);
    public static final RegistryObject<BlockItem> GREEN_CAT_BED_ITEM = bi(AdjunctBlocks.GREEN_CAT_BED);
    public static final RegistryObject<BlockItem> RED_CAT_BED_ITEM = bi(AdjunctBlocks.RED_CAT_BED);
    public static final RegistryObject<BlockItem> WHITE_CAT_BED_ITEM = bi(AdjunctBlocks.WHITE_CAT_BED);
    public static final RegistryObject<BlockItem> YELLOW_CAT_BED_ITEM = bi(AdjunctBlocks.YELLOW_CAT_BED);
    public static final RegistryObject<BlockItem> LIGHT_BLUE_CAT_BED_ITEM = bi(AdjunctBlocks.LIGHT_BLUE_CAT_BED);
    public static final RegistryObject<BlockItem> LIGHT_GRAY_CAT_BED_ITEM = bi(AdjunctBlocks.LIGHT_GRAY_CAT_BED);
    public static final RegistryObject<BlockItem> LIME_CAT_BED_ITEM = bi(AdjunctBlocks.LIME_CAT_BED);
    public static final RegistryObject<BlockItem> MAGENTA_CAT_BED_ITEM = bi(AdjunctBlocks.MAGENTA_CAT_BED);
    public static final RegistryObject<BlockItem> ORANGE_CAT_BED_ITEM = bi(AdjunctBlocks.ORANGE_CAT_BED);
    public static final RegistryObject<BlockItem> PINK_CAT_BED_ITEM = bi(AdjunctBlocks.PINK_CAT_BED);
    public static final RegistryObject<BlockItem> CYAN_CAT_BED_ITEM = bi(AdjunctBlocks.CYAN_CAT_BED);
    public static final RegistryObject<BlockItem> GRAY_CAT_BED_ITEM = bi(AdjunctBlocks.GRAY_CAT_BED);
    public static final RegistryObject<BlockItem> PURPLE_CAT_BED_ITEM = bi(AdjunctBlocks.PURPLE_CAT_BED);
    public static final RegistryObject<BlockItem> OLIVE_CAT_BED_ITEM = bi(AdjunctBlocks.OLIVE_CAT_BED);
    public static final RegistryObject<BlockItem> DISPLAY_CLOTH_ITEM = bi(AdjunctBlocks.DISPLAY_CLOTH);
    public static final RegistryObject<BlockItem> SEESTONE_ITEM = bi(AdjunctBlocks.SEESTONE);


    public static RegistryObject<BlockItem> bi(RegistryObject<Block> blockObject){
        return ITEMS.register(blockObject.getId().getPath(), () -> new BlockItemA(blockObject.get()));
    }

    public static RegistryObject<BlockItem> bi(RegistryObject<Block> blockObject, String descKey){
        return ITEMS.register(blockObject.getId().getPath(), () -> new BlockItemA(blockObject.get(), descKey));
    }

    public static void create(){
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
