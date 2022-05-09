package dev.quarris.adomnia.level

import com.mojang.datafixers.util.Function5
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.*
import net.minecraft.resources.RegistryOps
import net.minecraft.server.level.WorldGenRegion
import net.minecraft.world.level.LevelHeightAccessor
import net.minecraft.world.level.NoiseColumn
import net.minecraft.world.level.StructureFeatureManager
import net.minecraft.world.level.biome.BiomeManager
import net.minecraft.world.level.biome.BiomeSource
import net.minecraft.world.level.biome.Climate.Sampler
import net.minecraft.world.level.biome.MultiNoiseBiomeSource
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.chunk.ChunkAccess
import net.minecraft.world.level.chunk.ChunkGenerator
import net.minecraft.world.level.levelgen.GenerationStep
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings
import net.minecraft.world.level.levelgen.NoiseRouter
import net.minecraft.world.level.levelgen.blending.Blender
import net.minecraft.world.level.levelgen.structure.StructureSet
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

/**
 * Level Type Chunk Generator implementation for a single block skyblock experience
 */
class SingleSkyblockSource(
    pStructureSets: Registry<StructureSet>,
    pStructureOverrides: Optional<HolderSet<StructureSet>>,
    pBiomeSource: BiomeSource,
) : ChunkGenerator(pStructureSets, pStructureOverrides, pBiomeSource) {

    private var seed: Long = 0
    private lateinit var noises: Registry<NoiseParameters>
    private lateinit var settings: Holder<NoiseGeneratorSettings>
    private lateinit var router: NoiseRouter
    private lateinit var sampler: Sampler

    constructor(registries: RegistryAccess, seed: Long) : this(
        registries.registryOrThrow(Registry.STRUCTURE_SET_REGISTRY),
        MultiNoiseBiomeSource.Preset.OVERWORLD.biomeSource(registries.registryOrThrow(Registry.BIOME_REGISTRY), false),
        seed,
        registries.registryOrThrow(Registry.NOISE_REGISTRY),
        registries.registryOrThrow<NoiseGeneratorSettings>(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY)
            .getOrCreateHolder(NoiseGeneratorSettings.OVERWORLD)
    )

    constructor(
        structures: Registry<StructureSet>,
        biomes: BiomeSource,
        seed: Long,
        noises: Registry<NoiseParameters>,
        settings: Holder<NoiseGeneratorSettings>
    ) : this(structures, Optional.of(HolderSet.direct(arrayListOf())), biomes) {
        this.seed = seed;
        this.noises = noises
        this.settings = settings
        this.router = settings.value().createNoiseRouter(noises, seed)
        this.sampler = Sampler(
            router.temperature(),
            router.humidity(),
            router.continents(),
            router.erosion(),
            router.depth(),
            router.ridges(),
            router.spawnTarget()
        )
    }

    override fun codec(): Codec<out ChunkGenerator> {
        return CODEC
    }

    override fun withSeed(seed: Long): ChunkGenerator {
        return SingleSkyblockSource(
            this.structureSets,
            this.biomeSource.withSeed(seed),
            seed,
            this.noises,
            this.settings
        )
    }

    override fun climateSampler(): Sampler {
        return this.sampler
    }

    override fun applyCarvers(
        pLevel: WorldGenRegion,
        pSeed: Long,
        pBiomeManager: BiomeManager,
        pStructureFeatureManager: StructureFeatureManager,
        pChunk: ChunkAccess,
        pStep: GenerationStep.Carving
    ) = Unit

    override fun buildSurface(
        level: WorldGenRegion,
        structureFeaturesManager: StructureFeatureManager,
        chunk: ChunkAccess
    ) {
        if (chunk.pos.x == 0 && chunk.pos.z == 0) {
            level.setBlock(chunk.pos.getMiddleBlockPosition(64), Blocks.GRASS_BLOCK.defaultBlockState(), 3);
        }
    }

    override fun spawnOriginalMobs(pLevel: WorldGenRegion) = Unit

    override fun getGenDepth(): Int {
        return this.settings.value().noiseSettings().height();
    }

    override fun fillFromNoise(
        p_187748_: Executor,
        p_187749_: Blender,
        p_187750_: StructureFeatureManager,
        chunk: ChunkAccess
    ): CompletableFuture<ChunkAccess> = CompletableFuture.completedFuture(chunk)

    override fun getSeaLevel(): Int = this.settings.value().seaLevel


    override fun getMinY(): Int = this.settings.value().noiseSettings.minY


    override fun getBaseHeight(pX: Int, pZ: Int, pType: Heightmap.Types, pLevel: LevelHeightAccessor): Int = minY

    override fun getBaseColumn(pX: Int, pZ: Int, pLevel: LevelHeightAccessor): NoiseColumn = NoiseColumn(this.minY, arrayOf(Blocks.AIR.defaultBlockState()))


    override fun addDebugScreenInfo(p_208054_: MutableList<String>, p_208055_: BlockPos) = Unit

    companion object {
        val CODEC: Codec<SingleSkyblockSource> =
            RecordCodecBuilder.create { inst ->
                commonCodec(inst).and(inst.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter {
                        it.getBiomeSource()
                    }, Codec.LONG.fieldOf("seed").stable().forGetter {
                        it.seed
                    }, RegistryOps.retrieveRegistry(Registry.NOISE_REGISTRY).forGetter {
                        it.noises
                    }, NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter {
                        it.settings
                    }
                )).apply(
                    inst,
                    inst.stable(Function5 { structures: Registry<StructureSet>, biomes: BiomeSource, seed: Long, noises: Registry<NoiseParameters>, settings: Holder<NoiseGeneratorSettings> ->
                        SingleSkyblockSource(
                            structures,
                            biomes,
                            seed,
                            noises,
                            settings
                        )
                    })
                )
            }
    }

}