package com.qwq117458866249.multiblockjs.custom.block;

import net.minecraft.core.BlockPos;

/*
    Add these code pls
    private BlockPos vControllerPos = new BlockPos(0,0,0);

    public BlockPos getControllerPos() {
        return vControllerPos;
    }

    public void setControllerPos(BlockPos pPos) {
        vControllerPos = pPos;
        setChanged();
    }

    public void unFormEntity(){
        ((MultiblockPartBlock)getLevel().getBlockState(getBlockPos()).getBlock()).unForm(
                getLevel().getBlockState(getBlockPos()),
                getLevel(),
                getBlockPos()
        );
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("xc",getControllerPos().getX());
        tag.putInt("yc",getControllerPos().getY());
        tag.putInt("zc",getControllerPos().getZ());
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
 */
public interface MultiblockPartBE {
    BlockPos getControllerPos();

    void setControllerPos(BlockPos pPos);

    void unFormEntity();
}
