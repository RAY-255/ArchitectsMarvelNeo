package com.architectsmarvel.port.wip.enchanting;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

// WIP: Lapis enchanting power bonus (slab variant) — disabled, moved out of active package
public class LapisEnchantingSlabBlock extends SlabBlock {
    public LapisEnchantingSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return LapisEnchantingBlock.ENCHANT_POWER;
    }
}
