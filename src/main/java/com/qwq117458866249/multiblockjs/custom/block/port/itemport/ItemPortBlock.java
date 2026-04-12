package com.qwq117458866249.multiblockjs.custom.block.port.itemport;

import com.mojang.serialization.MapCodec;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ItemPortBlock extends MultiblockPartBlock implements EntityBlock {
    public ItemPortBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ItemPortBlock::new);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ItemPortBlockEntity(blockPos,blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!noOnBreak) {
            if (level.getBlockEntity(pos) instanceof ItemPortBlockEntity pPort) {
                pPort.drops();
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }
}
