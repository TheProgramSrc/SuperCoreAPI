package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.bukkit.inventory.ItemStack;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public class ItemUtils {

    public static boolean check(ItemStack i1, ItemStack i2){
        return i1.equals(i2);
    }

    public static boolean checkName(ItemStack i1, ItemStack i2, boolean ignoreCase){
        SimpleItem s1 = new SimpleItem(i1), s2 = new SimpleItem(i2);
        if(s1.getDisplayName() == null || s2.getDisplayName() == null) return false;
        String n1 = Utils.ct(s1.getDisplayName()), n2 = Utils.ct(s2.getDisplayName());
        return ignoreCase ? n1.equalsIgnoreCase(n2) : n1.contentEquals(n2);
    }

    public static boolean checkType(ItemStack i1, ItemStack i2) {
        return i1.getType().name().contentEquals(i2.getType().name());
    }

    public static boolean checkAmount(ItemStack i1, ItemStack i2) {
        return i1.getAmount() == i2.getAmount();
    }

    public static boolean checkLore(ItemStack i1, ItemStack i2) {
        SimpleItem s1 = new SimpleItem(i1), s2 = new SimpleItem(i2);
        if(s1.getLore() == null || s2.getLore() == null) return false;
        return s1.getLore().containsAll(s2.getLore());
    }
}
