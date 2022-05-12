package dev.quarris.adomnia.helper

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

object ItemStackHelper {
    fun shrinkItemForPlayer(player: Player, stack: ItemStack, amount: Int = 1) {
        if (!player.abilities.instabuild) {
            stack.shrink(amount)
        }
    }
}