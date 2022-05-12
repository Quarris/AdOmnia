package dev.quarris.adomnia.content.recipes

import net.minecraft.core.*
import net.minecraft.resources.*
import net.minecraft.world.Container
import net.minecraft.world.item.*
import net.minecraft.world.item.crafting.*
import net.minecraft.world.level.*

class ComposterRecipe(
    private val id: ResourceLocation,
    private val reactant: Ingredient,
    /**the output of the composter**/
    val result: ItemStack
) : Recipe<Container> {

    override fun getId(): ResourceLocation = id

    override fun getIngredients(): NonNullList<Ingredient> = NonNullList.of(reactant)

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    override fun matches(container: Container, pLevel: Level): Boolean {
        if (container.containerSize != 1) return false
        return reactant.test(container.getItem(0))
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    override fun assemble(pContainer: Container): ItemStack = result

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    override fun canCraftInDimensions(pWidth: Int, pHeight: Int): Boolean = true

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    override fun getResultItem(): ItemStack = result

    override fun getSerializer(): RecipeSerializer<*> {
        TODO("Not yet implemented")
    }

    override fun getType(): RecipeType<*> {
        TODO("Not yet implemented")
    }
}