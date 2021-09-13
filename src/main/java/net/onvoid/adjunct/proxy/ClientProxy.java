package net.onvoid.adjunct.proxy;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.client.model.DisplayClothTileRenderer;
import net.onvoid.adjunct.common.item.pizza.Topping;
import net.onvoid.adjunct.compat.quark.AdjunctVariantChestTileEntityRenderer;
import net.onvoid.adjunct.client.model.PizzaOvenTileRenderer;
import net.onvoid.adjunct.client.model.PizzaStationTileRenderer;
import net.onvoid.adjunct.common.block.AdjunctBlocks;
import net.onvoid.adjunct.compat.quark.AdjunctQuarkBlocks;
import net.onvoid.adjunct.common.item.AdjunctItems;
import net.onvoid.adjunct.common.tile.AdjunctTiles;
import net.onvoid.adjunct.common.item.pizza.PizzaHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void registerListeners(){
        super.registerListeners();
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        modBus.addListener(this::setupClient);
        modBus.addListener(this::onTextureStitch);
        modBus.addListener(this::loadComplete);
    }

    @SubscribeEvent
    public void setupClient(FMLClientSetupEvent e){
        e.enqueueWork(() ->
        {
            for (String topping : Topping.allKeys){
                ItemModelsProperties.register(AdjunctItems.PIZZA_ITEM.get(),
                        new ResourceLocation(Adjunct.MODID, topping), (stack, world, living) -> {
                            CompoundNBT tag = stack.getOrCreateTag();
                            return tag.getInt(topping);
                        });
            }
            ItemModelsProperties.register(AdjunctItems.KNOWLEDGE_SYRINGE_ITEM.get(),
                    new ResourceLocation(Adjunct.MODID, "exp"), (stack, world, living) -> {
                        CompoundNBT tag = stack.getOrCreateTag();
                        return tag.getInt("exp");
                    });
            ItemModelsProperties.register(AdjunctItems.KNOWLEDGE_SCARAB_ITEM.get(),
                    new ResourceLocation(Adjunct.MODID, "exp"), (stack, world, living) -> {
                        CompoundNBT tag = stack.getOrCreateTag();
                        return tag.getInt("exp");
                    });
            Minecraft.getInstance().getBlockColors().register((p_228061_0_, p_228061_1_, p_228061_2_, p_228061_3_) -> {
                return p_228061_1_ != null && p_228061_2_ != null ? BiomeColors.getAverageFoliageColor(p_228061_1_, p_228061_2_) : FoliageColors.getDefaultColor();
            }, AdjunctBlocks.OLIVE_LEAVES.get());
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                return Minecraft.getInstance().getBlockColors().getColor(blockstate, (IBlockDisplayReader)null, (BlockPos)null, tintIndex);
            }, AdjunctBlocks.OLIVE_LEAVES.get());
        });
        ClientRegistry.bindTileEntityRenderer(AdjunctTiles.PIZZA_STATION_TILE.get(), PizzaStationTileRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AdjunctTiles.PIZZA_OVEN_TILE.get(), PizzaOvenTileRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AdjunctTiles.OLIVE_SIGN.get(), SignTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AdjunctTiles.OLIVE_CHEST.get(), AdjunctVariantChestTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AdjunctTiles.OLIVE_TRAPPED_CHEST.get(), AdjunctVariantChestTileEntityRenderer::new);
        ClientRegistry.bindTileEntityRenderer(AdjunctTiles.DISPLAY_CLOTH_TILE.get(), DisplayClothTileRenderer::new);
        Atlases.addWoodType(AdjunctBlocks.OLIVE);
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.PIZZA_OVEN.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.BLACK_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.BLUE_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.BROWN_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.GREEN_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.RED_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.WHITE_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.YELLOW_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.LIGHT_BLUE_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.LIGHT_GRAY_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.LIME_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.MAGENTA_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.ORANGE_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.PINK_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.CYAN_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.GRAY_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.PURPLE_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.OLIVE_CAT_BED.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.PINEAPPLE_BUSH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.OLIVE_SAPLING.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.DECORATIVE_PINK_DOOR.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.DECORATIVE_PINK_GLASS.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.DISPLAY_CLOTH.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.SEESTONE.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.SEESTONE_BRICKS.get(), RenderType.translucent());
        RenderTypeLookup.setRenderLayer(AdjunctBlocks.PINK_SEESTONE.get(), RenderType.translucent());
        //if (ModList.get().isLoaded("quark")) {
            RenderTypeLookup.setRenderLayer(AdjunctQuarkBlocks.OLIVE_LADDER.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(AdjunctQuarkBlocks.OLIVE_LEAF_CARPET.get(), RenderType.cutoutMipped());
            RenderTypeLookup.setRenderLayer(AdjunctQuarkBlocks.OLIVE_HEDGE.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(AdjunctQuarkBlocks.OLIVE_POST.get(), RenderType.cutout());
            RenderTypeLookup.setRenderLayer(AdjunctQuarkBlocks.STRIPPED_OLIVE_POST.get(), RenderType.cutout());
            Minecraft.getInstance().getBlockColors().register((p_228061_0_, p_228061_1_, p_228061_2_, p_228061_3_) -> {
                return p_228061_1_ != null && p_228061_2_ != null ? BiomeColors.getAverageFoliageColor(p_228061_1_, p_228061_2_) : FoliageColors.getDefaultColor();
            }, AdjunctQuarkBlocks.OLIVE_HEDGE.get());
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                return Minecraft.getInstance().getBlockColors().getColor(blockstate, (IBlockDisplayReader)null, (BlockPos)null, tintIndex);
            }, AdjunctQuarkBlocks.OLIVE_HEDGE.get());
            Minecraft.getInstance().getBlockColors().register((p_228061_0_, p_228061_1_, p_228061_2_, p_228061_3_) -> {
                return p_228061_1_ != null && p_228061_2_ != null ? BiomeColors.getAverageFoliageColor(p_228061_1_, p_228061_2_) : FoliageColors.getDefaultColor();
            }, AdjunctQuarkBlocks.OLIVE_LEAF_CARPET.get());
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
                return Minecraft.getInstance().getBlockColors().getColor(blockstate, (IBlockDisplayReader)null, (BlockPos)null, tintIndex);
            }, AdjunctQuarkBlocks.OLIVE_LEAF_CARPET.get());
        //}
    }

    /*@SubscribeEvent
    static void registerModelLoader(ModelRegistryEvent e) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(Adjunct.MODID, "quark"), QuarkModel.LOADER);
    }*/

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre e) {
        if(e.getMap().location().toString().equals("minecraft:textures/atlas/chest.png")) {
            AdjunctVariantChestTileEntityRenderer.accept(e, AdjunctQuarkBlocks.OLIVE_CHEST.get());
            AdjunctVariantChestTileEntityRenderer.accept(e, AdjunctQuarkBlocks.OLIVE_TRAPPED_CHEST.get());
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void loadComplete(FMLLoadCompleteEvent e){
        return;
    }
}
