package net.onvoid.adjunct.compat.quark;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.block.AdjunctBlocks;
import net.onvoid.adjunct.common.tile.AdjunctTiles;

public class AdjunctQuarkBlocks {

    public static final DeferredRegister<Block> QUARK_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Adjunct.MODID);
    public static final RegistryObject<Block> OLIVE_BOOKSHELF = QUARK_BLOCKS.register("olive_bookshelf", () -> new AdjunctVariantBookshelfBlock(AbstractBlock.Properties.copy(Blocks.BOOKSHELF), true));
    public static final RegistryObject<Block> OLIVE_LADDER = QUARK_BLOCKS.register("olive_ladder", () -> new AdjunctVariantLadderBlock(AbstractBlock.Properties.copy(Blocks.LADDER), true));
    public static final RegistryObject<Block> OLIVE_HEDGE = QUARK_BLOCKS.register("olive_hedge", () -> new AdjunctHedgeBlock(AbstractBlock.Properties.copy(AdjunctBlocks.OLIVE_FENCE.get()), AdjunctBlocks.OLIVE_FENCE.get(), AdjunctBlocks.OLIVE_LEAVES.get()));
    public static final RegistryObject<Block> OLIVE_LEAF_CARPET = QUARK_BLOCKS.register("olive_leaf_carpet", () -> new AdjunctLeafCarpetBlock(Block.Properties.of(Material.CLOTH_DECORATION).strength(0F).sound(SoundType.GRASS).harvestTool(ToolType.HOE).noOcclusion(), AdjunctBlocks.OLIVE_LEAVES.get()));
    public static final RegistryObject<Block> OLIVE_VERTICAL_SLAB = QUARK_BLOCKS.register("olive_vertical_slab", () -> new AdjunctVerticalSlabBlock(AdjunctBlocks.OLIVE_SLAB.get()));
    public static final RegistryObject<Block> STRIPPED_OLIVE_POST = QUARK_BLOCKS.register("stripped_olive_post", () -> new AdjunctWoodPostBlock(AdjunctBlocks.STRIPPED_OLIVE_LOG.get(), null,false));
    public static final RegistryObject<Block> OLIVE_POST = QUARK_BLOCKS.register("olive_post", () -> new AdjunctWoodPostBlock(AdjunctBlocks.OLIVE_LOG.get(), STRIPPED_OLIVE_POST.get(), false));
    public static final RegistryObject<Block> VERTICAL_OLIVE_PLANKS = QUARK_BLOCKS.register("vertical_olive_planks", () -> new Block(AbstractBlock.Properties.copy(AdjunctBlocks.OLIVE_PLANKS.get())));
    public static final RegistryObject<Block> OLIVE_CHEST = QUARK_BLOCKS.register("olive_chest", () -> new AdjunctVariantChestBlock("olive", AbstractBlock.Properties.copy(Blocks.CHEST), () -> AdjunctTiles.OLIVE_CHEST.get()));
    public static final RegistryObject<Block> OLIVE_TRAPPED_CHEST = QUARK_BLOCKS.register("olive_trapped_chest", () -> new AdjunctVariantTrappedChestBlock("olive", AbstractBlock.Properties.copy(Blocks.CHEST), () -> AdjunctTiles.OLIVE_TRAPPED_CHEST.get()));

    public static void create(){
        QUARK_BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
