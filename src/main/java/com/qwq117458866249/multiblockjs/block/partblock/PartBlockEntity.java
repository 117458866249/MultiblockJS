package com.qwq117458866249.multiblockjs.block.partblock;

import com.qwq117458866249.multiblockjs.Utils;
import com.qwq117458866249.multiblockjs.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import com.qwq117458866249.multiblockjs.register.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PartBlockEntity extends BlockEntity implements MultiblockPartBE {
    private BlockState vBlockState = Blocks.AIR.defaultBlockState();
    private BlockPos vControllerPos = new BlockPos(0, 0, 0);

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("xc", getControllerPos().getX());
        tag.putInt("yc", getControllerPos().getY());
        tag.putInt("zc", getControllerPos().getZ());
        tag.putString("block", Utils.blockStateToString(vBlockState));
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
        vBlockState = Utils.stringToBlockState(tag.getString("block"));
        setChanged();
    }

    public PartBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegister.PART_BLOCK_ENTITY.get(), pos, blockState);
        setChanged();
    }

    public BlockPos getControllerPos() {
        return vControllerPos;
    }

    public void setControllerPos(BlockPos pPos) {
        vControllerPos = pPos;
        setChanged();
    }

    public void unFormEntity() {
        getLevel().setBlock(getBlockPos(), vBlockState, 3);
    }

    public static void formPartEntity(BlockPos pPos, Level pLevel) {
        BlockState vState = pLevel.getBlockState(pPos);
        pLevel.setBlock(pPos, BlockRegister.PART_BLOCK.get().defaultBlockState(), 3);
        if (pLevel.getBlockEntity(pPos) instanceof PartBlockEntity vPartBE) {
            vPartBE.vBlockState = vState;
        }
    }
}
