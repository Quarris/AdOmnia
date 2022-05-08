package dev.quarris.adomnia.content.items

import net.minecraft.world.*
import net.minecraft.world.item.context.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SaplingBlock

class AcornItem(
    /**
     * Specifics the type of sapling block that will be placed upon right clicking the item
     * on a growable block
     */
    private val target: Block,
    /**
     * Specify properties, mainly just the creative tab for acorn items
     */
    properties: Properties
) : AbstractModItem(properties) {


    /**
     * This attempts to place the sapling at block above the clicked
     * position. It will validate that the block can sustain the given
     * sapling first.
     */
    override fun useOn(context: UseOnContext): InteractionResult {
        if (target is SaplingBlock) {
            val clicked = context.clickedPos
            val level = context.level
            val state = level.getBlockState(clicked)
            if (state.canSustainPlant(level, clicked, context.clickedFace, target)) {
                val up = clicked.above()
                level.setBlockAndUpdate(up, target.defaultBlockState())
                shrinkItemstack(context)
                return InteractionResult.sidedSuccess(level.isClientSide)
            }
        }
        return super.useOn(context)
    }


}