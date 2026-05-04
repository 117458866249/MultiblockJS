package com.qwq117458866249.multiblockjs.block.port.suinputport;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.block.AllMultiblockPartBlock;
import com.qwq117458866249.multiblockjs.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class SuInputPortBlockEntity extends KineticBlockEntity implements MultiblockPartBE {
    public int requiredStress;
    public SuInputPortBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.SU_INPUT_PORT_ENTITY.get(), pos, state);
        requiredStress = MultiblockJS.REQUIRED_STRESSES.get(state.getBlock());
    }

    @Override
    public float calculateStressApplied() {
        lastStressApplied = requiredStress;
        return requiredStress;
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
        ((AllMultiblockPartBlock) getLevel().getBlockState(getBlockPos()).getBlock()).unForm(
                getLevel().getBlockState(getBlockPos()),
                getLevel(),
                getBlockPos()
        );
    }

    @Override
    public void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries,false);
        tag.putInt("xc", getControllerPos().getX());
        tag.putInt("yc", getControllerPos().getY());
        tag.putInt("zc", getControllerPos().getZ());
        setChanged();
    }

    @Override
    public void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries,false);
        vControllerPos = new BlockPos(
                tag.getInt("xc"),
                tag.getInt("yc"),
                tag.getInt("zc")
        );
        setChanged();
    }
}
