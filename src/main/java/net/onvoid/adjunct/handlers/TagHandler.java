package net.onvoid.adjunct.handlers;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.onvoid.adjunct.Adjunct;

public class TagHandler {

    public static ITag<Block> HEDGES;
    public static ITag<Block> CAT_BEDS;

    public static void init(){
        //ResourceLocation mineableAxe = new ResourceLocation("minecraft", "mineable/axe");
        HEDGES = BlockTags.createOptional(new ResourceLocation("quark", "hedges"));
        CAT_BEDS = BlockTags.createOptional(new ResourceLocation(Adjunct.MODID, "cat_beds"));
    }

}
