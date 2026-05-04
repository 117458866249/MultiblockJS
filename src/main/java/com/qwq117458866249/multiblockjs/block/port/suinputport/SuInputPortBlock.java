package com.qwq117458866249.multiblockjs.block.port.suinputport;

import com.qwq117458866249.multiblockjs.block.AllMultiblockPartBlock;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class SuInputPortBlock extends RotatedPillarKineticBlock implements AllMultiblockPartBlock, IBE<SuInputPortBlockEntity> {
    public boolean noOnBreak = false;



    public SuInputPortBlock(BlockBehaviour.Properties properties) {
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

    @Override
    public Class<SuInputPortBlockEntity> getBlockEntityClass() {
        return SuInputPortBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SuInputPortBlockEntity> getBlockEntityType() {
        return BlockEntityRegister.SU_INPUT_PORT_ENTITY.get();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return blockState.getValue(AXIS);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return true;
    }
}
