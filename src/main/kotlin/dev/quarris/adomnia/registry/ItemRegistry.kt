package dev.quarris.adomnia.registry

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.blocks.VariantState.*
import dev.quarris.adomnia.content.items.*
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.*
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ItemRegistry {
    val Registry: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, ModRef.ID)

    val OakComposter: RegistryObject<Item> = Registry.register("oak_composter") {
        object : BlockItem(BlockRegistry.OakComposter.get(), Properties().tab(CreativeTab)) {}
    }
    val BirchComposter: RegistryObject<Item> = Registry.register("birch_composter") {
        object : BlockItem(BlockRegistry.BirchComposter.get(), Properties().tab(CreativeTab)) {}
    }
    val SpruceComposter: RegistryObject<Item> = Registry.register("spruce_composter") {
        object : BlockItem(BlockRegistry.SpruceComposter.get(), Properties().tab(CreativeTab)) {}
    }
    val JungleComposter: RegistryObject<Item> = Registry.register("jungle_composter") {
        object : BlockItem(BlockRegistry.JungleComposter.get(), Properties().tab(CreativeTab)) {}
    }
    val DarkOakComposter: RegistryObject<Item> = Registry.register("dark_oak_composter") {
        object : BlockItem(BlockRegistry.DarkOakComposter.get(), Properties().tab(CreativeTab)) {}
    }
    val AcaciaComposter: RegistryObject<Item> = Registry.register("acacia_composter") {
        object : BlockItem(BlockRegistry.AcaciaComposter.get(), Properties().tab(CreativeTab)) {}
    }


    val OakAcorn: RegistryObject<AcornItem> = Registry.register("oak_acorn") {
        AcornItem(Blocks.OAK_SAPLING, Properties().tab(CreativeTab))
    }


    /**Private because it should only be accessed through this object, enforcing the
     * creation of item properties to be done here and not in place (the item constructor)**/
    private val CreativeTab = object : CreativeModeTab("AdOmnia") {
        override fun makeIcon(): ItemStack = ItemStack(OakComposter.get())
    }
}