package com.qwq117458866249.multiblockjs.event.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.qwq117458866249.multiblockjs.MultiblockJS;
import com.qwq117458866249.multiblockjs.event.MjsKubeEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import java.util.Map;

@EventBusSubscriber(modid = MultiblockJS.MOD_ID)
public class RecipeEventHandler {

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        MjsKubeEvents.RECIPE.post(new RecipeKubeEvent());
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new SimpleJsonResourceReloadListener(new Gson(), "reload_logger") {
            @Override
            protected void apply(Map<ResourceLocation, JsonElement> resourceLocationJsonElementMap, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
                MjsKubeEvents.RECIPE.post(new RecipeKubeEvent());
            }
        });
    }
}
