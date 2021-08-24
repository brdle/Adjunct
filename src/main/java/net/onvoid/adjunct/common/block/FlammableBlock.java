package net.onvoid.adjunct.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class FlammableBlock extends Block {
    private int flammability;
    private int spreadSpeed;

    public FlammableBlock(Material material, MaterialColor color, float hardness, float explosionResistance, SoundType sound, ToolType harvestTool, int flammability, int spreadSpeed) {
        super(AbstractBlock.Properties.of(material, color).harvestTool(harvestTool).strength(hardness, explosionResistance).sound(sound));
        this.flammability = flammability;
        this.spreadSpeed = spreadSpeed;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return this.spreadSpeed;
    }

    @Override
    public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return this.flammability;
    }
}
