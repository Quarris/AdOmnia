package dev.quarris.adomnia.utils

import java.util.NoSuchElementException
import kotlin.contracts.*
import kotlin.contracts.InvocationKind.*


/**
 * Optional/Maybe type for Kotlin.
 *
 * Simple immutable value wrapper with one of three possible states:
 *
 * - non-null value,
 * - null value,
 * - absent/unset/undefined/unspecified/missing.
 *
 * This is useful when we need to store information whether some variable/value was specified or not and at the same
 * time this variable is nullable, so we can't use `null` as "not specified". One example is parsing of JSON if we
 * need to distinguish latter two cases:
 *
 * - `{"foo": "bar"}`
 * - `{"foo": null}`
 * - `{}`
 */
@JvmInline
value class Opt<out V> @PublishedApi internal constructor(
    @PublishedApi internal val value: Any?
) {
    companion object {
        private val nil: Opt<Nothing> = Opt(Absent)

        infix fun <V> of(value: V): Opt<V> = Opt(value)
        fun nil(): Opt<Nothing> = Opt(Absent)
        fun <V : Any> ofNilable(value: V?): Opt<V> = if (value == null) nil() else of(value)
    }

    inline val isPresent: Boolean get() = value !== Absent
    inline val isAbsent: Boolean get() = value === Absent


    fun get(): V = getOrElse { throw NoSuchElementException() }
    operator fun invoke(): V = get()

    operator fun not(): Boolean = isAbsent

    fun getOrNull(): V? = getOrElse { null }

    override fun toString(): String {
        return if (isPresent) "Opt(value=$value)"
        else "Opt(value=empty)"
    }

    @PublishedApi
    internal object Absent
}


internal fun <V> opt(that: V): Opt<V> = Opt.of(that)

internal fun <V> optNil(that: V?): Opt<V> = Opt.ofNilable(that)


internal fun <V> nil(): Opt<V> = Opt.nil()

inline operator fun <V> Opt<V>.invoke(consumer: V.() -> Unit) {
    if (!this) return
    get().consumer()
}

@OptIn(ExperimentalContracts::class)
inline infix fun <R, V> Opt<V>.map(transformer: (V) -> R): R {
    contract {
        callsInPlace(transformer, AT_MOST_ONCE)
    }
    return map(transformer) { throw NoSuchElementException() }
}

@OptIn(ExperimentalContracts::class)
inline infix fun <R, V> Opt<V>.mapNil(transformer: (V) -> R): R? {
    contract {
        callsInPlace(transformer, AT_MOST_ONCE)
    }
    return map(transformer) { null }
}

@OptIn(ExperimentalContracts::class)
inline infix fun <V> Opt<V>.getOrElse(onAbsent: () -> V): V {
    contract {
        callsInPlace(onAbsent, AT_MOST_ONCE)
    }
    return map({ return@map it }, onAbsent)
}

@OptIn(ExperimentalContracts::class)
inline fun <V, R> Opt<V>.mapValue(transform: (V) -> R): Opt<R> {
    contract {
        callsInPlace(transform, AT_MOST_ONCE)
    }
    return map({ Opt.of(transform(it)) }, { Opt.nil() })
}

@OptIn(ExperimentalContracts::class)
inline infix fun <V> Opt<V>.ifPresent(onPresent: (V) -> Unit) {
    contract {
        callsInPlace(onPresent, AT_MOST_ONCE)
    }
    map(onPresent) {}
}

@OptIn(ExperimentalContracts::class)
inline infix fun Opt<*>.ifAbsent(onAbsent: () -> Unit) {
    contract {
        callsInPlace(onAbsent, AT_MOST_ONCE)
    }
    map({}, onAbsent)
}

@OptIn(ExperimentalContracts::class)

inline fun <V, R> Opt<V>.map(onPresent: (V) -> R, onAbsent: () -> R): R {
    contract {
        callsInPlace(onPresent, AT_MOST_ONCE)
        callsInPlace(onAbsent, AT_MOST_ONCE)
    }
    return if (isPresent) onPresent(value as V) else onAbsent()
}

fun <K, V> Map<K, V>.getOptional(key: K): Opt<V> {
    val value = this[key]
    return if (value != null || containsKey(key)) {
        Opt.of(value as V)
    } else {
        Opt.nil()
    }
}
