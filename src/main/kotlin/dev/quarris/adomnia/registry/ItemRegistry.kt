package dev.quarris.adomnia.registry

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.blocks.*
import dev.quarris.adomnia.content.items.*
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour.*
import net.minecraft.world.level.material.*
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ItemRegistry {
    val Registry: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, ModRef.ID)

    val Composter: RegistryObject<Item> = Registry.register("composter") {
        ComposterItem(Properties().stacksTo(1).tab(CreativeTab))
    }

    val OakAcorn: RegistryObject<AcornItem> = Registry.register("oak_acorn") {
        AcornItem(Blocks.OAK_SAPLING, Properties().tab(CreativeTab))
    }


    /**Private because it should only be accessed through this object, enforcing the
     * creation of item properties to be done here and not in place (the item constructor)**/
    private val CreativeTab = object : CreativeModeTab("AdOmnia") {
        override fun makeIcon(): ItemStack = ItemStack(Composter.get())
    }
}