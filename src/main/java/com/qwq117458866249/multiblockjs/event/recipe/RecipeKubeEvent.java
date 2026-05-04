package com.qwq117458866249.multiblockjs.event.recipe;

import com.qwq117458866249.multiblockjs.block.controller.ControllerBlockEntity;
import com.qwq117458866249.multiblockjs.event.any.ReadingRecipes;
import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class RecipeKubeEvent implements KubeEvent {
    public static List recipes = new ArrayList<>();

    public RecipeKubeEvent() {
        ControllerBlockEntity.recipes = recipes;
        ReadingRecipes.recipes = recipes;
        recipes = new ArrayList<>();
    }

    public void addRecipe(String pBlock, int pTime, Object[]... pRequirement) {
        recipes.add(new Object[]{
                BuiltInRegistries.BLOCK.get(
                        ResourceLocation.parse(pBlock)
                ),
                pTime,
                pRequirement
        });
    }
}
