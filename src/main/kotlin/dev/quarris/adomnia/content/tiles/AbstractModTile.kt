package dev.quarris.adomnia.content.tiles

import net.minecraft.core.BlockPos
import net.minecraft.nbt.*
import net.minecraft.network.*
import net.minecraft.network.protocol.*
import net.minecraft.network.protocol.game.*
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResult.*
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

/**
 * Creates a nice wrapper around a block entity for functionality that is common/shared
 * between all block entities within AdOmnia.
 */
abstract class AbstractModTile<T : BlockEntity>(type: BlockEntityType<T>, pos: BlockPos, state: BlockState) :
    BlockEntity(type, pos, state) {

    /**
     * Used to update the state of the block entity.
     * Called 20 times per second.
     *
     * Although you could use the block entities instance of the level,
     * the passed level is used as a way to avoid java-kotlin null safety, and the
     * level is most commonly accessed in the tick method.
     */
    open fun onTick(level: Level) = Unit

    /**
     * Called when a player right-clicks the block entity.
     * Called on both the server and client.
     *
     * By default, the result will just be passed on both the server
     * and client.
     *
     * Use [InteractionResult.sidedSuccess] for success that correct for the block entity side
     */
    open fun onUse(
        player: Player,
        level: Level,
        hitResult: BlockHitResult,
        itemInHand: ItemStack
    ): InteractionResult = PASS

    /**
     * Save your block entities data
     */
    protected open fun onSave(tag: CompoundTag) = Unit

    /**
     * Load your block entities data
     */
    protected open fun onLoad(tag: CompoundTag) = Unit

    /**
     * Invalidate your capabilities here
     */
    protected open fun onInvalidate() = Unit

    /**
     * Marks the block as changed, and updates the client's block entity instance
     */
    protected fun update() {
        setChanged()
        level?.sendBlockUpdated(worldPosition, blockState, blockState, 3)
    }

    /**
     * Delegate saving to our own save function
     */
    override fun saveAdditional(pTag: CompoundTag) {
        super.saveAdditional(pTag)
        onSave(pTag)
    }

    /**
     * Delegate loading to our own load function
     */
    override fun load(pTag: CompoundTag) {
        super.load(pTag)
        onLoad(pTag)
    }

    /**
     * Creates a packet containing our nbt data that is sent off to the client to update its instance
     */
    override fun getUpdatePacket(): Packet<ClientGamePacketListener>? = ClientboundBlockEntityDataPacket.create(this)

    /**
     * Used to invalidate the current capabilities of the block entity.
     */
    override fun invalidateCaps() {
        super.invalidateCaps()
        onInvalidate()
    }

    /**
     * Called when an update tag needs to be sent to the client. Simply serializes the nbt
     * data of this block entities instance
     */
    override fun getUpdateTag(): CompoundTag = serializeNBT()

    /**
     * Called when the chunk's TE update tag, gotten from [BlockEntity.getUpdateTag], is received on the client.
     */
    override fun handleUpdateTag(tag: CompoundTag) {
        super.handleUpdateTag(tag)
        load(tag)
    }

    /**
     * Called on the client upon receiving an update from the server via the [update] command.
     * Deserializes our data on the client to allow it to be in sync with the server.
     */
    override fun onDataPacket(net: Connection, pkt: ClientboundBlockEntityDataPacket) {
        super.onDataPacket(net, pkt)
        handleUpdateTag(pkt.tag!!)
    }

}