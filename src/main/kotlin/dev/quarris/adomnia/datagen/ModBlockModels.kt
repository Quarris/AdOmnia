package dev.quarris.adomnia.datagen

import dev.quarris.adomnia.*
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.BlockModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * Handles the generation of our models
 */
class ModBlockModels(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    BlockModelProvider(dataGenerator, ModRef.ID, existingFileHelper) {

    override fun registerModels() {
        composterModel("block/oak_composter", "oak")
        composterModel("block/spruce_composter", "spruce")
        composterModel("block/birch_composter", "birch")
        composterModel("block/jungle_composter", "jungle")
        composterModel("block/dark_oak_composter", "dark_oak")
        composterModel("block/acacia_composter", "acacia")
    }

    /**
     * Creates a child composter from the block composter using the target name for the textures.
     */
    private fun composterModel(name: String, targetName: String) {
        withExistingParent(modLoc(name).path, modLoc("block/composter"))
            .texture("planks", mcLoc("block/${targetName}_planks"))
            .texture("log_top", mcLoc("block/${targetName}_log_top"))
            .texture("log", mcLoc("block/${targetName}_log"))
            .texture("particle", mcLoc("block/${targetName}_planks"))
    }
}