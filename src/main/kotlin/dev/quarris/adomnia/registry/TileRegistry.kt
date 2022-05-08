package dev.quarris.adomnia.registry

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.tiles.*
import net.minecraft.core.*
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.*
import net.minecraft.world.level.block.state.*
import net.minecraftforge.registries.*

object TileRegistry {

    val Registry: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModRef.ID)

    val Composter: RegistryObject<BlockEntityType<ComposterTile>> = Registry.register("composter") {
        tile(BlockRegistry.Composter.get()) { ComposterTile(it.first, it.second) }
    }

    /**
     * Allows for easily creating tile entity builders
     */
    private inline fun <reified T : BlockEntity> tile(
        block: Block, crossinline supplier: (Pair<BlockPos, BlockState>) -> T,
    ): BlockEntityType<T> = BlockEntityType.Builder.of({ pos, state -> supplier(pos to state) }, block).build(null)


}