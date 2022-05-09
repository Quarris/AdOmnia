package dev.quarris.adomnia.registry

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.blocks.ComposterBlock
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour.*
import net.minecraft.world.level.material.*
import net.minecraftforge.registries.*

object BlockRegistry {
    val Registry: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, ModRef.ID)

    val OakComposter: RegistryObject<Block> = Registry.register("oak_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    val SpruceComposter: RegistryObject<Block> = Registry.register("spruce_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    val JungleComposter: RegistryObject<Block> = Registry.register("jungle_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    val BirchComposter: RegistryObject<Block> = Registry.register("birch_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    val DarkOakComposter: RegistryObject<Block> = Registry.register("dark_oak_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }
    val AcaciaComposter: RegistryObject<Block> = Registry.register("acacia_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

}