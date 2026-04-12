package com.qwq117458866249.multiblockjs.custom.event;

import com.qwq117458866249.multiblockjs.custom.event.definition.DefinitionKubeEvent;
import com.qwq117458866249.multiblockjs.custom.event.recipe.RecipeKubeEvent;
import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public interface MjsKubeEvents {
    EventGroup GROUP = EventGroup.of("MjsEvents");
    EventHandler DEFINITION = GROUP.server("definition", () -> DefinitionKubeEvent.class);
    EventHandler RECIPE = GROUP.server("recipe", () -> RecipeKubeEvent.class);
}
