package com.qwq117458866249.multiblockjs.custom.event.any;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.Utils;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBE;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBlock;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlock;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlockEntity;
import com.qwq117458866249.multiblockjs.custom.block.partblock.PartBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.ArrayList;

@EventBusSubscriber(modid = MultiblockJS.MOD_ID)
public class UnFormMultiBlock {
    public static ArrayList structures = new ArrayList<>();

    @SubscribeEvent
    public static void unFormStructure(BlockEvent.BreakEvent event) {

        if (structures.isEmpty()) {
            return;
        }

        ControllerBlockEntity vUnFormEntity;
        vUnFormEntity = null;

        if (event.getState().getBlock() instanceof ControllerBlock) {
            if (!event.getState().getValue(ControllerBlock.FORMED)) {
                return;
            }
        } else if (event.getLevel().getBlockEntity(event.getPos()) instanceof MultiblockPartBE pPart) {
            if (!(event.getLevel().getBlockState(event.getPos()).getBlock() instanceof PartBlock)) {
                if (event.getLevel().getBlockState(event.getPos()).getBlock() instanceof MultiblockPartBlock) {
                    if (!event.getLevel().getBlockState(event.getPos()).getValue(MultiblockPartBlock.FORMED)) {
                        return;
                    }
                }
            }
            event.setCanceled(true);
            MultiblockJS.LOGGER.debug(pPart.getControllerPos().toString());
            vUnFormEntity = (ControllerBlockEntity) event.getLevel().getBlockEntity(pPart.getControllerPos());
        } else {
            return;
        }

        try {
            ((ControllerBlock) vUnFormEntity.getBlockState().getBlock()).unForm(vUnFormEntity.getBlockState(), (Level) event.getLevel(), vUnFormEntity.getBlockPos());
        } catch (Exception ignored) {
        }

        try {
            ControllerBlockEntity fUnFormEntity = vUnFormEntity;
            if (vUnFormEntity != null) {
                structures.forEach(p -> {
                    Object[] pStructure = (Object[]) p;
                    if (fUnFormEntity.vStructure.equals(pStructure[0])) {
                        Object[][] pParts = (Object[][]) pStructure[2];
                        int[] vPos;

                        for (Object[] pPart : pParts) {
                            vPos = Utils.getDirectionPos(new int[]{
                                    ((Number) pPart[0]).intValue(),
                                    ((Number) pPart[1]).intValue(),
                                    ((Number) pPart[2]).intValue()
                            }, event.getLevel().getBlockState(fUnFormEntity.getBlockPos()).getValue(BlockStateProperties.HORIZONTAL_FACING));

                            Utils.unFormBlock(Utils.getRelativePos(fUnFormEntity.getBlockPos(), vPos[0], vPos[1], vPos[2]), (Level) event.getLevel());
                        }
                    }
                });
            }
        } catch (Exception ignored) {
        }

        if (vUnFormEntity == null) {
            structures.forEach(p -> {
                Object[] pStructure = (Object[]) p;
                if (BuiltInRegistries.BLOCK.wrapAsHolder(event.getState().getBlock()).getRegisteredName().equals(pStructure[1])) {
                    Object[][] pParts = (Object[][]) pStructure[2];
                    int[] vPos;

                    for (Object[] pPart : pParts) {
                        vPos = Utils.getDirectionPos(new int[]{
                                ((Number) pPart[0]).intValue(),
                                ((Number) pPart[1]).intValue(),
                                ((Number) pPart[2]).intValue()
                        }, event.getLevel().getBlockState(event.getPos()).getValue(BlockStateProperties.HORIZONTAL_FACING));

                        if (event.getLevel().getBlockEntity(Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2])) instanceof MultiblockPartBE pSinglePart) {
                            MultiblockJS.LOGGER.debug(pSinglePart.getControllerPos().toString() + " " + event.getPos());
                            if (pSinglePart.getControllerPos().equals(event.getPos())) {
                                Utils.unFormBlock(Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2]), (Level) event.getLevel());
                            }
                        }
                    }
                }
            });
        }
    }
}