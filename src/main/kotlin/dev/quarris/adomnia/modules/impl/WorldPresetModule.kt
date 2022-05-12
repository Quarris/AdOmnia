package dev.quarris.adomnia.modules.impl

import dev.quarris.adomnia.level.*
import dev.quarris.adomnia.modules.*
import net.minecraft.core.*
import net.minecraftforge.common.world.*
import net.minecraftforge.registries.ForgeRegistries

object WorldPresetModule : AbstractModule() {

    val SingleSkyblockGenerator by register(Registry.CHUNK_GENERATOR_REGISTRY, "single_skyblock") {
        SingleSkyblockSource.CODEC
    }

    val SingleSkyblock by register(ForgeRegistries.Keys.WORLD_TYPES, "single_skyblock"){
        ForgeWorldPreset { reg: RegistryAccess, seed: Long ->
            SingleSkyblockSource(reg, seed)
        }
    }

    /**
     * ID for this module.
     */
    override val id: String = "world"
}