package dev.quarris.adomnia.extensions

import dev.quarris.adomnia.modules.*
import net.minecraftforge.eventbus.api.*
import net.minecraftforge.eventbus.api.EventPriority.*
import net.minecraftforge.fml.event.IModBusEvent
import thedarkcolour.kotlinforforge.forge.*
import kotlin.reflect.*
import kotlin.reflect.full.*

/**
 * Registers a listener for the correct event bus depending on the passed event type.
 */
internal inline fun <reified T : Event> IModule.registerListener(crossinline consumer: (T) -> Unit) {
    if (T::class.isSubclassOf(IModBusEvent::class)) {
        MOD_BUS.addListener(NORMAL, true, T::class.java) {
            consumer(it)
        }
        debug("Registered mod bus event listener for event type: ${T::class.simpleName}")
    } else {
        FORGE_BUS.addListener(NORMAL, true, T::class.java) {
            consumer(it)
        }
        debug("Registered forge bus event listener for event type: ${T::class.simpleName}")
    }
}

/**
 * Finds all the child objects within this [IModule] instances and registers them using the [MOD_BUS].
 * Cast is okay because we check the subclass of the child instance.
 */
@Suppress("UNCHECKED_CAST")
internal fun IModule.registerHolders() {
    for (child in this::class.nestedClasses) //Iterate through nested child objects
        if (child.isSubclassOf(RegistryHolder::class)) //Check if child is registry holder
            registerHolder<RegistryHolder<*>>(child as KClass<RegistryHolder<*>>) //Register the instance
}

/**
 * this will attempt to register the given holder class, by first trying to get it's object instance.
 * If the object instance is null, it's simply not registered and a Darn log message is sent.
 */
internal fun <T : RegistryHolder<*>> IModule.registerHolder(holderClass: KClass<T>) {
    val instance = holderClass.objectInstance
    if (instance == null) {
        warn("Attempted to register a non-static instance of a registry holder, please make sure you use the kotlin type 'object' for the registry holder instance")
        return
    }
    instance.register(MOD_BUS)
    info("Registered registry holder named '${holderClass.simpleName}'")
}

/**
 * Allows for kotlin inference of the T type and calls the [registerHolder] using [T]
 */
internal inline fun <reified T : RegistryHolder<*>> IModule.registerHolder() = registerHolder<T>(T::class)
