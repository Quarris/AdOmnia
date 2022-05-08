package dev.quarris.adomnia

import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object ModRef {
    const val ID = "adomnia"
    val LOGGER: Logger = LogManager.getLogger(ID)
    
    fun res(name: String): ResourceLocation = ResourceLocation(ID, name)
}
