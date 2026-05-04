package com.qwq117458866249.multiblockjs.register;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.block.controller.ControllerBlockEntity;
import com.qwq117458866249.multiblockjs.block.custompartblock.CustomPartBE;
import com.qwq117458866249.multiblockjs.block.partblock.PartBlockEntity;
import com.qwq117458866249.multiblockjs.block.port.feport.FEPortBlockEntity;
import com.qwq117458866249.multiblockjs.block.port.fluidport.FluidPortBlockEntity;
import com.qwq117458866249.multiblockjs.block.port.itemport.ItemPortBlockEntity;
import com.qwq117458866249.multiblockjs.block.port.suinputport.SuInputPortBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MultiblockJS.MOD_ID);


    public static final Supplier<BlockEntityType<ControllerBlockEntity>> CONTROLLER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "controller_block_entity",
            () -> new BlockEntityType<>(ControllerBlockEntity::new, validControllerBlocks(), null)
    );

    public static final Supplier<BlockEntityType<PartBlockEntity>> PART_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "part_block_entity",
            () -> BlockEntityType.Builder.of(
                            PartBlockEntity::new,
                            BlockRegister.PART_BLOCK.get()
                    )
                    .build(null)
    );

    public static final Supplier<BlockEntityType<ItemPortBlockEntity>> ITEM_PORT_ENTITY = BLOCK_ENTITY_TYPES.register(
            "item_port_entity",
            () -> new BlockEntityType<>(ItemPortBlockEntity::new, validItemPortBlocks(), null)
    );

    public static final Supplier<BlockEntityType<FluidPortBlockEntity>> FLUID_PORT_ENTITY = BLOCK_ENTITY_TYPES.register(
            "fluid_port_entity",
            () -> new BlockEntityType<>(FluidPortBlockEntity::new, validFluidPortBlocks(), null)
    );

    public static final Supplier<BlockEntityType<FEPortBlockEntity>> FE_PORT_ENTITY = BLOCK_ENTITY_TYPES.register(
            "fe_port_entity",
            () -> new BlockEntityType<>(FEPortBlockEntity::new, validFEPortBlocks(), null)
    );

    public static final Supplier<BlockEntityType<SuInputPortBlockEntity>> SU_INPUT_PORT_ENTITY = BLOCK_ENTITY_TYPES.register(
            "su_input_port_entity",
            () -> new BlockEntityType<>(SuInputPortBlockEntity::new, validSuInputPortsBlocks(), null)
    );

    public static final Supplier<BlockEntityType<CustomPartBE>> CUSTOM_PART_ENTITY = BLOCK_ENTITY_TYPES.register(
            "custom_part_entity",
            () -> new BlockEntityType<>(CustomPartBE::new, validCustomPartBlocks(), null)
    );


    private static Set<Block> validControllerBlocks() {
        Set<Block> validBlocks = new HashSet<>();
        validBlocks.addAll(MultiblockJS.CONTROLLER_BLOCKS.values());
        return validBlocks;
    }

    private static Set<Block> validItemPortBlocks() {
        Set<Block> validBlocks = new HashSet<>();
        validBlocks.addAll(MultiblockJS.ITEM_PORTS.values());
        return validBlocks;
    }

    private static Set<Block> validFluidPortBlocks() {
        Set<Block> validBlocks = new HashSet<>();
        validBlocks.addAll(MultiblockJS.FLUID_PORTS.values());
        return validBlocks;
    }

    private static Set<Block> validFEPortBlocks() {
        Set<Block> validBlocks = new HashSet<>();
        validBlocks.addAll(MultiblockJS.FE_PORTS.values());
        return validBlocks;
    }

    private static Set<Block> validSuInputPortsBlocks() {
        Set<Block> validBlocks = new HashSet<>();
        validBlocks.addAll(MultiblockJS.SU_INPUT_PORTS.values());
        return validBlocks;
    }

    private static Set<Block> validCustomPartBlocks() {
        Set<Block> validBlocks = new HashSet<>();
        validBlocks.addAll(MultiblockJS.CUSTOM_PART_PORTS.values());
        return validBlocks;
    }

    public static void register(IEventBus pEventBus) {
        BLOCK_ENTITY_TYPES.register(pEventBus);
    }
}
