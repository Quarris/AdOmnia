package dev.quarris.adomnia.content.tiles

import dev.quarris.adomnia.extensions.debug
import dev.quarris.adomnia.registry.TileRegistry
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.util.Mth
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
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
     * The internal inventory for transforming input items into mulch
     */
    private val mulchInv: ItemStackHandler = ExecutableItemStackHandler(1, ::insertMulchItem)
    private val lazyMulchInv: LazyOptional<ItemStackHandler> = LazyOptional.of { mulchInv }

    /**
     * The internal inventory for transforming input items into mulch
     */
    private val reactantInv: ItemStackHandler = ExecutableItemStackHandler(1, ::insertReactantItem)
    private val lazyReactantInv: LazyOptional<ItemStackHandler> = LazyOptional.of { reactantInv }

    /**
     * The internal inventory for transforming input items into mulch
     */
    private val outputInv: ItemStackHandler = ExecutableItemStackHandler(1, onExtract = ::extractOutput)
    private val lazyOutputInv: LazyOptional<ItemStackHandler> = LazyOptional.of { outputInv }

    /**
     * The amount of mulch that was converted form its input items.
     * Mulch is always set in range (0, [MaxMulch])
     */
    var mulch: Int = 0
        private set(value) {
            field = Mth.clamp(value, 0, MaxMulch)
        }

    /**
     * Function to manually insert the mulch item (via right click etc.)
     */
    fun insertMulchItem(stack: ItemStack): Boolean {
        return insertMulchItem(0, stack, false).count != stack.count
    }

    /**
     * Called when an item is to be inserted into the composter
     */
    private fun insertMulchItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        if (stack.isEmpty) {
            return ItemStack.EMPTY
        }

        debug("Trying to inserting Mulch Item $stack")
        if (mulch < MaxMulch) {
            // TODO: Check is stack is a valid mulch item
            if (!simulate) {
                mulch += 10 // TODO: Add the configured mulch amount for the mulch item
                debug("Adding Mulch; New Mulch Levels: $mulch")
            }
            val retStack = stack.copy()
            retStack.shrink(1)
            return retStack
        }
        return stack
    }

    /**
     * Function to manually insert the reactant item (via right click etc.)
     */
    fun insertReactantItem(stack: ItemStack): Boolean {
        return insertReactantItem(0, stack, false).count != stack.count
    }

    /**
     * Called when a reactant item is to be inserted into the composter
     */
    private fun insertReactantItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        if (stack.isEmpty) {
            return ItemStack.EMPTY
        }
        return stack
    }

    /**
     * Called when an item is to be extract from the composter
     */
    private fun extractOutput(slot: Int, amount: Int, simulate: Boolean): ItemStack {
        if (amount == 0) {
            return ItemStack.EMPTY
        }
        /*
        If the mulch has been processed and the catalyst has been placed, this should return the output from the recipe
         */
        return ItemStack.EMPTY
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

    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return when (side) {
                Direction.UP -> lazyMulchInv.cast()
                Direction.DOWN -> lazyOutputInv.cast()
                else -> lazyReactantInv.cast()
            }
        }

        return super.getCapability(cap, side)
    }

    /**
     * Invalidate your capabilities here
     */
    override fun onInvalidate() {
    }
}

class ExecutableItemStackHandler(
    size: Int,
    private val onInsert: ((Int, ItemStack, Boolean) -> ItemStack)? = null,
    private val onExtract: ((Int, Int, Boolean) -> ItemStack)? = null
) : ItemStackHandler(size) {

    override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
        onInsert ?: return super.insertItem(slot, stack, simulate)
        return onInsert.invoke(slot, stack, simulate)
    }

    override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
        onExtract ?: return super.extractItem(slot, amount, simulate)
        return onExtract.invoke(slot, amount, simulate)
    }
}
