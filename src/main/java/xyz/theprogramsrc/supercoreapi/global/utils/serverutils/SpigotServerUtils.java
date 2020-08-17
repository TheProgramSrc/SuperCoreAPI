package xyz.theprogramsrc.supercoreapi.global.utils.serverutils;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class SpigotServerUtils {

    protected SpigotPlugin plugin;

    public SpigotServerUtils(){
        this.plugin = SpigotPlugin.i;
    }

    /**
     * Send a player to another server
     * @param player the player
     * @param server the server
     */
    public void sendToServer(Player player, String server){
        if(!this.plugin.getPlugin().getServer().getMessenger().isOutgoingChannelRegistered(this.plugin.getPlugin(), "BungeeCord")){
            this.plugin.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(this.plugin.getPlugin(), "BungeeCord");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try{
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        player.sendPluginMessage(this.plugin.getPlugin(), "BungeeCord", byteArrayOutputStream.toByteArray());
    }
}
