package dev.quarris.adomnia.content.tiles

import dev.quarris.adomnia.registry.*
import net.minecraft.core.BlockPos
import net.minecraft.nbt.*
import net.minecraft.world.*
import net.minecraft.world.InteractionResult.*
import net.minecraft.world.entity.player.*
import net.minecraft.world.item.*
import net.minecraft.world.level.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.*

/**
 * Used to turn certain blocks into other blocks. Usually should involve recipes that have natural
 * blocks such a dirt, grass, leaves, etc.
 */
class ComposterTile(pos: BlockPos, state: BlockState) :
    AbstractModTile<ComposterTile>(TileRegistry.Composter.get(), pos, state) {

    /**
     * Used to update the state of the block entity.
     * Called 20 times per second.
     *
     * Although you could use the block entities instance of the level,
     * the passed level is used as a way to avoid java-kotlin null safety, and the
     * level is most commonly accessed in the tick method.
     */
    override fun onTick(level: Level) {
    }

    /**
     * Called when a player right-clicks the block entity.
     * Called on both the server and client.
     *
     * By default, the result will just be passed on both the server
     * and client.
     *
     * Use [InteractionResult.sidedSuccess] for success that correct for the block entity side
     */
    override fun onUse(
        player: Player,
        level: Level,
        hitResult: BlockHitResult,
        itemInHand: ItemStack
    ): InteractionResult {
        return super.onUse(player, level, hitResult, itemInHand)
    }

    /**
     * Save your block entities data
     */
    override fun onSave(tag: CompoundTag) {
    }

    /**
     * Load your block entities data
     */
    override fun onLoad(tag: CompoundTag) {
    }

    /**
     * Invalidate your capabilities here
     */
    override fun onInvalidate() {
    }

    /**
     * Delegate saving to our own save function
     */
    override fun saveAdditional(pTag: CompoundTag) {
    }
}