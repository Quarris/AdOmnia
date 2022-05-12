package dev.quarris.adomnia

import dev.quarris.adomnia.modules.*
import dev.quarris.adomnia.registry.WorldPresetRegistry
import dev.quarris.adomnia.registry.*
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(ModRef.ID)
object ModRoot {

    init {
//        WorldPresetRegistry.PresetRegistry.register(MOD_BUS)
//        WorldPresetRegistry.ChunkGenRegistry.register(MOD_BUS)
//        ItemRegistry.Registry.register(MOD_BUS)
//        BlockRegistry.Registry.register(MOD_BUS)
//        TileRegistry.Registry.register(MOD_BUS)
        ModuleManager.buildModules()
    }

}