package com.qwq117458866249.multiblockjs.custom.event.recipe;

import com.qwq117458866249.multiblockjs.custom.block.controller.ControllerBlockEntity;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RecipeKubeEvent implements KubeEvent {
    public static List recipes = new ArrayList<>();

    public RecipeKubeEvent(){
        ControllerBlockEntity.recipes = recipes;
        recipes = new ArrayList<>();
    }

    public void addRecipe(String pBlock,int pTime,Object[]... pRequirement){
        recipes.add(new Object[]{
                BuiltInRegistries.BLOCK.get(
                        ResourceLocation.parse(pBlock)
                ),
                pTime,
                pRequirement
        });
    }
}
