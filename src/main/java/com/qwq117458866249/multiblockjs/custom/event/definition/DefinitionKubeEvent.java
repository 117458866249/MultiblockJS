package com.qwq117458866249.multiblockjs.custom.event.definition;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.Utils;
import com.qwq117458866249.multiblockjs.custom.block.MultiblockPartBlock;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlock;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlockEntity;
import com.qwq117458866249.multiblockjs.custom.event.any.UnFormMultiBlock;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;

public class DefinitionKubeEvent implements KubeEvent {
    public static ArrayList structures = new ArrayList<>();

    public DefinitionKubeEvent(PlayerInteractEvent.RightClickBlock event){
        UnFormMultiBlock.structures = structures;
        if (event.getLevel().isClientSide()){
            structures = new ArrayList<>();
            return;
        }

        if (
                !event.getEntity().isShiftKeyDown()||
                        !event.getItemStack().is(Items.AIR)||
                        event.getHand().equals(InteractionHand.OFF_HAND)
        ){
            structures = new ArrayList<>();
            return;
        }

        if(
                event.getLevel().getBlockEntity(event.getPos()) instanceof ControllerBlockEntity pEntity &&
                        event.getLevel().getBlockState(event.getPos()).getBlock() instanceof ControllerBlock pBlock
        ){
            final MutableComponent[] vFailSaid = {Component.literal("§7§oYou haven't built any multiblocks that meet the requirements! Here are the coordinates you might have built:\n")};

            structures.forEach(p -> {
                Object[] pStructure = (Object[]) p;
                if (BuiltInRegistries.BLOCK.wrapAsHolder(pBlock).getRegisteredName().equals(pStructure[1])){
                    Object[][] pParts = (Object[][]) pStructure[2];
                    boolean vIsForm = true;
                    int[] vPos;
                    MutableComponent vFailed = Component.literal("");

                    for (Object[] pPart:pParts){
                        vPos = Utils.getDirectionPos(new int[]{
                                ((Number) pPart[0]).intValue(),
                                ((Number) pPart[1]).intValue(),
                                ((Number) pPart[2]).intValue()
                        }, event.getLevel().getBlockState(event.getPos()).getValue(BlockStateProperties.HORIZONTAL_FACING));

                        if (
                                !BuiltInRegistries.BLOCK.wrapAsHolder(event.getLevel().getBlockState(
                                        Utils.getRelativePos(event.getPos(),vPos[0],vPos[1],vPos[2])
                                ).getBlock()).getRegisteredName().equals(pPart[3])
                        ){
                            vIsForm = false;
                            if (!vFailSaid[0].equals(Component.literal("1174ovo"))) {
                                if (vFailed.equals(Component.literal(""))) {
                                    vFailed = vFailed
                                            .append(
                                                    "\n§7"
                                                    + Component.translatable("multiblockjs." + pStructure[0]).getString()
                                                    + ":\n§a§l"
                                                    + Component.translatable(BuiltInRegistries.BLOCK.get(ResourceLocation.parse((String) pPart[3])).getDescriptionId()).getString()
                                                    + " §f[§4"
                                                    + Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2]).getX()
                                                    + " ,§c"
                                                    + Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2]).getY()
                                                    + " ,§6"
                                                    + Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2]).getZ()
                                                    + "§f]"
                                            );
                                } else {
                                    vFailed = vFailed.append(
                                            "\n§a§l"
                                                    + Component.translatable(BuiltInRegistries.BLOCK.get(ResourceLocation.parse((String) pPart[3])).getDescriptionId()).getString()
                                                    + " §f[§4"
                                                    + Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2]).getX()
                                                    + " ,§c"
                                                    + Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2]).getY()
                                                    + " ,§6"
                                                    + Utils.getRelativePos(event.getPos(), vPos[0], vPos[1], vPos[2]).getZ()
                                                    + "§f]"
                                            );
                                }
                            }
                        }
                    }

                    if (vIsForm){
                        pEntity.vStructure = (String) pStructure[0];
                        pBlock.form(event.getLevel().getBlockState(event.getPos()),event.getLevel(),event.getPos());
                        for (Object[] pPart:pParts){
                            vPos = Utils.getDirectionPos(new int[]{
                                    ((Number) pPart[0]).intValue(),
                                    ((Number) pPart[1]).intValue(),
                                    ((Number) pPart[2]).intValue()
                            }, event.getLevel().getBlockState(event.getPos()).getValue(BlockStateProperties.HORIZONTAL_FACING));

                            Utils.formBlock(
                                    Utils.getRelativePos(event.getPos(),vPos[0],vPos[1],vPos[2]),
                                    event.getPos(),
                                    event.getLevel()
                            );
                        }
                        vFailSaid[0] = Component.literal("1174ovo");
                    } else {
                        if (!vFailSaid[0].equals(Component.literal("1174ovo"))) {
                            vFailSaid[0].append(vFailed);
                        }
                    }
                }
            });
            if (
                    (!vFailSaid[0].equals(Component.literal("1174ovo")))
                            &&(!event.getLevel().getBlockState(event.getPos()).getValue(MultiblockPartBlock.FORMED))
            ) {
                event.getEntity().sendSystemMessage(vFailSaid[0]);
            }
        }
        structures = new ArrayList<>();
    }

    public DefinitionKubeEvent(){
        structures = new ArrayList<>();
        UnFormMultiBlock.structures = structures;
    }

    public void addStructure(String pId, String pController, Object[]... pParts){
        if (pParts == null){
            MultiblockJS.LOGGER.error("We didn't find your multiblock part!");
            return;
        }
        structures.add(new Object[]{pId,pController,pParts});
    }
}
