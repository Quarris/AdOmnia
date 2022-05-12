package dev.quarris.adomnia.modules

import com.google.common.collect.*
import dev.quarris.adomnia.*
import dev.quarris.adomnia.extensions.*
import dev.quarris.adomnia.extensions.info
import dev.quarris.adomnia.utils.*
import dev.quarris.adomnia.utils.Opt.*
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.*
import org.lwjgl.system.CallbackI.*
import java.util.*
import java.util.function.*
import kotlin.properties.*
import kotlin.reflect.*

/**
 * This is meant to be used as a wrapper around a [IForgeRegistry]. It's meant to be implemented as a child
 * object an of [IModule], per registry type. This allows us to "lateinit"/delegate the regstiering of
 * the registry objects.
 */
abstract class RegistryHolder<R : IForgeRegistryEntry<R>>(private val forgeRegistry: IForgeRegistry<R>) {
    //The actual registryObjects that are used for deferring the already deferred register 😂
    private val registryObjects: MutableMap<String, RegistryObject<R>> = HashMap()

    //Stores the registry objects supplier to a supplied name.
    private val registryQueue: Queue<Pair<String, () -> R>> = Queues.newArrayDeque()

    /**
     * dequeues the suppliers inside the [registryQueue] and stores
     * the registered values inside the [registryObjects] with the named
     * from the first portion of the [Pair] in the queue.
     *
     * Also initializes the deferred registered.
     */
    fun register(modBus: IEventBus) {
        //The registry is only initialized/accessed upon the registration here.
        val deferredRegister = DeferredRegister.create(forgeRegistry, ModRef.ID)
        while (registryQueue.peek() != null) {
            val data = registryQueue.remove()
            registryObjects[data.first] = deferredRegister.register(data.first, data.second)
            debug("Registry object with name '${data.first}' registered to forge registry '${forgeRegistry.registryName}'")
        }
        deferredRegister.register(modBus)
    }


    /**
     * A [ReadOnlyProperty] is a special kotlin type that allows us to us the 'by' operater.
     * This allows us to access the regstiry objects once they've been registred.
     */
    protected fun <T : R> register(
        name: String, supplier: () -> T
    ): ReadOnlyProperty<Any?, Opt<T>> {
        registryQueue.add(name to supplier)
        return object : ReadOnlyProperty<Any?, Opt<T>>, Supplier<Opt<T>>, () -> Opt<T> {

            /**
             * This is the get method implementation for the [Supplier] interface.
             * It is used as interloped for the [getValue] and [invoke] calls.
             */
            @Suppress("UNCHECKED_CAST")
            override fun get(): Opt<T> {
                if (!registryObjects.containsKey(name)) return Opt.nil()
                return Opt.of(registryObjects[name]!!.get() as T)
            }

            /**
             * This is the implementation for the [ReadOnlyProperty]. It returns
             * the value represented by the supplier
             */
            override fun getValue(thisRef: Any?, property: KProperty<*>): Opt<T> = get()

            /**
             * Implementation of the kotlin supplier
             */
            override fun invoke(): Opt<T> = get()
        }
    }


}

