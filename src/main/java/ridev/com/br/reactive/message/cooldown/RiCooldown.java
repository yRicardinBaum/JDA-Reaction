package ridev.com.br.reactive.message.cooldown;

import java.util.HashMap;
import java.util.Map;

public class RiCooldown {

    private static final Map<String, RiCooldown> cooldowns = new HashMap<>();
    private final int timeInSeconds;
    private final String id;
    private final String cooldownName;
    private long start;

    public RiCooldown(String id, String cooldownName, int timeInSeconds) {
        this.id = id;
        this.cooldownName = cooldownName;
        this.timeInSeconds = timeInSeconds;
    }

    public static boolean isInCooldown(String id, String cooldownName) {
        if (getTimeLeft(id, cooldownName) >= 1) {
            return true;
        }
        stop(id, cooldownName);
        return false;
    }

    private static void stop(String id, String cooldownName) {
        RiCooldown.cooldowns.remove(id + cooldownName);
    }

    private static RiCooldown getCooldown(String id, String cooldownName) {
        return RiCooldown.cooldowns.get(id + cooldownName);
    }

    public static int getTimeLeft(String id, String cooldownName) {
        RiCooldown cooldown = getCooldown(id, cooldownName);
        int f = -1;
        if (cooldown != null) {
            long now = System.currentTimeMillis();
            long cooldownTime = cooldown.start;
            int r = (int) (now - cooldownTime) / 1000;
            f = (r - cooldown.timeInSeconds) * -1;
        }
        return f;
    }

    public static double getTimeLeftDouble(String id, String cooldownName) {
        RiCooldown cooldown = getCooldown(id, cooldownName);
        double f = -1.0;
        if (cooldown != null) {
            long now = System.currentTimeMillis();
            long cooldownTime = cooldown.start;
            double r = (now - cooldownTime) / 1000;
            f = (r - cooldown.timeInSeconds) * -1;
        }
        return f;
    }

    public void start() {
        this.start = System.currentTimeMillis();
        RiCooldown.cooldowns.put(this.id + this.cooldownName, this);
    }
}