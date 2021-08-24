package net.onvoid.adjunct.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.onvoid.adjunct.AdjunctHelper;
import net.onvoid.adjunct.common.item.PizzaItem;
import net.onvoid.adjunct.handlers.PizzaHandler;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class PizzaOvenTile extends TileEntity implements ICapabilityProvider, ITickableTileEntity {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createHandler);
    private int fuelTicks;
    private int pizzaTicks;
    private boolean pizzaBurning;

    public PizzaOvenTile() {
        super(AdjunctTiles.PIZZA_OVEN_TILE.get());
        this.fuelTicks = 0;
        this.pizzaTicks = 0;
        this.pizzaBurning = false;
    }

    public void setFuelTicks(int ticks) {
        this.fuelTicks = ticks;
    }

    public void decrementFuelTicks(){
        if (this.fuelTicks > 0) {
            this.fuelTicks--;
        }
    }

    public void setPizzaTicks(int ticks){
        this.pizzaTicks = ticks;
    }

    public void incrementPizzaTicks(){
        this.pizzaTicks++;
    }

    public void setPizzaBurning(boolean burning){
        this.pizzaBurning = burning;
    }

    public int getFuelTicks(){
        return this.fuelTicks;
    }

    public int getPizzaTicks(){
        return this.pizzaTicks;
    }

    public boolean isPizzaBurning(){
        return this.pizzaBurning;
    }

    public void setChanged(){
        //Potentially problematic is something else calls setChanged()
        this.setPizzaTicks(0);
        this.setPizzaBurning(false);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public IItemHandler createHandler() {
        return new ItemStackHandler(1){
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack)
            {
                return PizzaHandler.isCrust(stack) || stack.getItem() instanceof PizzaItem;
            }

            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                if (!isItemValid(slot, stack)) {
                    return stack;
                }
                validateSlotIndex(slot);
                ItemStack pizza;
                if (this.stacks.get(slot).isEmpty()){
                    if (PizzaHandler.isCrust(stack)){
                        pizza = PizzaHandler.buildPizza(PizzaHandler.getCrustFromItem(stack), 0, 0, 0, 0, false);
                    } else if (stack.getItem() instanceof PizzaItem){
                        pizza = stack.copy();
                    } else {
                        return stack;
                    }
                    this.stacks.set(slot, pizza);
                    stack.shrink(1);
                    return stack;
                } else {
                    return stack;
                }
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate){
                ItemStack inSlot = this.stacks.get(slot);
                this.stacks.set(slot, ItemStack.EMPTY);
                return inSlot;
            }
        };
    }

    @Override
    public void tick() {
        IItemHandler handler = (IItemHandler) itemHandler.orElse(null);
        if (handler != null && !handler.getStackInSlot(0).isEmpty()) {
            if (!this.getLevel().isClientSide()) {
                if (this.getFuelTicks() > 0) {
                    if (this.getPizzaTicks() == PizzaHandler.COOK_TIME * 40) {
                        if (this.isPizzaBurning()) {
                            handler.extractItem(0, 1, false);
                            this.setChanged();
                            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
                            setPizzaBurning(false);
                            setPizzaTicks(0);
                        } else {
                            setPizzaBurning(true);
                        }
                    } else if (this.getPizzaTicks() == PizzaHandler.COOK_TIME * 20) {
                        CompoundNBT nbt = handler.getStackInSlot(0).getOrCreateTag();
                        nbt.putBoolean("cooked", true);
                        setPizzaBurning(true);
                    }
                    this.incrementPizzaTicks();
                }
                this.decrementFuelTicks();
            } else {
                // TODO: SEND PACKET
                //if (getFuelTicks() > 0) {
                    this.makeParticles();
                //}
            }
        }
    }

    private void makeParticles() {
        World world = this.getLevel();
        if (world != null) {
            BlockPos blockpos = this.getBlockPos();
            Random random = world.getRandom();
            if (random.nextFloat() < 0.03F) {
                for(int i = 0; i < random.nextInt(2) + 2; ++i) {
                    world.addAlwaysVisibleParticle(ParticleTypes.SMOKE, true, (double)blockpos.getX() + 0.5D + random.nextDouble() / 5.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockpos.getY() + 0.5D + random.nextDouble(), (double)blockpos.getZ() + 0.5D + random.nextDouble() / 5.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.08D, 0.0D);
                }
            }
            if (random.nextFloat() < 0.2F) {
                BasicParticleType type;
                if (random.nextInt(3) == 0){
                    type = ParticleTypes.FLAME;
                } else {
                    type = ParticleTypes.WHITE_ASH;
                }
                world.addAlwaysVisibleParticle(type, true, (double)blockpos.getX() + 0.5D + random.nextDouble() / 10.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockpos.getY() + random.nextDouble(), (double)blockpos.getZ() + 0.5D + random.nextDouble() / 10.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.01D, 0.0D);
            }
            if (random.nextFloat() < 0.07F) {
                world.addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, (double)blockpos.getX() + 0.5D + random.nextDouble() / 5.0D * (double)(random.nextBoolean() ? 1 : -1), (double)blockpos.getY() + 0.5D + random.nextDouble(), (double)blockpos.getZ() + 0.5D + random.nextDouble() / 5.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.08D, 0.0D);
            }
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(nbt.getCompound("inv")));
        this.setFuelTicks(nbt.getInt("fuel"));
        this.setPizzaTicks(nbt.getInt("pizza"));
        return;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        itemHandler.ifPresent(i -> {
            CompoundNBT invnbt = ((INBTSerializable<CompoundNBT>) i).serializeNBT();
            nbt.put("inv", invnbt);
        });
        nbt.putInt("fuel", getFuelTicks());
        nbt.putInt("pizza", getPizzaTicks());
        return nbt;
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket(){
        CompoundNBT nbtTag = new CompoundNBT();
        save(nbtTag);
        //Write data
        return new SUpdateTileEntityPacket(getBlockPos(), -1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
        CompoundNBT tag = pkt.getTag();
        load(getBlockState(), tag);
        //Handle data
        return;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbtTag = new CompoundNBT();
        save(nbtTag);
        //Write data
        nbtTag.putInt("fuel", getFuelTicks());
        nbtTag.putInt("pizza", getPizzaTicks());
        return nbtTag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag)
    {
        load(state, tag);
        //Handle data
        this.setFuelTicks(tag.getInt("fuel"));
        this.setPizzaTicks(tag.getInt("pizza"));
        return;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandler.invalidate();
    }
}
