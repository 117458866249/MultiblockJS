package com.qwq117458866249.multiblockjs.custom.event.any;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.Utils;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlock;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = MultiblockJS.MOD_ID)
public class ReadingRecipes {
    public static List recipes = new ArrayList<>();

    @SubscribeEvent
    public static void getRecipes(PlayerInteractEvent.RightClickBlock event){
        if (event.getLevel().isClientSide()){
            return;
        }
        if (event.getHand().equals(InteractionHand.OFF_HAND)){
            return;
        }
        if (!event.getEntity().getMainHandItem().is(Items.AIR)){
            return;
        }
        if (event.getLevel().getBlockEntity(event.getPos()) instanceof ControllerBlockEntity && event.getLevel().getBlockState(event.getPos()).getValue(ControllerBlock.FORMED)){
            event.getEntity().sendSystemMessage(Component.literal("\n\n§7Available Recipes:"));
            recipes.forEach(p->{
                Object[] vRecipe = (Object[]) p;
                Object[][] vRequirements = (Object[][]) vRecipe[2];
                int[] vPortPos;
                if (vRecipe[0].equals(event.getLevel().getBlockState(event.getPos()).getBlock())) {
                    event.getEntity().sendSystemMessage(Component.literal("§8------------"));
                    for (Object[] pRequirement : vRequirements) {
                        if (pRequirement[1].equals("input")) {
                            event.getEntity().sendSystemMessage(Component.literal("\n§2Inputs:"));
                            vPortPos = Utils.getDirectionPos(new int[]{
                                            ((Number) pRequirement[2]).intValue(),
                                            ((Number) pRequirement[3]).intValue(),
                                            ((Number) pRequirement[4]).intValue()
                                    },
                                    event.getLevel().getBlockState(event.getPos()).getValue(BlockStateProperties.HORIZONTAL_FACING)
                            );
                            switch ((String) pRequirement[0]) {
                                case "item" ->
                                    event.getEntity().sendSystemMessage(Component.literal("§dItem:§5" + Component.translatable(BuiltInRegistries.ITEM.get(
                                        ResourceLocation.parse(
                                                (String) pRequirement[5]
                                        )
                                    ).getDescriptionId()).getString() + "§b, Port at §9" +
                                            Utils.getRelativePos(event.getPos(),vPortPos[0], vPortPos[1], vPortPos[2]).getX() + " " +
                                            Utils.getRelativePos(event.getPos(),vPortPos[0], vPortPos[1], vPortPos[2]).getY() + " " +
                                            Utils.getRelativePos(event.getPos(),vPortPos[0], vPortPos[1], vPortPos[2]).getZ() + " "
                                    ));
                                case "fe" -> {

                                }
                                case "fluid" -> {

                                }
                                case "mek" -> {

                                }
                                case "su" -> {

                                }
                            }
                        }
                    }
                    for (Object[] pRequirement : vRequirements) {
                        if (pRequirement[1].equals("output")) {
                            event.getEntity().sendSystemMessage(Component.literal("\n§aOutputs:"));
                            vPortPos = Utils.getDirectionPos(new int[]{
                                            ((Number) pRequirement[2]).intValue(),
                                            ((Number) pRequirement[3]).intValue(),
                                            ((Number) pRequirement[4]).intValue()
                                    },
                                    event.getLevel().getBlockState(event.getPos()).getValue(BlockStateProperties.HORIZONTAL_FACING)
                            );
                            switch ((String) pRequirement[0]) {
                                case "item" ->
                                        event.getEntity().sendSystemMessage(Component.literal("§dItem:§5" + Component.translatable(BuiltInRegistries.ITEM.get(
                                                        ResourceLocation.parse(
                                                                (String) pRequirement[5]
                                                        )
                                                ).getDescriptionId()).getString() + "§b, Port at §9" +
                                                Utils.getRelativePos(event.getPos(),vPortPos[0], vPortPos[1], vPortPos[2]).getX() + " " +
                                                Utils.getRelativePos(event.getPos(),vPortPos[0], vPortPos[1], vPortPos[2]).getY() + " " +
                                                Utils.getRelativePos(event.getPos(),vPortPos[0], vPortPos[1], vPortPos[2]).getZ() + " "
                                        ));
                                case "fe" -> {

                                }
                                case "fluid" -> {

                                }
                                case "mek" -> {

                                }
                                case "su" -> {

                                }
                            }
                        }
                    }
                }
            });
        }
    }
}
