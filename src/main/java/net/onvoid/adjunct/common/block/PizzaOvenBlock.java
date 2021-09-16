package net.onvoid.adjunct.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.AdjunctHelper;
import net.onvoid.adjunct.common.item.pizza.Pizza;
import net.onvoid.adjunct.common.item.pizza.PizzaItem;
import net.onvoid.adjunct.common.tile.PizzaOvenTile;
import net.minecraft.state.StateContainer.Builder;
import net.onvoid.adjunct.common.item.pizza.PizzaHandler;


public class PizzaOvenBlock extends Block implements IForgeBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public PizzaOvenBlock(AbstractBlock.Properties properties) {
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
    public void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PizzaOvenTile();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (world.isClientSide()) {
            return ActionResultType.CONSUME;
        }
        ItemStack insert = player.getItemInHand(hand);
        TileEntity entity = world.getBlockEntity(pos);
        if (!insert.isEmpty() && insert.getCount() >= 1 && entity instanceof PizzaOvenTile) {
            PizzaOvenTile pizzaOven = (PizzaOvenTile) entity;
            int burnTime = ForgeHooks.getBurnTime(insert);
            if (burnTime > 0 && !(insert.getItem() instanceof PizzaItem)) {
                int total = pizzaOven.getFuelTicks() + burnTime;
                ((ServerPlayerEntity)player).sendMessage(
                        new TranslationTextComponent("block." + Adjunct.MODID + ".pizza_oven")
                                .withStyle(TextFormatting.GRAY)
                                .append(new TranslationTextComponent(Adjunct.MODID + ":desc.pizza_oven_fueling")
                                        .withStyle(TextFormatting.WHITE))
                                .append(new StringTextComponent(burnTime / 20 + " ")
                                        .withStyle(TextFormatting.GRAY))
                                .append(new TranslationTextComponent(Adjunct.MODID + ":desc.seconds")
                                        .withStyle(TextFormatting.GRAY))
                                .append(new StringTextComponent(" (")
                                        .withStyle(TextFormatting.WHITE))
                                .append(new StringTextComponent(total / 20 + " ")
                                        .withStyle(TextFormatting.GRAY))
                                .append(new TranslationTextComponent(Adjunct.MODID + ":desc.pizza_oven_fueling_1")
                                        .withStyle(TextFormatting.WHITE)), ChatType.GAME_INFO, Util.NIL_UUID);
                pizzaOven.setFuelTicks(total);
                insert.shrink(1);
                world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.BLOCK_UPDATE);
                return ActionResultType.CONSUME;
            }
            IItemHandler handler = (IItemHandler) pizzaOven.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                if (!handler.getStackInSlot(0).isEmpty()){
                    ((ServerPlayerEntity)player).sendMessage(
                            new TranslationTextComponent("item." + Adjunct.MODID + ".pizza")
                                    .withStyle(TextFormatting.GRAY)
                                    .append(new TranslationTextComponent(Adjunct.MODID + ":desc.pizza_oven_not_empty")
                                            .withStyle(TextFormatting.RED)), ChatType.GAME_INFO, Util.NIL_UUID);
                    return ActionResultType.CONSUME;
                }
                player.setItemInHand(hand, handler.insertItem(0, insert, false));
                if (player.getItemInHand(hand).isEmpty() && !handler.getStackInSlot(0).isEmpty()){
                    pizzaOven.setPizzaBurning(false);
                    pizzaOven.setPizzaTicks(0);
                    if (pizzaOven.getFuelTicks() >= PizzaHandler.COOK_TIME * 20) {
                        //success
                        ((ServerPlayerEntity) player).sendMessage(
                                new TranslationTextComponent("item." + Adjunct.MODID + ".pizza")
                                        .withStyle(TextFormatting.GRAY)
                                        .append(new TranslationTextComponent(Adjunct.MODID + ":desc.pizza_oven_inserting")
                                                .withStyle(TextFormatting.WHITE))
                                        .append(new StringTextComponent(PizzaHandler.COOK_TIME + " ")
                                                .withStyle(TextFormatting.GRAY))
                                        .append(new TranslationTextComponent(Adjunct.MODID + ":desc.seconds")
                                                .withStyle(TextFormatting.GRAY)), ChatType.GAME_INFO, Util.NIL_UUID);
                    } else {
                        //not enough fuel
                        /*((ServerPlayerEntity) player).sendMessage(
                                new TranslationTextComponent("item." + Adjunct.MODID + ".pizza")
                                        .withStyle(TextFormatting.GRAY)
                                        .append(new TranslationTextComponent(Adjunct.MODID + ":desc.pizza_oven_not_enough_fuel")
                                                .withStyle(TextFormatting.RED))
                                        .append(new StringTextComponent((pizzaOven.getFuelTicks() / 20) + " ")
                                                .withStyle(TextFormatting.GRAY))
                                        .append(new TranslationTextComponent(Adjunct.MODID + ":desc.seconds")
                                                .withStyle(TextFormatting.GRAY))
                                        .append(new TranslationTextComponent(Adjunct.MODID + ":desc.pizza_oven_not_enough_fuel_1")
                                                .withStyle(TextFormatting.RED)), ChatType.GAME_INFO, Util.NIL_UUID);*/
                        ((ServerPlayerEntity)player).sendMessage(
                                new StringTextComponent("I: " + insert.getOrCreateTag().toString()), ChatType.CHAT, Util.NIL_UUID);
                        Pizza p = new Pizza(insert, true);
                        ((ServerPlayerEntity)player).sendMessage(
                                new StringTextComponent("P: " + p.getNbt().toString() + " TOPS: " + p.getNumToppings()), ChatType.CHAT, Util.NIL_UUID);
                        Pizza p2 = new Pizza(p.getLesserPizza(), true);
                        ((ServerPlayerEntity)player).sendMessage(
                                new StringTextComponent("LT: " + p2.getNbt().toString() + " P2T: " + p2.getNumToppings()), ChatType.CHAT, Util.NIL_UUID);
                    }
                }
                pizzaOven.setChanged();
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
        if (!player.isCreative() && world.getBlockEntity(pos) instanceof PizzaOvenTile){
            PizzaOvenTile pizzaOven = (PizzaOvenTile) world.getBlockEntity(pos);
            IItemHandler handler = (IItemHandler) pizzaOven.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
            if (handler != null) {
                ItemStack retrieved = handler.getStackInSlot(0);
                if (!retrieved.isEmpty()) {
                    retrieved = handler.extractItem(0, 1, false);
                    AdjunctHelper.giveToHand(player, player.getUsedItemHand(), retrieved);
                    pizzaOven.setPizzaBurning(false);
                    pizzaOven.setPizzaTicks(0);
                    pizzaOven.setChanged();
                    world.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.BLOCK_UPDATE);
                    return;
                }
            }
        }
    }
}
