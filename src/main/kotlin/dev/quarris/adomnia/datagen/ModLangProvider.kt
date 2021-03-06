package dev.quarris.adomnia.datagen

import dev.quarris.adomnia.*
import dev.quarris.adomnia.modules.impl.*
import dev.quarris.adomnia.registry.*
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider

/**
 * Handles the generation of our translation files
 */
class ModLangProvider(dataGenerator: DataGenerator, locale: String) :
    LanguageProvider(dataGenerator, ModRef.ID, locale) {

    /**
     * Create our translations here
     */
    override fun addTranslations() {
//        add(ItemRegistry.OakComposter.get(), "Composter")
//        add(GrowModule.OakAcorn.get(), "Oak Acorn")
    }

}