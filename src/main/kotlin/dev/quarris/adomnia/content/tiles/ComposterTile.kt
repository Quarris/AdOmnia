package dev.quarris.adomnia.content.tiles

import dev.quarris.adomnia.extensions.getFirst
import dev.quarris.adomnia.registry.TileRegistry
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Mth
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.items.ItemStackHandler

/**
 * Composter turn compostable items (defined either by tags or recipes .. TBD) into Mulch.
 * Once Mulch is filled up to the [MaxMulch] amount,
 * it can be activated by a catalyst to turn it into an output item/fluid.
 */
class ComposterTile(pos: BlockPos, state: BlockState) :
    AbstractModTile<ComposterTile>(TileRegistry.Composter.get(), pos, state) {

    /**
     * Maximum mulch can the composter can store.
     */
    val MaxMulch: Int = 100

    /**
     * The internal inventory for storing items to compost into mulch
     */
    private val mulchItems: ItemStackHandler = ItemStackHandler(4)

    /**
     * The amount of mulch that was converted form its input items.
     * Mulch is always set in range (0, 100)
     */
    private var mulch: Int = 0
        set(value) {
            field = Mth.clamp(value, 0, MaxMulch)
        }

    /**
     * Called when the player stops on the block within the required bounds.
     * Turns internal valid items into mulch per stomp.
     */
    fun stomp() {
        if (mulch < MaxMulch) {
            mulchItems.getFirst().ifPresent {
                it.shrink(1)
                mulch += 10
            }
        }
    }

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
        tag.putInt("Mulch", mulch)
    }

    /**
     * Load your block entities data
     */
    override fun onLoad(tag: CompoundTag) {
        mulch = tag.getInt("Mulch")
    }

    /**
     * Invalidate your capabilities here
     */
    override fun onInvalidate() {
    }
}
