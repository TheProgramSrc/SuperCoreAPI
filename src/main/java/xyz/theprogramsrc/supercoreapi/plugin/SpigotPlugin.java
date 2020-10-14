package xyz.theprogramsrc.supercoreapi.plugin;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.spigot.commands.CommandResult;
import xyz.theprogramsrc.supercoreapi.spigot.commands.SpigotCommand;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.precreated.MaterialBrowser;
import xyz.theprogramsrc.supercoreapi.spigot.utils.SpigotConsole;
import xyz.theprogramsrc.supercoreapi.spigot.utils.xseries.XMaterial;

public class SpigotPlugin extends xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin {


    @Override
    public void onPluginLoad() {

    }

    @Override
    public void onPluginEnable() {
        new Command();
    }

    @Override
    public void onPluginDisable() {

    }

    static class Command extends SpigotCommand {

        @Override
        public String getCommand() {
            return "scapi";
        }

        @Override
        public CommandResult onPlayerExecute(Player player, String[] args) {
            new MaterialBrowser(player){
                @Override
                public void onBack(ClickAction clickAction) {
                    this.close();
                }

                @Override
                public void onSelect(ClickAction clickAction, XMaterial xMaterial) {
                    clickAction.getPlayer().getInventory().addItem(xMaterial.parseItem());
                }
            }.open();
            return CommandResult.COMPLETED;
        }

        @Override
        public CommandResult onConsoleExecute(SpigotConsole console, String[] args) {
            return CommandResult.COMPLETED;
        }
    }
}
