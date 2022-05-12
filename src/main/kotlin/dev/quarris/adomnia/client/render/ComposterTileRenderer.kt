package dev.quarris.adomnia.client.render

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat
import dev.quarris.adomnia.ModRef
import dev.quarris.adomnia.content.tiles.ComposterTile
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderType.CompositeState
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context
import net.minecraft.resources.ResourceLocation

class ComposterTileRenderer(
    ctx: Context
) : BlockEntityRenderer<ComposterTile> {

    private val mulchTexture: ResourceLocation = ModRef.res("textures/misc/mulch.png")
    private val mulchRenderType: RenderType = RenderType.create("mulch", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, false, CompositeState.builder().setLightmapState(RenderStateHelper.lightmap(true)).setShaderState(RenderStateHelper.solidShaderRenderType()).setTextureState(TextureStateShard(mulchTexture, false, false)).createCompositeState(true))

    override fun render(
        tile: ComposterTile,
        pPartialTick: Float,
        matrix: PoseStack,
        pBufferSource: MultiBufferSource,
        light: Int,
        pPackedOverlay: Int
    ) {
        if (tile.mulch > 0) {
            val buffer = pBufferSource.getBuffer(mulchRenderType)
            val mulchPerc = tile.mulch / tile.MaxMulch.toFloat()
            vertex(buffer, matrix, 2f, 3/16f + mulchPerc * 10/16f, 2f, 1f, 1f, 1f, 2f, 2f, light)
            vertex(buffer, matrix, 2f, 3/16f + mulchPerc * 10/16f, 14f, 1f, 1f, 1f, 2f, 14f, light)
            vertex(buffer, matrix, 14f, 3/16f + mulchPerc * 10/16f, 14f, 1f, 1f, 1f, 14f, 14f, light)
            vertex(buffer, matrix, 14f, 3/16f + mulchPerc * 10/16f, 02f, 1f, 1f, 1f, 14f, 2f, light)
        }
    }

    private fun vertex(buffer: VertexConsumer, matrix: PoseStack, x: Float, y: Float, z: Float, r: Float, g: Float, b: Float, u: Float, v: Float, light: Int) {
        val pose = matrix.last().pose()
        val normal = matrix.last().normal()
        buffer.vertex(pose, x / 16f, y, z / 16f).color(r, g, b, 1f).uv(u / 16f, v / 16f).uv2(light)
            .normal(normal, 0f, 1f, 0f).endVertex()
    }
}