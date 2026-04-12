package com.qwq117458866249.multiblockjs.register;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.custom.block.partblock.PartBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegister {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MultiblockJS.MOD_ID);

    public static final DeferredBlock<Block> PART_BLOCK =
            BLOCKS.register("part_block",()->new PartBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.METAL)
                    .mapColor(MapColor.COLOR_CYAN)
                    .strength(1.2f, 3.2f)
                    .noOcclusion()
            ));

    public static void register(IEventBus pEventBus) {
        BLOCKS.register(pEventBus);
    }
}
