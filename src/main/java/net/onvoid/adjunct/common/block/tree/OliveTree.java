package net.onvoid.adjunct.common.block.tree;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Blocks;
import net.minecraft.block.WoodType;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.onvoid.adjunct.common.block.AdjunctBlocks;

import javax.annotation.Nullable;
import java.util.Random;

public class OliveTree extends Tree {
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> OLIVE;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> OLIVE_BEES_0002;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> OLIVE_BEES_002;
    public static ConfiguredFeature<BaseTreeFeatureConfig, ?> OLIVE_BEES_005;

    @Nullable
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getConfiguredFeature(Random rand, boolean p_225546_2_) {
        return p_225546_2_ ? OLIVE_BEES_005 : OLIVE;
    }
}
