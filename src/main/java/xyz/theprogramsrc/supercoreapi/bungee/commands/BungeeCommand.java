package xyz.theprogramsrc.supercoreapi.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.theprogramsrc.supercoreapi.bungee.BungeeModule;
import xyz.theprogramsrc.supercoreapi.bungee.BungeePlugin;
import xyz.theprogramsrc.supercoreapi.bungee.utils.BungeeConsole;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;

public abstract class BungeeCommand extends BungeeModule {

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

    public abstract String getCommand();

    public String getPermission(){
        return null;
    }

    public String[] getAliases(){
        return new String[0];
    }

    public abstract CommandResult onPlayerExecute(ProxiedPlayer player, String[] args);

    public abstract CommandResult onConsoleExecute(BungeeConsole console, String[] args);
}
