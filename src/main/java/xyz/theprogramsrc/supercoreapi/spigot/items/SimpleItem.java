package xyz.theprogramsrc.supercoreapi.spigot.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.theprogramsrc.supercoreapi.global.utils.StringUtils;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotUtils;
import xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture.SkinTexture;
import xyz.theprogramsrc.supercoreapi.spigot.utils.xseries.XMaterial;

import java.lang.reflect.Field;
import java.util.*;

/**
 * SimpleItem is a representation of a ItemStack with more options in one class.
 */
public class SimpleItem {

    private final SpigotUtils utils = ((SpigotUtils)SpigotPlugin.i.getSuperUtils());
    private ItemStack item;
    private LinkedHashMap<String, String> placeholders;

    /**
     * Create a new SimpleItem from an ItemStack
     * @param itemStack the {@link ItemStack ItemStack}
     */
    public SimpleItem(ItemStack itemStack){
        this.item = itemStack;
        this.placeholders = new LinkedHashMap<>();
    }

    /**
     * Create a new SimpleItem from a {@link XMaterial XMaterial}
     * @param xMaterial the {@link XMaterial XMaterial}
     */
    public SimpleItem(XMaterial xMaterial){
        this(xMaterial.parseItem());
    }

    /**
     * Create a new SimpleItem from a {@link SkinTexture SkinTexure}
     * @param skinTexture the {@link SkinTexture SkinTexure}
     */
    public SimpleItem(SkinTexture skinTexture){
        this(XMaterial.PLAYER_HEAD);
        this.setSkin(skinTexture);
    }

    /**
     * Sets the Display Name to this SimpleItem
     * @param displayName the Display Name
     * @return this SimpleItem
     */
    public SimpleItem setDisplayName(String displayName){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null) {
            meta.setDisplayName(new StringUtils(this.utils.color(displayName)).placeholders(this.placeholders).get());
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Sets the lore to this SimpleItem
     * @param loreLines the lines to set
     * @return this SimpleItem
     */
    public SimpleItem setLore(String... loreLines){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null) {
            LinkedList<String> lore = new LinkedList<>();
            for (String line : Utils.toList(this.utils.color(loreLines))) {
                lore.add(new StringUtils(line).placeholders(this.placeholders).get());
            }
            meta.setLore(lore);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Adds new lore lines to this SimpleItem
     * @param loreLines the lines to add
     * @return this SimpleItem
     */
    public SimpleItem addLoreLines(String... loreLines){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null) {
            LinkedList<String> lore = new LinkedList<>(meta.getLore() != null ? meta.getLore() : Collections.emptyList());
            for (String line : Utils.toList(SpigotPlugin.i.getSuperUtils().color(loreLines))) {
                lore.add(new StringUtils(line).placeholders(this.placeholders).get());
            }
            meta.setLore(lore);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Changes the first lore line with a new value
     * @param loreLine the new value to replace the first lore line of this SimpleItem
     * @return this SimpleItem
     */
    public SimpleItem setFirstLoreLine(String loreLine){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null){
            LinkedList<String> newLore = new LinkedList<>();
            newLore.add(new StringUtils(this.utils.color(loreLine)).placeholders(this.placeholders).get());
            if(meta.getLore() != null) {
                for (String line : Utils.ct(meta.getLore())) {
                    newLore.add(new StringUtils(this.utils.color(line)).placeholders(this.placeholders).get());
                }
            }
            meta.setLore(newLore);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Adds a new lore line to this SimpleItem 
     * @param loreLine the line to add
     * @return this SimpleItem
     */
    public SimpleItem addLoreLine(String loreLine){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null){
            LinkedList<String> newLore = new LinkedList<>();
            if(meta.getLore() != null)
                newLore.addAll(meta.getLore());
            newLore.add(new StringUtils(this.utils.color(loreLine)).placeholders(this.placeholders).get());
            meta.setLore(newLore);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Replaces the specified index with a new lore line
     * @param index the index to replace the lore line
     * @param loreLine the lore line to set in the specified index
     * @return this SimpleItem
     */
    public SimpleItem setLoreLine(int index, String loreLine){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null){
            LinkedList<String> newLore = new LinkedList<>(meta.getLore() != null ? meta.getLore() : Collections.emptyList());
            newLore.set(index, new StringUtils(this.utils.color(loreLine)).placeholders(this.placeholders).get());
            meta.setLore(newLore);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Sets the skull owner of this SimpleItem.
     * <i>THIS ONLY WORKS IF THE ITEM IS A {@link XMaterial#PLAYER_HEAD PlayerHead}</i>
     *
     * @param name the name of the owning player
     * @return this SimpleItem
     */
    public SimpleItem setOwner(String name){
        SkullMeta meta = ((SkullMeta)this.item.getItemMeta());
        if(meta != null){
            meta.setOwner(new StringUtils(name).placeholders(this.placeholders).get());
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Sets the skull owner of this SimpleItem.
     * <i>THIS ONLY WORKS IF THE ITEM IS A {@link XMaterial#PLAYER_HEAD PlayerHead}</i>
     *
     * @param offlinePlayer the skull owner of this SimpleItem
     * @return this SimpleItem
     */
    public SimpleItem setOwningPlayer(OfflinePlayer offlinePlayer){
        SkullMeta meta = ((SkullMeta)this.item.getItemMeta());
        if(meta != null){
            meta.setOwningPlayer(offlinePlayer);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Sets the SkinTexture of this SimpleItem
     * <i>THIS ONLY WORKS IF THE ITEM IS A {@link XMaterial#PLAYER_HEAD PlayerHead}</i>
     *
     * @param skinTexture the skin to set
     * @return this SimpleItem
     */
    public SimpleItem setSkin(SkinTexture skinTexture){
        if(skinTexture == null)
            return this;
        if(skinTexture.getUrl() == null || skinTexture.getUrl().equals("null"))
            return this;
        if(!(this.item.getItemMeta() instanceof SkullMeta))
            return this;
        SkullMeta meta = ((SkullMeta)this.item.getItemMeta());
        if(meta != null){
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
            byte[] skinBytes = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", skinTexture.getUrl()).getBytes());
            gameProfile.getProperties().put("textures", new Property("textures", new String(skinBytes)));

            try {
                Field profile = meta.getClass().getDeclaredField("profile");
                profile.setAccessible(true);
                profile.set(meta, gameProfile);
            }catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Sets the SkinTexture of this SimpleItem
     * <i>THIS ONLY WORKS IF THE ITEM IS A {@link XMaterial#PLAYER_HEAD PlayerHead}</i>
     *
     * @param textureURL the url of skin to set
     * @return this SimpleItem
     */
    public SimpleItem setSkin(String textureURL){
        return setSkin(SpigotPlugin.i.getSkinManager().getSkin(textureURL));
    }

    /**
     * Adds a new Enchantment to this SimpleItem
     * @param enchantment the enchantment to add
     * @param level the level of the enchantment
     * @return this SimpleItem
     */
    public SimpleItem addEnchantment(Enchantment enchantment, int level){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null){
            meta.addEnchant(enchantment, level, true);
            this.item.setItemMeta(meta);
        }
        return this;
    }


    /**
     * Adds a new Enchantment to this SimpleItem
     * <i>The level added will be 1</i>
     *
     * @param enchantment the enchantment to add
     * @return this SimpleItem
     */
    public SimpleItem addEnchantment(Enchantment enchantment){
        return this.addEnchantment(enchantment, 1);
    }

    /**
     * Removes an Enchantment from this SimpleItem
     * @param enchantment the enchantment to remove
     * @return this SimpleItem
     */
    public SimpleItem removeEnchantment(Enchantment enchantment){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null){
            meta.removeEnchant(enchantment);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Adds new flags to this SimpleItem
     * @param itemFlags the {@link ItemFlag flags} to add
     * @return this SimpleItem
     */
    public SimpleItem addFlag(ItemFlag... itemFlags){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null){
            meta.addItemFlags(itemFlags);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Removes the specified flags from this SimpleItem
     * @param itemFlags the {@link ItemFlag flags} to remove
     * @return this SimpleItem
     */
    public SimpleItem removeFlag(ItemFlag... itemFlags){
        ItemMeta meta = this.item.getItemMeta();
        if(meta != null){
            meta.removeItemFlags(itemFlags);
            this.item.setItemMeta(meta);
        }
        return this;
    }

    /**
     * Sets if the enchantments of this SimpleItem should be shown
     * @param show if it's <b>true</b>, the enchantments will be shown, otherwise they will be hidden
     * @return this SimpleItem
     */
    public SimpleItem setShowEnchantments(boolean show){
        ItemFlag flag = ItemFlag.HIDE_ENCHANTS;
        if(show) {
            this.removeFlag(flag);
        } else {
            this.addFlag(flag);
        }
        return this;
    }

    /**
     * Sets if this SimpleItem should be glowing
     * @param glowing if it's <b>true</b>, the item will be glowing, otherwise it won't be glowing
     * @return this SimpleItem
     * @see #setShowEnchantments(boolean)
     * @see #addEnchantment(Enchantment)
     */
    public SimpleItem setGlowing(boolean glowing){
        Enchantment enchantment = Enchantment.DURABILITY;
        if(glowing){
            this.addEnchantment(enchantment);
            this.setShowEnchantments(false);
        }else{
            this.removeEnchantment(enchantment);
            this.setShowEnchantments(true);
        }
        return this;
    }

    /**
     * Sets if this SimpleItem should be shown the attributes
     * @param showAttributes true to show the attributes, false to hide them
     * @return this SimpleItem
     */
    public SimpleItem setShowAttributes(boolean showAttributes){
        ItemFlag flag = ItemFlag.HIDE_ATTRIBUTES;
        if(showAttributes){
            this.removeFlag(flag);
        }else{
            this.addFlag(flag);
        }
        return this;
    }

    /**
     * Sets the amount of this SimpleItem
     * @param amount the amount to set
     * @return this SimpleItem
     */
    public SimpleItem setAmount(int amount){
        this.item.setAmount(amount);
        return this;
    }

    /**
     * Sets the material of this SimpleItem
     * @param material the material to set
     * @return this SimpleItem
     */
    public SimpleItem setMaterial(XMaterial material){
        this.item = material.parseItem();
        return this;
    }

    /**
     * Gets the display name of this SimpleItem
     * @return the display name
     */
    public String getDisplayName(){
        ItemMeta meta = this.item.getItemMeta();
        if(meta == null)
            return "null";
        return Utils.ct(meta.getDisplayName());
    }

    /**
     * Gets the lore of this SimpleItem
     * @return the lore
     */
    public LinkedList<String> getLore(){
        ItemMeta meta = this.item.getItemMeta();
        if(meta == null)
            return new LinkedList<>();
        return meta.getLore() != null ? new LinkedList<>(this.utils.color(meta.getLore())) : new LinkedList<>();
    }

    /**
     * Adds a placeholder to this SimpleItem
     * @param placeholder the placeholder
     * @param replacement the value/replacement of the placeholder
     * @return this SimpleItem
     */
    public SimpleItem addPlaceholder(String placeholder, String replacement){
        this.placeholders.put(placeholder, replacement);
        return this;
    }


    /**
     * Removes a placeholder from this SimpleItem
     * @param placeholder the placeholder
     * @return this SimpleItem
     */
    public SimpleItem removePlaceholder(String placeholder){
        this.placeholders.remove(placeholder);
        return this;
    }

    /**
     * Sets the placeholders to this SimpleItem
     * @param placeholders the placeholders to set
     * @param replace true to replace the old placeholders, false to keep them and add the new ones.
     * @return this SimpleItem
     */
    public SimpleItem setPlaceholders(Map<String, String> placeholders, boolean replace){
        if(replace){
            this.placeholders = new LinkedHashMap<>(placeholders);
        }else{
            this.placeholders.putAll(placeholders);
        }
        return this;
    }

    /**
     * Adds the placeholders to the SimpleItem
     * @param placeholders the placeholders to add
     * @return this SimpleItem
     */
    public SimpleItem setPlaceholders(Map<String, String> placeholders){
        return this.setPlaceholders(placeholders, false);
    }

    /**
     * Build this SimpleItem into a ItemStack
     * @return the {@link ItemStack ItemStack} of this SimpleItem
     */
    public ItemStack build(){
        String name = this.getDisplayName();
        LinkedList<String> lore = this.getLore();
        this.setDisplayName(new StringUtils(name).placeholders(this.placeholders).get());
        this.setLore(lore.stream().map(s-> new StringUtils(s).placeholders(this.placeholders).get()).toArray(String[]::new));
        return new ItemStack(this.item);
    }

    /**
     * Duplicate this SimpleItem
     * @return this SimpleItem duplicated
     */
    public SimpleItem duplicate(){
        return new SimpleItem(this.item).setPlaceholders(this.placeholders);
    }

    /**
     * Gets if the Display Name of this SimpleItem is Null
     * @return true if it's null, otherwise, false
     */
    public boolean hasDisplayName(){
        return this.getDisplayName() != null;
    }

    /**
     * Gets if the Lore of this SimpleItem is Null
     * @return true if it's null, otherwise, false
     */
    public boolean hasLore(){
        return this.getLore() != null;
    }
}
