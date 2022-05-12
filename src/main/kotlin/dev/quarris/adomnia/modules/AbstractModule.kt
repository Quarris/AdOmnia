package dev.quarris.adomnia.modules

import com.google.common.collect.*
import dev.quarris.adomnia.*
import dev.quarris.adomnia.extensions.debug
import dev.quarris.adomnia.utils.*
import net.minecraftforge.registries.*
import thedarkcolour.kotlinforforge.forge.*
import java.util.*
import java.util.function.*
import kotlin.collections.HashMap
import kotlin.properties.*
import kotlin.reflect.*

abstract class AbstractModule : IModule {

    //The actual registryObjects that are used for deferring the already deferred register 😂
    private val registryObjects: MutableMap<IForgeRegistry<*>, MutableMap<String, RegistryObject<*>>> = HashMap()

    //The map of unregistered queues containing the initialization data for the give registry object
    private val unregisteredObjects = HashMap<IForgeRegistry<*>, Queue<Pair<String, () -> Any>>>()

    //Caches our deferred registries
    private val registries = HashMap<IForgeRegistry<*>, DeferredRegister<*>>()

    /**
     * Called upon the module being loaded.
     */
    @Suppress("UNCHECKED_CAST")
    override fun onInit() {
        for ((key, queue) in unregisteredObjects) {
            val deferredRegister =
                registries.getOrPut(key) {
                    DeferredRegister.create(
                        key,
                        ModRef.ID
                    )
                }
            val target = registryObjects.getOrPut(key) { HashMap() }
            while (queue.peek() != null) {
                val data = queue.remove()
                target[data.first] = register(deferredRegister, data.first, data.second)
                debug("Registry object with name '${data.first}' registered to forge registry '${key.registryName}'")
            }
            deferredRegister.register(MOD_BUS)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T : Any> register(
        deferredRegister: DeferredRegister<*>,
        name: String,
        value: Supplier<T>
    ): RegistryObject<T> {
        val cast = deferredRegister as DeferredRegister<T>
        return cast.register(name, value)
    }


    /**
     * Registers the given entry to the corresponding [targetRegistry], using the passed [supplier]
     */
    protected fun <T : IForgeRegistryEntry<T>> register(
        targetRegistry: IForgeRegistry<T>, name: String, supplier: () -> T
    ): ReadOnlyProperty<Any?, Opt<T>> {
        val targetQueue = unregisteredObjects.getOrPut(targetRegistry) { Queues.newArrayDeque() }
        targetQueue.add(name to supplier)
        return object : ReadOnlyProperty<Any?, Opt<T>>, Supplier<Opt<T>>, () -> Opt<T> {

            /**
             * This is the get method implementation for the [Supplier] interface.
             * It is used as interloped for the [getValue] and [invoke] calls.
             */
            @Suppress("UNCHECKED_CAST")
            override fun get(): Opt<T> {
                if (!registryObjects.containsKey(targetRegistry)) return Opt.nil()
                val target = registryObjects[targetRegistry]!!
                if (!target.containsKey(name)) return Opt.nil()
                return Opt.of(target[name]!!.get() as T)
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