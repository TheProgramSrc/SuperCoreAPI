package xyz.theprogramsrc.supercoreapi.spigot.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class BlockUtils{

    /**
     * Gets the blocks in a specifier radius
     * @param location The central location
     * @param radius the blocks radius amount
     * @return the blocks in the specified radius
     */
    public static Block[] getBlocksInRadius(Location location, int radius) {
        ArrayList<Block> r = new ArrayList<>();

        for(double x = location.getX() - (double)radius; x <= location.getX() + (double)radius; ++x) {
            for(double y = location.getY() - (double)radius; y <= location.getY() + (double)radius; ++y) {
                for(double z = location.getZ() - (double)radius; z <= location.getZ() + (double)radius; ++z) {
                    Location loc = new Location(location.getWorld(), x, y, z);
                    r.add(loc.getBlock());
                }
            }
        }
        Block[] blocks = new Block[r.size()];
        blocks = r.toArray(blocks);
        return blocks;
    }
}
