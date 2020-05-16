/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.dialog;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
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
    private String closeWord;

    public Dialog(SpigotPlugin plugin, Player player){
        super(plugin, false);
        this.player = player;
        this.placeholders = new HashMap<>();
        this.openDialog();
    }

    public void openDialog(){
        this.getSpigotTasks().runTask(()->{
            HandlerList.unregisterAll(this);
            this.closeWord = this.getSettings().getCloseWord().toLowerCase();
            this.listener(this);
            this.getPlayer().closeInventory();
            sendTitleAndActionbar();
            if(this.canClose()){
                this.getSuperUtils().sendMessage(this.getPlayer(), Base.DIALOG_HOW_TO_CLOSE.options().vars(this.closeWord).toString());
            }
        });
    }

    private void sendTitleAndActionbar() {
        Title.sendTitle(this.getPlayer(), 0, 999, 0, this.apply(Utils.ct(this.getTitle() != null ? this.getTitle() : "")), this.apply(Utils.ct(this.getSubtitle() != null ? this.getSubtitle() : "")));
        Actionbar.sendActionBar(this.getPlayer(), this.apply(Utils.ct(this.getActionbar() != null ? this.getActionbar() : "")));
    }

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
                this.getSuperUtils().sendMessage(this.getPlayer(), Base.DIALOG_HOW_TO_CLOSE.toString().replace("{CloseWord}", this.closeWord));
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(event.getPlayer().equals(this.getPlayer())){
            event.setCancelled(true);
            String close = this.closeWord;
            String message = event.getMessage();
            if(message.toLowerCase().equals(close.toLowerCase())){
                if(this.canClose()){
                    this.close();
                    this.getSuperUtils().sendMessage(this.getPlayer(), Base.DIALOG_CLOSED.toString());
                }
            }else{
                boolean result = this.onResult(message);
                if(result){
                    this.close();
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Dialog setRecall(Recall<Player> recall){
        this.recall = recall;
        return this;
    }

    public Dialog addPlaceholders(HashMap<String, String> placeholders){
        this.placeholders.putAll(placeholders);
        return this;
    }

    public Dialog addPlaceholder(String key, String value){
        this.placeholders.put(key,value);
        return this;
    }

    public Dialog removePlaceholder(String key){
        this.placeholders.remove(key);
        return this;
    }

    private String apply(String s){
        final AtomicReference<String> r = new AtomicReference<>(s);
        this.placeholders.forEach((k,v)-> r.set(r.get().replace(k,v)));
        return r.get();
    }

    public abstract String getTitle();

    public abstract String getSubtitle();

    public abstract String getActionbar();

    public abstract boolean onResult(String playerInput);

    public boolean canClose(){
        return true;
    }

    public void onDialogClose(){

    }
}
