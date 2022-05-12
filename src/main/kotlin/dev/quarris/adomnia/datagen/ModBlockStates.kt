package dev.quarris.adomnia.datagen

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.blocks.*
import dev.quarris.adomnia.content.blocks.VariantState.*
import dev.quarris.adomnia.registry.*
import net.minecraft.data.DataGenerator
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.*
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * Handles all the data generation for the block state
 */
class ModBlockStates(generator: DataGenerator, private val existingFileHelper: ExistingFileHelper) :
    BlockStateProvider(generator, ModRef.ID, existingFileHelper) {

    /**
     * Create our block states here
     */
    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    override fun registerStatesAndModels() {
        composterBlockstate(BlockRegistry.BirchComposter.get())
        composterBlockstate(BlockRegistry.JungleComposter.get())
//        composterBlockstate(BlockRegistry.OakComposter.get())
        composterBlockstate(BlockRegistry.SpruceComposter.get())
        composterBlockstate(BlockRegistry.DarkOakComposter.get())
        composterBlockstate(BlockRegistry.AcaciaComposter.get())
    }


    /**
     * Creates the blockstate for a composter block
     */
    private fun composterBlockstate(block: Block) {
        val name = block.registryName?.path
        simpleBlock(
            block, ModelFile.ExistingModelFile(
                modLoc("block/$name"), existingFileHelper
            )
        )
    }

}