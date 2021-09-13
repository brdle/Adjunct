package net.onvoid.adjunct.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.block.tree.OliveTree;

public class AdjunctBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Adjunct.MODID);

    public static final RegistryObject<Block> PIZZA_STATION = BLOCKS.register("pizza_station", () -> new PizzaStationBlock(Material.WOOD, MaterialColor.COLOR_BROWN, 1.75F, 2.5F, SoundType.WOOD, ToolType.AXE, 5, 5));
    public static final RegistryObject<Block> PIZZA_OVEN = BLOCKS.register("pizza_oven", () -> new PizzaOvenBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.COLOR_RED).strength(1.75F, 6.0F).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
    public static final RegistryObject<Block> PINEAPPLE_BUSH = BLOCKS.register("pineapple_bush", () -> new PineappleBushBlock(Material.PLANT, ToolType.HOE, 0.0F, SoundType.SWEET_BERRY_BUSH));
    public static final RegistryObject<Block> PINEAPPLE_UPSIDE_DOWN_CAKE = BLOCKS.register("pineapple_upside_down_cake", () -> new CakeBlockA(AbstractBlock.Properties.copy(Blocks.CAKE), 3, 1.1f));

    public static final RegistryObject<Block> DECORATIVE_PINK_DOOR = BLOCKS.register("decorative_pink_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.COLOR_PINK).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<Block> EGG_CRATE = BLOCKS.register("egg_crate", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2.0F, 3.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> EGG_CRATE_EGGLESS = BLOCKS.register("egg_crate_eggless", () -> new Block(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2.0F, 3.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE)));
    public static final RegistryObject<Block> ELEMENTAL_LIGHT = BLOCKS.register("elemental_light", () -> new Block(AbstractBlock.Properties.of(Material.STONE, MaterialColor.SAND).strength(2.0F, 3.0F).sound(SoundType.GLASS).harvestTool(ToolType.PICKAXE).lightLevel((p_235464_0_) -> { return 15; })));
    public static final RegistryObject<Block> DECORATIVE_PINK_GLASS = BLOCKS.register("decorative_pink_glass", () -> new GlassBlock(AbstractBlock.Properties.of(Material.GLASS, MaterialColor.COLOR_PINK).harvestTool(ToolType.PICKAXE).strength(1.0F, 3.0F).sound(SoundType.GLASS).noOcclusion()));

    public static final WoodType OLIVE = WoodType.create(new ResourceLocation(Adjunct.MODID, "olive").toString());
    public static final RegistryObject<Block> OLIVE_PLANKS = BLOCKS.register("olive_planks", () -> new FlammableBlock(Material.WOOD, MaterialColor.SAND, 2.0F, 3.0F, SoundType.WOOD, ToolType.AXE, 20, 5));
    public static final RegistryObject<Block> OLIVE_SAPLING = BLOCKS.register("olive_sapling", () -> new SaplingBlock(new OliveTree(), AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<LogBlock> STRIPPED_OLIVE_LOG = BLOCKS.register("stripped_olive_log", () -> log(MaterialColor.SAND, MaterialColor.SAND, null));
    public static final RegistryObject<LogBlock> OLIVE_LOG = BLOCKS.register("olive_log", () -> log(MaterialColor.SAND, MaterialColor.QUARTZ, STRIPPED_OLIVE_LOG.get()));
    public static final RegistryObject<Block> OLIVE_LEAVES = BLOCKS.register("olive_leaves", () -> new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(AdjunctBlocks::ocelotOrParrot).isSuffocating(AdjunctBlocks::never).isViewBlocking(AdjunctBlocks::never)));
    public static final RegistryObject<Block> STRIPPED_OLIVE_WOOD = BLOCKS.register("stripped_olive_wood", () -> log(MaterialColor.SAND, MaterialColor.SAND, null));
    public static final RegistryObject<Block> OLIVE_WOOD = BLOCKS.register("olive_wood", () -> log(MaterialColor.SAND, MaterialColor.SAND, STRIPPED_OLIVE_WOOD.get()));
    public static final RegistryObject<OliveStandingSignBlock> OLIVE_SIGN = BLOCKS.register("olive_sign", () -> new OliveStandingSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).noCollission().strength(1.0F).sound(SoundType.WOOD), OLIVE));
    public static final RegistryObject<OliveWallSignBlock> OLIVE_WALL_SIGN = BLOCKS.register("olive_wall_sign", () -> new OliveWallSignBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(OLIVE_SIGN.get()), OLIVE));
    public static final RegistryObject<Block> OLIVE_PRESSURE_PLATE = BLOCKS.register("olive_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.of(Material.WOOD, OLIVE_PLANKS.get().defaultMaterialColor()).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> OLIVE_TRAPDOOR = BLOCKS.register("olive_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(AdjunctBlocks::never)));
    public static final RegistryObject<Block> OLIVE_STAIRS = BLOCKS.register("olive_stairs", () -> new StairsBlock(OLIVE_PLANKS.get().defaultBlockState(), AbstractBlock.Properties.copy(OLIVE_PLANKS.get())));
    public static final RegistryObject<Block> POTTED_OLIVE_SAPLING = BLOCKS.register("potted_olive_sapling", () -> new FlowerPotBlock(OLIVE_SAPLING.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> OLIVE_BUTTON = BLOCKS.register("olive_button", () -> new WoodButtonBlock(AbstractBlock.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> OLIVE_SLAB = BLOCKS.register("olive_slab", () -> new SlabBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.SAND).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> OLIVE_FENCE_GATE = BLOCKS.register("olive_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.of(Material.WOOD, OLIVE_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> OLIVE_FENCE = BLOCKS.register("olive_fence", () -> new FenceBlock(AbstractBlock.Properties.of(Material.WOOD, OLIVE_PLANKS.get().defaultMaterialColor()).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> OLIVE_DOOR = BLOCKS.register("olive_door", () -> new DoorBlock(AbstractBlock.Properties.of(Material.WOOD, OLIVE_PLANKS.get().defaultMaterialColor()).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistryObject<Block> BLACK_CAT_BED = BLOCKS.register("black_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_BLACK).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.BLACK));
    public static final RegistryObject<Block> BLUE_CAT_BED = BLOCKS.register("blue_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_BLUE).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.BLUE));
    public static final RegistryObject<Block> BROWN_CAT_BED = BLOCKS.register("brown_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_BROWN).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.BROWN));
    public static final RegistryObject<Block> GREEN_CAT_BED = BLOCKS.register("green_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_GREEN).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.GREEN));
    public static final RegistryObject<Block> RED_CAT_BED = BLOCKS.register("red_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_RED).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.RED));
    public static final RegistryObject<Block> WHITE_CAT_BED = BLOCKS.register("white_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.TERRACOTTA_WHITE).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.WHITE));
    public static final RegistryObject<Block> YELLOW_CAT_BED = BLOCKS.register("yellow_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_YELLOW).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.YELLOW));
    public static final RegistryObject<Block> LIGHT_BLUE_CAT_BED = BLOCKS.register("light_blue_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_BLUE).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.LIGHT_BLUE));
    public static final RegistryObject<Block> LIGHT_GRAY_CAT_BED = BLOCKS.register("light_gray_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.LIGHT_GRAY));
    public static final RegistryObject<Block> LIME_CAT_BED = BLOCKS.register("lime_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_LIGHT_GREEN).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.LIME));
    public static final RegistryObject<Block> MAGENTA_CAT_BED = BLOCKS.register("magenta_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_MAGENTA).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.MAGENTA));
    public static final RegistryObject<Block> ORANGE_CAT_BED = BLOCKS.register("orange_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_ORANGE).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.ORANGE));
    public static final RegistryObject<Block> PINK_CAT_BED = BLOCKS.register("pink_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_PINK).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.PINK));
    public static final RegistryObject<Block> CYAN_CAT_BED = BLOCKS.register("cyan_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_CYAN).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.CYAN));
    public static final RegistryObject<Block> GRAY_CAT_BED = BLOCKS.register("gray_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_GRAY).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.GRAY));
    public static final RegistryObject<Block> PURPLE_CAT_BED = BLOCKS.register("purple_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOL, MaterialColor.COLOR_PURPLE).strength(1.0F).sound(SoundType.WOOL).noOcclusion(), DyeColor.PURPLE));
    public static final RegistryObject<Block> OLIVE_CAT_BED = BLOCKS.register("olive_cat_bed", () -> new CatBedBlock(AbstractBlock.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F).sound(SoundType.WOOD).noOcclusion(), null));
    public static final RegistryObject<Block> DISPLAY_CLOTH = BLOCKS.register("display_cloth", () -> new DisplayClothBlock(AbstractBlock.Properties.of(Material.CLOTH_DECORATION, MaterialColor.SNOW).strength(0.1F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> SEESTONE = BLOCKS.register("seestone", () -> new GlassBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.STONE).harvestTool(ToolType.PICKAXE).strength(2.0F, 3.0F).sound(SoundType.STONE).noOcclusion()));
    public static final RegistryObject<Block> SEESTONE_BRICKS = BLOCKS.register("seestone_bricks", () -> new GlassBlock(AbstractBlock.Properties.of(Material.STONE, MaterialColor.STONE).harvestTool(ToolType.PICKAXE).strength(2.0F, 3.0F).sound(SoundType.STONE).noOcclusion()));

    private static boolean never (BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    private static boolean never (BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    private static LogBlock log(MaterialColor color1, MaterialColor color2, Block stripTo) {
        return new LogBlock(AbstractBlock.Properties.of(Material.WOOD, (p_235431_2_) -> {
            return p_235431_2_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? color1 : color2;
        }).strength(2.0F).sound(SoundType.WOOD), stripTo);
    }

    public static void create(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
