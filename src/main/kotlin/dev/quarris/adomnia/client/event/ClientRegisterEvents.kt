package dev.quarris.adomnia.client.event

import dev.quarris.adomnia.ModRef
import dev.quarris.adomnia.client.render.ComposterTileRenderer
import dev.quarris.adomnia.registry.TileRegistry
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(value = [Dist.CLIENT], modid = ModRef.ID, bus = EventBusSubscriber.Bus.MOD)
object ClientRegisterEvents {

    @SubscribeEvent
    fun registerTileRenderers(event: RegisterRenderers) {
        event.registerBlockEntityRenderer(TileRegistry.Composter.get()) { ctx -> ComposterTileRenderer(ctx) }
    }

}