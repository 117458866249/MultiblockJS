package com.qwq117458866249.multiblockjs.custom.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public abstract class MultiblockPartBlock extends BaseEntityBlock {
    public static final BooleanProperty FORMED = BooleanProperty.create("formed");
    public boolean noOnBreak = false;

    public MultiblockPartBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FORMED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FORMED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FORMED, false);
    }

    public void form(BlockState pState, Level pLevel, BlockPos pPos) {
        noOnBreak = true;
        pLevel.setBlock(pPos, pState.setValue(FORMED, true), 3);
        noOnBreak = false;
    }

    public void unForm(BlockState pState, Level pLevel, BlockPos pPos) {
        noOnBreak = true;
        pLevel.setBlock(pPos, pState.setValue(FORMED, false), 3);
        noOnBreak = false;
    }
}
