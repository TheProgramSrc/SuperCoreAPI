package xyz.theprogramsrc.supercoreapi.global.utils.serverutils;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class BungeeServerUtils {

    private final BungeePlugin plugin;

    public BungeeServerUtils(){
        this.plugin = BungeePlugin.i;
    }

    public void sendToServer(ProxiedPlayer player, String server){
        this.plugin.getProxy().registerChannel("BungeeCord");
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
