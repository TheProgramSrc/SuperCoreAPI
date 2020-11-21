package xyz.theprogramsrc.supercoreapi.spigot.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotConsole;

import java.util.ArrayList;
import java.util.List;

public abstract class SpigotCommand extends SpigotModule {

    /**
     * Creates a new command for Spigot and registers it
     */
    public SpigotCommand() {
        BukkitCommand command = new BukkitCommand(this.getCommand(), "", "/", Utils.toList(this.getAliases())) {
            @Override
            public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                CommandResult result;
                if (sender instanceof Player) {
                    Player player = ((Player) sender);
                    if(SpigotCommand.this.getPermission() != null && !SpigotCommand.this.getPermission().isEmpty()){
                        if(!player.hasPermission(SpigotCommand.this.getPermission())){
                            result = CommandResult.NO_PERMISSION;
                        }else{
                            result = SpigotCommand.this.onPlayerExecute(player, args);
                        }
                    }else{
                        result = SpigotCommand.this.onPlayerExecute(player, args);
                    }
                } else {
                    result = SpigotCommand.this.onConsoleExecute(new SpigotConsole(), args);
                }
                SpigotCommand.this.onResult(sender, result);
                return true;
            }

            @Override
            public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
                if(sender instanceof Player){
                    List<String> complete = SpigotCommand.this.getCommandComplete(((Player) sender), alias, args);
                    return complete != null ? complete : new ArrayList<>();
                }else{
                    return super.tabComplete(sender, alias, args);
                }
            }

            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) throws IllegalArgumentException {
                return this.tabComplete(sender, alias, args);
            }
        };
        command.setPermission(this.getPermission());
        this.spigotPlugin.getSuperUtils().registerBukkitCommand(command);
    }

    private void onResult(CommandSender sender, CommandResult result){
        if(result == CommandResult.NO_PERMISSION){
            sender.sendMessage(this.spigotPlugin.getSuperUtils().color(Base.NO_PERMISSION.toString()));
        }else if(result == CommandResult.NO_ACCESS){
            sender.sendMessage(this.spigotPlugin.getSuperUtils().color(Base.NO_ACCESS.toString()));
        }else if(result == CommandResult.NOT_SUPPORTED){
            if(sender instanceof Player){
                sender.sendMessage(this.spigotPlugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.CONSOLE.toString()).toString()));
            }else{
                sender.sendMessage(this.spigotPlugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.PLAYERS.toString()).toString()));
            }
        }else if(result == CommandResult.INVALID_ARGS){
            sender.sendMessage(this.spigotPlugin.getSuperUtils().color(Base.INVALID_ARGUMENTS.toString()));
        }
    }

    /**
     * Gets the command
     * @return the command
     */
    public abstract String getCommand();

    /**
     * Gets the permission to use the command
     * @return the permission
     */
    public String getPermission(){
        return null;
    }

    /**
     * Gets all the available aliases
     * @return the aliases
     */
    public String[] getAliases(){
        return new String[0];
    }

    /**
     * Executed when a player executes the command
     * @param player Who execute the command
     * @param args Arguments of the command
     * @return The CommandResult
     */
    public abstract CommandResult onPlayerExecute(Player player, String[] args);

    /**
     * Executed when the console executes the command
     * @param console The console
     * @param args the arguments of the command
     * @return The CommandResult
     */
    public abstract CommandResult onConsoleExecute(SpigotConsole console, String[] args);

    /**
     * Gets all the available command completions for a specific player
     * @param player the player who is requesting the completion
     * @param alias the alias being used
     * @param args All arguments passed to the command, split via ' '
     * @return all the available command completions for the provided player
     */
    public List<String> getCommandComplete(Player player, String alias, String[] args){
        return new ArrayList<>();
    }

}
