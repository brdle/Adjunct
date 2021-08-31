package net.onvoid.adjunct.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.onvoid.adjunct.AdjunctHelper;
import net.onvoid.adjunct.common.item.pizza.Topping;
import net.onvoid.adjunct.common.tile.PizzaStationTile;

import java.util.UUID;

public class PizzaStationBlock extends FlammableBlock implements IForgeBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public PizzaStationBlock(Material material, MaterialColor color, float hardness, float explosionResistance, SoundType sound, ToolType harvestTool, int flammability, int spreadSpeed) {
        super(material, color, hardness, explosionResistance, sound, harvestTool, flammability, spreadSpeed);
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
        return new PizzaStationTile();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.isClientSide()) {
            return ActionResultType.SUCCESS;
        }
        ItemStack insert = player.getItemInHand(hand);
        if (!insert.isEmpty() && insert.getCount() >= 1 && world.getBlockEntity(pos) instanceof PizzaStationTile) {
            PizzaStationTile pizzaStation = (PizzaStationTile) world.getBlockEntity(pos);
            IItemHandler handler = (IItemHandler) pizzaStation.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                ItemStack remaining = null;
                if (insert.hasContainerItem()){
                    remaining = insert.getContainerItem();
                }
                ItemStack retrieved = handler.insertItem(0, insert, false);
                if (!player.isCreative()){
                    player.setItemInHand(hand, retrieved);
                    if (retrieved.isEmpty() && remaining != null){
                        AdjunctHelper.giveToHand(player, hand, remaining);
                    }
                }
                pizzaStation.setChanged();
                world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public void attack(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClientSide()){
            return;
        }
        if (!player.isCreative() && world.getBlockEntity(pos) instanceof PizzaStationTile){
            PizzaStationTile pizzaStation = (PizzaStationTile) world.getBlockEntity(pos);
            IItemHandler handler = (IItemHandler) pizzaStation.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                ItemStack retrieved = handler.getStackInSlot(0);
                ((ServerPlayerEntity)player).sendMessage(new StringTextComponent(Topping.crusts.toString()), Util.NIL_UUID);
                if (!retrieved.isEmpty()) {
                    retrieved = handler.extractItem(0, 1, false);
                    AdjunctHelper.giveToHand(player, player.getUsedItemHand(), retrieved);
                    pizzaStation.setChanged();
                    world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.BLOCK_UPDATE);
                    return;
                }
            }
        }
    }
}
