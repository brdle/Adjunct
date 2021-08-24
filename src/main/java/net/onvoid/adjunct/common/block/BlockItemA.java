package net.onvoid.adjunct.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.onvoid.adjunct.common.item.AdjunctItems;

public class BlockItemA extends BlockItem {
    public BlockItemA(Block block) {
        super(block, new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT));
    }
}
