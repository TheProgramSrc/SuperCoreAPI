package xyz.theprogramsrc.supercoreapi.spigot.gui.precreated;

import com.cryptomorin.xseries.XMaterial;

import org.bukkit.entity.Player;

import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.gui.Gui;
import xyz.theprogramsrc.supercoreapi.spigot.gui.objets.*;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;

public abstract class ConfirmationGui extends Gui{

    public ConfirmationGui(Player player, boolean automaticallyOpen){
        super(player, automaticallyOpen);
    }
    
    public ConfirmationGui(Player player) {
        super(player);
    }

    @Override
    public GuiRows getRows() {
        return GuiRows.THREE;
    }

    @Override
    public void onBuild(GuiModel model) {
        model.setButton(12, new GuiEntry(new SimpleItem(XMaterial.EMERALD_BLOCK).setDisplayName(Base.CONFIRM.toString()), this::onConfirm));
        model.setButton(14, new GuiEntry(new SimpleItem(XMaterial.REDSTONE_BLOCK).setDisplayName(Base.DECLINE.toString()), this::onDecline));
        
    }

    public abstract void onConfirm(GuiAction action);

    public abstract void onDecline(GuiAction action);

    public abstract void onBack(GuiAction action);
    
}
