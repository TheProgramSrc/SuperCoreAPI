package xyz.theprogramsrc.supercoreapi.spigot.recipes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;
import xyz.theprogramsrc.supercoreapi.spigot.utils.ItemUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeCreator extends SpigotModule {

    private final HashMap<String, CustomRecipe> recipes;

    /**
     * Create a new custom recipe!
     *
     * @param plugin The main plugin
     */
    public RecipeCreator(SpigotPlugin plugin){
        super(plugin);
        this.recipes = new HashMap<>();
    }

    public void addRecipe(String id, CustomRecipe recipe){
        if(this.recipes.containsKey(id)){
            throw new IllegalArgumentException("The identifier '" + id + "' is already in use!");
        }else{
            this.recipes.put(id, recipe);
        }
    }

    public void removeRecipe(String id){
        this.recipes.remove(id);
    }

    public HashMap<String, CustomRecipe> getRecipes() {
        return recipes;
    }

    @EventHandler
    public void onCraftPrepare(PrepareItemCraftEvent event){
        if(!event.isRepair()){
            CraftingInventory inv = event.getInventory();
            HashMap<Integer, ItemStack> items = new HashMap<>();
            List<ItemStack> contents = Arrays.stream(inv.getMatrix()).filter(Utils::nonNull).collect(Collectors.toList());

            for(int i = 1; i <= 9; ++i){
                items.put(i, inv.getItem(i));
            }

            for (CustomRecipe recipe : this.recipes.values()) {
                List<ItemStack> unordered = Arrays.stream(recipe.getRecipeItems()).filter(i-> i.getSlot() == -1).filter(i-> i.getItem() != null).map(RecipeItem::getItem).collect(Collectors.toList());
                if(!unordered.isEmpty()){
                    if(contents.containsAll(unordered)){
                        inv.setResult(recipe.getResult());
                    }
                }else{
                    HashMap<Integer, ItemStack> recipes = new HashMap<>();
                    Arrays.stream(recipe.getRecipeItems()).filter(i-> i.getSlot() != -1).forEach(r-> recipes.put(r.getSlot(), r.getItem()));
                    int matches = 0;
                    for(int i = 1; i <= 9; ++i){
                        ItemStack recipeItem = recipes.get(i);
                        ItemStack inCraft = items.get(i);
                        if(inCraft != null && recipeItem != null){
                            if(check(inCraft, recipeItem)){
                                matches++;
                            }
                        }
                    }

                    if(matches == recipes.size()){
                        inv.setResult(recipe.getResult());
                    }
                }
            }
        }
    }

    private boolean check(ItemStack i1, ItemStack i2){
        SimpleItem s1 = new SimpleItem(i1), s2 = new SimpleItem(i2);
        boolean base = ItemUtils.check(i1, i2), lore = ItemUtils.checkLore(i1, i2), name = ItemUtils.checkName(i1, i2, false), type = ItemUtils.checkType(i1, i2);
        if(!s1.hasLore() || !s2.hasLore()){
            if(!s1.hasDisplayName() || !s2.hasDisplayName()){
                return base && type;
            }else{
                return base && type && name;
            }
        }else{
            if(!s1.hasDisplayName() || !s2.hasDisplayName()){
                return base && type && lore;
            }else{
                return base && type && lore && name;
            }
        }
    }
}
