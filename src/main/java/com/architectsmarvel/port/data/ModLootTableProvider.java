package com.architectsmarvel.port.data;

import com.architectsmarvel.port.block.ModBlocks;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public final class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Set.of(), List.of(new SubProviderEntry(provider -> new ModBlockLootSubProvider(provider), LootContextParamSets.BLOCK)), lookupProvider);
    }

    private static final class ModBlockLootSubProvider extends BlockLootSubProvider {
        private ModBlockLootSubProvider(HolderLookup.Provider provider) {
            super(Set.<Item>of(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void generate() {
            for (Block block : getKnownBlocks()) {
                if (block instanceof SlabBlock slab) {
                    add(slab, createSlabItemTable(slab));
                } else {
                    dropSelf(block);
                }
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.all().stream().map(deferred -> (Block) deferred.get()).toList();
        }
    }
}
