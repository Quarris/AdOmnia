package dev.quarris.adomnia.content.items

import net.minecraft.world.item.*
import net.minecraft.world.item.context.*

abstract class AbstractModItem(properties: Properties) : Item(properties) {
    /**
     * This will decrease the stack size by one, or delete it there is only
     * one item.
     */
    protected fun shrinkItemstack(context: UseOnContext) {
        val player = context.player ?: return
        if (!player.abilities.instabuild) {
            val itemInHands = context.itemInHand
            itemInHands.shrink(1)
            player.setItemInHand(context.hand, itemInHands)
        }
    }


}