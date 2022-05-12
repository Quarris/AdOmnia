package dev.quarris.adomnia.modules.impl

import dev.quarris.adomnia.content.blocks.ComposterBlock
import dev.quarris.adomnia.content.tiles.*
import dev.quarris.adomnia.extensions.info
import dev.quarris.adomnia.modules.*
import dev.quarris.adomnia.utils.*
import net.minecraft.world.item.*
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockBehaviour.*
import net.minecraft.world.level.material.*
import net.minecraftforge.registries.*

object ComposterModule : AbstractModule() {
    //======================== Blocks ===========================
    val OakComposterBlock by register(ForgeRegistries.BLOCKS, "oak_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    val SpruceComposterBlock by register(ForgeRegistries.BLOCKS, "spruce_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }
    val JungleComposterBlock by register(ForgeRegistries.BLOCKS, "jungle_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    val BirchComposterBlock by register(ForgeRegistries.BLOCKS, "birch_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    val DarkOakComposterBlock by register(ForgeRegistries.BLOCKS, "dark_oak_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }
    val AcaciaComposterBlock by register(ForgeRegistries.BLOCKS, "acacia_composter") {
        ComposterBlock(Properties.of(Material.WOOD).strength(2.0f, 3.0f).sound(SoundType.WOOD))
    }

    //======================== Items ===========================
    val OakComposterItem by register<Item, BlockItem>(ForgeRegistries.ITEMS, "oak_composter") {
        object : BlockItem(OakComposterBlock(), Properties().tab(CreativeTab)) {}
    }
    val BirchComposterItem by register<Item, BlockItem>(ForgeRegistries.ITEMS, "birch_composter") {
        object : BlockItem(BirchComposterBlock(), Properties().tab(CreativeTab)) {}
    }
    val SpruceComposterItem by register<Item, BlockItem>(ForgeRegistries.ITEMS, "spruce_composter") {
        object : BlockItem(SpruceComposterBlock(), Properties().tab(CreativeTab)) {}
    }
    val JungleComposterItem by register<Item, BlockItem>(ForgeRegistries.ITEMS, "jungle_composter") {
        object : BlockItem(JungleComposterBlock(), Properties().tab(CreativeTab)) {}
    }
    val DarkOakComposterItem by register<Item, BlockItem>(ForgeRegistries.ITEMS, "dark_oak_composter") {
        object : BlockItem(DarkOakComposterBlock(), Properties().tab(CreativeTab)) {}
    }
    val AcaciaComposterItem by register<Item, BlockItem>(ForgeRegistries.ITEMS, "acacia_composter") {
        object : BlockItem(AcaciaComposterBlock(), Properties().tab(CreativeTab)) {}
    }

    //======================== Tiles ===========================
    val Composter by register(ForgeRegistries.BLOCK_ENTITIES, "composter") {
        tile(
            OakComposterBlock(),
            AcaciaComposterBlock(),
            DarkOakComposterBlock(),
            SpruceComposterBlock(),
            BirchComposterBlock(),
            JungleComposterBlock()
        ) { ComposterTile(it.first, it.second) }
    }


    /**Private because it should only be accessed through this object, enforcing the
     * creation of item properties to be done here and not in place (the item constructor)**/
    private val CreativeTab: CreativeModeTab = object : CreativeModeTab("AdOmnia Composter") {
        override fun makeIcon(): ItemStack = ItemStack(BirchComposterItem())
    }

    /**
     * Initialize everything relating to the composter module
     */
    override fun onInit() {
        info("Registering the composter module!")
        super.onInit()
    }

    override val id: String = "composter"


}