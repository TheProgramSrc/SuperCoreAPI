package xyz.theprogramsrc.supercoreapi.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;
import xyz.theprogramsrc.supercoreapi.bungee.utils.BungeeConsole;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;

/**
 * A extension for easier control with commands
 */
public abstract class BungeeCommand extends BungeeModule {

    /**
     * Creates a new command for BungeeCord and registers it
     *
     * @param plugin The BungeeCord plugin
     */
    public BungeeCommand(final BungeePlugin plugin){
        super(plugin);
        getProxy().getPluginManager().registerCommand(plugin, new Command(this.getCommand(), this.getPermission(), this.getAliases()) {
            @Override
            public void execute(CommandSender sender, String[] args) {
                CommandResult result;
                if(sender instanceof ProxiedPlayer){
                    ProxiedPlayer player = ((ProxiedPlayer)sender);
                    result = BungeeCommand.this.onPlayerExecute(player, args);
                }else{
                    result = BungeeCommand.this.onConsoleExecute(new BungeeConsole(plugin), args);
                }
                BungeeCommand.this.onResult(sender, result);
            }
        });
    }

    private void onResult(CommandSender sender, CommandResult result){
        if(result == CommandResult.NO_PERMISSION){
            sender.sendMessage(new TextComponent(this.plugin.getSuperUtils().color(Base.NO_PERMISSION.toString())));
        }else if(result == CommandResult.NO_ACCESS){
            sender.sendMessage(new TextComponent(this.plugin.getSuperUtils().color(Base.NO_ACCESS.toString())));
        }else if(result == CommandResult.NOT_SUPPORTED){
            if(sender instanceof ProxiedPlayer){
                sender.sendMessage(new TextComponent(this.plugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.CONSOLE.toString()).toString())));
            }else{
                sender.sendMessage(new TextComponent(this.plugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.PLAYERS.toString()).toString())));
            }
        }else if(result == CommandResult.INVALID_ARGS){
            sender.sendMessage(new TextComponent(this.plugin.getSuperUtils().color(Base.INVALID_ARGUMENTS.toString())));
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
    public abstract CommandResult onPlayerExecute(ProxiedPlayer player, String[] args);

    /**
     * Executed when the console executes the command
     * @param console The console
     * @param args the arguments of the command
     * @return The CommandResult
     */
    public abstract CommandResult onConsoleExecute(BungeeConsole console, String[] args);
}
