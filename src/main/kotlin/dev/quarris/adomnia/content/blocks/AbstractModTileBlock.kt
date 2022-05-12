package dev.quarris.adomnia.content.blocks

import dev.quarris.adomnia.content.tiles.AbstractModTile
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.registries.RegistryObject

/**
 * Adds functionality that will be used throughout the blocks within the adomnia mod
 */
abstract class AbstractModTileBlock<T : AbstractModTile<T>>(
    properties: Properties,
    private val type: RegistryObject<BlockEntityType<T>>
) : AbstractModBlock(properties), EntityBlock {

    @Suppress("UNCHECKED_CAST")
    fun getTile(level: Level, pos: BlockPos): T = level.getBlockEntity(pos) as T

    /**
     * Delegates the creation of the block entity via the passed [type]
     */
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity? = type.get().create(pPos, pState)

    /**
     * Uses the [TickDelegator] to delegate ticking to the [AbstractModTile] instances.
     * Cast is safe because the classes [T] extends [AbstractModTile]
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : BlockEntity?> getTicker(
        pLevel: Level,
        pState: BlockState,
        pBlockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? = TickDelegator as BlockEntityTicker<T>

    /**
     * This is used internally to delegate all ticking straight to the [AbstractModTile] onTick method
     */
    private object TickDelegator : BlockEntityTicker<AbstractModTile<*>> {
        /**
         * Delegate to block entity
         */
        override fun tick(pLevel: Level, pPos: BlockPos, pState: BlockState, pBlockEntity: AbstractModTile<*>) =
            pBlockEntity.onTick(pLevel)
    }
}