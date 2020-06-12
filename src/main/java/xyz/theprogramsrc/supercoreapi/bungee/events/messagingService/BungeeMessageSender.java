package xyz.theprogramsrc.supercoreapi.bungee.events.messagingService;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;

import java.util.Collection;

public class BungeeMessageSender extends BungeeModule {

    public BungeeMessageSender(BungeePlugin plugin) {
        super(plugin);
    }

    /**
     * Send a message using a ProxiedPlayer through the Plugin channel ()
     * @param player Player to use
     * @param data1 The subchannel to use
     * @param data2 The data to send
     */
    public void sendMessage(ProxiedPlayer player, String data1, String data2){
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if ( networkPlayers == null || networkPlayers.isEmpty()) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(data1);
        out.writeUTF(data2);
        player.getServer().getInfo().sendData(this.getPlugin().getPluginMessagingChannelName(), out.toByteArray());
    }

    /**
     * Send a message using a ProxiedPlayer through the "BungeeCord" channel
     * @param player Player to use
     * @param data1 The subchannel to use
     * @param data2 The data to send
     */
    public void sendBungeeMessage(ProxiedPlayer player, String data1, String data2){
        Collection<ProxiedPlayer> networkPlayers = ProxyServer.getInstance().getPlayers();
        if ( networkPlayers == null || networkPlayers.isEmpty()) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(data1);
        out.writeUTF(data2);
        player.getServer().getInfo().sendData("BungeeCord", out.toByteArray());
    }
}
