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

/**
 * Auto-bonemeal growing module
 * Spamming crouch will cause bonemeal effects on nearby blocks
 */
object GrowModule : IModule {

    override fun onInit() {
    }

    /**
     * Random used in calculating chance to apply effect
     */
    private val rand = Random()

    /**
     * Called from the EntityMixin class when the player uses the Sneak Key
     * @see [dev.quarris.adomnia.mixins.EntityMixin.crouchHandler]
     */
    fun onCrouch(level: Level, pos: BlockPos, player: Player) {
        this.applyBonemealEffectInArea(level, pos, player, 0.1, 1)
    }

    // Sprint growing is removed for the time being. May be reimplemented as a config defaulted to false
    //@SubscribeEvent
    fun onSprint(event: PlayerTickEvent) {
        if (event.player.isSprinting && rand.nextDouble() < 0.001) {
            this.applyBonemealEffectInArea(event.player.level, event.player.blockPosition(), event.player, 0.1, 1)
        }
    }

    /**
     * If the random chance check is met, it gathers all blocks poses which can be affected by a bonemeal in the given radius around the player.
     * Then it chooses a random position out of the found list to bonemeal.
     * This ensures that given the random chance, exactly one of the available blocks will be affected.
     */
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

    /**
     * Applies the bonemeal affect to the given position.
     * @see [applyBonemealEffectInArea]
     */
    private fun applyBonemealEffect(level: Level, pos: BlockPos, player: Player, chance: Double): Boolean {
        if (BoneMealItem.applyBonemeal(ItemStack(Items.BONE_MEAL), level, pos, player)) {
            level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 0)
            return true
        }

        return false
    }

    /**
     * ID for this module.
     */
    override val id: String = "grow"

}