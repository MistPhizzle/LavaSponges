package com.mistphizzle.lavasponges;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockListener implements Listener {

	LavaSponges plugin;
	
	public BlockListener(LavaSponges plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onFlowTo(BlockFromToEvent event) {
		Block block = event.getBlock();
		if (block != null) {
			if (block.getType() == Material.STATIONARY_LAVA || block.getType() == Material.LAVA) {
				for (Block b: getBlocksAroundPoint(block.getLocation(), plugin.getConfig().getInt("radius"))) {
					if (b.getType() == Material.SPONGE) {
						event.setCancelled(true); // Cancel if there is a sponge within the radius.
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerLeftClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if (block != null) {
				if (block.getType() == Material.SPONGE) {
					block.breakNaturally();
				}
			}
		}
	}
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		if (block != null) {
			if (block.getType() == Material.SPONGE) {
				for (Block b: getBlocksAroundPoint(block.getLocation(), plugin.getConfig().getInt("radius"))) {
					if (b != null) {
						if (b.getType() == Material.LAVA || b.getType() == Material.STATIONARY_LAVA) {
							b.setType(Material.AIR);
						}
					}
				}
			}
		}
	}
	
	public static List<Block> getBlocksAroundPoint(Location location, double radius) {
		List<Block> blocks = new ArrayList<Block>();

		int xorg = location.getBlockX();
		int yorg = location.getBlockY();
		int zorg = location.getBlockZ();

		int r = (int) radius * 4;

		for (int x = xorg - r; x <= xorg + r; x++) {
			for (int y = yorg - r; y <= yorg + r; y++) {
				for (int z = zorg - r; z <= zorg + r; z++) {
					Block block = location.getWorld().getBlockAt(x, y, z);
					if (block.getLocation().distance(location) <= radius) {
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}
}
