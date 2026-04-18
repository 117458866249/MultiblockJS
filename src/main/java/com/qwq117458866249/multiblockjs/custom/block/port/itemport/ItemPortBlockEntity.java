package com.qwq117458866249.multiblockjs.custom.block.port.itemport;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBlock;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class ItemPortBlockEntity extends RandomizableContainerBlockEntity implements MultiblockPartBE, Container {
    private final int vSize;
    public ItemStackHandler vInventory;

    public ItemPortBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegister.ITEM_PORT_ENTITY.get(), pos, blockState);
        this.vSize = MultiblockJS.ITEM_SIZES.get(blockState.getBlock());
        this.vInventory = new ItemStackHandler(vSize){
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    private BlockPos vControllerPos = new BlockPos(0,0,0);

    public BlockPos getControllerPos() {
        return vControllerPos;
    }

    public void setControllerPos(BlockPos pPos) {
        vControllerPos = pPos;
        setChanged();
    }

    @Override
    public void unFormEntity() {
        ((MultiblockPartBlock)getLevel().getBlockState(getBlockPos()).getBlock()).unForm(
                getLevel().getBlockState(getBlockPos()),
                getLevel(),
                getBlockPos()
        );
    }

    // Item

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("xc",vControllerPos.getX());
        tag.putInt("yc",vControllerPos.getY());
        tag.putInt("zc",vControllerPos.getZ());
        tag.put("Inventory", vInventory.serializeNBT(registries));
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("");
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> vReturn = NonNullList.withSize(vSize,ItemStack.EMPTY);
        for (int i = 0; i < vSize; i++) {
            try {
                vReturn.set(i, vInventory.getStackInSlot(i));
            } catch (Exception ignored){}
        }        return vReturn;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        for (int i = 0;i < nonNullList.size();i++){
            try {
                setItem(i,nonNullList.get(i));
            } catch (Exception ignored) {}
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        vControllerPos = new BlockPos(
                tag.getInt("xc"),
                tag.getInt("yc"),
                tag.getInt("zc")
        );
        vInventory.deserializeNBT(registries,tag.getCompound("Inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(vSize);
        for (int i = 0; i< vSize; i++){
            inventory.setItem(i,vInventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public int getContainerSize(){
        return vSize;
    }

    public ItemStack getItem(int pIndex){
        return vInventory.getStackInSlot(pIndex);
    }

    public void setItem(int pIndex,ItemStack pStack){
        vInventory.setStackInSlot(pIndex, pStack);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }
}
