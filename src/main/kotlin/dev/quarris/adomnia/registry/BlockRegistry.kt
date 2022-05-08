package dev.quarris.adomnia.registry

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.blocks.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.*
import net.minecraft.world.level.material.*
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object BlockRegistry {
    val Registry: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, ModRef.ID)


    val Composter: RegistryObject<Block> = Registry.register("composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(4f))
    }

}