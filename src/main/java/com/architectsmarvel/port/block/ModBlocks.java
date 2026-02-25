package com.architectsmarvel.port.block;

import com.architectsmarvel.port.ArchitectsMarvelPort;
import com.architectsmarvel.port.item.ModItems;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArchitectsMarvelPort.MOD_ID);

    private static final Map<String, DeferredBlock<? extends Block>> ENTRIES = new LinkedHashMap<>();

    public static final DeferredBlock<StairBlock> LAPIS_STAIRS = register("lapis_stairs", () -> new LapisEnchantingStairBlock(Blocks.LAPIS_BLOCK.defaultBlockState(), stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<SlabBlock> LAPIS_SLAB = register("lapis_slab", () -> new LapisEnchantingSlabBlock(stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<Block> LAPIS_BRICKS = register("lapis_bricks", () -> new LapisEnchantingBlock(stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<StairBlock> LAPIS_BRICK_STAIRS = register("lapis_brick_stairs", () -> new LapisEnchantingStairBlock(LAPIS_BRICKS.get().defaultBlockState(), stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<SlabBlock> LAPIS_BRICK_SLAB = register("lapis_brick_slab", () -> new LapisEnchantingSlabBlock(stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<Block> CHISELED_LAPIS_BRICKS = register("chiseled_lapis_bricks", () -> new LapisEnchantingBlock(stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<Block> LAPIS_TILES = register("lapis_tiles", () -> new LapisEnchantingBlock(stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<StairBlock> LAPIS_TILE_STAIRS = register("lapis_tile_stairs", () -> new LapisEnchantingStairBlock(LAPIS_TILES.get().defaultBlockState(), stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<SlabBlock> LAPIS_TILE_SLAB = register("lapis_tile_slab", () -> new LapisEnchantingSlabBlock(stoneProps(Blocks.LAPIS_BLOCK)));
    public static final DeferredBlock<Block> LAPIS_LAMP = register("lapis_lamp", () -> new LapisEnchantingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP).lightLevel(s -> 15)));

    public static final DeferredBlock<AmethystBlock> AMETHYST_BRICKS = register("amethyst_bricks", () -> new AmethystBlock(stoneProps(Blocks.AMETHYST_BLOCK)));
    public static final DeferredBlock<AmethystBlock> CHISELED_AMETHYST_BRICKS = register("chiseled_amethyst_bricks", () -> new AmethystBlock(stoneProps(Blocks.AMETHYST_BLOCK)));
    public static final DeferredBlock<RotatedPillarBlock> AMETHYST_MOSAIC = register("amethyst_mosaic", () -> new RotatedPillarBlock(stoneProps(Blocks.AMETHYST_BLOCK)));
    public static final DeferredBlock<Block> AMETHYST_LAMP = register("amethyst_lamp", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.REDSTONE_LAMP).lightLevel(s -> 15)));

    public static final DeferredBlock<StairBlock> CALCITE_STAIRS = register("calcite_stairs", () -> new StairBlock(Blocks.CALCITE.defaultBlockState(), stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<SlabBlock> CALCITE_SLAB = register("calcite_slab", () -> new SlabBlock(stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<Block> CALCITE_BRICKS = register("calcite_bricks", () -> new Block(stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<StairBlock> CALCITE_BRICK_STAIRS = register("calcite_brick_stairs", () -> new StairBlock(CALCITE_BRICKS.get().defaultBlockState(), stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<SlabBlock> CALCITE_BRICK_SLAB = register("calcite_brick_slab", () -> new SlabBlock(stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<Block> CHISELED_CALCITE_BRICKS = register("chiseled_calcite_bricks", () -> new Block(stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<Block> CALCITE_TILES = register("calcite_tiles", () -> new Block(stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<StairBlock> CALCITE_TILE_STAIRS = register("calcite_tile_stairs", () -> new StairBlock(CALCITE_TILES.get().defaultBlockState(), stoneProps(Blocks.CALCITE)));
    public static final DeferredBlock<SlabBlock> CALCITE_TILE_SLAB = register("calcite_tile_slab", () -> new SlabBlock(stoneProps(Blocks.CALCITE)));

    public static final DeferredBlock<StonePillarBlock> STONE_PILLAR = register("stone_pillar", () -> new StonePillarBlock(stoneProps(Blocks.STONE_BRICKS)));

    public static final DeferredBlock<SpotlightBlock> SPOTLIGHT = register("spotlight", SpotlightBlock::new);
    public static final DeferredBlock<SpotlightLightBlock> SPOTLIGHT_LIGHT = registerNoItem("spotlight_light", () -> new SpotlightLightBlock(BlockBehaviour.Properties.of()));

    private ModBlocks() {}

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static Map<String, DeferredBlock<? extends Block>> entries() {
        return ENTRIES;
    }

    public static Collection<DeferredBlock<? extends Block>> all() {
        return ENTRIES.values();
    }

    private static BlockBehaviour.Properties stoneProps(Block template) {
        return BlockBehaviour.Properties.ofFullCopy(template).sound(SoundType.STONE).strength(1.8F, 6.0F);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> supplier) {
        DeferredBlock<T> block = BLOCKS.register(name, supplier);
        ENTRIES.put(name, block);
        ModItems.registerBlockItem(name, block);
        return block;
    }

    private static <T extends Block> DeferredBlock<T> registerNoItem(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }
}
