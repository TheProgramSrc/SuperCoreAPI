package xyz.theprogramsrc.supercoreapi.spigot.commands.precreated;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.commands.SpigotCommand;
import xyz.theprogramsrc.supercoreapi.spigot.utils.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public abstract class SuperCoreAPICommand extends SpigotCommand {

    private final DecimalFormat format = new DecimalFormat("##.##");
    
    protected void executeInfoCommand(CommandSender sender){
        Field tpsField = null;
        Object minecraftServer = null;
        if(Utils.hasClass(ReflectionUtils.NMS + "MinecraftServer")){
            try {
                minecraftServer = ReflectionUtils.getNMSClass("MinecraftServer").getMethod("getServer").invoke(null);
                tpsField = minecraftServer.getClass().getField("recentTps");
            } catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
                this.plugin.addError(e);
                e.printStackTrace();
            }
        }


        String serverVersion = Bukkit.getVersion(),
                nmsVersion = ReflectionUtils.VERSION,
                os = System.getProperty("os.name"),
                ram = format.format(Runtime.getRuntime().maxMemory() / (1024 * 1024)) + "Mb",
                currentPlayers = Bukkit.getOnlinePlayers().size()+"",
                bungee = this.plugin.isBungeeInstance()+"",
                lastErrors = this.plugin.getLastErrors().size()+"";
        this.getSuperUtils().sendMessage(sender, "&bServer information:");
        this.getSuperUtils().sendMessage(sender, String.format("&7%s Version: &9%s", this.plugin.getPluginName(), this.plugin.getPluginVersion()));
        this.getSuperUtils().sendMessage(sender, "&7Server Version: &9" + serverVersion);
        this.getSuperUtils().sendMessage(sender, "&7NMS Version: &9" + nmsVersion);
        this.getSuperUtils().sendMessage(sender, "&7OS: &9" + os);
        this.getSuperUtils().sendMessage(sender, "&7RAM: &9" + ram);
        this.getSuperUtils().sendMessage(sender, "&7Players: &9" + currentPlayers);
        this.getSuperUtils().sendMessage(sender, "&7Is Bungee: &9" + bungee);
        this.getSuperUtils().sendMessage(sender, "&7Last Errors: &9" + lastErrors);
        if(tpsField != null){
            try{
                double[] tpsData = ((double[]) tpsField.get(minecraftServer));
                this.getSuperUtils().sendMessage(sender, "&6TPS from last 1m, 5m, 15m: &a"
                        + format.format(tpsData[0]) + ", "
                        + format.format(tpsData[1]) + ", "
                        + format.format(tpsData[2])
                );
            }catch (IllegalAccessException e){
                this.plugin.addError(e);
                e.printStackTrace();
            }
        }
    }

    protected void executePasteCommand(CommandSender sender){
        if(!this.plugin.getLastErrors().isEmpty()){
            StringBuilder builder = new StringBuilder();
            builder.append(Utils.exceptionToString(this.plugin.getLastError())).append("\n\n");
            builder.append("Server ID: ").append(this.plugin.getPluginDataStorage().getString("stats_id"));
            try{
                String key = Utils.uploadPaste(builder.toString());
                if(key == null){
                    this.getSuperUtils().sendMessage(sender, "&cUnable to upload paste to https://paste.theprogramsrc.xyz/");
                }else{
                    this.getSuperUtils().sendMessage(sender, "&aPaste successfully uploaded to&c https://paste.theprogramsrc.xyz/" + key);
                    this.plugin.removeError(this.plugin.getLastErrors().size()-1);
                }
            }catch (IOException e){
                this.getSuperUtils().sendMessage(sender, "&cUnable to upload paste to https://paste.theprogramsrc.xyz/");
                e.printStackTrace();
            }
        }else{
            this.getSuperUtils().sendMessage(sender, "&cNo previous errors found. Try again later.");
        }
    }
}
