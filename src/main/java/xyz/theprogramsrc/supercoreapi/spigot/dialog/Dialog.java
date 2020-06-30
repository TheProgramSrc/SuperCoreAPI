package xyz.theprogramsrc.supercoreapi.spigot.dialog;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.theprogramsrc.supercoreapi.Recall;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.Time;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.TimerEvent;
import xyz.theprogramsrc.supercoreapi.spigot.packets.Actionbar;
import xyz.theprogramsrc.supercoreapi.spigot.packets.Title;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Dialog extends SpigotModule {

    private final Player player;
    private Recall<Player> recall;
    private final HashMap<String, String> placeholders;

    /**
     * Creates a new dialog between the plugin and a player
     * @param plugin the plugin
     * @param player the player
     */
    public Dialog(SpigotPlugin plugin, Player player){
        super(plugin, false);
        this.player = player;
        this.placeholders = new HashMap<>();
        this.openDialog();
    }

    /**
     * Used to open the dialog
     */
    public void openDialog(){
        this.getSpigotTasks().runTask(()->{
            HandlerList.unregisterAll(this);
            this.listener(this);
            this.getPlayer().closeInventory();
            sendTitleAndActionbar();
            if(this.canClose()){
                this.getSuperUtils().sendMessage(this.getPlayer(), Base.DIALOG_HOW_TO_CLOSE.toString());
            }
        });
    }

    private void sendTitleAndActionbar() {
        Title.sendTitle(this.getPlayer(), 0, 999, 0, this.apply(Utils.ct(this.getTitle() != null ? this.getTitle() : "")), this.apply(Utils.ct(this.getSubtitle() != null ? this.getSubtitle() : "")));
        Actionbar.sendActionBar(this.getPlayer(), this.apply(Utils.ct(this.getActionbar() != null ? this.getActionbar() : "")));
    }

    /**
     * Closes the current dialog
     */
    public void close(){
        this.getSpigotTasks().runTask(()->{
            HandlerList.unregisterAll(this);
            Title.clearTitle(this.getPlayer());
            Actionbar.clearActionbar(this.getPlayer());
            this.onDialogClose();
            if(this.recall != null){
                this.recall.run(this.getPlayer());
            }
        });
    }

    @EventHandler
    public void syncMessages(TimerEvent event){
        if(event.getTime() == Time.TICK){
            sendTitleAndActionbar();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event){
        if(this.canClose()){
            if(this.getPlayer().equals(event.getPlayer())){
                this.getSuperUtils().sendMessage(this.getPlayer(), Base.DIALOG_HOW_TO_CLOSE.toString());
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        if(this.getPlayer().equals(event.getPlayer())){
            if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK){
                if(this.canClose()){
                    this.getSuperUtils().sendMessage(event.getPlayer(), Base.DIALOG_CLOSED.toString());
                    this.close();
                }
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(event.getPlayer().equals(this.getPlayer())){
            event.setCancelled(true);
            String message = event.getMessage();
            boolean result = this.onResult(message);
            if(result){
                this.close();
            }
        }
    }

    /**
     * Gets the player
     * @return The player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the Recall that will be executed while the dialog is closed
     * @param recall The recall
     * @return This dialog
     */
    public Dialog setRecall(Recall<Player> recall){
        this.recall = recall;
        return this;
    }

    /**
     * Add placeholders to the Dialog
     * @param placeholders The placeholders
     * @return This dialog
     */
    public Dialog addPlaceholders(HashMap<String, String> placeholders){
        this.placeholders.putAll(placeholders);
        return this;
    }

    /**
     * Add a single placeholder to the Dialog
     * @param key The key of the placeholder
     * @param value The value of the placeholder
     * @return This dialog
     */
    public Dialog addPlaceholder(String key, String value){
        this.placeholders.put(key,value);
        return this;
    }

    /**
     * Removes a single placeholder to the Dialog
     * @param key The key of the placeholder
     * @return This dialog
     */
    public Dialog removePlaceholder(String key){
        this.placeholders.remove(key);
        return this;
    }

    private String apply(String s){
        final AtomicReference<String> r = new AtomicReference<>(s);
        this.placeholders.forEach((k,v)-> r.set(r.get().replace(k,v)));
        return r.get();
    }

    /**
     * Gets the title of the dialog
     * @return the title
     */
    public abstract String getTitle();

    /**
     * Gets the subtitle of the dialog
     * @return the subtitle
     */
    public abstract String getSubtitle();

    /**
     * Gets the actionbar of the dialog
     * @return the actionbar
     */
    public abstract String getActionbar();

    /**
     * A representation of the result of the dialog
     * @param playerInput The input of the player
     * @return true if the dialog can be closed, otherwise false
     */
    public abstract boolean onResult(String playerInput);

    /**
     * Checks if this dialog can be closed or not
     * @return true if the dialog can be closed, otherwise false
     */
    public boolean canClose(){
        return true;
    }

    /**
     * Executed while the dialog is closed
     */
    public void onDialogClose(){

    }
}
