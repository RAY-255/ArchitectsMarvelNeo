package com.architectsmarvel.port.item;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArchitectsMarvelPort.MOD_ID);

    private static final Map<String, DeferredItem<BlockItem>> BLOCK_ITEMS = new LinkedHashMap<>();

    private ModItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    public static Collection<DeferredItem<BlockItem>> blockItems() {
        return BLOCK_ITEMS.values();
    }

    public static void registerBlockItem(String name, DeferredBlock<? extends Block> block) {
        BLOCK_ITEMS.put(name, ITEMS.registerSimpleBlockItem(name, block));
    }
}
