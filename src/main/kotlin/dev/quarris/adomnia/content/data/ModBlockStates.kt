package dev.quarris.adomnia.content.data

import dev.quarris.adomnia.*
import dev.quarris.adomnia.content.blocks.*
import dev.quarris.adomnia.content.blocks.VariantState.*
import dev.quarris.adomnia.registry.*
import net.minecraft.data.DataGenerator
import net.minecraftforge.client.model.generators.*
import net.minecraftforge.common.data.ExistingFileHelper

/**
 * Handles all the data generation for the block state
 */
class ModBlockStates(generator: DataGenerator, private val existingFileHelper: ExistingFileHelper) :
    BlockStateProvider(generator, ModRef.ID, existingFileHelper) {

    /**
     * Create our block states here
     */
    @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
    override fun registerStatesAndModels() {
//        simpleBlock(
//            BlockRegistry.Composter.get(),
//            ModelFile.ExistingModelFile(modLoc("block/composter"), existingFileHelper)
//        )

        getVariantBuilder(BlockRegistry.Composter.get()).forAllStates {
            when (it.getValue(ComposterBlock.ComposterVariant)) {
                Oak -> arrayOf(
                    ConfiguredModel(
                        ModelFile.ExistingModelFile(
                            modLoc("block/oak_composter"),
                            existingFileHelper
                        )
                    )
                )
                Spruce -> arrayOf(
                    ConfiguredModel(
                        ModelFile.ExistingModelFile(
                            modLoc("block/spruce_composter"),
                            existingFileHelper
                        )
                    )
                )
                Birch -> arrayOf(
                    ConfiguredModel(
                        ModelFile.ExistingModelFile(
                            modLoc("block/birch_composter"),
                            existingFileHelper
                        )
                    )
                )
                Jungle -> arrayOf(
                    ConfiguredModel(
                        ModelFile.ExistingModelFile(
                            modLoc("block/jungle_composter"),
                            existingFileHelper
                        )
                    )
                )
            }
        }
    }
}