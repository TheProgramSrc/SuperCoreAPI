package xyz.theprogramsrc.supercoreapi.spigot.gui;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

import xyz.theprogramsrc.supercoreapi.global.objects.RecurringTask;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.gui.events.GuiCloseEvent;
import xyz.theprogramsrc.supercoreapi.spigot.gui.events.GuiEvent;
import xyz.theprogramsrc.supercoreapi.spigot.gui.events.GuiOpenEvent;
import xyz.theprogramsrc.supercoreapi.spigot.gui.events.GuiOutsideClickEvent;
import xyz.theprogramsrc.supercoreapi.spigot.gui.events.GuiUpdateEvent;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiAction;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiAction.ClickType;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiEntry;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiModel;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiRows;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.GuiTitle;

public abstract  class Gui extends SpigotModule {

    public final UUID uuid = UUID.randomUUID(); 
    private RecurringTask task;
    public final Player player;
    public Inventory inv;
    public boolean preventOpening = false, compatibilityClicking = false, canCloseGui = true;
    private final LinkedHashMap<Integer, Consumer<GuiAction>> actions = new LinkedHashMap<>();
    protected final LinkedList<Consumer<GuiEvent>> events = new LinkedList<>();

    /**
     * Create a new Gui
     * @param player the player to show the Gui
     * @param automaticallyOpen if the Gui should be opened automatically
     */
    public Gui(Player player, boolean automaticallyOpen) {
        super(false);
        this.player = player;
        this.debug("Opening Gui with UUID '" + this.uuid + "'");
        this.task = this.getSpigotTasks().runRepeatingTask(0L, 1L, () -> {
            if(this.inv == null || this.player == null || !this.getSuperUtils().removeColor(this.player.getOpenInventory().getTitle()).equals(this.getSuperUtils().removeColor(this.getTitle().getTitle())) || this.inv.getSize() != this.getRows().size) return;
            if(this.preventOpening){
                this.task.stop();
                HandlerList.unregisterAll(this);
                return;
            }

            GuiModel model = new GuiModel(this.getTitle(), this.getRows());
            this.onBuild(model);
            LinkedHashMap<Integer, GuiEntry> buttons = model.getButtons();
            IntStream.rangeClosed(0, (this.getRows().size - 1)).forEach(slot -> {
                GuiEntry entry = buttons.get(slot);
                if(entry == null){
                    this.inv.setItem(slot, null);
                    this.actions.remove(slot);
                }else{
                    this.inv.setItem(slot, entry.item);
                    if(entry.action != null){
                        this.actions.put(slot, entry.action);
                    }
                }
            });
            this.events.stream().forEach(c-> c.accept(new GuiUpdateEvent(this)));
            this.player.updateInventory();
        });
        if(automaticallyOpen) this.open();
    }

    /**
     * Create a new Gui
     * @param player the player to show the Gui
     */
    public Gui(Player player){
        this(player, true);
    }

    /**
     * Open the Gui
     */
    public void open(){
        if(this.player == null) return;
        this.preventOpening = false;
        if(this.inv == null) {
            this.task.start();
            this.listener(this);
        }
        this.inv = Bukkit.createInventory(null, this.getRows().size, this.getTitle().getTitle());
        this.events.stream().forEach(c-> c.accept(new GuiOpenEvent(this)));
        this.getSpigotTasks().runTask(() -> {
            if(this.compatibilityClicking) this.player.closeInventory();
            this.player.openInventory(this.inv);
        });
    }

    /**
     * Close the Gui
     */
    public void close(){
        this.preventOpening = true;
        this.getSpigotTasks().runTask(this.player::closeInventory);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent e){
        if(this.inv != null && this.player != null && e.getInventory().equals(this.inv) && e.getPlayer().equals(this.player)){
            this.inv = null;
            this.task.stop();
            HandlerList.unregisterAll(this);
            this.events.stream().forEach(c -> c.accept(new GuiCloseEvent(this)));
            if(!this.canCloseGui && this.player != null && !this.preventOpening){
                this.open();
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(!e.getWhoClicked().equals(this.player)) return;
        if(e.getSlotType() == SlotType.OUTSIDE) {
            e.setCancelled(true);
            this.events.stream().forEach(c-> c.accept(new GuiOutsideClickEvent(this)));
            return;
        }
        e.setCancelled(true);
        if(!(e.getClickedInventory() instanceof PlayerInventory) && this.actions.containsKey(e.getSlot())){
            this.actions.get(e.getSlot()).accept(new GuiAction(this, this.player, ClickType.fromEvent(e)));
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e){
        if(!e.getWhoClicked().equals(this.player)) return;
        if(e.getInventory() instanceof PlayerInventory) return;
        if(e.getInventorySlots().size() > 1) {
            e.setCancelled(true);
        }
     }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e){
        if(!e.getPlayer().equals(this.player)) return;
        this.close();
    }

    /**
     * Get the title of the Gui
     * @return the title
     */
    public abstract GuiTitle getTitle();

    /**
     * Get the rows of the Gui
     * @return the rows
     */
    public abstract GuiRows getRows();

    /**
     * Executed to build the Gui
     * @param model the Gui Model
     */
    public abstract void onBuild(GuiModel model);
    
}
