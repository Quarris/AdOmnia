package dev.quarris.adomnia.content.data

import dev.quarris.adomnia.*
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
        add(ItemRegistry.OakComposter.get(), "Composter")
        add(ItemRegistry.OakAcorn.get(), "Oak Acorn")
    }

}