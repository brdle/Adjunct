package net.onvoid.adjunct.compat.quark;

import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.onvoid.adjunct.common.tile.AdjunctTiles;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/tile/VariantChestTileEntity.java

public class AdjunctVariantChestTileEntity extends ChestTileEntity {
    protected AdjunctVariantChestTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public AdjunctVariantChestTileEntity() {
        super(AdjunctTiles.OLIVE_CHEST.get());
    }

    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)(this.worldPosition.getX() - 1), (double)this.worldPosition.getY(), (double)(this.worldPosition.getZ() - 1), (double)(this.worldPosition.getX() + 2), (double)(this.worldPosition.getY() + 2), (double)(this.worldPosition.getZ() + 2));
    }
}
