package net.onvoid.adjunct.compat.quark;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.Iterator;

// Code modified from Quark, a mod by Vazkii, all copyright belonging to Quark and its creator(s)
// Licensed under CC BY-NC-SA 3.0: https://github.com/VazkiiMods/Quark/blob/master/LICENSE.md
// File modified from: https://github.com/VazkiiMods/Quark/blob/master/src/main/java/vazkii/quark/content/building/block/WoodPostBlock.java

public class AdjunctWoodPostBlock extends Block implements IWaterLoggable {
    private static final VoxelShape SHAPE_X = Block.box(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    private static final VoxelShape SHAPE_Y = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
    private static final VoxelShape SHAPE_Z = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<Direction.Axis> AXIS;
    public static final BooleanProperty[] CHAINED;
    public Block strippedBlock = null;

    public AdjunctWoodPostBlock(Block parent, Block stripped, boolean nether) {
        super(Properties.copy(parent).sound(nether ? SoundType.STEM : SoundType.WOOD));
        BlockState state = (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(WATERLOGGED, false)).setValue(AXIS, Direction.Axis.Y);
        BooleanProperty[] var6 = CHAINED;
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            BooleanProperty prop = var6[var8];
            state = (BlockState)state.setValue(prop, false);
        }

        this.strippedBlock = stripped;
        this.registerDefaultState(state);
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if (this.strippedBlock != null && toolType == ToolType.AXE) {
            BlockState newState = this.strippedBlock.defaultBlockState();

            Property p;
            for(Iterator var8 = state.getProperties().iterator(); var8.hasNext(); newState = (BlockState)newState.setValue(p, state.getValue(p))) {
                p = (Property)var8.next();
            }

            return newState;
        } else {
            return super.getToolModifiedState(state, world, pos, player, stack, toolType);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch((Direction.Axis)state.getValue(AXIS)) {
            case X:
                return SHAPE_X;
            case Y:
                return SHAPE_Y;
            default:
                return SHAPE_Z;
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !(Boolean)state.getValue(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getState(context.getLevel(), context.getClickedPos(), context.getClickedFace().getAxis());
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        BlockState newState = this.getState(worldIn, pos, (Direction.Axis)state.getValue(AXIS));
        if (!newState.equals(state)) {
            worldIn.setBlockAndUpdate(pos, newState);
        }

    }

    private BlockState getState(World world, BlockPos pos, Direction.Axis axis) {
        BlockState state = (BlockState)((BlockState)this.defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER)).setValue(AXIS, axis);
        Direction[] var5 = Direction.values();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Direction d = var5[var7];
            if (d.getAxis() != axis) {
                BlockState sideState = world.getBlockState(pos.relative(d));
                if (sideState.getBlock() instanceof ChainBlock && sideState.getValue(BlockStateProperties.AXIS) == d.getAxis() || d == Direction.DOWN && sideState.getBlock() instanceof LanternBlock && (Boolean)sideState.getValue(LanternBlock.HANGING)) {
                    BooleanProperty prop = CHAINED[d.ordinal()];
                    state = (BlockState)state.setValue(prop, true);
                }
            }
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{WATERLOGGED, AXIS});
        BooleanProperty[] var2 = CHAINED;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            BooleanProperty prop = var2[var4];
            builder.add(new Property[]{prop});
        }

    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        AXIS = BlockStateProperties.AXIS;
        CHAINED = new BooleanProperty[]{BooleanProperty.create("chain_down"), BooleanProperty.create("chain_up"), BooleanProperty.create("chain_north"), BooleanProperty.create("chain_south"), BooleanProperty.create("chain_west"), BooleanProperty.create("chain_east")};
    }
}
