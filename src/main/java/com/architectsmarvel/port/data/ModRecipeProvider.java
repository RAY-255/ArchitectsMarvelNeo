package com.architectsmarvel.port.data;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import com.architectsmarvel.port.block.ModBlocks;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public final class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput out) {
        familyRecipes(out, ModBlocks.LAPIS_BRICKS.get(), ModBlocks.LAPIS_BRICK_STAIRS.get(), ModBlocks.LAPIS_BRICK_SLAB.get());
        familyRecipes(out, ModBlocks.LAPIS_TILES.get(), ModBlocks.LAPIS_TILE_STAIRS.get(), ModBlocks.LAPIS_TILE_SLAB.get());
        familyRecipes(out, ModBlocks.CALCITE_BRICKS.get(), ModBlocks.CALCITE_BRICK_STAIRS.get(), ModBlocks.CALCITE_BRICK_SLAB.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LAPIS_BRICKS.get(), 4)
            .pattern("##")
            .pattern("##")
            .define('#', Items.LAPIS_BLOCK)
            .unlockedBy("has_lapis_block", has(Items.LAPIS_BLOCK))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LAPIS_TILES.get(), 4)
            .pattern("##")
            .pattern("##")
            .define('#', ModBlocks.LAPIS_BRICKS.get())
            .unlockedBy("has_lapis_bricks", has(ModBlocks.LAPIS_BRICKS.get()))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_LAPIS_BRICKS.get())
            .pattern("#")
            .pattern("#")
            .define('#', ModBlocks.LAPIS_BRICK_SLAB.get())
            .unlockedBy("has_lapis_brick_slab", has(ModBlocks.LAPIS_BRICK_SLAB.get()))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_BRICKS.get(), 4)
            .pattern("##")
            .pattern("##")
            .define('#', Items.AMETHYST_BLOCK)
            .unlockedBy("has_amethyst_block", has(Items.AMETHYST_BLOCK))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_AMETHYST_BRICKS.get())
            .pattern("##")
            .pattern("##")
            .define('#', Items.AMETHYST_SHARD)
            .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.AMETHYST_MOSAIC.get(), 2)
            .pattern("#")
            .pattern("#")
            .define('#', ModBlocks.AMETHYST_BRICKS.get())
            .unlockedBy("has_amethyst_bricks", has(ModBlocks.AMETHYST_BRICKS.get()))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_BRICKS.get(), 4)
            .pattern("##")
            .pattern("##")
            .define('#', Items.CALCITE)
            .unlockedBy("has_calcite", has(Items.CALCITE))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CALCITE_TILES.get(), 4)
            .pattern("##")
            .pattern("##")
            .define('#', ModBlocks.CALCITE_BRICKS.get())
            .unlockedBy("has_calcite_bricks", has(ModBlocks.CALCITE_BRICKS.get()))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_CALCITE_BRICKS.get())
            .pattern("#")
            .pattern("#")
            .define('#', ModBlocks.CALCITE_BRICK_SLAB.get())
            .unlockedBy("has_calcite_brick_slab", has(ModBlocks.CALCITE_BRICK_SLAB.get()))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.LAPIS_LAMP.get())
            .pattern("###")
            .pattern("#R#")
            .pattern("###")
            .define('#', ModBlocks.LAPIS_BRICKS.get())
            .define('R', Items.REDSTONE_LAMP)
            .unlockedBy("has_lapis_bricks", has(ModBlocks.LAPIS_BRICKS.get()))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.AMETHYST_LAMP.get())
            .pattern("###")
            .pattern("#R#")
            .pattern("###")
            .define('#', ModBlocks.AMETHYST_BRICKS.get())
            .define('R', Items.REDSTONE_LAMP)
            .unlockedBy("has_amethyst_bricks", has(ModBlocks.AMETHYST_BRICKS.get()))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STONE_PILLAR.get(), 2)
            .pattern("#")
            .pattern("#")
            .define('#', Items.STONE_BRICKS)
            .unlockedBy("has_stone_bricks", has(Items.STONE_BRICKS))
            .save(out);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.SPOTLIGHT.get())
            .pattern(" I ")
            .pattern("RGR")
            .pattern(" C ")
            .define('I', Items.IRON_INGOT)
            .define('R', Items.REDSTONE)
            .define('G', Items.GLASS_PANE)
            .define('C', Items.COPPER_INGOT)
            .unlockedBy("has_redstone", has(Items.REDSTONE))
            .save(out);

        Map<ItemLike, List<StonecutTarget>> targets = new LinkedHashMap<>();
        targets.put(Items.LAPIS_BLOCK, List.of(
            new StonecutTarget(ModBlocks.LAPIS_STAIRS.get(), 1),
            new StonecutTarget(ModBlocks.LAPIS_SLAB.get(), 2),
            new StonecutTarget(ModBlocks.LAPIS_BRICKS.get(), 1),
            new StonecutTarget(ModBlocks.LAPIS_BRICK_STAIRS.get(), 1),
            new StonecutTarget(ModBlocks.LAPIS_BRICK_SLAB.get(), 2),
            new StonecutTarget(ModBlocks.CHISELED_LAPIS_BRICKS.get(), 1),
            new StonecutTarget(ModBlocks.LAPIS_TILES.get(), 1),
            new StonecutTarget(ModBlocks.LAPIS_TILE_STAIRS.get(), 1),
            new StonecutTarget(ModBlocks.LAPIS_TILE_SLAB.get(), 2)
        ));
        targets.put(Items.CALCITE, List.of(
            new StonecutTarget(ModBlocks.CALCITE_BRICKS.get(), 1),
            new StonecutTarget(ModBlocks.CALCITE_BRICK_STAIRS.get(), 1),
            new StonecutTarget(ModBlocks.CALCITE_BRICK_SLAB.get(), 2),
            new StonecutTarget(ModBlocks.CHISELED_CALCITE_BRICKS.get(), 1),
            new StonecutTarget(ModBlocks.CALCITE_TILES.get(), 1)
        ));
        targets.put(Items.AMETHYST_BLOCK, List.of(
            new StonecutTarget(ModBlocks.AMETHYST_BRICKS.get(), 1),
            new StonecutTarget(ModBlocks.CHISELED_AMETHYST_BRICKS.get(), 1),
            new StonecutTarget(ModBlocks.AMETHYST_MOSAIC.get(), 1)
        ));
        targets.put(Items.STONE_BRICKS, List.of(
            new StonecutTarget(ModBlocks.STONE_PILLAR.get(), 1)
        ));

        for (Map.Entry<ItemLike, List<StonecutTarget>> e : targets.entrySet()) {
            ItemLike source = e.getKey();
            String sourceName = path(source);
            for (StonecutTarget target : e.getValue()) {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(source), RecipeCategory.BUILDING_BLOCKS, target.item(), target.count())
                    .unlockedBy("has_" + sourceName, has(source))
                    .save(out, ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, path(target.item()) + "_from_stonecutting_" + sourceName));
            }
        }
    }

    private void familyRecipes(RecipeOutput output, ItemLike full, ItemLike stairs, ItemLike slab) {
        String source = path(full);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
            .pattern("#  ")
            .pattern("## ")
            .pattern("###")
            .define('#', full)
            .unlockedBy("has_" + source, has(full))
            .save(output, ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, path(stairs)));

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
            .pattern("###")
            .define('#', full)
            .unlockedBy("has_" + source, has(full))
            .save(output, ResourceLocation.fromNamespaceAndPath(ArchitectsMarvelPort.MOD_ID, path(slab)));
    }

    private static String path(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }

    private record StonecutTarget(ItemLike item, int count) {}
}
