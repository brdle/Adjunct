package net.onvoid.adjunct.compat.quark;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.block.BlockItemA;
import net.onvoid.adjunct.common.block.BlockItemAddon;
import net.onvoid.adjunct.common.item.AdjunctItems;

import java.util.concurrent.Callable;

public class AdjunctQuarkItems {

    public static String addonMod = "quark";

    public static final DeferredRegister<Item> QUARK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Adjunct.MODID);
    public static final RegistryObject<BlockItem> OLIVE_BOOKSHELF = biCompat(AdjunctQuarkBlocks.OLIVE_BOOKSHELF);
    public static final RegistryObject<BlockItem> OLIVE_LADDER = biCompat(AdjunctQuarkBlocks.OLIVE_LADDER);
    public static final RegistryObject<BlockItem> OLIVE_HEDGE = biCompat(AdjunctQuarkBlocks.OLIVE_HEDGE);
    public static final RegistryObject<BlockItem> OLIVE_LEAF_CARPET = biCompat(AdjunctQuarkBlocks.OLIVE_LEAF_CARPET);
    public static final RegistryObject<BlockItem> OLIVE_VERTICAL_SLAB = biCompat(AdjunctQuarkBlocks.OLIVE_VERTICAL_SLAB);
    public static final RegistryObject<BlockItem> OLIVE_POST = biCompat(AdjunctQuarkBlocks.OLIVE_POST);
    public static final RegistryObject<BlockItem> STRIPPED_OLIVE_POST = biCompat(AdjunctQuarkBlocks.STRIPPED_OLIVE_POST);
    public static final RegistryObject<BlockItem> VERTICAL_OLIVE_PLANKS = biCompat(AdjunctQuarkBlocks.VERTICAL_OLIVE_PLANKS);
    public static final RegistryObject<BlockItem> OLIVE_CHEST = QUARK_ITEMS.register("olive_chest", () -> new BlockItemAddon(AdjunctQuarkBlocks.OLIVE_CHEST.get(), addonMod, new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)
            .setISTER(() -> variantChestISTER())));
    public static final RegistryObject<BlockItem> OLIVE_TRAPPED_CHEST = QUARK_ITEMS.register("olive_trapped_chest", () -> new BlockItemAddon(AdjunctQuarkBlocks.OLIVE_TRAPPED_CHEST.get(), addonMod, new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)
            .setISTER(() -> variantTrappedChestISTER())));

    public static RegistryObject<BlockItem> bi(RegistryObject<Block> blockObject){
        return QUARK_ITEMS.register(blockObject.getId().getPath(), () -> new BlockItemA(blockObject.get()));
    }

    public static RegistryObject<BlockItem> biCompat(RegistryObject<Block> blockObject){
        return QUARK_ITEMS.register(blockObject.getId().getPath(), () -> new BlockItemAddon(blockObject.get(), addonMod, new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT)));
    }

    @OnlyIn(Dist.CLIENT)
    private static Callable<ItemStackTileEntityRenderer> variantChestISTER() {
        return () -> new AdjunctVariantChestItemRenderer<TileEntity>(AdjunctVariantChestTileEntity::new);
    }

    @OnlyIn(Dist.CLIENT)
    private static Callable<ItemStackTileEntityRenderer> variantTrappedChestISTER() {
        return () -> new AdjunctVariantChestItemRenderer<TileEntity>(AdjunctVariantTrappedChestTileEntity::new);
    }

    public static void create(){
        QUARK_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
