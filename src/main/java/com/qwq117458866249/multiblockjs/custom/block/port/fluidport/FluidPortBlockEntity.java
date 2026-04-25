package com.qwq117458866249.multiblockjs.custom.block.port.fluidport;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBlock;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class FluidPortBlockEntity extends BlockEntity implements MultiblockPartBE {
    private final int vSize;
    public FluidTank vTank;

    public FluidPortBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegister.FLUID_PORT_ENTITY.get(), pos, blockState);
        this.vSize = MultiblockJS.FLUID_SIZES.get(blockState.getBlock());
        this.vTank = new FluidTank(vSize) {
            @Override
            protected void onContentsChanged() {
                FluidPortBlockEntity.this.setChanged();
                if (level != null && !level.isClientSide) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            }
        };
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

    // Fluid
    public IFluidHandler getFluidHandler() {
        return vTank;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("xc", getControllerPos().getX());
        tag.putInt("yc", getControllerPos().getY());
        tag.putInt("zc", getControllerPos().getZ());
        CompoundTag tankTag = new CompoundTag();
        vTank.writeToNBT(registries, tankTag);
        tag.put("Tank", tankTag);
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
        if (tag.contains("Tank")) {
            vTank.readFromNBT(registries, tag.getCompound("Tank"));
        }
        setChanged();
    }
}
