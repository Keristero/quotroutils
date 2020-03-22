package quotroutils.plugin;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {
	
	private int xmin;
	private int xmax;
	private int zmin;
	private int zmax;
	private List<String> entityBlacklist;
	private List<String> worldsWhitelist;
	
	public CreatureSpawnListener(Main main) {
		this.xmin = main.config.getInt("ZoneXMin");
		this.xmax = main.config.getInt("ZoneXMax");
		this.zmin = main.config.getInt("ZoneZMin");
		this.zmax = main.config.getInt("ZoneZMax");
		this.entityBlacklist = main.config.getStringList("EntitiesBlacklist");
		this.worldsWhitelist = main.config.getStringList("WorldsWhitelist");
		this.worldsWhitelist.replaceAll(String::toUpperCase);
		//
		main.Log("Blocking monster spawns in region x:"+this.xmin+" to "+ this.xmax+" and z:"+this.zmin+" to "+ this.zmax+" in worlds: "+this.worldsWhitelist+". also blocking blacklisted entities: "+this.entityBlacklist);
		//
	}
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawnEvent(CreatureSpawnEvent event){
		
		
		//Get entity that server is attempting to spawn
		LivingEntity entity = event.getEntity();
		if(entity == null) {
			//For false alarms
			return;
		}
		Location location = entity.getLocation();
		
		//Check if the entity is a monster, or if it is blacklisted
		if(entity instanceof Monster|| this.entityBlacklist.contains(entity.getType().toString())) {
			
			//Check if the location's world is in the whitelist
			if(this.worldsWhitelist.contains(location.getWorld().getName().toUpperCase())){
				
				
				//Now check if the location is within the block zone
				if(location.getBlockX() >= this.xmin && location.getBlockX() <= this.xmax ) {
					if(location.getBlockZ() >= this.zmin && location.getBlockZ() <= this.zmax ) {
						
						//Block spawn
						event.setCancelled(true);
					}
				}
				
			}
		}
    }
}
