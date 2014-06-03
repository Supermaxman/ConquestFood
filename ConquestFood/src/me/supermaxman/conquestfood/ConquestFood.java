package me.supermaxman.conquestfood;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;



public class ConquestFood extends JavaPlugin {
    public static FileConfiguration conf;
	public static ConquestFood plugin;
	public static final Logger log = Logger.getLogger("Minecraft");
	
	static HashMap<String, Integer> foods = new HashMap<String, Integer>();
	static HashMap<String, Long> combat = new HashMap<String, Long>();
	
	
	public void onEnable() {
		plugin = this;
		
		saveDefaultConfig();
		loadTitles();
        
		getServer().getPluginManager().registerEvents(new ConquestFoodListener(), plugin);
		
		log.info(getName() + " has been enabled.");
		
	}
	
	
	static void loadTitles() {
		
		try {
			plugin.reloadConfig();
			conf = plugin.getConfig();
			if (conf.isConfigurationSection("foods")) {
		           for (Map.Entry<String, Object> entry : conf.getConfigurationSection("foods").getValues(false).entrySet()) {
		        	   foods.put(entry.getKey().toUpperCase(), Integer.parseInt(conf.getString("foods." + entry.getKey())));
		           }
			}
			
		} catch (Exception e) {
			log.warning("[" + plugin.getName() + "] Titles are invalid in config.yml! Could not load the values.");
		}
	}
	
	public void onDisable() {
		
		log.info(getName() + " has been disabled.");
	}
	
}