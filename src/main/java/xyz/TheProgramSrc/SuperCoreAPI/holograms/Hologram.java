/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import xyz.TheProgramSrc.SuperCoreAPI.SuperCore;
import xyz.TheProgramSrc.SuperCoreAPI.SuperModule;
import xyz.TheProgramSrc.SuperCoreAPI.utils.Utils;

import java.util.LinkedList;

public class Hologram extends SuperModule {

    private LinkedList<String> lines;
    private LinkedList<ArmorStand> armorStands;
    private Location location;

    public Hologram(SuperCore core, Location location, LinkedList<String> lines){
        super(core, true);
        this.location = location;
        this.armorStands = new LinkedList<>();
        this.lines = lines;
        this.loadHologram();
    }

    public void loadHologram(){
        try{
            for(double i = 0.0; i < this.lines.size(); ++i){
                String line = this.lines.get((int) i);
                double y = this.location.getY() - (i / 5);
                Location loc = new Location(this.location.getWorld(), this.location.getX(), y, this.location.getZ(), this.location.getYaw(), this.location.getPitch());
                ArmorStand armorStand = ((ArmorStand) Utils.requireNonNull(loc.getWorld()).spawnEntity(loc, EntityType.ARMOR_STAND));
                armorStand.setGravity(false);
                armorStand.setArms(false);
                armorStand.setBasePlate(false);
                armorStand.setSmall(true);
                armorStand.setVisible(false);
                armorStand.setCustomName(Utils.ct(line));
                armorStand.setCustomNameVisible(true);
                this.armorStands.add(armorStand);
            }
        }catch (Exception ex){
            this.debug(ex);
        }
    }

    public void unloadHologram(){
        for(ArmorStand armorStand : this.armorStands){
            armorStand.remove();
        }
    }

    public void setLine(int index, String text){
        this.lines.set(index,text);
        this.unloadHologram();
        this.loadHologram();
    }

    public void addLine(String text){
        this.lines.add(text);
        this.unloadHologram();
        this.loadHologram();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        if(e.getRightClicked() instanceof ArmorStand){
            ArmorStand armorStand = ((ArmorStand) e.getRightClicked());
            if(!armorStand.isVisible()){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onManipulate(PlayerArmorStandManipulateEvent e){
        if(!e.getRightClicked().isVisible()){
            e.setCancelled(true);
        }
    }
}
