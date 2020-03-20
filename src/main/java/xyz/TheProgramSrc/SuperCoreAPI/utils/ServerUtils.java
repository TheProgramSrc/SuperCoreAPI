/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

public class ServerUtils extends SuperModule {

    public ServerUtils(SuperCore core){
        super(core);
        this.getCore().getServer().getMessenger().registerOutgoingPluginChannel(this.getCore(), "BungeeCord");
    }

    public void sendToServer(Player player, String server){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try{
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(server);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        player.sendPluginMessage(this.getCore(), "BungeeCord", byteArrayOutputStream.toByteArray());
    }

    public int getMaxPlayers(){
        return Bukkit.getMaxPlayers();
    }

    public int getOnlinePlayers(){
        return Bukkit.getOnlinePlayers().size();
    }

    public Player getRandomPlayer(){
        HashMap<Integer, Player> hashMap = new HashMap<>();
        int i = 0;
        for(Player player : Bukkit.getOnlinePlayers()){
            hashMap.put(i, player);
            ++i;
        }
        return hashMap.get(Utils.fairRoundedRandom(0, hashMap.size()));
    }
}
