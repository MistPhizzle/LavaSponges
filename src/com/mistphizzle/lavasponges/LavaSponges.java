package com.mistphizzle.lavasponges;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class LavaSponges extends JavaPlugin {

	public static Plugin plugin;
	public static Logger log;
	
	public void onEnable() {
		plugin = this;
		LavaSponges.log = this.getLogger();
		
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		
		getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		
	}

}
