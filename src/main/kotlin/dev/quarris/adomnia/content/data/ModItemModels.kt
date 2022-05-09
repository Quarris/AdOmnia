package dev.quarris.adomnia.content.data

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
        withExistingParent(modLoc("item/oak_composter").path, modLoc("block/oak_composter"))
//        withExistingParent(modLoc("item/spruce_composter").path, modLoc("block/spruce_composter"))
//        withExistingParent(modLoc("item/jungle_composter").path, modLoc("block/jungle_composter"))
//        withExistingParent(modLoc("item/birch_composter").path, modLoc("block/birch_composter"))

        withExistingParent(ItemRegistry.OakAcorn.get().registryName!!.path, mcLoc("item/handheld"))
            .texture("layer0", modLoc("item/oak_acorn"))

    }
}