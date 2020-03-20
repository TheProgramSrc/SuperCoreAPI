/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.dialogs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.events.timer.Time;
import xyz.TheProgramSrc.SuperCoreAPI.events.timer.TimerEvent;
import xyz.TheProgramSrc.SuperCoreAPI.packets.Actionbar;
import xyz.TheProgramSrc.SuperCoreAPI.packets.Title;
import xyz.TheProgramSrc.SuperCoreAPI.translation.Base;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Recall;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Dialog extends SuperModule {

    private Player player;
    private Recall<Player> recall;
    private HashMap<String, String> placeholders;
    private String closeWord;
    private boolean opened;

    public Dialog(SuperCore core, Player player){
        super(core, false);
        this.player = player;
        this.placeholders = new HashMap<>();
        this.openDialog();
    }

    public void openDialog(){
        this.getTaskUtil().runTask(()->{
            HandlerList.unregisterAll(this);
            this.closeWord = this.getSystemSettings().getCloseWord().toLowerCase();
            this.listener(this);
            this.getPlayer().closeInventory();
            Title.sendFullTitle(this.getPlayer(), 0, 999, 0, this.apply(Utils.ct(this.getTitle() != null ? this.getTitle() : "")), this.apply(Utils.ct(this.getSubtitle() != null ? this.getSubtitle() : "")));
            Actionbar.sendActionbar(this.getPlayer(), this.apply(Utils.ct(this.getActionbar() != null ? this.getActionbar() : "")));
            Utils.sendMessage(this.getPlayer(), Base.DIALOG_HOW_TO_CLOSE.toString().replace("{CloseWord}", this.closeWord));
            this.opened = true;
        });
    }

    public void close(){
        this.getTaskUtil().runTask(()->{
            HandlerList.unregisterAll(this);
            Title.clearTitle(this.getPlayer());
            Actionbar.clearActionbar(this.getPlayer());
            this.opened = false;
            this.recall.run(this.getPlayer());
        });
    }

    @EventHandler
    public void syncMessages(TimerEvent event){
        if(event.getTime() == Time.TICK){
            if(!this.opened) this.opened = true;
            Title.sendFullTitle(this.getPlayer(), 0, 999, 0, this.apply(Utils.ct(this.getTitle() != null ? this.getTitle() : "")), this.apply(Utils.ct(this.getSubtitle() != null ? this.getSubtitle() : "")));
            Actionbar.sendActionbar(this.getPlayer(), this.apply(Utils.ct(this.getActionbar() != null ? this.getActionbar() : "")));
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(this.opened){
            if(this.getPlayer().equals(event.getPlayer())){
                Utils.sendMessage(this.getPlayer(), Base.DIALOG_HOW_TO_CLOSE.toString().replace("{CloseWord}", this.closeWord));
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(event.getPlayer().equals(this.getPlayer())){
            if(this.opened){
                event.setCancelled(true);
                String close = this.closeWord;
                String message = event.getMessage();
                if(new String(message.getBytes()).toLowerCase().equals(close.toLowerCase())){
                    if(this.canClose()){
                        this.close();
                    }
                }else{
                    boolean result = this.onResult(message);
                    if(result){
                        this.close();
                    }
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
