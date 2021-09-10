package net.onvoid.adjunct.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.AdjunctHelper;
import net.onvoid.adjunct.common.tile.DisplayClothTile;

public class DisplayClothBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public DisplayClothBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.getStateDefinition().any().setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new DisplayClothTile();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.isClientSide()) {
            return ActionResultType.SUCCESS;
        }
        ItemStack insert = player.getItemInHand(hand);
        TileEntity entity = world.getBlockEntity(pos);
        if (!insert.isEmpty() && insert.getCount() >= 1 && entity instanceof DisplayClothTile) {
            DisplayClothTile displayCloth = (DisplayClothTile) entity;
            IItemHandler handler = (IItemHandler) displayCloth.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                if (handler.getStackInSlot(0).isEmpty()) {
                    player.setItemInHand(hand, handler.insertItem(0, insert, false));
                    displayCloth.setChanged();
                    world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.BLOCK_UPDATE);
                } else {
                    Adjunct.LOGGER.debug(handler.getStackInSlot(0));
                }
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void attack(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClientSide()){
            return;
        }
        if (!player.isCreative() && world.getBlockEntity(pos) instanceof DisplayClothTile){
            DisplayClothTile displayCloth = (DisplayClothTile) world.getBlockEntity(pos);
            IItemHandler handler = (IItemHandler) displayCloth.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                ItemStack retrieved = handler.getStackInSlot(0);
                if (!retrieved.isEmpty()) {
                    retrieved = handler.extractItem(0, 1, false);
                    AdjunctHelper.giveToHand(player, player.getUsedItemHand(), retrieved);
                    displayCloth.setChanged();
                    world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.BLOCK_UPDATE);
                    return;
                }
            }
        }
    }

    public VoxelShape getShape(BlockState pState, IBlockReader pLevel, BlockPos pPos, ISelectionContext pContext) {
        return SHAPE;
    }

    /**
     * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face passed in.
     */
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, IWorld pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public boolean canSurvive(BlockState pState, IWorldReader pLevel, BlockPos pPos) {
        return !pLevel.isEmptyBlock(pPos.below());
    }
}
