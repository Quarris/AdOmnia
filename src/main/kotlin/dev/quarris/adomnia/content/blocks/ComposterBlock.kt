package dev.quarris.adomnia.content.blocks

import dev.quarris.adomnia.content.tiles.*
import dev.quarris.adomnia.registry.*
import net.minecraft.core.*
import net.minecraft.world.level.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.*
import net.minecraft.world.level.block.state.*
import net.minecraft.world.phys.shapes.*

/**
 * A block that has custom recipes for generating resources
 */
class ComposterBlock(properties: Properties) :
    AbstractModTileBlock<ComposterTile>(properties, TileRegistry.Composter), EntityBlock {

    private val composterShape = Shapes.empty()
        .join(Shapes.box(0.0625, 0.0, 0.0625, 0.25, 0.0625, 0.25), BooleanOp.OR)
        .join(Shapes.box(0.75, 0.0, 0.0625, 0.9375, 0.0625, 0.25), BooleanOp.OR)
        .join(Shapes.box(0.75, 0.0, 0.75, 0.9375, 0.0625, 0.9375), BooleanOp.OR)
        .join(Shapes.box(0.0625, 0.0, 0.75, 0.25, 0.0625, 0.9375), BooleanOp.OR)
        .join(Shapes.box(0.0625, 0.0625, 0.0625, 0.9375, 0.125, 0.9375), BooleanOp.OR)
        .join(Shapes.box(0.0625, 0.125, 0.0625, 0.125, 0.9375, 0.9375), BooleanOp.OR)
        .join(Shapes.box(0.875, 0.125, 0.0625, 0.9375, 0.9375, 0.9375), BooleanOp.OR)
        .join(Shapes.box(0.125, 0.125, 0.0625, 0.875, 0.9375, 0.125), BooleanOp.OR)
        .join(Shapes.box(0.125, 0.125, 0.875, 0.875, 0.9375, 0.9375), BooleanOp.OR)

    /**
     * Our shape is not dependent on the composter as it doesn't rotate or have any state,
     * so we only need a single shaep
     */
    override fun getShape(
        pState: BlockState,
        pLevel: BlockGetter,
        pPos: BlockPos,
        pContext: CollisionContext
    ): VoxelShape = composterShape

}