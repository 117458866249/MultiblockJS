package com.qwq117458866249.multiblockjs.plugin;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerKubeBlock;
import com.qwq117458866249.multiblockjs.custom.block.custompartblock.CustomPartKubeBlock;
import com.qwq117458866249.multiblockjs.custom.block.port.itemport.ItemPortKubeBlock;
import com.qwq117458866249.multiblockjs.custom.event.MjsKubeEvents;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import net.minecraft.core.registries.Registries;

public class MultiblockJsPlugin implements KubeJSPlugin {
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.BLOCK, reg -> {
            reg.add(MultiblockJS.modRL("controller"), ControllerKubeBlock.class, ControllerKubeBlock::new);
            reg.add(MultiblockJS.modRL("item_port"), ItemPortKubeBlock.class, ItemPortKubeBlock::new);
            reg.add(MultiblockJS.modRL("custom_part"), CustomPartKubeBlock.class, CustomPartKubeBlock::new);
        });
    }

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(MjsKubeEvents.GROUP);
    }
}
