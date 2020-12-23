package com.willfp.eco.util.integrations.anticheat;

import com.willfp.eco.util.plugin.AbstractEcoPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for Anticheat Integrations
 */
public class AnticheatManager {
    private static final AbstractEcoPlugin plugin = AbstractEcoPlugin.getInstance();
    private static final Set<AnticheatWrapper> anticheats = new HashSet<>();

    /**
     * Register a new anticheat
     *
     * @param anticheat The anticheat to register
     */
    public static void register(AnticheatWrapper anticheat) {
        if (anticheat instanceof Listener) {
            plugin.getEventManager().registerListener((Listener) anticheat);
        }
        anticheats.add(anticheat);
    }

    /**
     * Exempt a player from triggering anticheats
     *
     * @param player The player to exempt
     */
    public static void exemptPlayer(Player player) {
        anticheats.forEach(anticheat -> anticheat.exempt(player));
    }

    /**
     * Unexempt a player from triggering anticheats
     * This is ran a tick after it is called to ensure that there are no event timing conflicts
     *
     * @param player The player to remove the exemption
     */
    public static void unexemptPlayer(Player player) {
        plugin.getScheduler().runLater(() -> {
            anticheats.forEach(anticheat -> anticheat.unexempt(player));
        }, 1);
    }
}