package com.architectsmarvel.port.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class StonePillarBlock extends RotatedPillarBlock {
    public static final MapCodec<StonePillarBlock> CODEC = simpleCodec(StonePillarBlock::new);
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public StonePillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(AXIS, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction.Axis axis = context.getClickedFace().getAxis();
        BlockPos pos = context.getClickedPos();
        LevelAccessor level = context.getLevel();

        Direction positive = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        Direction negative = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE);

        return this.defaultBlockState()
            .setValue(AXIS, axis)
            .setValue(UP, level.getBlockState(pos.relative(positive)).is(this))
            .setValue(DOWN, level.getBlockState(pos.relative(negative)).is(this));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        Direction.Axis axis = state.getValue(AXIS);
        Direction positive = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE);
        Direction negative = Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE);

        if (direction == positive) {
            return state.setValue(UP, neighborState.is(this));
        }
        if (direction == negative) {
            return state.setValue(DOWN, neighborState.is(this));
        }
        return state;
    }

    @Override
    public MapCodec<? extends RotatedPillarBlock> codec() {
        return CODEC;
    }
}
