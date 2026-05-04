package com.qwq117458866249.multiblockjs.block.port.suinputport;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.Utils;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Files;

public class SuInputPortKubeBlock extends BlockBuilder {
    public int requiredStress = 0;

    public SuInputPortKubeBlock(ResourceLocation id) {
        super(id);
    }

    public void setRequiredStress(int pValue) {
        requiredStress = pValue;
    }

    @Override
    public Block createObject() {
        SuInputPortBlock vBlock = new SuInputPortBlock(this.createProperties());
        MultiblockJS.SU_INPUT_PORTS.put(this.id, vBlock);
        MultiblockJS.REQUIRED_STRESSES.put(vBlock, requiredStress);

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
