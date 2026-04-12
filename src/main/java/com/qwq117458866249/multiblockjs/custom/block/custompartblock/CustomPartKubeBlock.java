package com.qwq117458866249.multiblockjs.custom.block.custompartblock;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class CustomPartKubeBlock extends BlockBuilder {
    public CustomPartKubeBlock(ResourceLocation id) {
        super(id);
    }

    @Override
    public Block createObject() {
        CustomPartBlock vBlock = new CustomPartBlock(this.createProperties());
        MultiblockJS.CUSTOM_PART_PORTS.put(this.id,vBlock);
        return vBlock;
    }
}
