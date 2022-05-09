package dev.quarris.adomnia.modules.impl

import dev.quarris.adomnia.ModRef
import dev.quarris.adomnia.modules.IModule
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BoneMealItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.LevelEvent
import net.minecraft.world.phys.AABB
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import java.util.*

@Mod.EventBusSubscriber(modid = ModRef.ID)
object GrowModule : IModule {

    private val rand = Random()

    fun onCrouch(level: Level, pos: BlockPos, player: Player) {
        this.applyBonemealEffectInArea(level, pos, player, 0.1, 1)
    }

    @SubscribeEvent
    fun onSprint(event: PlayerTickEvent) {
        if (event.player.isSprinting && rand.nextDouble() < 0.001) {
            this.applyBonemealEffectInArea(event.player.level, event.player.blockPosition(), event.player, 0.1, 1)
        }
    }

    private fun applyBonemealEffectInArea(level: Level, origin: BlockPos, player: Player, chance: Double, radius: Int) {
        if (rand.nextDouble() < chance) {
            val poses = BlockPos.betweenClosedStream(AABB(origin).inflate(radius.toDouble())).map { it.immutable() }.filter {
                val state = level.getBlockState(it)
                state.block is BonemealableBlock
            }.toList()

            if (!poses.isEmpty()) {
                this.applyBonemealEffect(level, poses[rand.nextInt(poses.size)], player, chance)
            }
        }
    }

    private fun applyBonemealEffect(level: Level, pos: BlockPos, player: Player, chance: Double): Boolean {
        if (BoneMealItem.applyBonemeal(ItemStack(Items.BONE_MEAL), level, pos, player)) {
            level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0)
            return true
        }

        return false
    }

    private const val id: String = "grow"
    override val name: String
        get() = this.id

}