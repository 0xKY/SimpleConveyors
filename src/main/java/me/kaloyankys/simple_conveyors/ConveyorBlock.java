package me.kaloyankys.simple_conveyors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ConveyorBlock extends Block {
    public static final VoxelShape EAST = VoxelShapes.cuboid(0.1, 0.0, 0.0, 1.0, 0.9, 1.0);
    public static final VoxelShape WEST = VoxelShapes.cuboid(0.0, 0.0, 0.0, 0.9, 0.9, 1.0);
    public static final VoxelShape NORTH = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.9, 0.9);
    public static final VoxelShape SOUTH = VoxelShapes.cuboid(0.0, 0.0, 0.1, 1.0, 0.9, 1.0);
    public static final DirectionProperty FACING = DirectionProperty.of("facing");

    public ConveyorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.appendProperties(builder);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing());
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        float y = !world.getBlockState(pos.offset(state.get(FACING))).isOf(Conveyors.CONVEYOR) ? 0.4f : -0.13f;
        float x = !world.getBlockState(pos.offset(state.get(FACING))).isOf(Conveyors.CONVEYOR) ?
                state.get(FACING).getOffsetX() : state.get(FACING).getOffsetX() / (16.0f);
        float z = !world.getBlockState(pos.offset(state.get(FACING))).isOf(Conveyors.CONVEYOR) ?
                state.get(FACING).getOffsetZ() : state.get(FACING).getOffsetZ() / (16.0f);
        entity.addVelocity(x, y, z);
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.getBlockState(entity.getLandingPos()).isOf(Conveyors.CONVEYOR)) {
            entity.setVelocity(state.get(FACING).getOffsetX() / 3.0f, 0.13, state.get(FACING).getOffsetZ() / 3.0f);
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean canMobSpawnInside() {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> NORTH;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            case EAST -> EAST;
            default -> VoxelShapes.fullCube();
        };
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        BlockEntity container = world.getBlockEntity(pos.up());
        if (container != null && world.getBlockState(pos.offset(state.get(FACING))).isOf(Conveyors.CONVEYOR)) {
            world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
            world.setBlockState(pos.up().offset(state.get(FACING)), container.getCachedState());
        }
    }
}
