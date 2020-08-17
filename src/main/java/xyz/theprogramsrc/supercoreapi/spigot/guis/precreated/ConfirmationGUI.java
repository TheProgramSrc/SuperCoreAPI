package xyz.theprogramsrc.supercoreapi.spigot.guis.precreated;

import org.bukkit.entity.Player;
import xyz.theprogramsrc.supercoreapi.global.translations.Base;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUI;
import xyz.theprogramsrc.supercoreapi.spigot.guis.GUIButton;
import xyz.theprogramsrc.supercoreapi.spigot.guis.action.ClickAction;
import xyz.theprogramsrc.supercoreapi.spigot.guis.objects.GUIRows;
import xyz.theprogramsrc.supercoreapi.spigot.items.SimpleItem;
import xyz.theprogramsrc.supercoreapi.spigot.utils.xseries.XMaterial;

public abstract class ConfirmationGUI extends GUI {

    public ConfirmationGUI(Player player) {
        super(player);
    }

    @Override
    protected GUIRows getRows() {
        return GUIRows.THREE;
    }

    @Override
    protected GUIButton[] getButtons() {
        return new GUIButton[]{
                new GUIButton(12, new SimpleItem(XMaterial.EMERALD_BLOCK).setDisplayName(Base.CONFIRM.toString()), this::onConfirm),
                new GUIButton(14, new SimpleItem(XMaterial.REDSTONE_BLOCK).setDisplayName(Base.DECLINE.toString()), this::onDecline),
                new GUIButton(26, this.getPreloadedItems().getBackItem(), this::onBack)
        };
    }

    public abstract void onConfirm(ClickAction clickAction);

    public abstract void onDecline(ClickAction clickAction);

    public abstract void onBack(ClickAction clickAction);
}
