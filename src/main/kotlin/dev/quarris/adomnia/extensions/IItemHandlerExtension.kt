package dev.quarris.adomnia.extensions

import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler
import java.util.Optional

fun IItemHandler.getFirst(): Optional<ItemStack> {
    for (i in 0..this.slots) {
        val item = this.getStackInSlot(i)
        if (!item.isEmpty) {
            return Optional.of(item)
        }
    }

    return Optional.empty()
}

