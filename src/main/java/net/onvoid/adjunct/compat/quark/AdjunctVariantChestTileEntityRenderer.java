package net.onvoid.adjunct.compat.quark;

import net.minecraft.block.*;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.onvoid.adjunct.Adjunct;
import java.util.HashMap;
import java.util.Map;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/client/render/VariantChestTileEntityRenderer.java

public class AdjunctVariantChestTileEntityRenderer extends AdjunctGenericChestTERenderer<ChestTileEntity> {
    private static Map<Block, ChestTextureBatch> chestTextures = new HashMap();
    public static Block invBlock = null;

    public AdjunctVariantChestTileEntityRenderer(TileEntityRendererDispatcher disp) {
        super(disp);
    }

    public RenderMaterial getMaterial(ChestTileEntity t, ChestType type) {
        Block block = invBlock;
        if (block == null) {
            block = t.getBlockState().getBlock();
        }

        ChestTextureBatch batch = (ChestTextureBatch)chestTextures.get(block);
        if (batch == null) {
            return null;
        } else {
            switch(type) {
                case LEFT:
                    return batch.left;
                case RIGHT:
                    return batch.right;
                default:
                    return batch.normal;
            }
        }
    }

    public static void accept(TextureStitchEvent.Pre event, Block chest) {
        ResourceLocation atlas = event.getMap().location();
        if (chest instanceof AdjunctVariantChestBlock) {
            AdjunctVariantChestBlock prov = (AdjunctVariantChestBlock) chest;
            String path = prov.getChestTexturePath();
            if (!prov.isTrap()) {
                add(event, atlas, chest, path, "normal", "left", "right");
            } else {
                add(event, atlas, chest, path, "trap", "trap_left", "trap_right");
            }
        }

    }

    private static void add(TextureStitchEvent.Pre event, ResourceLocation atlas, Block chest, String path, String normal, String left, String right) {
        ResourceLocation resNormal = new ResourceLocation(Adjunct.MODID, path + normal);
        ResourceLocation resLeft = new ResourceLocation(Adjunct.MODID, path + left);
        ResourceLocation resRight = new ResourceLocation(Adjunct.MODID, path + right);
        ChestTextureBatch batch = new ChestTextureBatch(atlas, resNormal, resLeft, resRight);
        chestTextures.put(chest, batch);
        event.addSprite(resNormal);
        event.addSprite(resLeft);
        event.addSprite(resRight);
    }

    private static class ChestTextureBatch {
        public final RenderMaterial normal;
        public final RenderMaterial left;
        public final RenderMaterial right;

        public ChestTextureBatch(ResourceLocation atlas, ResourceLocation normal, ResourceLocation left, ResourceLocation right) {
            this.normal = new RenderMaterial(atlas, normal);
            this.left = new RenderMaterial(atlas, left);
            this.right = new RenderMaterial(atlas, right);
        }
    }
}
