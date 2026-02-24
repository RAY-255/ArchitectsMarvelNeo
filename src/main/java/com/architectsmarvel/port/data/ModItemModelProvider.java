package com.architectsmarvel.port.data;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import com.architectsmarvel.port.block.ModBlocks;
import java.util.Map;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArchitectsMarvelPort.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Map.Entry<String, DeferredBlock<? extends Block>> entry : ModBlocks.entries().entrySet()) {
            String name = entry.getKey();
            String parentPath;
            if (name.equals("spotlight")) {
                parentPath = "block/spotlight_block_off_0";
            } else if (name.equals("stone_pillar")) {
                parentPath = "block/stone_pillar_middle";
            } else if (name.endsWith("_wall")) {
                parentPath = "block/" + name + "_inventory";
            } else {
                parentPath = "block/" + name;
            }
            withExistingParent(name, ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, parentPath));
        }
    }
}
