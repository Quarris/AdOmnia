package dev.quarris.adomnia.modules.impl

import dev.quarris.adomnia.content.items.*
import dev.quarris.adomnia.extensions.*
import dev.quarris.adomnia.modules.*
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.phys.AABB
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.registries.*
import java.util.*
import net.minecraft.world.item.Items as McItems

/**
 * Auto-bonemeal growing module
 * Spamming crouch will cause bonemeal effects on nearby blocks
 */
object GrowModule : AbstractModule() {
    val OakAcorn by register(ForgeRegistries.ITEMS, "oak_acorn") {
        SaplingSeedItem(Blocks.OAK_SAPLING, Properties().tab(CreativeTab))
    }
    val BirchSeed by register(ForgeRegistries.ITEMS, "birch_seed") {
        SaplingSeedItem(Blocks.BIRCH_SAPLING, Properties().tab(CreativeTab))
    }
    val SpruceCone by register(ForgeRegistries.ITEMS, "spruce_cone") {
        SaplingSeedItem(Blocks.SPRUCE_SAPLING, Properties().tab(CreativeTab))
    }
    val JungleSeed by register(ForgeRegistries.ITEMS, "jungle_seed") {
        SaplingSeedItem(Blocks.JUNGLE_SAPLING, Properties().tab(CreativeTab))
    }
    val GrassStarter by register(ForgeRegistries.ITEMS, "grass_starter") {
        GrassStarterItem(Properties().tab(CreativeTab))
    }

    /**Private because it should only be accessed through this object, enforcing the
     * creation of item properties to be done here and not in place (the item constructor)**/
    private val CreativeTab: CreativeModeTab = object : CreativeModeTab("AdOmnia Grow") {
        override fun makeIcon(): ItemStack = ItemStack(OakAcorn())
    }

    /**
     * This is used to initialize the module. The [registerHolder] will find all the child objects
     * implementing the [RegistryHolder] class and register them. This allows us to only register
     * items when a given module is initialized.
     */
    override fun onInit() {
        super.onInit()
        registerListener(::onSprint)
    }

    // Sprint growing is removed for the time being. May be reimplemented as a config defaulted to false
    private fun onSprint(event: PlayerTickEvent) {
        if (event.player.isSprinting && rand.nextDouble() < 0.001) {
            this.applyBonemealEffectInArea(event.player.level, event.player.blockPosition(), event.player, 0.1, 1)
        }
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


    /**
     * If the random chance check is met, it gathers all blocks poses which can be affected by a bonemeal in the given radius around the player.
     * Then it chooses a random position out of the found list to bonemeal.
     * This ensures that given the random chance, exactly one of the available blocks will be affected.
     */
    private fun applyBonemealEffectInArea(level: Level, origin: BlockPos, player: Player, chance: Double, radius: Int) {
        if (rand.nextDouble() < chance) {
            val poses =
                BlockPos.betweenClosedStream(AABB(origin).inflate(radius.toDouble())).map { it.immutable() }.filter {
                    val state = level.getBlockState(it)
                    state.block is BonemealableBlock
                }.toList()

            if (poses.isNotEmpty()) {
                this.applyBonemealEffect(level, poses[rand.nextInt(poses.size)], player, chance)
            }
        }
    }

    /**
     * Applies the bonemeal affect to the given position.
     * @see [applyBonemealEffectInArea]
     */
    private fun applyBonemealEffect(level: Level, pos: BlockPos, player: Player, chance: Double): Boolean {
        if (BoneMealItem.applyBonemeal(ItemStack(McItems.BONE_MEAL), level, pos, player)) {
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