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
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ItemPortBlockEntity extends BaseContainerBlockEntity implements MultiblockPartBE {
    private final int size;
    private NonNullList<ItemStack> items;

    public ItemPortBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegister.ITEM_PORT_ENTITY.get(), pos, blockState);
        this.size = MultiblockJS.ITEM_SIZES.get(blockState.getBlock());
        items = NonNullList.withSize(size, ItemStack.EMPTY);
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
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("xc",vControllerPos.getX());
        tag.putInt("yc",vControllerPos.getY());
        tag.putInt("zc",vControllerPos.getZ());
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    protected Component getDefaultName() {
        return Component.literal("");
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        items = NonNullList.withSize(size, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items, registries);
        vControllerPos = new BlockPos(
                tag.getInt("xc"),
                tag.getInt("yc"),
                tag.getInt("zc")
        );
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(size);
        for (int i = 0;i<size;i++){
            inventory.setItem(i,getItem(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }
}
