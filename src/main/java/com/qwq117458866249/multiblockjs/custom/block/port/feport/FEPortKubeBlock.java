package com.qwq117458866249.multiblockjs.custom.block.port.feport;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.Utils;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Files;

public class FEPortKubeBlock extends BlockBuilder {
    public FEPortKubeBlock(ResourceLocation id) {
        super(id);
    }

    public int size = 0;

    public void setSize(int pValue) {
        size = pValue;
    }

    @Override
    public Block createObject() {
        FEPortBlock vBlock = new FEPortBlock(this.createProperties());
        MultiblockJS.FE_PORTS.put(this.id, vBlock);
        MultiblockJS.FE_SIZES.put(vBlock, size);

        String[] vNsPath = {
                this.id.getNamespace(),
                this.id.getPath()
        };
        if (!Files.exists(FMLPaths.GAMEDIR.get().resolve("kubejs/assets/" + vNsPath[0] + "/blockstates/" + vNsPath[1] + ".json"))) {
            try {
                Utils.writeToGameDir("kubejs/assets/" + vNsPath[0] + "/blockstates/" + vNsPath[1] + ".json", getBlockStateJson(vNsPath[0], vNsPath[1]));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return vBlock;
    }

    private static String getBlockStateJson(String pNamespace, String pPath) {
        return "{\n" +
                "    \"variants\": {\n" +
                "        \"formed=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "\"\n" +
                "        },\n" +
                "        \"formed=true\": {\n" +
                "            \"model\": \"multiblockjs:block/empty\"\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}