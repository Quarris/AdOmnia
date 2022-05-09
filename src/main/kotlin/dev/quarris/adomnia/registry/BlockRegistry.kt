package dev.quarris.adomnia.registry

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.blocks.ComposterBlock
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour.*
import net.minecraft.world.level.material.*
import net.minecraftforge.registries.*

object BlockRegistry {
    val Registry: DeferredRegister<Block> = DeferredRegister.create(ForgeRegistries.BLOCKS, ModRef.ID)

    val Composter: RegistryObject<Block> = Registry.register("composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

}