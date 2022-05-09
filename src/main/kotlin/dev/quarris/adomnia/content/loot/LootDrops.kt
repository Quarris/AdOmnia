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
import javax.annotation.*


@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object LootDrops {
    /**
     * Register our modifier serializers
     */
    @SubscribeEvent
    fun registerModifiers(registryEvent: Register<GlobalLootModifierSerializer<*>?>) {
        registryEvent.registry.register(GrassDropSerializer().setRegistryName(ModRef.ID, "seed_drops"))
    }

    /**
     * Creates/reads our loot table modifier, bound to the json file (global_seed_modifier.json)
     */
    class GrassDropSerializer : GlobalLootModifierSerializer<GrassDropModifier>() {
        override fun read(
            location: ResourceLocation?,
            `object`: JsonObject?,
            conditions: Array<LootItemCondition>
        ): GrassDropModifier = GrassDropModifier(conditions)

        override fun write(instance: GrassDropModifier): JsonObject = JsonObject()
    }

    /**
     * This is applied to allow for our acorn to have a chance of dropping with the minecraft vanialla
     * grass drops
     */
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