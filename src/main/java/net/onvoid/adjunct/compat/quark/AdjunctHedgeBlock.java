package net.onvoid.adjunct.compat.quark;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.onvoid.adjunct.handlers.TagHandler;
import java.util.function.BooleanSupplier;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/block/HedgeBlock.java

public class AdjunctHedgeBlock extends FenceBlock {

    final Block leaf;
    private BooleanSupplier enabledSupplier = () -> {
        return true;
    };
    public static final BooleanProperty EXTEND = BooleanProperty.create("extend");

    public AdjunctHedgeBlock(Block.Properties properties, Block fence, Block leaf) {
        super(properties);
        this.leaf = leaf;
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(EXTEND, false));
    }

    @Override
    public boolean connectsTo(BlockState state, boolean isSideSolid, Direction direction) {
        return state.getBlock().is(TagHandler.HEDGES);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return facing == Direction.UP && !(Boolean)state.getValue(WATERLOGGED) && plantable.getPlantType(world, pos) == PlantType.PLAINS;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos down = blockpos.below();
        BlockState downState = iblockreader.getBlockState(down);
        return (BlockState)super.getStateForPlacement(context).setValue(EXTEND, downState.getBlock().is(TagHandler.HEDGES));
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if ((Boolean)stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return facing == Direction.DOWN ? (BlockState)stateIn.setValue(EXTEND, facingState.getBlock().is(TagHandler.HEDGES)) : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(new Property[]{EXTEND});
    }

    @OnlyIn(Dist.CLIENT)
    public IBlockColor getBlockColor() {
        BlockColors colors = Minecraft.getInstance().getBlockColors();
        BlockState leafState = this.leaf.defaultBlockState();
        return (state, world, pos, tintIndex) -> {
            return colors.getColor(leafState, world, pos, tintIndex);
        };
    }

    @OnlyIn(Dist.CLIENT)
    public IItemColor getItemColor() {
        ItemColors colors = Minecraft.getInstance().getItemColors();
        ItemStack leafStack = new ItemStack(this.leaf);
        return (stack, tintIndex) -> {
            return colors.getColor(leafStack, tintIndex);
        };
    }

    public AdjunctHedgeBlock setCondition(BooleanSupplier enabledSupplier) {
        this.enabledSupplier = enabledSupplier;
        return this;
    }

    public boolean doesConditionApply() {
        return this.enabledSupplier.getAsBoolean();
    }
}