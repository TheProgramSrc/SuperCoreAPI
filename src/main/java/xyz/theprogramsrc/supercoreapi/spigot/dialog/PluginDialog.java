package xyz.theprogramsrc.supercoreapi.spigot.dialog;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;
import xyz.theprogramsrc.supercoreapi.spigot.events.timer.TimerEvent;

import static xyz.theprogramsrc.supercoreapi.spigot.events.timer.Time.TICK;

public abstract class PluginDialog extends SpigotModule {

    private final Player player;
    private String title;
    private String subtitle;
    private String actionbar;

    public PluginDialog(SpigotPlugin plugin, Player player){
        super(plugin, true);
        this.player = player;
    }

    @EventHandler
    public void syncMessages(TimerEvent event){
        if(event.getTime() == TICK){

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(this.player.equals(event.getPlayer())){
            if(this.canClose()){

            }
        }
    }

    public boolean canClose(){
        return true;
    }

    public abstract String getTitle();

    public abstract String getSubtitle();

    public abstract String getActionbar();
}
