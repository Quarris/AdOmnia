package dev.quarris.adomnia.content.blocks

import net.minecraft.util.*

enum class VariantState : StringRepresentable {
    Oak,
    Spruce,
    Birch,
    Jungle;

    override fun getSerializedName(): String = name.lowercase()
}