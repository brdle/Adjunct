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
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DisplayClothTile extends TileEntity implements ICapabilityProvider {
    private LazyOptional<IItemHandler> itemHandler = LazyOptional.of(this::createHandler);

    public DisplayClothTile() {
        super(AdjunctTiles.DISPLAY_CLOTH_TILE.get());
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
                return true;
            }

            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.isEmpty()) {
                    return stack;
                }
                if (!isItemValid(slot, stack)) {
                    return stack;
                }
                validateSlotIndex(slot);
                ItemStack insert = stack.copy();
                insert.setCount(1);
                this.stacks.set(slot, insert);
                stack.shrink(1);
                return stack;
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
