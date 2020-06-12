package xyz.theprogramsrc.supercoreapi.spigot.events.messagingService;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

public class SpigotMessagingSender extends SpigotModule {

    public SpigotMessagingSender(SpigotPlugin plugin) {
        super(plugin);
    }

    /**
     * Send a message to the BungeeCord server using the Plugin Channel
     * @param player The player to use
     * @param subchannel The subchannel
     * @param data The data to send
     */
    public void sendMessage(Player player, String subchannel, String data){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);
        out.writeUTF(data);
        player.sendPluginMessage(((SpigotPlugin)this.plugin), this.getPlugin().getPluginMessagingChannelName(), out.toByteArray());
    }

    /**
     * Send a message to the BungeeCord server using the BungeeCord Channel
     * @param player The player to use
     * @param subchannel The subchannel
     * @param data The data to send
     */
    public void sendBungeeMessage(Player player, String subchannel, String data){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subchannel);
        out.writeUTF(data);
        player.sendPluginMessage(((SpigotPlugin)this.plugin), "BungeeCord", out.toByteArray());
    }
}
