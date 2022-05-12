package dev.quarris.adomnia.modules

import dev.quarris.adomnia.*
import dev.quarris.adomnia.extensions.*
import dev.quarris.adomnia.extensions.info
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.*
import org.objectweb.asm.*
import kotlin.reflect.*
import kotlin.reflect.full.*

/**
 * Statically handles the registration of the modules via classpath searching
 * through the FML provided scan results. TODO: make modules only load if configured todo so
 */
object ModuleManager {
    private val modules = HashMap<String, IModule>()

    /**
     * This will locate all module class instances on the class path and
     * add them to the modules list.
     * grab class instances thave have the
     * [IModule] class as a implementing interface anyway, so we know
     * The suppression of the unchecked cast is okay because we onlyit's of type IModule
     */
    @Suppress("UNCHECKED_CAST")
    fun buildModules() {
        val scanResults = ModList.get().getModFileById(ModRef.ID).file.scanResult
        val moduleTypes = scanResults.classes.filter {
            it.interfaces.contains(Type.getType(IModule::class.java)) || it.parent == Type.getType(AbstractModule::class.java)
        }
        val moduleClasses = moduleTypes.mapNotNull {
            try {
                debug("Loaded module class: ${it.clazz.className}")
                Class.forName(it.clazz.className).kotlin as KClass<IModule>
            } catch (ex: Exception) {
                //Ignored, just dont load
                warn("Failed to load module class: ${it.clazz.className}")
                null
            }
        }
        instantiateModules(moduleClasses)
    }

    /**
     * Creates the instances of the modules, and calls their initializers
     */
    private fun instantiateModules(moduleClasses: List<KClass<IModule>>) {
        info("Loading modules: $moduleClasses")
        for (module in moduleClasses) {
            try {
                val instance = module.objectInstance ?: module.createInstance()
                //TODO check config here to see if module is enabled or not
                instance.onInit()
                modules[instance.id] = instance
                info("Successfully registered module with id ${instance.id}!")
            } catch (ex: Exception) {
                warn("Failed to create module instance of module type ${module.simpleName}")
                continue
            }
        }
    }

}