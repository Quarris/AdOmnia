package dev.quarris.adomnia.content.items

import dev.quarris.adomnia.content.blocks.*
import dev.quarris.adomnia.registry.*
import net.minecraft.world.item.BlockItem

class ComposterItem(properties: Properties, val variant: VariantState) :
    BlockItem(BlockRegistry.Composter.get(), properties)