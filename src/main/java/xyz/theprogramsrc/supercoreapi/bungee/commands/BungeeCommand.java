package xyz.theprogramsrc.supercoreapi.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.utils.BungeeConsole;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;

/**
 * BungeeCommand is a representation of a {@link Command Command}
 * with ease of control and customization.
 */
public abstract class BungeeCommand extends BungeeModule {

    /**
     * Create a new {@link BungeeCommand BungeeCommand}
     */
    public BungeeCommand(){
        this.debug("Registering command '" + this.getCommand() + "'");
        getProxy().getPluginManager().registerCommand(this.bungeePlugin, new Command(this.getCommand(), this.getPermission(), this.getAliases()) {
            @Override
            public void execute(CommandSender sender, String[] args) {
                CommandResult result;
                if(sender instanceof ProxiedPlayer){
                    ProxiedPlayer player = ((ProxiedPlayer) sender);
                    result = BungeeCommand.this.onPlayerExecute(player, args);
                }else{
                    result = BungeeCommand.this.onConsoleExecute(new BungeeConsole(), args);
                }
                BungeeCommand.this.onResult(sender, result);
            }
        });
    }

    private void onResult(CommandSender sender, CommandResult result){
        if(result == CommandResult.NO_PERMISSION){
            sender.sendMessage(new TextComponent(this.bungeePlugin.getSuperUtils().color(Base.NO_PERMISSION.toString())));
        }else if(result == CommandResult.NO_ACCESS){
            sender.sendMessage(new TextComponent(this.bungeePlugin.getSuperUtils().color(Base.NO_ACCESS.toString())));
        }else if(result == CommandResult.NOT_SUPPORTED){
            if(sender instanceof ProxiedPlayer){
                sender.sendMessage(new TextComponent(this.bungeePlugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.CONSOLE.toString()).toString())));
            }else{
                sender.sendMessage(new TextComponent(this.bungeePlugin.getSuperUtils().color(Base.NOT_SUPPORTED.options().vars(Base.PLAYERS.toString()).toString())));
            }
        }else if(result == CommandResult.INVALID_ARGS){
            sender.sendMessage(new TextComponent(this.bungeePlugin.getSuperUtils().color(Base.INVALID_ARGUMENTS.toString())));
        }
    }

    /**
     * Gets the command executed to run the {@link BungeeCommand BungeeCommand}
     * @return The command
     */
    public abstract String getCommand();

    /**
     * Gets the permission to use the command
     *
     * @return The permission to use the command
     * @see #getCommand()
     */
    public String getPermission(){
        return null;
    }

    /**
     * Gets all the available aliases.
     * The aliases are more commands to run the {@link BungeeCommand BungeeCommand}
     *
     * @return The aliases
     */
    public String[] getAliases(){
        return new String[0];
    }

    /**
     * Executed when a player executes the command
     *
     * @param player Who execute the command
     * @param args The arguments of the command
     * @return The {@link CommandResult Result} of the command
     */
    public abstract CommandResult onPlayerExecute(ProxiedPlayer player, String[] args);

    /**
     * Executed when the console executes the command
     *
     * @param console The console
     * @param args The arguments of the command
     * @return The {@link CommandResult Result} of the command
     */
    public abstract CommandResult onConsoleExecute(BungeeConsole console, String[] args);
}
