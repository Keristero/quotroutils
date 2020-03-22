package quotroutils.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class RainListener implements Listener {
	
	private double decimalChance;
	
	public RainListener(Main main) {
		this.decimalChance = main.config.getDouble("DecimalChanceOfRain");
		
		main.Log("Rain will start successfully 1 in "+(1/this.decimalChance)+" times");
	}
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent event){
		if(event.toWeatherState() == true) {
			//If rain is supposed to be starting, generate a random number
			double randomNumber = Math.random();
			if(randomNumber > this.decimalChance){
				//Cancel the rain
				event.setCancelled(true);
				//System.out.println("(quotroutils) Cancelled start of rain!");
			}
		}
    }
}
