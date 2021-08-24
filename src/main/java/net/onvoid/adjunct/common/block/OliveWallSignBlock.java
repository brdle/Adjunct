package net.onvoid.adjunct.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.onvoid.adjunct.common.tile.OliveSignTileEntity;

public class OliveWallSignBlock extends WallSignBlock {
    public OliveWallSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public boolean hasTileEntity(BlockState stateIn) {
        return true;
    }

    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new OliveSignTileEntity();
    }
}
