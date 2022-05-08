package dev.quarris.adomnia

import dev.quarris.adomnia.registry.*
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.*

@Mod(ModRef.ID)
object ModRoot {
    init {
        ItemRegistry.Registry.register(MOD_BUS)
        BlockRegistry.Registry.register(MOD_BUS)
        TileRegistry.Registry.register(MOD_BUS)
    }
}