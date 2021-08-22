package xyz.theprogramsrc.supercoreapi.spigot.guis;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.cryptomorin.xseries.XMaterial;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import xyz.theprogramsrc.supercoreapi.global.objects.RecurringTask;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickType;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIClickEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUICloseEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIEmptyClickEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIOpenEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIOutsideClickEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.events.GUIUpdateEvent;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;

/**
 * @deprecated As of version 5.2.0 the GUI system has been replaced with {@link xyz.theprogramsrc.supercoreapi.spigot.gui.Gui}.
 */
@Deprecated public abstract class GUI extends SpigotModule {

    private final UUID uuid = UUID.randomUUID();
    private final Player player;
    private Inventory inv;
    private final LinkedHashMap<Integer, GUIButton> buttons;
    private String previousTitle;
    private int previousSize;
    private boolean manuallyClosed;
    private RecurringTask task;
    protected boolean compatibilityClicking = false;

    /**
     * Create a new {@link GUI GUI}
     * @param player the player to show the {@link GUI GUI}
     */
    public GUI(Player player){
        super(false);
        this.manuallyClosed = false;
        this.player = player;
        this.buttons = new LinkedHashMap<>();
        this.task = this.getSpigotTasks().runRepeatingTask(1L, 1L, ()->{
            if(this.inv != null){
                if(!this.previousTitle.equals(this.getTitle()) || this.getRows().getSize() != this.previousSize){
                    this.task.stop();
                    HandlerList.unregisterAll(this);
                    this.open();
                    this.task.start();
                    this.listener(GUI.this);
                }

                GUIButton[] buttonsArray = this.getButtons();
                this.addButtons(buttonsArray);

                this.inv.clear();
                for(Map.Entry<Integer, GUIButton> entry : new LinkedHashMap<>(this.buttons).entrySet()){
                    int slot = entry.getKey();
                    if(slot < this.getRows().getSize() && slot >= 0){
                        ItemStack item = entry.getValue().getItemStack();
                        if(item != null){
                            this.inv.setItem(slot, item);
                        }
                    }
                }
                this.onEvent(new GUIUpdateEvent(this));
                this.player.updateInventory();
            }
        });
    }

    /**
     * Opens the {@link GUI GUI}
     */
    public void open(){
        if(this.inv == null){
            this.task.start();
            this.listener(this);
        }
        this.manuallyClosed = false;
        this.previousTitle = this.getTitle();
        this.previousSize = this.getRows().getSize();
        this.inv = Bukkit.createInventory(null, this.getRows().getSize(), this.getSuperUtils().color(this.isTitleCentered() ? this.getCenteredTitle() : this.getTitle()));
        this.getSpigotTasks().runTask(()->{
            GUIOpenEvent guiOpenEvent = new GUIOpenEvent(this);
            this.onEvent(guiOpenEvent);
            if(this.compatibilityClicking){
                this.player.closeInventory();
            }

            this.player.openInventory(this.inv);
        });
    }

    /**
     * Closes the {@link GUI GUI}
     */
    public void close(){
        this.manuallyClosed = true;
        this.getSpigotTasks().runTask(this.player::closeInventory);
    }

    /**
     * Adds the specified buttons to the GUI
     * @param buttons the buttons to add
     *
     * @see #addButton(GUIButton)
     */
    public void addButtons(Collection<GUIButton> buttons){
        if(buttons == null) return;
        for(GUIButton button : buttons)
            this.addButton(button);
    }

    /**
     * Adds the specified buttons to the GUI
     * @param buttons the buttons to add
     *
     * @see #addButton(GUIButton)
     */
    public void addButtons(GUIButton... buttons){
        if(buttons == null) return;
        for(GUIButton button : buttons)
            this.addButton(button);
    }

    /**
     * Adds a button inside the {@link GUI GUI} using the last empty slot if there is no slot specified inside the {@link GUIButton button}
     * @param button the button to add inside the {@link GUI GUI}
     */
    public void addButton(GUIButton button){
        if(button == null) return;
        this.setButton(button.getSlot(), button);
    }

    /**
     * Sets a button inside the {@link GUI GUI} overriding the slot specified in the {@link GUIButton button}
     * @param slot the slot where should the button be placed
     * @param button the button to add inside the {@link GUI GUI}
     */
    public void setButton(int slot, GUIButton button){
        if(slot < 0){
            while(this.buttons.containsKey(slot)){
                slot++;
            }
            button.setSlot(slot);
        }

        if(this.buttons.containsKey(slot)){
            if(this.buttons.get(slot).equals(button)){
                return;
            }
        }
        this.buttons.put(slot, button);
    }

    /**
     * Gets a button from the GUI
     * @param slot the slot
     * @return the button located in the slot or null if there is no button
     */
    public GUIButton getButton(int slot){
        return this.buttons.get(slot);
    }

    /**
     * Removes all the buttons in the specified slots
     * @param slots the slots of the buttons to remove
     */
    public void removeButtons(int... slots){
        for(int slot : slots)
            this.removeButton(slot);
    }

    /**
     * Removes a button from the GUI
     * @param slot the slot of the button to remove
     */
    public void removeButton(int slot){
        this.buttons.remove(slot);
    }

    /**
     * Removes all the buttons from the GUI
     */
    public void clearButtons(){
        this.buttons.clear();
    }

    /**
     * Checks if there is a button in the given slot
     * @param slot the slot to check
     * @return true if there is a button, otherwise false
     */
    public boolean containsButton(int slot){
        return this.buttons.containsKey(slot);
    }

    /**
     * Alternative method to add items into a {@link GUI GUI}
     * @return the buttons to add inside the {@link GUI GUI}
     */
    protected GUIButton[] getButtons(){
        return new GUIButton[0];
    }

    protected abstract GUIRows getRows();

    protected abstract String getTitle();

    /**
     * Check if the {@link GUI} can be closed
     * @return true if can be closed, otherwise false
     */
    public boolean canCloseGUI(){
        return true;
    }

    /**
     * Check if the title must be centered
     * @return true if the title must be centered, otherwise false
     */
    public boolean isTitleCentered(){
        return false;
    }

    /**
     * Gets the centered title
     * @return the centered title
     */
    public String getCenteredTitle() {
        String title = this.getTitle();
        StringBuilder result = new StringBuilder();
        int spaces = (27 - this.spigotPlugin.getSuperUtils().removeColor(title).length());

        for (int i = 0; i < spaces; i++) {
            result.append(" ");
        }

        return result.append(title).toString();
    }

    /**
     * Executed when a {@link GUIEvent GUIEvent} is called
     * @param event the {@link GUIEvent event} called
     */
    public void onEvent(GUIEvent event){ }

    @EventHandler(priority = EventPriority.LOW)
    public void onClose(InventoryCloseEvent event){
        if(this.inv != null && this.player != null && event.getInventory().equals(this.inv) && event.getPlayer().equals(this.player)){
            this.inv = null;
            this.task.stop();
            HandlerList.unregisterAll(this);
            GUICloseEvent closeEvent = new GUICloseEvent(this);
            this.onEvent(closeEvent);
            if(closeEvent.isCancelled() || (!this.canCloseGUI() && !this.manuallyClosed)){
                this.open();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onClick(InventoryClickEvent event){
        if(this.inv != null && this.player != null){
            if(event.getInventory().equals(this.inv) && event.getWhoClicked().equals(this.player) && (!(event.getInventory() instanceof PlayerInventory))){
                Player who = ((Player)event.getWhoClicked());
                event.setCancelled(true);
                ItemStack item = event.getCurrentItem();
                InventoryType.SlotType slotType = event.getSlotType();
                int slot = event.getRawSlot();
                if(slotType == InventoryType.SlotType.OUTSIDE){
                    GUIOutsideClickEvent outsideClickEvent = new GUIOutsideClickEvent(this);
                    this.onEvent(outsideClickEvent);
                    event.setCancelled(!outsideClickEvent.canDrop());
                }else{
                    event.setCancelled(true);
                    GUIEmptyClickEvent emptyClickEvent = new GUIEmptyClickEvent(this, slot);
                    if(item == null || item.getType() == XMaterial.AIR.parseMaterial()){
                        this.onEvent(emptyClickEvent);
                    }else{
                        if(this.buttons.containsKey(slot)){
                            GUIButton button = this.buttons.get(slot);
                            GUIClickEvent clickEvent = new GUIClickEvent(this, button, slot);
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
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event){
        if(this.inv != null && event.getPlayer().equals(this.player)){
            this.close();
        }
    }

    /**
     * Gets the {@link Inventory Bukkit Inventory}
     * @return the {@link Inventory Bukkit Inventory}
     */
    public Inventory getBukkitInventory() {
        return this.inv;
    }

    /**
     * Gets the player who is viewing the {@link GUI gui}
     * @return the player who is viewing the {@link GUI gui}
     */
    public Player getPlayer() {
        return this.player;
    }

}
