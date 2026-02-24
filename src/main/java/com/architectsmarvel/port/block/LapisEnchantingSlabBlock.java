package com.architectsmarvel.port.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LapisEnchantingSlabBlock extends SlabBlock {
    public LapisEnchantingSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return LapisEnchantingBlock.ENCHANT_POWER;
    }
}
