package com.qwq117458866249.multiblockjs.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface AllMultiblockPartBlock {
    BooleanProperty FORMED = BooleanProperty.create("formed");
    void form(BlockState pState, Level pLevel, BlockPos pPos);
    void unForm(BlockState pState, Level pLevel, BlockPos pPos);
}