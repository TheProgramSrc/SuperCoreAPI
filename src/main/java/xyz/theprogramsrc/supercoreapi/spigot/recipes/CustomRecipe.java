package xyz.theprogramsrc.supercoreapi.spigot.recipes;

import org.bukkit.inventory.ItemStack;

public class CustomRecipe {

    private final ItemStack result;
    private final RecipeItem[] recipeItems;

    public CustomRecipe(ItemStack result, RecipeItem[] recipeItems) {
        this.result = result;
        this.recipeItems = recipeItems;
    }

    public ItemStack getResult() {
        return result;
    }

    public RecipeItem[] getRecipeItems() {
        return recipeItems;
    }
}
