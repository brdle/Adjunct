package net.onvoid.adjunct.proxy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.pizza.Topping;
import net.onvoid.adjunct.compat.quark.AdjunctQuarkBlocks;
import net.onvoid.adjunct.compat.quark.AdjunctQuarkEvents;
import net.onvoid.adjunct.compat.quark.AdjunctQuarkItems;
import net.onvoid.adjunct.common.block.tree.OliveTree;
import net.onvoid.adjunct.common.tile.AdjunctTiles;
import net.onvoid.adjunct.handlers.EventHandler;
import net.onvoid.adjunct.common.item.pizza.PizzaHandler;
import net.onvoid.adjunct.handlers.TagHandler;
import net.onvoid.adjunct.common.block.AdjunctBlocks;
import net.onvoid.adjunct.common.item.AdjunctItems;
import net.onvoid.adjunct.handlers.WorldGenHandler;

@Mod.EventBusSubscriber(modid = Adjunct.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {

    public void start() {
        PizzaHandler.createTags();
        AdjunctBlocks.create();
        //if (ModList.get().isLoaded("quark")){
            AdjunctQuarkBlocks.create();
            AdjunctQuarkItems.create();
        //}
        AdjunctTiles.create();
        AdjunctItems.create();
        Topping.registerTags();
        Topping.initKeys();
        TagHandler.init();
        registerListeners();
    }

    public void registerListeners(){
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modBus.addListener(this::setup);
        forgeBus.register(new EventHandler());
        forgeBus.register(new WorldGenHandler());
        forgeBus.register(new AdjunctQuarkEvents());
    }

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent e){
        e.enqueueWork(() ->
        {
            WorldGenHandler.PATCH_PINEAPPLE_BUSH = WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Adjunct.MODID, "patch_pineapple_bush"), Feature.RANDOM_PATCH.configured((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(AdjunctBlocks.PINEAPPLE_BUSH.get().defaultBlockState().setValue(SweetBerryBushBlock.AGE, Integer.valueOf(1))), SimpleBlockPlacer.INSTANCE)).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK, Blocks.JUNGLE_LEAVES)).tries(64).xspread(4).zspread(4).yspread(4).noProjection().build()));
            WorldGenHandler.PATCH_PINEAPPLE_SPARSE = WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Adjunct.MODID, "patch_pineapple_sparse"), WorldGenHandler.PATCH_PINEAPPLE_BUSH.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
            WorldGenHandler.PATCH_PINEAPPLE_DECORATED = WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(Adjunct.MODID, "patch_pineapple_decorated"), WorldGenHandler.PATCH_PINEAPPLE_BUSH.decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE).chance(12));
            OliveTree.OLIVE = WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("olive_tree"), Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(AdjunctBlocks.OLIVE_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(AdjunctBlocks.OLIVE_LEAVES.get().defaultBlockState()), new BlobFoliagePlacer(FeatureSpread.fixed(2), FeatureSpread.fixed(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1))).ignoreVines().build()));
            OliveTree.OLIVE_BEES_0002 =  WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("olive_bees_0002"), Feature.TREE.configured(OliveTree.OLIVE.config().withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_0002))));
            OliveTree.OLIVE_BEES_002 = WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("olive_bees_002"), Feature.TREE.configured(OliveTree.OLIVE.config().withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_002))));
            OliveTree.OLIVE_BEES_005 = WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation("olive_bees_005"), Feature.TREE.configured(OliveTree.OLIVE.config().withDecorators(ImmutableList.of(Features.Placements.BEEHIVE_005))));
            WoodType.register(AdjunctBlocks.OLIVE);
        });
    }
}
