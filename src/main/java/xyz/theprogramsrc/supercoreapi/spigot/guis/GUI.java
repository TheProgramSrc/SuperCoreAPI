/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.Time;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.TimerEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickType;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIClickEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIEmptyClickEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIOutsideClickEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.utils.xseries.XMaterial;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public abstract class GUI extends SpigotModule {

    private Inventory inventory;
    private final HashMap<String, String> placeholders;
    private final Player player;
    private HashMap<Integer, GUIButton> buttons;

    public GUI(SpigotPlugin plugin, Player player){
        super(plugin);
        this.player = player;
        this.placeholders = new HashMap<>();
        this.buttons = new HashMap<>();
    }

    public void open(){
        this.getSpigotTasks().runTask(()->{
            if(this.inventory == null){
                this.plugin.listener(this);
            }
            this.inventory = Bukkit.createInventory(null, this.getRows().getSize(), this.plugin.getSuperUtils().color(this.applyPlaceholders(this.centerTitle() ? this.getCenteredTitle() : this.getTitle())));
            this.player.openInventory(this.inventory);
        });
    }

    public void close(){
        this.getSpigotTasks().runTask(()->{
            HandlerList.unregisterAll(this);
            this.inventory = null;
            this.player.closeInventory();
        });
    }

    public GUIButton getButtonFromGUI(int slot){
        return this.buttons.getOrDefault(slot, null);
    }

    public boolean hasItems(){
        return this.buttons.size() != 0;
    }

    public void clear(){
        this.buttons.clear();
        this.inventory.clear();
    }

    public boolean centerTitle(){
        return true;
    }

    public String getCenteredTitle() {
        String title = this.getTitle();
        StringBuilder result = new StringBuilder();
        int spaces = (27 - this.plugin.getSuperUtils().removeColor(title).length());

        for (int i = 0; i < spaces; i++) {
            result.append(" ");
        }

        return result.append(title).toString();
    }

    public void placeholder(String key, String value){
        this.placeholders.put(key, value);
    }

    public void remPlaceholder(String key){
        this.placeholders.remove(key);
    }

    public void placeholders(Map<String, String> placeholders){
        this.placeholders.putAll(placeholders);
    }

    protected String applyPlaceholders(String text){
        final AtomicReference<String> r = new AtomicReference<>(text);
        this.placeholders.forEach((k,v)-> r.set(r.get().replace(k,v)));
        return r.get();
    }

    @EventHandler
    public void syncItems(TimerEvent event){
        if(event.getTime() == Time.TICK){
            if(this.inventory != null){
                this.buttons = new HashMap<>();
                GUIButton[] buttons = this.getButtons();
                if(buttons == null) return;
                for (GUIButton b : buttons) {
                    int slot = b.getSlot();
                    if (slot == -1) {
                        for(slot = 0; this.buttons.containsKey(slot);){
                            slot++;
                        }
                    }

                    this.buttons.put(slot, b);
                }

                this.inventory.clear();

                for(GUIButton b : this.buttons.values()){
                    int slot = b.getSlot();
                    ItemStack item = b.getItemStack();
                    if(item != null){
                        if(slot <= this.getRows().getSize() && slot >= 0){
                            this.inventory.setItem(slot, item);
                        }
                    }
                }


                this.player.updateInventory();
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(this.inventory != null){
            if(event.getInventory().equals(this.inventory)){
                if(event.getPlayer().equals(this.player)){
                    this.getSpigotTasks().runTask(()->{
                        HandlerList.unregisterAll(this);
                        this.inventory = null;
                    });
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(this.inventory != null){
            if(event.getPlayer().equals(this.player)){
                HandlerList.unregisterAll(this);
                this.inventory = null;
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player who = ((Player)event.getWhoClicked());
        if(this.player.equals(who)){
            if(this.inventory != null){
                if(event.getInventory().equals(this.inventory) || Utils.equals(event.getClickedInventory(), this.inventory)){
                    int slot = event.getSlot();
                    ItemStack item = event.getCurrentItem();
                    InventoryType.SlotType slotType = event.getSlotType();
                    if(slotType == InventoryType.SlotType.OUTSIDE){
                        GUIOutsideClickEvent outsideClickEvent = new GUIOutsideClickEvent(this, who);
                        this.onEvent(outsideClickEvent);
                        event.setCancelled(!outsideClickEvent.canDrop());
                    }else{
                        event.setCancelled(true);
                        if(item == null){
                            GUIEmptyClickEvent emptyClickEvent = new GUIEmptyClickEvent(this, slot, player);
                            this.onEvent(emptyClickEvent);
                            return;
                        }

                        if(item.getType() == XMaterial.AIR.parseMaterial()){
                            GUIEmptyClickEvent emptyClickEvent = new GUIEmptyClickEvent(this, slot, player);
                            this.onEvent(emptyClickEvent);
                            return;
                        }

                        GUIButton button = this.buttons.get(slot);
                        GUIClickEvent clickEvent = new GUIClickEvent(this, who, button, slot);
                        this.onEvent(clickEvent);
                        if(button.getAction() != null){
                            ClickType clickType = ClickType.fromEvent(event);
                            ClickAction clickAction = new ClickAction(this, who, button, clickType, slot);
                            button.getAction().onClick(clickAction);
                        }
                    }
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getBukkitInventory() {
        return inventory;
    }

    protected void onEvent(GUIEvent event){}

    protected abstract String getTitle();

    protected abstract GUIRows getRows();

    protected abstract GUIButton[] getButtons();
}
