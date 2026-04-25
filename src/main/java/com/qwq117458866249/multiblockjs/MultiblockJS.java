package com.qwq117458866249.multiblockjs;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlock;
import com.qwq117458866249.multiblockjs.custom.block.custompartblock.CustomPartBlock;
import com.qwq117458866249.multiblockjs.custom.block.port.fluidport.FluidPortBlock;
import com.qwq117458866249.multiblockjs.custom.block.port.itemport.ItemPortBlock;
import com.qwq117458866249.multiblockjs.register.BlockEntityRegister;
import com.qwq117458866249.multiblockjs.register.BlockRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.HashMap;

@Mod(MultiblockJS.MOD_ID)
public class MultiblockJS {
    public static final String MOD_ID = "multiblockjs";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final BiMap<ResourceLocation, ControllerBlock> CONTROLLER_BLOCKS = HashBiMap.create();
    public static final BiMap<ResourceLocation, ItemPortBlock> ITEM_PORTS = HashBiMap.create();
    public static final BiMap<ResourceLocation, FluidPortBlock> FLUID_PORTS = HashBiMap.create();
    public static final BiMap<ResourceLocation, CustomPartBlock> CUSTOM_PART_PORTS = HashBiMap.create();

    public static final HashMap<Block, Integer> ITEM_SIZES = new HashMap<>();
    public static final HashMap<Block, Integer> FLUID_SIZES = new HashMap<>();

    public MultiblockJS(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        BlockEntityRegister.register(modEventBus);
        BlockRegister.register(modEventBus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    public static ResourceLocation modRL(String pPath) {
        return ResourceLocation.fromNamespaceAndPath("multiblockjs", pPath);
    }
}
