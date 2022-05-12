@file:Suppress("DuplicatedCode")

package dev.quarris.adomnia.modules

import com.google.common.collect.*
import dev.quarris.adomnia.*
import dev.quarris.adomnia.extensions.*
import dev.quarris.adomnia.utils.*
import net.minecraft.core.*
import net.minecraft.resources.*
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.*
import net.minecraft.world.level.block.state.*
import net.minecraftforge.registries.*
import thedarkcolour.kotlinforforge.forge.*
import java.util.*
import java.util.function.*
import kotlin.properties.*
import kotlin.reflect.*

abstract class AbstractModule : IModule {

    //The actual registryObjects that are used for deferring the already deferred register 😂
    protected val registryObjects: MutableMap<IForgeRegistry<*>, MutableMap<String, RegistryObject<*>>> = HashMap()

    //The actual registryObjects that are used for deferring the already deferred register 😂
    protected val vanillaRegistryObjects: MutableMap<ResourceKey<Registry<*>>, MutableMap<String, RegistryObject<*>>> =
        HashMap()

    //The map of unregistered queues containing the initialization data for the give registry object
    protected val unregisteredObjects = HashMap<IForgeRegistry<*>, Queue<Pair<String, () -> Any>>>()

    //The map of unregistered queues containing the initialization data for the give registry object
    protected val unregisteredVanillaObjects =
        HashMap<Pair<KClass<*>, ResourceKey<Registry<*>>>, Queue<Pair<String, () -> Any>>>()

    //Caches our deferred registries
    protected val registries = HashMap<IForgeRegistry<*>, DeferredRegister<*>>()

    //Caches our deferred registries
    protected val vanillaRegistries = HashMap<ResourceKey<Registry<*>>, DeferredRegister<*>>()


    /**
     * Called upon the module being loaded.
     */
    @Suppress("UNCHECKED_CAST")
    override fun onInit() {
        for ((key, queue) in sortRegistry()) {
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
                target[data.first] = castedRegister(deferredRegister, data.first, data.second)
                debug("Registry object with name '${data.first}' registered to forge registry '${key.registryName}'")
            }
            deferredRegister.register(MOD_BUS)
        }
        for ((key, queue) in unregisteredVanillaObjects) {
            val deferredRegister = deferredRegister(key.first, key.second)
            val target = vanillaRegistryObjects.getOrPut(key.second) { HashMap() }

            while (queue.peek() != null) {
                val data = queue.remove()
                target[data.first] = castedRegister(deferredRegister, data.first, data.second)
                debug("Vanilla registry object with name '${data.first}' registered to forge registry '${key.second.registryName}'")
            }
            deferredRegister.register(MOD_BUS)
        }
    }

    /**
     * Sorts the registry so that the blocks are registered before the items, and the items are regstiered
     * before the block entities, and then everything else
     */
    private fun sortRegistry(): SortedMap<IForgeRegistry<*>, Queue<Pair<String, () -> Any>>> {
        val comparator = compareBy<IForgeRegistry<*>> {
            when (it) {
                ForgeRegistries.BLOCKS -> 1
                ForgeRegistries.ITEMS -> 2
                ForgeRegistries.BLOCK_ENTITIES -> 3
                else -> 0
            }
        }
        return unregisteredObjects.toSortedMap(comparator)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T : Any> deferredRegister(type: KClass<T>, key: ResourceKey<Registry<*>>): DeferredRegister<*> {
        if (vanillaRegistries.containsKey(key as ResourceKey<Registry<*>>))
            return vanillaRegistries[key]!! as DeferredRegister<T>
        val registry: DeferredRegister<T> = DeferredRegister.create(
            key.location(),
            ModRef.ID
        )
        vanillaRegistries[key] = registry
        return registry
    }


    @Suppress("UNCHECKED_CAST")
    protected inline fun <reified T : Any> castedRegister(
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
    @Suppress("UNCHECKED_CAST")
    protected inline fun <reified T : Any> register(
        targetRegistry: ResourceKey<Registry<T>>,
        name: String,
        noinline supplier: () -> T
    ): ReadOnlyProperty<Any?, Opt<T>> = register(T::class, targetRegistry, name, supplier)

    /**
     * Registers the given entry to the corresponding [targetRegistry], using the passed [supplier]
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <T : Any> register(
        type: KClass<T>,
        targetRegistry: ResourceKey<Registry<T>>,
        name: String,
        supplier: () -> T
    ): ReadOnlyProperty<Any?, Opt<T>> {
        val genericRegistry = targetRegistry as ResourceKey<Registry<*>>
        val targetQueue = unregisteredVanillaObjects.getOrPut(type to genericRegistry) { Queues.newArrayDeque() }
        targetQueue.add(name to supplier)
        return object : ReadOnlyProperty<Any?, Opt<T>>, Supplier<Opt<T>>, () -> Opt<T> {

            /**
             * This is the get method implementation for the [Supplier] interface.
             * It is used as interloped for the [getValue] and [invoke] calls.
             */
            @Suppress("UNCHECKED_CAST")
            override fun get(): Opt<T> {
                if (!vanillaRegistryObjects.containsKey(genericRegistry)) return Opt.nil()
                val target = vanillaRegistryObjects[genericRegistry]!!
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

    /**
     * Registers the given entry to the corresponding [targetRegistry], using the passed [supplier]
     */
    protected fun <B : IForgeRegistryEntry<B>, T : B> register(
        targetRegistry: IForgeRegistry<B>, name: String, supplier: () -> T
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
                val value = target[name]!!
                if (!value.isPresent) return Opt.nil()
                return Opt.of(value.get() as T)
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

    /**
     * Allows for easily creating tile entity builders
     */
    protected inline fun <reified T : BlockEntity> tile(
        vararg block: Block, crossinline supplier: (Pair<BlockPos, BlockState>) -> T,
    ): BlockEntityType<T> = BlockEntityType.Builder.of({ pos, state -> supplier(pos to state) }, *block).build(null)


}