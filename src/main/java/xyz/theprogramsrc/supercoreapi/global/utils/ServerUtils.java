package xyz.theprogramsrc.supercoreapi.global.utils;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ServerUtils {

    protected SuperPlugin<?> plugin;

    public ServerUtils(SuperPlugin<?> plugin){
        this.plugin = plugin;
    }

    /**
     * Send a player to another server
     * @param player the player
     * @param server the server
     */
    public void sendToServer(Object player, String server){
        boolean canBungee = false;
        try{
            Class.forName("net.md_5.bungee.api.connection.ProxiedPlayer");
            canBungee = true;
        }catch (ClassNotFoundException ignored){ }

        if(canBungee){
            if(player instanceof net.md_5.bungee.api.connection.ProxiedPlayer){
                this.sendToServerBungee(((net.md_5.bungee.api.connection.ProxiedPlayer)player), server);
            }else{
                this.sendToServerBukkit(((org.bukkit.entity.Player)player), server);
            }
        }else{
            if(player instanceof org.bukkit.entity.Player){
                this.sendToServerBukkit(((org.bukkit.entity.Player)player), server);
            }else{
                throw new IllegalArgumentException("The argument 'player' must be org.bukkit.entity.Player or net.md_5.bungee.api.connection.ProxiedPlayer");
            }
        }
    }

    private void sendToServerBukkit(org.bukkit.entity.Player player, String server){
        if(!((org.bukkit.plugin.java.JavaPlugin)this.plugin.getPlugin()).getServer().getMessenger().isOutgoingChannelRegistered(((org.bukkit.plugin.java.JavaPlugin)this.plugin.getPlugin()), "BungeeCord")){
            ((org.bukkit.plugin.java.JavaPlugin)this.plugin.getPlugin()).getServer().getMessenger().registerOutgoingPluginChannel(((org.bukkit.plugin.java.JavaPlugin)this.plugin.getPlugin()), "BungeeCord");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try{
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        player.sendPluginMessage(((org.bukkit.plugin.java.JavaPlugin)this.plugin.getPlugin()), "BungeeCord", byteArrayOutputStream.toByteArray());
    }

    private void sendToServerBungee(net.md_5.bungee.api.connection.ProxiedPlayer player, String server){
        ((net.md_5.bungee.api.plugin.Plugin)this.plugin).getProxy().registerChannel("BungeeCord");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try{
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        player.sendData("BungeeCord", byteArrayOutputStream.toByteArray());
    }


}

