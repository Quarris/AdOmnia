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
        ComposterItem(Properties().stacksTo(1).tab(CreativeTab), Oak)
    }
    val BirchComposter: RegistryObject<Item> = Registry.register("birch_composter") {
        ComposterItem(Properties().stacksTo(1).tab(CreativeTab), Birch)
    }
    val SpruceComposter: RegistryObject<Item> = Registry.register("spruce_composter") {
        ComposterItem(Properties().stacksTo(1).tab(CreativeTab), Spruce)
    }
    val JungleComposter: RegistryObject<Item> = Registry.register("jungle_composter") {
        ComposterItem(Properties().stacksTo(1).tab(CreativeTab), Jungle)
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