package net.onvoid.adjunct.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.onvoid.adjunct.common.block.tree.OliveTree;

import java.util.ArrayList;
import java.util.Arrays;

public class WorldGenHandler {
    public static ConfiguredFeature<?, ?> PATCH_PINEAPPLE_BUSH;
    public static ConfiguredFeature<?, ?> PATCH_PINEAPPLE_SPARSE;
    public static ConfiguredFeature<?, ?> PATCH_PINEAPPLE_DECORATED;

    @SubscribeEvent (priority = EventPriority.NORMAL)
    public void biomeLoading(BiomeLoadingEvent e){
        // PINEAPPLE GENERATION
        if (e.getCategory().equals(Biome.Category.JUNGLE)){
            e.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, PATCH_PINEAPPLE_BUSH);
            e.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, PATCH_PINEAPPLE_SPARSE);
            e.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, PATCH_PINEAPPLE_DECORATED);
        // OLIVE TREE GENERATION
        } else if (e.getCategory().equals(Biome.Category.FOREST) || e.getCategory().equals(Biome.Category.SAVANNA)){
            e.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, OliveTree.OLIVE);
            e.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, OliveTree.OLIVE_BEES_0002);
            e.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, OliveTree.OLIVE_BEES_002);
            e.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, OliveTree.OLIVE_BEES_005);
        }
    }
}
