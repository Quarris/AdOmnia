package dev.quarris.adomnia.content.loot

import com.google.gson.*
import dev.quarris.adomnia.*
import dev.quarris.adomnia.registry.*
import net.minecraft.resources.*
import net.minecraft.world.item.*
import net.minecraft.world.level.storage.loot.*
import net.minecraft.world.level.storage.loot.predicates.*
import net.minecraftforge.common.loot.*
import net.minecraftforge.event.RegistryEvent.*
import net.minecraftforge.eventbus.api.*
import net.minecraftforge.fml.common.*
import net.minecraftforge.registries.*
import javax.annotation.*


@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object GrassDrops {

    @SubscribeEvent
    fun registerModifiers(registryEvent: Register<GlobalLootModifierSerializer<*>?>) {
        registryEvent.registry.register(GrassDropSerializer().setRegistryName(ModRef.ID, "seed_drops"))
    }

    class GrassDropSerializer : GlobalLootModifierSerializer<GrassDropModifier>() {
        override fun read(
            location: ResourceLocation?,
            `object`: JsonObject?,
            conditions: Array<LootItemCondition>
        ): GrassDropModifier {
            return GrassDropModifier(conditions)
        }

        override fun write(instance: GrassDropModifier): JsonObject {
            return JsonObject()
        }
    }

    class GrassDropModifier(conditionsIn: Array<LootItemCondition>) :
        LootModifier(conditionsIn) {
        @Nonnull
        public override fun doApply(generatedLoot: MutableList<ItemStack>, context: LootContext): List<ItemStack> {
            val finalLootList: MutableList<ItemStack> = ArrayList()
            val randomValue = Math.random()
            if (randomValue < 0.05) finalLootList.add(ItemStack(ItemRegistry.OakAcorn.get()))
            return finalLootList
        }
    }
}