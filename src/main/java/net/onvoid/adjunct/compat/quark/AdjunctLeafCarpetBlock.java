package net.onvoid.adjunct.compat.quark;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/block/LeafCarpetBlock.java

public class AdjunctLeafCarpetBlock extends Block {

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

    private final BlockState baseState;
    private ItemStack baseStack;

    public AdjunctLeafCarpetBlock(Block.Properties properties, Block base) {
        super(properties);

        baseState = base.defaultBlockState();
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, ISelectionContext p_220071_4_) {
        return VoxelShapes.empty();
    }

    public IItemColor getItemColor() {
        if(baseStack == null)
            baseStack = new ItemStack(baseState.getBlock());

        return (stack, tintIndex) -> Minecraft.getInstance().getItemColors().getColor(baseStack, tintIndex);
    }

    public IBlockColor getBlockColor() {
        return (state, worldIn, pos, tintIndex) -> Minecraft.getInstance().getBlockColors().getColor(baseState, worldIn, pos, tintIndex);
    }

}