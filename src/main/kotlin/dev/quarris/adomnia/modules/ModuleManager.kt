package dev.quarris.adomnia.modules

import dev.quarris.adomnia.modules.impl.GrowModule

object ModuleManager {

    private val modules = HashMap<String, IModule>()
    private val enabledModules = ArrayList<String>()

    init {
        this.addModule(GrowModule).enable()
    }

    fun addModule(module: IModule) : IModule {
        modules[module.name] = module
        return module
    }

    fun setEnabled(name: String, enable: Boolean) {
        if (enable) enabledModules.add(name)
        else enabledModules.remove(name)
    }

    fun isEnabled(name: String) : Boolean = enabledModules.contains(name)
}