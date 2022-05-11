package dev.quarris.adomnia.modules


/**
 * Module to implement with the ModuleManager system
 * Each module is to be designed for a single feature and to be enabled/disabled via configs
 */
interface IModule {

    fun onInit()

    fun registerListener() {
        /*
        event = invoker.getEventType()
        if (event is IModBusEvent)
            MOD_BUS....
        else
            FORGE_BUS....


         */
    }

    /**
     * Enables this module.
     */
    fun enable() = ModuleManager.setEnabled(this.id, true)

    /**
     * Checks if this module is currently enabled.
     */
    val isEnabled: Boolean
        get() = ModuleManager.isEnabled(this.id)

    /**
     * ID for this module.
     */
    val id: String

}