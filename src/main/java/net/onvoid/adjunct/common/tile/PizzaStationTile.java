package net.onvoid.adjunct.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.onvoid.adjunct.Adjunct;
import net.onvoid.adjunct.common.item.pizza.Pizza;
import net.onvoid.adjunct.common.item.pizza.PizzaItem;
import net.onvoid.adjunct.common.item.pizza.PizzaHandler;
import net.onvoid.adjunct.common.item.pizza.Topping;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PizzaStationTile extends TileEntity implements ICapabilityProvider {
    public PizzaStationTile() {
        super(AdjunctTiles.PIZZA_STATION_TILE.get());
    }

    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createHandler);

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
                return (stack.getItem().is(Topping.allTag()) || (stack.getItem() instanceof PizzaItem && !(new Pizza(stack, true)).isCooked()));
            }

            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.isEmpty() || !isItemValid(slot, stack)) {
                    return stack;
                }
                validateSlotIndex(slot);
                ItemStack existing = this.stacks.get(slot);
                ItemStack updated = stack.copy();
                if (stack.getItem() instanceof PizzaItem){
                    if (existing.isEmpty()) {
                        existing = ItemStack.EMPTY;
                    }
                    this.stacks.set(slot, stack);
                    onContentsChanged(slot);
                    return existing;
                } else {
                    if (existing.isEmpty()) {
                        if (!Topping.is(updated, Topping.CRUST)){
                            return stack;
                        }
                        //Create new pizza
                        this.stacks.set(slot, new Pizza(updated, false).buildStack());
                        onContentsChanged(slot);
                        updated.shrink(1);
                        return updated;
                    } else {
                        if (!(existing.getItem() instanceof PizzaItem)) {
                            this.stacks.set(slot, ItemStack.EMPTY);
                            onContentsChanged(slot);
                            return stack;
                        }
                        //Modify existing pizza
                        Pizza modified = new Pizza(existing, true);
                        if (modified.addStack(updated)){
                            this.stacks.set(slot, modified.buildStack());
                            onContentsChanged(slot);
                            updated.shrink(1);
                            return updated;
                        } else {
                            return stack;
                        }
                    }
                }
            }
        };
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(nbt.getCompound("inv")));
        return;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        itemHandler.ifPresent(i -> {
            CompoundNBT invnbt = ((INBTSerializable<CompoundNBT>) i).serializeNBT();
            nbt.put("inv", invnbt);
        });
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
        return nbtTag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag)
    {
        load(state, tag);
        //Handle data
        return;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        itemHandler.invalidate();
    }
}
