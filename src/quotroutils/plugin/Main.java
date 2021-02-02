package quotroutils.plugin;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public  FileConfiguration config = this.getConfig();
	
	@Override
    public void onEnable() {
		// start up
		// reloads
		// plug in reloads
		config.addDefault("ManageWeather", true);
		config.addDefault("DecimalChanceOfRain", 0.1);
		config.addDefault("BlockMonstersInZone", true);
		config.addDefault("AirportsEnabled", true);
		config.addDefault("BlockTeleportsOutsideZone", true);
		config.addDefault("ZoneXMin", -100);
		config.addDefault("ZoneXMax", 100);
		config.addDefault("ZoneZMin", -100);
		config.addDefault("ZoneZMax", 100);
		config.addDefault("MaximumIgnoredTeleportDistance", 10);
		List<String> defaultEntitiesBlacklist = Arrays.asList("SLIME");
		config.addDefault("EntitiesBlacklist", defaultEntitiesBlacklist);
		List<String> defaultWorldsWhitelist = Arrays.asList("world");
		config.addDefault("WorldsWhitelist", defaultWorldsWhitelist);
		config.options().copyDefaults(true);
		saveConfig();
		
		if (config.getBoolean("ManageWeather")) {
			//Register the RainListener listener
			getServer().getPluginManager().registerEvents(new RainListener(this), this);
		}
		
		if (config.getBoolean("BlockMonstersInZone")) {
			//Register the RainListener listener
			getServer().getPluginManager().registerEvents(new CreatureSpawnListener(this), this);
		}
		
		if (config.getBoolean("BlockTeleportsOutsideZone")) {
			//Register the RainListener listener
			getServer().getPluginManager().registerEvents(new TeleportListener(this), this);
		}
    }
   
    @Override
    public void onDisable() {
		// shutdown
		// reloads
		// plug in reloads
       
    }
    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("onehundredpercentnastybadbad")) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.GREEN + "You have courage!");
			} else {
				sender.sendMessage("Hello server!");
			}
            
            return true;
        }
        return false;
    }
    public void Log(String message) {
    	System.out.println("[quotroutils] " + message);
    }
}
