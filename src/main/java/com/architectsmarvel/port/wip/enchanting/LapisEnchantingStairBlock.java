package com.architectsmarvel.port.wip.enchanting;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

// WIP: Lapis enchanting power bonus (stair variant) — disabled, moved out of active package
public class LapisEnchantingStairBlock extends StairBlock {
    public LapisEnchantingStairBlock(BlockState baseState, Properties properties) {
        super(baseState, properties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return LapisEnchantingBlock.ENCHANT_POWER;
    }
}
