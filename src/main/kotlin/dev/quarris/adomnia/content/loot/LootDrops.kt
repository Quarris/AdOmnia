package dev.quarris.adomnia.content.loot

import com.google.gson.JsonObject
import dev.quarris.adomnia.ModRef
import dev.quarris.adomnia.registry.ItemRegistry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraftforge.common.loot.GlobalLootModifierSerializer
import net.minecraftforge.common.loot.LootModifier
import net.minecraftforge.event.RegistryEvent.Register
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import javax.annotation.Nonnull


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
     * This is applied to allow for our acorn to have a chance of dropping with the minecraft vanilla
     * grass drops
     */
    class GrassDropModifier(conditionsIn: Array<LootItemCondition>) :
        LootModifier(conditionsIn) {
        @Nonnull
        public override fun doApply(generatedLoot: MutableList<ItemStack>, context: LootContext): List<ItemStack> {
            if (Math.random() < 0.05) {
                generatedLoot.add(ItemStack(ItemRegistry.OakAcorn.get()))
            }
            if (Math.random() < 0.05) {
                generatedLoot.add(ItemStack(ItemRegistry.BirchSeed.get()))
            }
            if (Math.random() < 0.05) {
                generatedLoot.add(ItemStack(ItemRegistry.SpruceCone.get()))
            }
            if (Math.random() < 0.05) {
                generatedLoot.add(ItemStack(ItemRegistry.JungleSeed.get()))
            }
            return generatedLoot
        }
    }
}