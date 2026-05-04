package com.qwq117458866249.multiblockjs.block.port.feport;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.block.MultiblockPartBlock;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;

public class FEPortBlockEntity extends BlockEntity implements MultiblockPartBE {
    public int size;
    public FEPortEnergyStorage storage;

    public FEPortBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegister.FE_PORT_ENTITY.get(), pos, blockState);
        size = MultiblockJS.FE_SIZES.get(blockState.getBlock());
        storage = new FEPortEnergyStorage(size, this::onEnergyChanged);
    }

    private BlockPos vControllerPos = new BlockPos(0, 0, 0);

    public BlockPos getControllerPos() {
        return vControllerPos;
    }

    public void setControllerPos(BlockPos pPos) {
        vControllerPos = pPos;
        setChanged();
    }

    public void unFormEntity() {
        ((MultiblockPartBlock) getLevel().getBlockState(getBlockPos()).getBlock()).unForm(
                getLevel().getBlockState(getBlockPos()),
                getLevel(),
                getBlockPos()
        );
    }

    // Energy
    private void onEnergyChanged() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("xc", getControllerPos().getX());
        tag.putInt("yc", getControllerPos().getY());
        tag.putInt("zc", getControllerPos().getZ());
        tag.putInt("Energy", storage.getEnergyStored());
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        vControllerPos = new BlockPos(
                tag.getInt("xc"),
                tag.getInt("yc"),
                tag.getInt("zc")
        );
        storage.setEnergy(tag.getInt("Energy"));
        setChanged();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.putInt("Energy", storage.getEnergyStored());
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
        if (tag.contains("Energy")) {
            storage.setEnergy(tag.getInt("Energy"));
        }
    }

    public static class FEPortEnergyStorage extends EnergyStorage {
        public Runnable onChanged;

        public FEPortEnergyStorage(int capacity, Runnable pOnChanged) {
            super(capacity);
            this.onChanged = pOnChanged;
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int received = super.receiveEnergy(maxReceive, simulate);
            if (!simulate && received > 0) {
                onChanged.run();
            }
            return received;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            int extracted = super.extractEnergy(maxExtract, simulate);
            if (!simulate && extracted > 0) {
                onChanged.run();
            }
            return extracted;
        }

        public void setEnergy(int energy) {
            this.energy = Math.max(0, Math.min(capacity, energy));
        }
    }
}
