package dev.quarris.adomnia.modules

/**
 * Module to implement with the ModuleManager system
 * Each module is to be designed for a single feature and to be enabled/disabled via configs
 */
interface IModule {
    /**
     * Enables this module.
     */
    fun enable() = ModuleManager.setEnabled(this.name, true)

    /**
     * Checks if this module is currently enabled.
     */
    val isEnabled: Boolean
        get() = ModuleManager.isEnabled(this.name)

    /**
     * Name of this module, used for data storage
     */
    val name: String

}