package quotroutils.plugin;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {
    private int xmin;
    private int xmax;
    private int zmin;
    private int zmax;
    private int MaximumIgnoredTeleportDistance;
    private List < String > worldsWhitelist;
    private String[] w_you;
    private String[] w_cannot;
    private String[] w_teleport;
    private String[] w_to;
    private String[] w_or;
    private String[] w_from;
    private String[] w_the;
    private String[] w_outlands;

    public TeleportListener(Main main) {
        this.xmin = main.config.getInt("ZoneXMin");
        this.xmax = main.config.getInt("ZoneXMax");
        this.zmin = main.config.getInt("ZoneZMin");
        this.zmax = main.config.getInt("ZoneZMax");
        this.MaximumIgnoredTeleportDistance = main.config.getInt("MaximumIgnoredTeleportDistance");
        this.worldsWhitelist = main.config.getStringList("WorldsWhitelist");
        this.worldsWhitelist.add("WORLD_NETHER");
        this.worldsWhitelist.replaceAll(String::toUpperCase);
        System.out.println("(quotroutils) Blocking teleports in region x:" + this.xmin + " to " + this.xmax + " and z:" + this.zmin + " to " + this.zmax);
        w_you = new String[] {
            "YOYU",
            "YOU",
            "U",
            "Y'ALL",
            "YE",
            "YOUSE",
            "THEE",
            "YOU LOSERS",
            "HACKERS SUCH AS YOUR SELF",
            "THEM PEEPS",
            "DOGS",
            "CATS",
            "EVERYONE ELSE"
        };
        w_cannot = new String[] {
            "CANNOT",
            "CANT",
            "NOT",
            "NO",
            "be excluded from",
            "ARE UNABLE TO",
            "BE INCAPABLE OF",
            "SHALL NOT",
            "ARE UNLIKELY TO"
        };
        w_teleport = new String[] {
            "TELEPORT",
            "BLIP BLOOP",
            "SWOOSH",
            "transport across a disatance instantaneously",
            "JAUNTE",
            "BEAT IT",
            "ABSCOND",
            "DASH",
            "GALLOP",
            "FLIT",
            "BOLT",
            "FLEE",
            "BOAT ON BLUE ICE"
        };
        w_to = new String[] {
            "TO",
            "DIRECTED TOWARD",
            "UNTO",
            "APON",
            "UNTWORDS",
            "FACING THROUGH",
            "INTO",
            "->",
            "INSIDE",
            "CONTRA",
            "UP TILL",
            "IN TWAIN"
        };
        w_or = new String[] {
            "OR",
            "ORR",
            "ORE",
            "ON THE OTHER HAND",
            "CONVERSELY",
            "AND RESPECTIVELY",
            "IN TURN",
            "WIT",
            "OPPOSITELY",
            "||"
        };
        w_from = new String[] {
            "FROM",
            "FRAM",
            "FROOM",
            "AGAINST",
            "WHENCE",
            "OFF",
            "BEGINNING AT",
            "AS FAR BACK AS",
            "POR",
            "<-"
        };
        w_the = new String[] {
            "THEE",
            "THE",
            "TH",
            "THY",
            "THA",
            "THEET",
            "AN",
            "THAT"
        };
        w_outlands = new String[] {
            "OUTLANDS!",
            "OUTSIDE",
            "MORDOR",
            "GONETOWN",
            "ANTI-IN LANDS",
            "OUTSIDE",
            "SPOOKVILLE",
            "adventure_world",
            "Place that is outside of the border",
            "MARMIVILLE",
            "Toto - Africa (Official Music Video)",
            "60902583",
            "THE ABYSS",
            "TORMENT",
            "PANDEMONIUM",
            "THE BLAZES",
            "LIMBO",
            "NIGHTMARE",
            "MARMI'S MANSION"
        };
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location locationA = event.getFrom();
        Location locationB = event.getTo();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();
        String teleportCauseString = cause.toString();

        List < Location > locations = Arrays.asList(new Location[] {
            locationA,
            locationB
        });

        for (Location location: locations) {
            if (((teleportCauseString == "COMMAND") || (teleportCauseString == "PLUGIN") || (teleportCauseString == "UNKNOWN")) && (worldsWhitelist.contains(location.getWorld().getName().toUpperCase()))) {
                if ((location.getBlockX() <= xmin) || (location.getBlockX() >= xmax) || (location.getBlockZ() <= zmin) || (location.getBlockZ() >= zmax)) {
                    if (locationA.distance(locationB) >= this.MaximumIgnoredTeleportDistance) {
                        event.setCancelled(true);
                        player.sendMessage(rw(w_you) + " " + rw(w_cannot) + " " + rw(w_teleport) + " " + rw(w_to) + " " + rw(w_or) + " " + rw(w_from) + " " + rw(w_the) + " " + rw(w_outlands) + " ");
                        return;
                    }
                }
            }
        }
    }

    private String rw(String[] arr) {
        return arr[new java.util.Random().nextInt(arr.length)];
    }
}