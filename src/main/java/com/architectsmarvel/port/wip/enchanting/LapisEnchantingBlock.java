package com.architectsmarvel.port.wip.enchanting;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

// WIP: Lapis enchanting power bonus — disabled, moved out of active package
public class LapisEnchantingBlock extends Block {
    public static final float ENCHANT_POWER = 1.0F / 3.0F;

    public LapisEnchantingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return ENCHANT_POWER;
    }
}
