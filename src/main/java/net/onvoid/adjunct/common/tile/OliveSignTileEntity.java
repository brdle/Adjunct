package net.onvoid.adjunct.common.tile;

import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class OliveSignTileEntity extends SignTileEntity {

    @Override
    public TileEntityType<OliveSignTileEntity> getType(){
        return AdjunctTiles.OLIVE_SIGN.get();
    }
}
