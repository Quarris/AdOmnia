package dev.quarris.adomnia.extensions

import dev.quarris.adomnia.*

/**
 * Logs out an info message using the modref logger.
 */
internal fun info(message: String) = ModRef.LOGGER.info(message)

/**
 * Logs out an debug message using the modref logger.
 */
internal fun debug(message: String) = ModRef.LOGGER.debug(message)


/**
 * Logs out an warn message using the modref logger.
 */
internal fun warn(message: String) = ModRef.LOGGER.warn(message)


/**
 * Logs out an trace message using the modref logger.
 */
internal fun trace(message: String) = ModRef.LOGGER.trace(message)
