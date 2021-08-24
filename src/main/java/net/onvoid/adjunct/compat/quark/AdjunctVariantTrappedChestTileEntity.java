package net.onvoid.adjunct.compat.quark;

import net.minecraft.tileentity.TileEntityType;
import net.onvoid.adjunct.common.tile.AdjunctTiles;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/tile/VariantTrappedChestTileEntity.java

public class AdjunctVariantTrappedChestTileEntity extends AdjunctVariantChestTileEntity {
    protected AdjunctVariantTrappedChestTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public AdjunctVariantTrappedChestTileEntity() {
        super(AdjunctTiles.OLIVE_TRAPPED_CHEST.get());
    }
}
