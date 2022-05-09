package dev.quarris.adomnia.content.data

import dev.quarris.adomnia.*
import dev.quarris.adomnia.registry.*
import net.minecraft.data.DataGenerator
import net.minecraft.world.level.block.Block
import net.minecraftforge.client.model.generators.BlockModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * Handles the generation of our models
 */
class ModBlockModels(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockModelProvider(dataGenerator, ModRef.ID, existingFileHelper) {

    override fun registerModels() {
        withExistingParent(modLoc("block/oak_composter").path, modLoc("block/composter"))
            .texture("planks", mcLoc("block/oak_planks"))
            .texture("log_top", mcLoc("block/oak_log_top"))
            .texture("log", mcLoc("block/oak_log"))

//        withExistingParent(modLoc("block/spruce_composter").path, BlockRegistry.Composter.get().registryName!!.path)
//            .texture("planks", mcLoc("block/spruce_planks"))
//            .texture("log_top", mcLoc("block/spruce_log_top"))
//            .texture("log", mcLoc("block/spruce_log"))
//
//        withExistingParent(modLoc("block/jungle_composter").path, BlockRegistry.Composter.get().registryName!!.path)
//            .texture("planks", mcLoc("block/jungle_planks"))
//            .texture("log_top", mcLoc("block/jungle_log_top"))
//            .texture("log", mcLoc("block/jungle_log"))
//
//        withExistingParent(modLoc("block/birch_composter").path, BlockRegistry.Composter.get().registryName!!.path)
//            .texture("planks", mcLoc("block/birch_planks"))
//            .texture("log_top", mcLoc("block/birch_log_top"))
//            .texture("log", mcLoc("block/birch_log"))


    }
}