package net.onvoid.adjunct.compat.quark;

import com.google.common.base.Supplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/block/VariantChestBlock.java

public class AdjunctVariantChestBlock extends ChestBlock {

    private String path;

    public  AdjunctVariantChestBlock(String type, AbstractBlock.Properties properties, Supplier<TileEntityType<? extends ChestTileEntity>> tileSupplier) {
        super(properties, tileSupplier);
        this.path = type + "/";
    }

    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new AdjunctVariantChestTileEntity();
    }

    public String getChestTexturePath() {
        return "model/chest/" + this.path;
    }

    public boolean isTrap() {
        return false;
    }
}
