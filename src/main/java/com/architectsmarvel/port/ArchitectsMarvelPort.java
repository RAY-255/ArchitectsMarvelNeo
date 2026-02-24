package com.architectsmarvel.port;

import com.architectsmarvel.port.block.ModBlocks;
import com.architectsmarvel.port.data.ModDataGenerators;
import com.architectsmarvel.port.item.ModItems;
import com.architectsmarvel.port.tab.ModCreativeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(ArchitectsMarvelPort.MOD_ID)
public final class ArchitectsMarvelPort {
    public static final String MOD_ID = "architectsmarvel_port";

    public ArchitectsMarvelPort(IEventBus modBus) {
        ModBlocks.register(modBus);
        ModItems.register(modBus);
        ModCreativeTabs.register(modBus);
        modBus.addListener(ModDataGenerators::gatherData);
    }
}
