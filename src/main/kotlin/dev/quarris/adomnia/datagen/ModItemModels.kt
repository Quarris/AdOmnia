package dev.quarris.adomnia.datagen

import dev.quarris.adomnia.*
import dev.quarris.adomnia.registry.*
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * Handles the generation of our models
 */
class ModItemModels(dataGenerator: DataGenerator, existingFileHelper: ExistingFileHelper) :
    ItemModelProvider(dataGenerator, ModRef.ID, existingFileHelper) {

    override fun registerModels() {
        composterModel("oak_composter")
        composterModel("spruce_composter")
        composterModel("jungle_composter")
        composterModel("birch_composter")
        composterModel("dark_oak_composter")
        composterModel("acacia_composter")


        withExistingParent(ItemRegistry.OakAcorn.get().registryName!!.path, mcLoc("item/handheld"))
            .texture("layer0", modLoc("item/oak_acorn"))
    }

    private fun composterModel(name: String){
        withExistingParent(modLoc("item/$name").path, modLoc("block/$name"))
    }
}