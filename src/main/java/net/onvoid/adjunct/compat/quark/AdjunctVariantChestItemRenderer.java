package net.onvoid.adjunct.compat.quark;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import java.util.function.Supplier;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/block/VariantChestBlock.java

@OnlyIn(Dist.CLIENT)
public class AdjunctVariantChestItemRenderer<T extends TileEntity> extends ItemStackTileEntityRenderer {
    private final Supplier<T> tile;

    public AdjunctVariantChestItemRenderer(Supplier<T> tile) {
        this.tile = tile;
    }

    public void renderByItem(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay) {
        AdjunctVariantChestTileEntityRenderer.invBlock = ((BlockItem)stack.getItem()).getBlock();
        TileEntityRendererDispatcher.instance.renderItem(this.tile.get(), matrix, buffer, light, overlay);
        AdjunctVariantChestTileEntityRenderer.invBlock = null;
    }
}
