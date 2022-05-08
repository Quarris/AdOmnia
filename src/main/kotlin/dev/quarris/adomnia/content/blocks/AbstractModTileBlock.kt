package dev.quarris.adomnia.content.blocks

import dev.quarris.adomnia.content.tiles.*
import net.minecraft.core.*
import net.minecraft.world.*
import net.minecraft.world.entity.player.*
import net.minecraft.world.level.*
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.*
import net.minecraft.world.level.block.state.*
import net.minecraft.world.phys.*
import net.minecraftforge.registries.RegistryObject

/**
 * Adds functionality that will be used throughout the blocks within the adomnia mod
 */
abstract class AbstractModTileBlock<T : AbstractModTile<T>>(
    properties: Properties,
    private val type: RegistryObject<BlockEntityType<T>>
) : AbstractModBlock(properties), EntityBlock {

    /**
     * Delegates the creation of the block entity via the passed [type]
     */
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity? = type.get().create(pPos, pState)

    /**
     * Deletes the right click action on the block to the instance of the tile entity at the given position.
     */
    override fun use(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pPlayer: Player,
        pHand: InteractionHand,
        pHit: BlockHitResult
    ): InteractionResult {
        val tile = pLevel.getBlockEntity(pPos)
        if (tile !is AbstractModTile<*>) return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit)
        val itemInHand = pPlayer.getItemInHand(pHand)
        return tile.onUse(pPlayer, pLevel, pHit, itemInHand)
    }

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