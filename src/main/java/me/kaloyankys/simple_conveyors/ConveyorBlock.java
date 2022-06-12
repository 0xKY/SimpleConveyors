package me.kaloyankys.simple_conveyors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ConveyorBlock extends Block {
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
        entity.setVelocity(state.get(FACING).getOffsetX() / (16.0f - world.getReceivedRedstonePower(pos) + 3) , 0, state.get(FACING).getOffsetZ() / (16.0f - world.getReceivedRedstonePower(pos) + 3));
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.setVelocity(state.get(FACING).getOffsetX() / 3.0f, 0.1, state.get(FACING).getOffsetZ() / 3.0f);
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean canMobSpawnInside() {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.1, 0.0, 0.0, 0.9, 1.0, 1.0);
    }
}
