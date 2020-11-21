package xyz.theprogramsrc.supercoreapi.spigot.commands.precreated;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.commands.CommandResult;
import xyz.theprogramsrc.supercoreapi.spigot.commands.SpigotCommand;
import xyz.theprogramsrc.supercoreapi.spigot.utils.ReflectionUtils;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotConsole;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;

public class SuperCoreAPICommand extends SpigotCommand {

    private final DecimalFormat format = new DecimalFormat("##.##");

    @Override
    public CommandResult onPlayerExecute(Player player, String[] args) {
        if(args.length == 0){
            return CommandResult.INVALID_ARGS;
        }else{
            if(args[0].equalsIgnoreCase("info")){
                this.sendInfo(player);
            }else if(args[0].equalsIgnoreCase("paste")){
                StringBuilder builder = new StringBuilder();
                builder.append(Utils.exceptionToString(this.plugin.getLastError())).append("\n\n");
                builder.append("Server ID: ").append(this.plugin.getPluginDataStorage().getString("stats_id"));
                try{
                    String key = Utils.uploadPaste(builder.toString());
                    if(key == null){
                        this.getSuperUtils().sendMessage(player, "&cUnable to upload paste to https://paste.theprogramsrc.xyz/");
                    }else{
                        this.getSuperUtils().sendMessage(player, "&aPaste successfully uploaded to &chttps://paste.theprogramsrc.xyz/" + key);
                        this.plugin.removeError(this.plugin.getLastErrors().size()-1);
                    }
                }catch (IOException e){
                    this.getSuperUtils().sendMessage(player, "&cUnable to upload paste to https://paste.theprogramsrc.xyz/");
                    e.printStackTrace();
                }
            }else{
                return CommandResult.INVALID_ARGS;
            }
        }
        return CommandResult.COMPLETED;
    }

    @Override
    public CommandResult onConsoleExecute(SpigotConsole console, String[] args) {
        if(args.length == 0){
            return CommandResult.INVALID_ARGS;
        }else{
            if(args[0].equalsIgnoreCase("info")){
                this.sendInfo(console.parseConsoleCommandSender());
            }else if(args[0].equalsIgnoreCase("paste")){
                StringBuilder builder = new StringBuilder();
                builder.append(Utils.exceptionToString(this.plugin.getLastError())).append("\n\n");
                builder.append("Server ID: ").append(this.plugin.getPluginDataStorage().getString("stats_id"));
                try{
                    String key = Utils.uploadPaste(builder.toString());
                    if(key == null){
                        this.log("&cUnable to upload paste to https://paste.theprogramsrc.xyz/");
                    }else{
                        this.log("&aPaste successfully uploaded to &chttps://paste.theprogramsrc.xyz/" + key);
                        this.plugin.removeError(this.plugin.getLastErrors().size()-1);
                    }
                }catch (IOException e){
                    this.log("&cUnable to upload paste to https://paste.theprogramsrc.xyz/");
                    e.printStackTrace();
                }
            }else{
                return CommandResult.INVALID_ARGS;
            }
        }
        return CommandResult.COMPLETED;
    }

    @Override
    public String getCommand() {
        return "supercoreapi";
    }

    @Override
    public String getPermission() {
        return "command.supercoreapi";
    }

    private void sendInfo(CommandSender player){
        Field tpsField = null;
        Object minecraftServer = null;
        try {
            minecraftServer = ReflectionUtils.getNMSClass("MinecraftServer").getMethod("getServer").invoke(null);
            tpsField = minecraftServer.getClass().getField("recentTps");
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            this.plugin.addError(e);
            e.printStackTrace();
        }


        String coreVersion = this.plugin.SUPER_CORE_API_VERSION,
                serverVersion = Bukkit.getVersion(),
                nmsVersion = ReflectionUtils.VERSION,
                os = System.getProperty("os.name"),
                ram = format.format(Runtime.getRuntime().maxMemory() / (1024 * 1024)) + "Mb",
                currentPlayers = Bukkit.getOnlinePlayers().size()+"",
                bungee = this.plugin.isBungeeInstance()+"",
                lastErrors = this.plugin.getLastErrors().size()+"";
        this.getSuperUtils().sendMessage(player, "&bServer information:");
        this.getSuperUtils().sendMessage(player, "&7SuperCoreAPI Version: &9" + coreVersion);
        this.getSuperUtils().sendMessage(player, "&7Server Version: &9" + serverVersion);
        this.getSuperUtils().sendMessage(player, "&7NMS Version: &9" + nmsVersion);
        this.getSuperUtils().sendMessage(player, "&7OS: &9" + os);
        this.getSuperUtils().sendMessage(player, "&7RAM: &9" + ram);
        this.getSuperUtils().sendMessage(player, "&7Players: &9" + currentPlayers);
        this.getSuperUtils().sendMessage(player, "&7Is Bungee: &9" + bungee);
        this.getSuperUtils().sendMessage(player, "&7Last Errors: &9" + lastErrors);
        if(tpsField != null){
            try{
                double[] tps = ((double[]) tpsField.get(minecraftServer));
                this.getSuperUtils().sendMessage(player, "&6TPS from last 1m, 5m, 15m: &a" + format.format(tps[0]) + ", "
                        + format.format(tps[1]) + ", " + format.format(tps[2]));
            }catch (IllegalAccessException e){
                this.plugin.addError(e);
                e.printStackTrace();
            }
        }
    }
}
