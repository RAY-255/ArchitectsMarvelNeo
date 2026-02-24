package com.architectsmarvel.port.data;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import com.architectsmarvel.port.block.ModBlocks;
import com.architectsmarvel.port.block.SpotlightBlock;
import com.architectsmarvel.port.block.StonePillarBlock;
import java.util.Map;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public final class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArchitectsMarvelPort.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (Map.Entry<String, DeferredBlock<? extends Block>> entry : ModBlocks.entries().entrySet()) {
            String name = entry.getKey();
            Block block = entry.getValue().get();

            if (name.equals("spotlight")) {
                registerSpotlight(entry.getValue());
                continue;
            }
            if (name.equals("stone_pillar")) {
                registerStonePillar(entry.getValue());
                continue;
            }

            if (block instanceof StairBlock stair) {
                ResourceLocation texture = resolveTexture(name);
                stairsBlock(stair, texture);
                simpleBlockItem(stair, models().stairs(name, texture, texture, texture));
                continue;
            }

            if (block instanceof SlabBlock slab) {
                ResourceLocation texture = resolveTexture(name);
                slabBlock(slab, texture, texture);
                simpleBlockItem(slab, models().slab(name, texture, texture, texture));
                continue;
            }

            if (block instanceof WallBlock wall) {
                ResourceLocation texture = resolveTexture(name);
                wallBlock(wall, texture);
                ModelFile inventory = models().wallInventory(name + "_inventory", texture);
                simpleBlockItem(wall, inventory);
                continue;
            }

            if (block instanceof RotatedPillarBlock pillar && name.equals("amethyst_mosaic")) {
                ResourceLocation side = ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, "block/amethyst_mosaic_side1");
                ResourceLocation end = ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, "block/amethyst_mosaic_top");
                axisBlock(pillar, side, end);
                simpleBlockItem(pillar, models().cubeColumn(name, side, end));
                continue;
            }

            ResourceLocation texture = resolveTexture(name);
            simpleBlockWithItem(block, models().cubeAll(name, texture));
        }
    }

    private void registerStonePillar(DeferredBlock<? extends Block> pillarHolder) {
        Block pillar = pillarHolder.get();

        ModelFile single = models().cubeColumn("stone_pillar_single", modLoc("block/stone_column_side"), modLoc("block/stone_column_top"));
        ModelFile lower = models().cubeColumn("stone_pillar_lower", modLoc("block/stone_column_lower"), modLoc("block/stone_column_top"));
        ModelFile middle = models().cubeColumn("stone_pillar_middle", modLoc("block/stone_column_middle"), modLoc("block/stone_column_top"));
        ModelFile upper = models().cubeColumn("stone_pillar_upper", modLoc("block/stone_column_upper"), modLoc("block/stone_column_top"));

        getVariantBuilder(pillar).forAllStates(state -> {
            boolean up = state.getValue(StonePillarBlock.UP);
            boolean down = state.getValue(StonePillarBlock.DOWN);
            ModelFile chosen = !up && !down ? single : (up && down ? middle : (up ? upper : lower));
            return ConfiguredModel.builder().modelFile(chosen).build();
        });

        simpleBlockItem(pillar, middle);
    }

    private void registerSpotlight(DeferredBlock<? extends Block> spotlightHolder) {
        Block spotlight = spotlightHolder.get();

        ModelFile[][] modelsByState = new ModelFile[2][4];
        for (int lit = 0; lit <= 1; lit++) {
            for (int indicator = 0; indicator <= 3; indicator++) {
                String name = "spotlight_block_" + (lit == 1 ? "on" : "off") + "_" + indicator;
                modelsByState[lit][indicator] = models().withExistingParent(name, mcLoc("block/orientable_with_bottom"))
                    .texture("top", mcLoc("block/furnace_top"))
                    .texture("side", modLoc(lit == 1 ? "block/spotlight_side_lit" : "block/spotlight_side"))
                    .texture("front", modLoc("block/spotlight_indicator_" + indicator))
                    .texture("bottom", modLoc(lit == 1 ? "block/spotlight_bottom_lit" : "block/spotlight_bottom"));
            }
        }

        getVariantBuilder(spotlight)
            .forAllStates(state -> {
                int lit = state.getValue(SpotlightBlock.LIT) ? 1 : 0;
                int indicator = state.getValue(SpotlightBlock.INDICATOR);
                int yRot = switch (state.getValue(SpotlightBlock.FACING)) {
                    case NORTH -> 180;
                    case SOUTH -> 0;
                    case WEST -> 90;
                    case EAST -> 270;
                    default -> 0;
                };
                return ConfiguredModel.builder()
                    .modelFile(modelsByState[lit][indicator])
                    .rotationY(yRot)
                    .build();
            });

        simpleBlockItem(spotlight, modelsByState[0][0]);
    }

    private ResourceLocation resolveTexture(String blockName) {
        return switch (blockName) {
            case "lapis_stairs", "lapis_slab" -> ResourceLocation.fromNamespaceAndPath("minecraft", "block/lapis_block");
            default -> ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, "block/" + normalizedBaseTexture(blockName));
        };
    }

    private static String normalizedBaseTexture(String blockName) {
        String texture = blockName
            .replace("_stairs", "")
            .replace("_slab", "")
            .replace("_wall", "");

        if (texture.endsWith("_brick") || texture.endsWith("_tile")) {
            return texture + "s";
        }
        return texture;
    }
}
