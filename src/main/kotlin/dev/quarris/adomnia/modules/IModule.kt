package dev.quarris.adomnia.modules

import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.IEventBus
import thedarkcolour.kotlinforforge.forge.*


/**
 * Module to implement with the ModuleManager system
 * Each module is to be designed for a single feature and to be enabled/disabled via configs
 */
interface IModule {

    /**
     * Called upon the module being loaded.
     */
    fun onInit()

    /**
     * ID for this module.
     */
    val id: String

}