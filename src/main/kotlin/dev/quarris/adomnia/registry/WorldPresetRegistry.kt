package dev.quarris.adomnia.registry

import com.mojang.serialization.Codec
import dev.quarris.adomnia.ModRef
import dev.quarris.adomnia.level.SingleSkyblockSource
import net.minecraft.core.Registry
import net.minecraft.core.RegistryAccess
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraftforge.common.world.ForgeWorldPreset
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object WorldPresetRegistry {
//
//    val PresetRegistry: DeferredRegister<ForgeWorldPreset> = DeferredRegister.create(ForgeRegistries.Keys.WORLD_TYPES, ModRef.ID)
//    val ChunkGenRegistry: DeferredRegister<Codec<out ChunkGenerator>> = DeferredRegister.create(Registry.CHUNK_GENERATOR_REGISTRY, ModRef.ID)
//
//    val SingleSkyblockGenerator: RegistryObject<Codec<out ChunkGenerator>> = ChunkGenRegistry.register("single_skyblock") { SingleSkyblockSource.CODEC }
//
//    val SingleSkyblock: RegistryObject<ForgeWorldPreset> = PresetRegistry.register("single_skybock") {
//        ForgeWorldPreset { reg: RegistryAccess, seed: Long ->
//            SingleSkyblockSource(reg, seed)
//        }
//    }
//
}