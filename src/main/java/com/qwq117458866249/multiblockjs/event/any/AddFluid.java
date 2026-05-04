package com.qwq117458866249.multiblockjs.event.any;

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
