package net.onvoid.adjunct.common.block;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import net.onvoid.adjunct.common.item.AdjunctItems;

import javax.annotation.Nullable;
import java.util.List;

public class BlockItemAddon extends BlockItem {

    private String addonMod;

    public BlockItemAddon(Block block, String addonMod, Item.Properties properties) {
        super(block, properties);
        this.addonMod = addonMod;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!ModList.get().isLoaded(this.addonMod)){
            tooltip.add(new TranslationTextComponent("adjunct:desc.warning").withStyle(TextFormatting.RED).withStyle(TextFormatting.BOLD));
            tooltip.add(new TranslationTextComponent("adjunct:desc.addon").withStyle(TextFormatting.RED).append(new TranslationTextComponent("adjunct:desc.addon_1").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC)));
            tooltip.add(new TranslationTextComponent("adjunct:desc.addon_2").withStyle(TextFormatting.RED));
            tooltip.add(new StringTextComponent(this.addonMod).withStyle(TextFormatting.GRAY).withStyle(TextFormatting.UNDERLINE));
        }
    }

    public String getAddonMod(){
        return this.addonMod;
    }
}
