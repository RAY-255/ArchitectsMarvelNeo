package com.architectsmarvel.port.tab;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import com.architectsmarvel.port.block.ModBlocks;
import com.architectsmarvel.port.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArchitectsMarvelPort.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main", () ->
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + ArchitectsMarvelPort.MOD_ID + ".main"))
            .icon(() -> ModBlocks.LAPIS_BRICKS.asItem().getDefaultInstance())
            .displayItems((parameters, output) -> ModItems.blockItems().forEach(item -> output.accept(item.get())))
            .build()
    );

    private ModCreativeTabs() {
    }

    public static void register(IEventBus modBus) {
        CREATIVE_MODE_TABS.register(modBus);
    }
}
