package dev.quarris.adomnia.content.blocks

import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.*

/**
 * Adds functionality that will be used throughout the blocks within the adomnia mod
 */
abstract class AbstractModBlock(properties: Properties) : Block(properties) {

    /**
     * Extends the voxel shape class to allow for .join on shapes in a builder fashion.
     */
    protected fun VoxelShape.join(other: VoxelShape, op: BooleanOp): VoxelShape = Shapes.join(this, other, op)
}