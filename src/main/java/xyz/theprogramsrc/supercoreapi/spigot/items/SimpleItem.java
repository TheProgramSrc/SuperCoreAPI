package xyz.theprogramsrc.supercoreapi.spigot.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture.SkinTexture;
import xyz.theprogramsrc.supercoreapi.spigot.utils.xseries.XMaterial;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleItem {

    private XMaterial material;
    private int amount = 1;
    private List<String> lore;
    private String displayName;
    private SkinTexture skinTexture;
    private HashMap<Enchantment, Integer> enchantments;
    private List<ItemFlag> flags;
    private final HashMap<String, String> placeholders;

    /**
     * Create a new Simple Item from ItemStack
     * @param itemStack the item
     */
    public SimpleItem(ItemStack itemStack){
        this.lore = new ArrayList<>();
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
        this.material = XMaterial.fromItem(itemStack);
        this.amount = itemStack.getAmount();
        if(itemStack.getItemMeta() != null){
            if(itemStack.hasItemMeta()){
                ItemMeta meta = itemStack.getItemMeta();
                if(meta instanceof SkullMeta){
                    SkullMeta skullMeta = ((SkullMeta)meta);
                    this.skinTexture = SkinTexture.fromMojang(skullMeta.getOwner());
                }
                this.lore = meta.getLore();
                this.displayName = meta.getDisplayName();
                this.flags = new ArrayList<>(meta.getItemFlags());
            }
        }
        this.enchantments = new HashMap<>(itemStack.getEnchantments());
        this.placeholders = new HashMap<>();
    }

    /**
     * Create a new SimpleItem from a SkinTexture
     * @param skinTexture the skin
     */
    public SimpleItem(SkinTexture skinTexture){
        this.material = XMaterial.PLAYER_HEAD;
        this.skinTexture = skinTexture;
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.placeholders = new HashMap<>();
    }

    /**
     * Create a new SimpleItem from a Material
     * @param material the material
     */
    public SimpleItem(XMaterial material) {
        this.material = material;
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.placeholders = new HashMap<>();
    }

    /**
     * Create a new SimpleItem from the DisplayName
     * @param displayName the DisplayName
     */
    public SimpleItem(String displayName) {
        this.material = XMaterial.STONE;
        this.displayName = displayName;
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.placeholders = new HashMap<>();
    }

    /**
     * Adds a new placeholder to the Item
     * @param key the key of the placeholder
     * @param value the value of the placeholder
     * @return this item
     */
    public SimpleItem addPlaceholder(String key, String value){
        this.placeholders.put(key, value);
        return this;
    }

    /**
     * Removes a placeholder from the Item
     * @param key the key of the placeholder
     * @return this item
     */
    public SimpleItem removePlaceholder(String key){
        this.placeholders.remove(key);
        return this;
    }

    /**
     * Sets the material of the item
     * @param material the material
     * @return this item
     */
    public SimpleItem setMaterial(XMaterial material){
        this.material = material;
        return this;
    }

    /**
     * Sets the amount of the item
     * @param amount the amount
     * @return this item
     */
    public SimpleItem setAmount(int amount){
        this.amount = amount;
        return this;
    }

    /**
     * Sets the lore of the item
     * @param lore the lore
     * @return this item
     */
    public SimpleItem setLore(List<String> lore){
        this.lore = new ArrayList<>(lore);
        return this;
    }

    /**
     * Sets the lore of the item
     * @param lore the lore
     * @return this item
     */
    public SimpleItem setLore(String... lore){
        this.lore = Utils.toList(lore);
        return this;
    }

    /**
     * Sets the first line of the lore
     * @param text the text
     * @return this item
     */
    public SimpleItem setFirstLoreLine(String text){
        List<String> lore = new ArrayList<>();
        lore.add(text);
        lore.addAll(this.lore);
        this.lore = lore;
        return this;
    }

    /**
     * Adds a new line to the lore
     * @param text the text
     * @return this item
     */
    public SimpleItem addLoreLine(String text){
        this.lore.add(text);
        return this;
    }

    /**
     * Adds new lines to the lore
     * @param lore the text
     * @return this item
     */
    public SimpleItem addLoreLines(String... lore){
        this.lore.addAll(Utils.toList(lore));
        return this;
    }

    /**
     * Sets a specific line of the lore
     * @param index the index to set
     * @param text the text
     * @return this item
     */
    public SimpleItem setLoreLine(int index, String text){
        if(index < 0) index = this.lore.size();
        this.lore.set(index, text);
        return this;
    }

    /**
     * Removes the last line from the lore
     * @return this item
     */
    public SimpleItem removeLastLoreLine(){
        this.lore.remove(this.lore.size()-1);
        return this;
    }

    /**
     * Removes a specific line from the lore
     * @param index the index
     * @return this item
     */
    public SimpleItem removeLoreLine(int index){
        if(index >= this.lore.size()) index = this.lore.size()-1;
        if(index < 0) index = 0;
        this.lore.remove(index);
        return this;
    }

    /**
     * Sets the skin texture of the item
     * @param skin the skin
     * @return this item
     */
    public SimpleItem setSkin(SkinTexture skin){
        this.skinTexture = skin;
        return this;
    }

    /**
     * Adds an enchantment to the item
     * @param enchantment the enchantment
     * @return this item
     */
    public SimpleItem addEnchantment(Enchantment enchantment){
        return this.addEnchantment(enchantment, 1);
    }

    /**
     * Adds an enchantment and specify the level
     * @param enchantment the enchantment
     * @param level the level
     * @return this item
     */
    public SimpleItem addEnchantment(Enchantment enchantment, int level){
        this.enchantments.put(enchantment, level);
        return this;
    }

    /**
     * Removes an enchantment from the item
     * @param enchantment the enchantment
     * @return this item
     */
    public SimpleItem removeEnchantment(Enchantment enchantment){
        this.enchantments.remove(enchantment);
        return this;
    }

    /**
     * Adds a flag to the item
     * @param flag the flag
     * @return this item
     */
    public SimpleItem addFlag(ItemFlag flag){
        this.flags.add(flag);
        return this;
    }

    /**
     * Adds flags to the item
     * @param flags the flags
     * @return this item
     */
    public SimpleItem addFlags(ItemFlag... flags){
        this.flags.addAll(Utils.toList(flags));
        return this;
    }

    /**
     * Removes a flag from the item
     * @param flag the flag
     * @return this item
     */
    public SimpleItem removeFlag(ItemFlag flag){
        this.flags.remove(flag);
        return this;
    }

    /**
     * Sets if the enchantments should be shown
     * @param showEnchantments true for show the enchantments, false to hide them
     * @return this item
     */
    public SimpleItem setShowEnchantments(boolean showEnchantments){
        ItemFlag flag = ItemFlag.HIDE_ENCHANTS;
        if(showEnchantments){
            this.flags.remove(flag);
        }else{
            this.flags.add(flag);
        }
        return this;
    }

    /**
     * Sets if the attributes should be shown
     * @param showAttributes true to show the attributes, false to hide them
     * @return this item
     */
    public SimpleItem setShowAttributes(boolean showAttributes){
        ItemFlag flag = ItemFlag.HIDE_ATTRIBUTES;
        if(showAttributes){
            this.flags.remove(flag);
        }else{
            this.flags.add(flag);
        }
        return this;
    }

    /**
     * Sets the DisplayName of the item
     * @param name the name
     * @return this item
     */
    public SimpleItem setDisplayName(String name){
        this.displayName = name;
        return this;
    }

    /**
     * Sets the enchantments of the item
     * @param enchantments the enchantments
     * @return this item
     */
    public SimpleItem setEnchantments(HashMap<Enchantment, Integer> enchantments) {
        this.enchantments = new HashMap<>(enchantments);
        return this;
    }

    /**
     * Sets the flags of the item
     * @param flags the flags
     * @return this item
     */
    public SimpleItem setFlags(List<ItemFlag> flags){
        this.flags = new ArrayList<>(flags);
        return this;
    }

    /**
     * Gets the material of this item
     * @return the material of this item
     */
    public XMaterial getMaterial() {
        return material;
    }

    /**
     * Gets the amount of this item
     * @return the amount of this item
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the lore of this item
     * @return the lore of this item
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Gets the displayName of this item
     * @return the displayName of this item
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the skin texture of this item
     * @return the skin texture of this item
     */
    public SkinTexture getSkinTexture() {
        return skinTexture;
    }

    /**
     * Gets the enchantments of this item
     * @return the enchantments of this item
     */
    public HashMap<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    /**
     * Gets the flags of this item
     * @return the flags of this item
     */
    public List<ItemFlag> getFlags() {
        return flags;
    }

    /**
     * Checks if this item have any enchantments
     * @return if this item have any enchantments
     */
    public boolean hasEnchantments(){
        return this.getEnchantments().size() != 0;
    }

    /**
     * Checks if this item have any flags
     * @return if this item have any flags
     */
    public boolean hasFlags(){
        return this.getFlags().size() != 0;
    }

    /**
     * Checks if this item have any skin
     * @return if this item have any skin
     */
    public boolean hasSkin(){
        return this.getSkinTexture() != null;
    }

    /**
     * Duplicates this item
     * @return the duplicated item
     */
    public SimpleItem duplicate(){
        return new SimpleItem(this.getMaterial()).setAmount(this.getAmount()).setLore(this.getLore()).setDisplayName(this.getDisplayName()).setSkin(this.getSkinTexture()).setEnchantments(this.getEnchantments()).setFlags(this.getFlags());
    }

    /**
     * Transforms this item into a ItemStack
     * @return this item as ItemStack
     */
    public ItemStack build(){
        ItemStack item = this.material.parseItem();
        if(this.skinTexture != null){
            SkullMeta meta = ((SkullMeta)item.getItemMeta());
            if(meta != null){
                GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "");
                byte[] skinBytes = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", this.getSkinTexture().getUrl()).getBytes());
                gameProfile.getProperties().put("textures", new Property("textures", new String(skinBytes)));

                try {
                    Field profile = meta.getClass().getDeclaredField("profile");
                    profile.setAccessible(true);
                    profile.set(meta, gameProfile);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                meta.setDisplayName(this.apply(Utils.ct(this.getDisplayName())));
                meta.setLore(this.apply(Utils.ct(this.getLore())));
                meta.getItemFlags().addAll(this.getFlags());
                item.setItemMeta(meta);
            }
        }else{
            ItemMeta meta = item.getItemMeta();
            if(meta != null){
                meta.setDisplayName(this.apply(Utils.ct(this.getDisplayName())));
                meta.setLore(this.apply(Utils.ct(this.getLore())));
                meta.getItemFlags().addAll(this.getFlags());
                item.setItemMeta(meta);
            }
        }
        item.addUnsafeEnchantments(this.getEnchantments());
        item.setAmount(this.getAmount());
        return item;
    }

    private String apply(String text){
        AtomicReference<String> r = new AtomicReference<>(text);
        this.placeholders.forEach((k,v)-> r.set(r.get().replace(k,v)));
        return r.get();
    }

    private List<String> apply(List<String> list){
        List<String> r = new ArrayList<>();
        list.forEach(s-> r.add(apply(s)));
        return r;
    }
}
