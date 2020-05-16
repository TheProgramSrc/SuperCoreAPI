/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc (https://theprogramsrc.xyz)
 */

package xyz.theprogramsrc.supercoreapi.spigot.hologram;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import xyz.theprogramsrc.supercoreapi.global.utils.Utils;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotModule;
import xyz.theprogramsrc.supercoreapi.spigot.SpigotPlugin;

import java.util.LinkedList;

public class Hologram extends SpigotModule {

    private final LinkedList<String> lines;
    private final LinkedList<ArmorStand> armorStands;
    private final Location location;

    public Hologram(SpigotPlugin plugin, Location location, LinkedList<String> lines){
        super(plugin);
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
            ex.printStackTrace();
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