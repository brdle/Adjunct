package net.onvoid.adjunct.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.AdjunctItems;

import javax.annotation.Nullable;
import java.util.List;

public class BlockItemA extends BlockItem {
    private String descKey = "";

    public BlockItemA(Block block) {
        super(block, new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT));
    }

    public BlockItemA(Block block, String descKey){
        super(block, new Item.Properties().tab(AdjunctItems.TAB_ADJUNCT));
        this.descKey = descKey;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!this.descKey.equals("")) {
            tooltip.add(new TranslationTextComponent(this.getRegistryName().toString() + "." + descKey).withStyle(TextFormatting.GRAY));
        }
    }
}
