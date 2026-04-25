package com.qwq117458866249.multiblockjs.custom.event.any;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.custom.block.port.fluidport.FluidPortBlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BucketItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.fluids.FluidUtil;

//@EventBusSubscriber(modid = MultiblockJS.MOD_ID)
public class AddFluid {

    // Cyka blyat a lotttttt of bug motherfucking
    /*@SubscribeEvent
    public static void fillingFluid(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()){
            return;
        }
        if (event.getHand().equals(InteractionHand.OFF_HAND)){
            return;
        }
        if (event.getLevel().getBlockEntity(event.getPos()) instanceof FluidPortBlockEntity && event.getItemStack().getItem() instanceof BucketItem) {
            event.setCanceled(true);
            FluidUtil.interactWithFluidHandler(event.getEntity(), event.getHand(), event.getLevel(), event.getPos(), event.getFace());
        }
    }*/
}
