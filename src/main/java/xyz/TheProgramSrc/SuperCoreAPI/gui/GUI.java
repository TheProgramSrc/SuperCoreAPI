/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.events.timer.Time;
import xyz.TheProgramSrc.SuperCoreAPI.events.timer.TimerEvent;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.GUIButton;
import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.ClickAction;
import xyz.TheProgramSrc.SuperCoreAPI.gui.events.GUIClickEvent;
import xyz.TheProgramSrc.SuperCoreAPI.gui.events.GUIEmptyClickEvent;
import xyz.TheProgramSrc.SuperCoreAPI.gui.events.GUIEvent;
import xyz.TheProgramSrc.SuperCoreAPI.gui.events.GUIOutsideClickEvent;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class GUI extends SuperModule {

    private Inventory inventory;
    private Player player;
    private BukkitTask task;
    private HashMap<Integer, GUIButton> buttons;
    private HashMap<String, String> placeholders;

    public GUI(SuperCore core, Player player, boolean autoOpen){
        super(core, false);
        this.player = player;
        this.placeholders = new HashMap<>();
        if(autoOpen) this.open();
    }

    public GUI(SuperCore core, Player player){
        this(core, player, false);
    }

    public void open(){
        if(this.inventory == null){
            this.listener(this);
        }
        this.inventory = Bukkit.createInventory(null, this.getSize(), this.apply(Utils.ct(this.getTitle())));
        loadGUIButtonsAndInventory();
        this.loadUI();
        this.player.openInventory(this.inventory);
    }

    public void close(){
        HandlerList.unregisterAll(this);
        this.inventory = null;
        this.getTaskUtil().runTask(()->this.player.closeInventory());
    }

    public int getSize(){
        return 54;
    }

    public GUIButton getButton(int slot){
        return this.buttons.get(slot);
    }

    public boolean hasItems(){
        return this.buttons.size() != 0;
    }

    public void clear(){
        this.inventory.clear();
        this.buttons.clear();
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getBukkitInventory() {
        return inventory;
    }

    public abstract String getTitle();

    public abstract GUIButton[] getButtons();

    @EventHandler
    public void syncItems(TimerEvent event){
        if(event.getTime() == Time.TICK){
            if(this.inventory != null){
                loadGUIButtonsAndInventory();
            }
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event){
        if(this.inventory != null){
            if(event.getInventory().equals(this.inventory)){
                if(event.getPlayer().equals(this.player)){
                    loadGUIButtonsAndInventory();
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if(this.inventory != null){
            if(event.getInventory().equals(this.inventory)){
                if(event.getPlayer().equals(this.player)){
                    this.getTaskUtil().runTask(()->{
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
                if(event.getInventory().equals(this.inventory) || event.getClickedInventory().equals(this.inventory)){
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

    public void loadUI(){

    }

    public abstract void onEvent(GUIEvent event);

    public GUI addPlaceholder(String key, String value){
        this.placeholders.put(key, value);
        return this;
    }

    public GUI removePlaceholder(String key){
        this.placeholders.remove(key);
        return this;
    }

    private String apply(String text){
        AtomicReference<String> r = new AtomicReference<>(text);
        this.placeholders.forEach((k,v)-> r.set(r.get().replace(k,v)));
        return r.get();
    }



    private void loadGUIButtonsAndInventory() {
        this.buttons = new HashMap<>();
        GUIButton[] buttons = this.getButtons();
        if(buttons == null) return;
        for (GUIButton b : buttons) {
            int slot = b.getSlot();
            if (slot == -1) {
                for(slot = 0; this.buttons.containsKey(slot); ++slot);
            }

            this.buttons.put(slot, b);
        }

        this.inventory.clear();

        for(GUIButton b : this.buttons.values()){
            int slot = b.getSlot();
            ItemStack item = b.getItemStack();
            if(item != null){
                if(slot <= this.getSize() && slot >= 0){
                    this.inventory.setItem(slot, item);
                }
            }
        }


        this.player.updateInventory();
    }

}
