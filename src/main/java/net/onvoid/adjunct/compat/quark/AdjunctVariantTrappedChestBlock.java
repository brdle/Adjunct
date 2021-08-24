package net.onvoid.adjunct.compat.quark;

import com.google.common.base.Supplier;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.IBlockReader;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/block/VariantTrappedChestBlock.java

public class AdjunctVariantTrappedChestBlock extends AdjunctVariantChestBlock {

    private String path;

    public AdjunctVariantTrappedChestBlock(String type, Properties properties, Supplier<TileEntityType<? extends ChestTileEntity>> tileSupplier) {
        super(type, properties, tileSupplier);
        this.path = type + "/";
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new AdjunctVariantTrappedChestTileEntity();
    }

    @Override
    public boolean isTrap() {
        return true;
    }
}
