package com.qwq117458866249.multiblockjs.block.custompartblock;

import com.qwq117458866249.multiblockjs.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.block.MultiblockPartBlock;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CustomPartBE extends BlockEntity implements MultiblockPartBE {
    private BlockPos vControllerPos = new BlockPos(0, 0, 0);

    public CustomPartBE(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegister.CUSTOM_PART_ENTITY.get(), pos, blockState);
    }

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

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("xc", getControllerPos().getX());
        tag.putInt("yc", getControllerPos().getY());
        tag.putInt("zc", getControllerPos().getZ());
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
        setChanged();
    }
}
