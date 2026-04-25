package com.qwq117458866249.multiblockjs.custom.block.controller;

import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.Utils;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Files;

public class ControllerKubeBlock extends BlockBuilder {
    public RenderShape vShape = RenderShape.MODEL;

    public void setRenderShape(RenderShape pShape) {
        this.vShape = pShape;
    }

    public ControllerKubeBlock(ResourceLocation id) {
        super(id);
    }

    @Override
    public Block createObject() {
        ControllerBlock vBlock = new ControllerBlock(this.createProperties(), vShape);
        MultiblockJS.CONTROLLER_BLOCKS.put(this.id, vBlock);

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

    // What the hell is this...
    private static String getBlockStateJson(String pNamespace, String pPath) {
        return "{\n" +
                "    \"variants\": {\n" +
                "        \"facing=north,formed=true,working=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_formed\"\n" +
                "        },\n" +
                "        \"facing=east,formed=true,working=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_formed\",\n" +
                "            \"y\": 90\n" +
                "        },\n" +
                "        \"facing=south,formed=true,working=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_formed\",\n" +
                "            \"y\": 180\n" +
                "        },\n" +
                "        \"facing=west,formed=true,working=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_formed\",\n" +
                "            \"y\": 270\n" +
                "        },\n" +
                "        \"facing=north,formed=true,working=true\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_working\"\n" +
                "        },\n" +
                "        \"facing=east,formed=true,working=true\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_working\",\n" +
                "            \"y\": 90\n" +
                "        },\n" +
                "        \"facing=south,formed=true,working=true\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_working\",\n" +
                "            \"y\": 180\n" +
                "        },\n" +
                "        \"facing=west,formed=true,working=true\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "_working\",\n" +
                "            \"y\": 270\n" +
                "        },\n" +
                "        \"facing=north,formed=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "\"\n" +
                "        },\n" +
                "        \"facing=east,formed=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "\",\n" +
                "            \"y\": 90\n" +
                "        },\n" +
                "        \"facing=south,formed=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "\",\n" +
                "            \"y\": 180\n" +
                "        },\n" +
                "        \"facing=west,formed=false\": {\n" +
                "            \"model\": \"" + pNamespace + ":block/" + pPath + "\",\n" +
                "            \"y\": 270\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
}
