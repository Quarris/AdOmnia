package dev.quarris.adomnia.registry

import dev.quarris.adomnia.ModRef
import dev.quarris.adomnia.content.items.GrassStarterItem
import dev.quarris.adomnia.content.items.SaplingSeedItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ItemRegistry {
    val Registry: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, ModRef.ID)

    //    val OakComposter: RegistryObject<Item> = Registry.register("oak_composter") {
//        object : BlockItem(BlockRegistry.OakComposter.get(), Properties().tab(CreativeTab)) {}
//    }
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

    val OakAcorn: RegistryObject<SaplingSeedItem> = Registry.register("oak_acorn") {
        SaplingSeedItem(Blocks.OAK_SAPLING, Properties().tab(CreativeTab))
    }
    val BirchSeed: RegistryObject<SaplingSeedItem> = Registry.register("birch_seed") {
        SaplingSeedItem(Blocks.BIRCH_SAPLING, Properties().tab(CreativeTab))
    }
    val SpruceCone: RegistryObject<SaplingSeedItem> = Registry.register("spruce_cone") {
        SaplingSeedItem(Blocks.SPRUCE_SAPLING, Properties().tab(CreativeTab))
    }
    val JungleSeed: RegistryObject<SaplingSeedItem> = Registry.register("jungle_seed") {
        SaplingSeedItem(Blocks.JUNGLE_SAPLING, Properties().tab(CreativeTab))
    }

    val GrassStarter: RegistryObject<GrassStarterItem> = Registry.register("grass_starter") {
        GrassStarterItem(Properties().tab(CreativeTab))
    }


    /**Private because it should only be accessed through this object, enforcing the
     * creation of item properties to be done here and not in place (the item constructor)**/
    private val CreativeTab = object : CreativeModeTab("AdOmnia") {
        override fun makeIcon(): ItemStack = ItemStack(BirchComposter.get())
    }
}