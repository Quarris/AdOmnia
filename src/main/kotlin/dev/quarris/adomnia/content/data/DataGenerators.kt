package dev.quarris.adomnia.content.data

import dev.quarris.adomnia.*
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.*
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object DataGenerators {

    @SubscribeEvent
    fun gatherData(event: GatherDataEvent) = with(event) {
        if (includeClient()) {
            generator.addProvider(ModBlockModels(generator, existingFileHelper))
            generator.addProvider(ModItemModels(generator, existingFileHelper))
            generator.addProvider(ModLangProvider(generator, "en_us"))
            generator.addProvider(ModBlockStates(generator, existingFileHelper))
        }
    }

}