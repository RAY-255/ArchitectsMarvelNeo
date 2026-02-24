package com.architectsmarvel.port.data;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import com.architectsmarvel.port.block.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ArchitectsMarvelPort.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var mineableWithPickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        var needsStoneTool = tag(BlockTags.NEEDS_STONE_TOOL);
        var slabTag = tag(BlockTags.SLABS);
        var stairTag = tag(BlockTags.STAIRS);
        var wallTag = tag(BlockTags.WALLS);

        for (Block block : ModBlocks.all().stream().map(deferred -> deferred.get()).toList()) {
            mineableWithPickaxe.add(block);
            needsStoneTool.add(block);
            if (block instanceof SlabBlock) {
                slabTag.add(block);
            } else if (block instanceof StairBlock) {
                stairTag.add(block);
            } else if (block instanceof WallBlock) {
                wallTag.add(block);
            }
        }
    }
}
