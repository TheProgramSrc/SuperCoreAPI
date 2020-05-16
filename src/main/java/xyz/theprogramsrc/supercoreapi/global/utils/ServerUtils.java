/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.global.utils;

import xyz.theprogramsrc.supercoreapi.SuperPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ServerUtils {

    protected SuperPlugin<?> plugin;

    public ServerUtils(SuperPlugin<?> plugin){
        this.plugin = plugin;
    }

    public void sendToServer(org.bukkit.entity.Player player, String server){
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

    public void sendToServer(net.md_5.bungee.api.connection.ProxiedPlayer player, String server){
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

