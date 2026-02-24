package com.architectsmarvel.port.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;

public class SpotlightBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<SpotlightBlock> CODEC = simpleCodec(SpotlightBlock::new);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty INDICATOR = IntegerProperty.create("indicator", 0, 3);

    public SpotlightBlock() {
        this(Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 10.0F)
            .sound(SoundType.COPPER_BULB)
            .randomTicks()
            .lightLevel(state -> state.getValue(LIT) ? 10 : 0));
    }

    private SpotlightBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(LIT, false)
            .setValue(INDICATOR, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT, INDICATOR);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, facing);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            syncStateAndLight(level, pos, state);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            syncStateAndLight(level, pos, state);
        }
        super.neighborChanged(state, level, pos, block, fromPos, isMoving);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        syncStateAndLight(level, pos, state);
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        syncStateAndLight(level, pos, state);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        syncStateAndLight(level, pos, state);
    }

    private static void syncStateAndLight(LevelAccessor level, BlockPos pos, BlockState currentState) {
        boolean powered = level.hasNeighborSignal(pos);
        int sideSignal = getSpecialSideSignal(level, pos, currentState.getValue(FACING));
        int indicator = signalToIndicator(sideSignal);
        int lightLevel = signalToLightLevel(sideSignal);

        BlockState newState = currentState.setValue(LIT, powered).setValue(INDICATOR, indicator);
        if (!newState.equals(currentState)) {
            level.setBlock(pos, newState, 3);
        }

        updateSingleLightBlock(level, pos, powered, lightLevel);
    }

    private static int getSpecialSideSignal(LevelAccessor level, BlockPos pos, Direction facing) {
        BlockPos sidePos = pos.relative(facing);
        return level.getSignal(sidePos, facing);
    }

    private static int signalToIndicator(int signal) {
        if (signal <= 0) {
            return 0;
        }
        if (signal <= 5) {
            return 1;
        }
        if (signal <= 10) {
            return 2;
        }
        return 3;
    }

    private static int signalToLightLevel(int signal) {
        if (signal <= 0) {
            return 12;
        }
        if (signal <= 5) {
            return 8;
        }
        if (signal <= 10) {
            return 4;
        }
        return 0;
    }

    private static void updateSingleLightBlock(LevelAccessor level, BlockPos spotlightPos, boolean powered, int lightLevel) {
        BlockPos lightPos = spotlightPos.below();
        BlockState belowState = level.getBlockState(lightPos);

        if (!powered || lightLevel == 0) {
            if (belowState.is(ModBlocks.SPOTLIGHT_LIGHT.get())) {
                level.removeBlock(lightPos, false);
            }
            return;
        }

        if (belowState.isAir() || belowState.is(ModBlocks.SPOTLIGHT_LIGHT.get())) {
            BlockState lightState = ModBlocks.SPOTLIGHT_LIGHT.get().defaultBlockState().setValue(SpotlightLightBlock.LEVEL, lightLevel);
            level.setBlock(lightPos, lightState, 3);
        }
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
