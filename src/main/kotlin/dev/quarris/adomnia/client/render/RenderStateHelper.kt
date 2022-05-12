package dev.quarris.adomnia.client.render

import net.minecraft.client.renderer.RenderStateShard

object RenderStateHelper : RenderStateShard("", {}, {}) {

    fun lightmap(on: Boolean): LightmapStateShard = if (on) LIGHTMAP else NO_LIGHTMAP
    fun solidShaderRenderType(): ShaderStateShard = RENDERTYPE_SOLID_SHADER


}