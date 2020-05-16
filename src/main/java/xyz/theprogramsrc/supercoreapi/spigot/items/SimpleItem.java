/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

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
import xyz.theprogramsrc.supercoreapi.spigot.utils.xseries.XMaterial;
import xyz.theprogramsrc.supercoreapi.spigot.utils.skintexture.SkinTexture;

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

    public SimpleItem(SkinTexture skinTexture){
        this.material = XMaterial.PLAYER_HEAD;
        this.skinTexture = skinTexture;
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.placeholders = new HashMap<>();
    }

    public SimpleItem(XMaterial material) {
        this.material = material;
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.placeholders = new HashMap<>();
    }

    public SimpleItem(String displayName) {
        this.material = XMaterial.STONE;
        this.displayName = displayName;
        this.enchantments = new HashMap<>();
        this.flags = new ArrayList<>();
        this.lore = new ArrayList<>();
        this.placeholders = new HashMap<>();
    }

    public SimpleItem addPlaceholder(String key, String value){
        this.placeholders.put(key, value);
        return this;
    }

    public SimpleItem removePlaceholder(String key){
        this.placeholders.remove(key);
        return this;
    }

    public SimpleItem setMaterial(XMaterial material){
        this.material = material;
        return this;
    }

    public SimpleItem setAmount(int amount){
        this.amount = amount;
        return this;
    }

    public SimpleItem setLore(List<String> lore){
        this.lore = new ArrayList<>(lore);
        return this;
    }

    public SimpleItem setLore(String... lore){
        this.lore = Utils.toList(lore);
        return this;
    }

    public SimpleItem setFirstLoreLine(String text){
        List<String> lore = new ArrayList<>();
        lore.add(text);
        lore.addAll(this.lore);
        this.lore = lore;
        return this;
    }

    public SimpleItem addLoreLine(String text){
        this.lore.add(text);
        return this;
    }

    public SimpleItem addLoreLines(String... lore){
        this.lore.addAll(Utils.toList(lore));
        return this;
    }

    public SimpleItem setLoreLine(int index, String text){
        if(index < 0) index = this.lore.size();
        this.lore.set(index, text);
        return this;
    }

    public SimpleItem removeLastLoreLine(){
        this.lore.remove(this.lore.size()-1);
        return this;
    }

    public SimpleItem removeLoreLine(int index){
        if(index >= this.lore.size()) index = this.lore.size()-1;
        if(index < 0) index = 0;
        this.lore.remove(index);
        return this;
    }

    public SimpleItem setSkin(SkinTexture skin){
        this.skinTexture = skin;
        return this;
    }

    public SimpleItem addEnchantment(Enchantment enchantment){
        return this.addEnchantment(enchantment, 1);
    }

    public SimpleItem addEnchantment(Enchantment enchantment, int level){
        this.enchantments.put(enchantment, level);
        return this;
    }

    public SimpleItem removeEnchantment(Enchantment enchantment){
        this.enchantments.remove(enchantment);
        return this;
    }

    public SimpleItem addFlag(ItemFlag flag){
        this.flags.add(flag);
        return this;
    }

    public SimpleItem addFlags(ItemFlag... flags){
        this.flags.addAll(Utils.toList(flags));
        return this;
    }

    public SimpleItem removeFlag(ItemFlag flag){
        this.flags.remove(flag);
        return this;
    }

    public SimpleItem setShowEnchantments(boolean showEnchantments){
        ItemFlag flag = ItemFlag.HIDE_ENCHANTS;
        if(showEnchantments){
            this.flags.remove(flag);
        }else{
            this.flags.add(flag);
        }
        return this;
    }

    public SimpleItem setShowAttributes(boolean showAttributes){
        ItemFlag flag = ItemFlag.HIDE_ATTRIBUTES;
        if(showAttributes){
            this.flags.remove(flag);
        }else{
            this.flags.add(flag);
        }
        return this;
    }

    public SimpleItem setDisplayName(String name){
        this.displayName = name;
        return this;
    }

    public SimpleItem setEnchantments(HashMap<Enchantment, Integer> enchantments) {
        this.enchantments = new HashMap<>(enchantments);
        return this;
    }

    public SimpleItem setFlags(List<ItemFlag> flags){
        this.flags = new ArrayList<>(flags);
        return this;
    }

    public XMaterial getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getLore() {
        return lore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SkinTexture getSkinTexture() {
        return skinTexture;
    }

    public HashMap<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public List<ItemFlag> getFlags() {
        return flags;
    }

    public boolean hasEnchantments(){
        return this.getEnchantments().size() != 0;
    }

    public boolean hasFlags(){
        return this.getFlags().size() != 0;
    }

    public boolean hasSkin(){
        return this.getSkinTexture() != null;
    }

    public SimpleItem duplicate(){
        return new SimpleItem(this.getMaterial()).setAmount(this.getAmount()).setLore(this.getLore()).setDisplayName(this.getDisplayName()).setSkin(this.getSkinTexture()).setEnchantments(this.getEnchantments()).setFlags(this.getFlags());
    }

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
