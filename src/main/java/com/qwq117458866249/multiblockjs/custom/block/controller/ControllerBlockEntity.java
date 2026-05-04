package com.qwq117458866249.multiblockjs.custom.block.controller;

import com.qwq117458866249.multiblockjs.Utils;
import com.qwq117458866249.multiblockjs.custom.block.port.feport.FEPortBlockEntity;
import com.qwq117458866249.multiblockjs.custom.block.port.fluidport.FluidPortBlockEntity;
import com.qwq117458866249.multiblockjs.custom.block.port.itemport.ItemPortBlockEntity;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControllerBlockEntity extends BlockEntity {
    // I don't want to cast it muhehehehehehehehe
    // NBT
    public String vStructure = "";

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("structure", vStructure);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        vStructure = tag.getString("structure");
    }

    public ControllerBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegister.CONTROLLER_BLOCK_ENTITY.get(), pos, blockState);
    }

    // Parse Recipe
    public int vProgress = 0;
    public Object[] vParsingRecipe = new Object[]{
            "awa",
            -1,
            "pwp"
    };

    public static List recipes = new ArrayList<>();

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, ControllerBlockEntity pEntity) {
        pEntity.eTick(pLevel, pPos, pState);
    }

    public void eTick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        if (!pState.getValue(ControllerBlock.FORMED)) {
            return;
        }
        if (vProgress == 0) {
            recipes.forEach(p -> {
                Object[] vRecipe = (Object[]) p;
                Object[][] vRequirements = (Object[][]) vRecipe[2];
                AtomicBoolean vCanParse = new AtomicBoolean(true);
                int[] vPortPos;
                if (vRecipe[0].equals(pState.getBlock())) {
                    for (Object[] pRequirement : vRequirements) {
                        if (vCanParse.get() && pRequirement[1].equals("input")) {
                            vCanParse.set(false);
                            vPortPos = Utils.getDirectionPos(new int[]{
                                            ((Number) pRequirement[2]).intValue(),
                                            ((Number) pRequirement[3]).intValue(),
                                            ((Number) pRequirement[4]).intValue()
                                    },
                                    pState.getValue(BlockStateProperties.HORIZONTAL_FACING)
                            );
                            switch ((String) pRequirement[0]) {
                                case "item" -> {
                                    if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof ItemPortBlockEntity pPort) {
                                        pPort.getItems().forEach(pStack -> {
                                            if (
                                                    pStack.is(
                                                            BuiltInRegistries.ITEM.get(
                                                                    ResourceLocation.parse(
                                                                            (String) pRequirement[5]
                                                                    )
                                                            )
                                                    ) && pStack.getCount() >= ((Number) pRequirement[6]).intValue()
                                                            && (!vCanParse.get())
                                            ) {
                                                vCanParse.set(true);
                                            }
                                        });
                                    }
                                }
                                case "fe" -> {
                                    if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof FEPortBlockEntity pPort) {
                                        vCanParse.set(pPort.storage.getEnergyStored() >= ((Number) pRequirement[5]).intValue());
                                    }
                                }
                                case "fluid" -> {
                                    if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof FluidPortBlockEntity pPort) {
                                        if (pPort.vTank.getFluid().getFluid().equals(
                                                BuiltInRegistries.FLUID.get(
                                                        ResourceLocation.parse(
                                                                (String) pRequirement[5]
                                                        )
                                                )
                                        ) &&
                                                pPort.vTank.getFluidAmount() >= ((Number) pRequirement[6]).intValue()
                                        ) {
                                            vCanParse.set(true);
                                        }
                                    }
                                }
                                case "mek" -> {

                                }
                                case "su" -> {

                                }
                                case "block" -> {
                                    vCanParse.set(level.getBlockState(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])).getBlock().equals(BuiltInRegistries.BLOCK.get(ResourceLocation.parse(pRequirement[5].toString()))));
                                }
                            }
                        }
                    }
                    if (vCanParse.get()) {
                        vParsingRecipe = vRecipe;
                        for (Object[] pRequirement : vRequirements) {
                            if (vCanParse.get() && pRequirement[1].equals("input")) {
                                vCanParse.set(false);
                                vPortPos = Utils.getDirectionPos(new int[]{
                                                ((Number) pRequirement[2]).intValue(),
                                                ((Number) pRequirement[3]).intValue(),
                                                ((Number) pRequirement[4]).intValue()
                                        },
                                        pState.getValue(BlockStateProperties.HORIZONTAL_FACING)
                                );
                                switch ((String) pRequirement[0]) {
                                    case "item" -> {
                                        if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof ItemPortBlockEntity pPort) {
                                            pPort.getItems().forEach(pStack -> {
                                                if (
                                                        pStack.is(
                                                                BuiltInRegistries.ITEM.get(
                                                                        ResourceLocation.parse(
                                                                                (String) pRequirement[5]
                                                                        )
                                                                )
                                                        ) && pStack.getCount() >= ((Number) pRequirement[6]).intValue()
                                                                && (!vCanParse.get())
                                                ) {
                                                    pStack.setCount(pStack.getCount() - ((Number) pRequirement[6]).intValue());
                                                }
                                            });
                                        }
                                    }
                                    case "fe" -> {
                                        if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof FEPortBlockEntity pPort) {
                                            pPort.storage.extractEnergy(((Number) pRequirement[5]).intValue(), false);
                                        }
                                    }
                                    case "fluid" -> {
                                        if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof FluidPortBlockEntity pPort) {
                                            if (pPort.vTank.getFluid().getFluid().equals(
                                                    BuiltInRegistries.FLUID.get(
                                                            ResourceLocation.parse(
                                                                    (String) pRequirement[5]
                                                            )
                                                    )
                                            ) &&
                                                    pPort.vTank.getFluidAmount() >= ((Number) pRequirement[6]).intValue()
                                            ) {
                                                pPort.vTank.setFluid(
                                                        new FluidStack(
                                                                pPort.vTank.getFluid().getFluid(),
                                                                pPort.vTank.getFluidAmount() - ((Number) pRequirement[6]).intValue()
                                                        )
                                                );
                                            }
                                        }
                                    }
                                    case "mek" -> {

                                    }
                                    case "su" -> {

                                    }
                                }
                            }
                        }
                    }
                }
            });
        }

        if ((Integer) vParsingRecipe[1] != -1) {
            vProgress++;
        } else {
            vProgress = 0;
        }

        if (vProgress >= (Integer) vParsingRecipe[1] && (!((Integer) vParsingRecipe[1] == -1))) {
            vProgress = 0;
            Object[][] vRequirements = (Object[][]) vParsingRecipe[2];
            int[] vPortPos;
            AtomicBoolean vIsFinished = new AtomicBoolean(false);
            for (Object[] pRequirement : vRequirements) {
                vIsFinished.set(false);
                if (pRequirement[1].equals("output")) {
                    vPortPos = Utils.getDirectionPos(new int[]{
                                    ((Number) pRequirement[2]).intValue(),
                                    ((Number) pRequirement[3]).intValue(),
                                    ((Number) pRequirement[4]).intValue()
                            },
                            pState.getValue(BlockStateProperties.HORIZONTAL_FACING)
                    );
                    switch ((String) pRequirement[0]) {
                        case "item" -> {
                            if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof ItemPortBlockEntity pPort) {
                                pPort.getItems().forEach(pStack -> {
                                    if (pStack.is(
                                            BuiltInRegistries.ITEM.get(
                                                    ResourceLocation.parse(
                                                            (String) pRequirement[5]
                                                    )
                                            )
                                    ) && (!vIsFinished.get())) {
                                        pStack.grow(((Number) pRequirement[6]).intValue());
                                    }
                                });
                                if (!vIsFinished.get()) {
                                    for (int i = 0; i < pPort.getContainerSize(); i++) {
                                        if ((!vIsFinished.get()) && pPort.getItem(i).isEmpty()) {
                                            pPort.setItem(i, new ItemStack(
                                                    BuiltInRegistries.ITEM.get(
                                                            ResourceLocation.parse(
                                                                    (String) pRequirement[5]
                                                            )
                                                    ),
                                                    ((Number) pRequirement[6]).intValue()
                                            ));
                                            vIsFinished.set(true);
                                        }
                                    }
                                    vIsFinished.set(true);
                                }
                            }
                        }
                        case "fe" -> {
                            if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof FEPortBlockEntity pPort) {
                                pPort.storage.receiveEnergy(((Number) pRequirement[5]).intValue(), false);
                            }
                        }
                        case "fluid" -> {
                            if (level.getBlockEntity(Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2])) instanceof FluidPortBlockEntity pPort) {
                                if (pPort.vTank.getFluid().getFluid().equals(
                                        BuiltInRegistries.FLUID.get(
                                                ResourceLocation.parse(
                                                        (String) pRequirement[5]
                                                )
                                        )
                                ) ||
                                        pPort.vTank.getFluidAmount() <= 0
                                ) {
                                    pPort.vTank.setFluid(
                                            new FluidStack(
                                                    BuiltInRegistries.FLUID.get(
                                                            ResourceLocation.parse(
                                                                    (String) pRequirement[5]
                                                            )
                                                    ),
                                                    pPort.vTank.getFluidAmount() + ((Number) pRequirement[6]).intValue()
                                            )
                                    );
                                }
                            }
                        }
                        case "mek" -> {

                        }
                        case "su" -> {

                        }
                        case "command" -> {
                            Utils.runCommand((String) pRequirement[5], level, Utils.getRelativePos(pPos, vPortPos[0], vPortPos[1], vPortPos[2]));
                        }
                    }
                }
            }
            vParsingRecipe = new Object[]{
                    "awa",
                    -1,
                    "pwp"
            };
        }

        // State
        if (vProgress > 0) {
            pLevel.setBlock(pPos, pState.setValue(ControllerBlock.WORKING, true), 3);
            setChanged();
        } else {
            pLevel.setBlock(pPos, pState.setValue(ControllerBlock.WORKING, false), 3);
            setChanged();
        }
        if (vProgress % 100 == 1) {
            level.playSound(
                    null,
                    pPos.getX() + 0.5,
                    pPos.getY() + 1.0,
                    pPos.getZ() + 0.5,
                    SoundEvent.createVariableRangeEvent(ResourceLocation.parse("multiblockjs:" + vStructure)),
                    SoundSource.VOICE,
                    1,
                    1
            );
        }
    }
}
