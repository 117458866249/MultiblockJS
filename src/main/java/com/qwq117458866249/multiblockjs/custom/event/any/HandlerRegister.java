package com.qwq117458866249.multiblockjs.custom.event.any;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = MultiblockJS.MOD_ID)
public class HandlerRegister {
    @SubscribeEvent
    public static void registerCapabilitiesItem(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                BlockEntityRegister.ITEM_PORT_ENTITY.get(),
                (entity, side) -> entity.vInventory
        );
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                BlockEntityRegister.FLUID_PORT_ENTITY.get(),
                (blockEntity, side) -> blockEntity.getFluidHandler()
        );
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                BlockEntityRegister.FE_PORT_ENTITY.get(),
                (blockEntity, side) -> blockEntity.storage
        );
    }
}
