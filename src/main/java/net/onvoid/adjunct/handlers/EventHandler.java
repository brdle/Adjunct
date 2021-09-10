package net.onvoid.adjunct.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.AdjunctHelper;
import net.onvoid.adjunct.client.model.PizzaOvenTileRenderer;
import net.onvoid.adjunct.client.model.PizzaStationTileRenderer;
import net.onvoid.adjunct.common.block.AdjunctBlocks;
import net.onvoid.adjunct.common.block.tree.OliveTree;
import net.onvoid.adjunct.common.entity.goal.CatLieOnCatBedGoal;
import net.onvoid.adjunct.common.item.AdjunctItems;
import net.onvoid.adjunct.common.tile.AdjunctTiles;

public class EventHandler {

    /*@OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;
        for (int i = 0; i < 3; i++) {
            Minecraft.getInstance().font.draw(e.getMatrixStack(), "Stuff", 0, i * 10, 0xFFFFFF);
        }
    }*/

    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof CatEntity){
            CatEntity cat = (CatEntity) e.getEntity();
            cat.goalSelector.addGoal(5, new CatLieOnCatBedGoal(cat, 1.1D, 10));
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.EntityInteract e) {
        if (e.getTarget() instanceof SheepEntity && !e.getWorld().isClientSide()) {
            if (e.getPlayer().getItemInHand(e.getHand()).getItem().equals(Items.BUCKET)) {
                SheepEntity sheep = (SheepEntity) e.getTarget();
                if (!sheep.isBaby() && sheep.isAlive()) {
                    e.getPlayer().getItemInHand(e.getHand()).shrink(1);
                    AdjunctHelper.giveToHand(e.getPlayer(), e.getHand(), new ItemStack(Items.MILK_BUCKET));
                    return;
                }
            }
        }
    }
}
