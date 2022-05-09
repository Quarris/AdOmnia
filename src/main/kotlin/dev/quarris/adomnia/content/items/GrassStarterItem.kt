package dev.quarris.adomnia.content.items

import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks

class GrassStarterItem(props: Properties) : AbstractModItem(props) {

    /**
     * Creates a Grass Block from Dirt Block on Right Click
     */
    override fun useOn(ctx: UseOnContext): InteractionResult {
        if (!ctx.level.isClientSide() && ctx.level.getBlockState(ctx.clickedPos).block == Blocks.DIRT) {
            ctx.level.setBlock(ctx.clickedPos, Blocks.GRASS_BLOCK.defaultBlockState(), 3)
            this.shrinkItemstack(ctx)
        }

        return super.useOn(ctx)
    }

}