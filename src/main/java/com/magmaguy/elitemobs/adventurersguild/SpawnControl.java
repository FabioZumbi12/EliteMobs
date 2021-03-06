package com.magmaguy.elitemobs.adventurersguild;

import com.magmaguy.elitemobs.config.AdventurersGuildConfig;
import com.magmaguy.elitemobs.config.ConfigValues;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpawnControl implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {

        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) return;
        if (!event.getLocation().getWorld().getName().equals(ConfigValues.adventurersGuildConfig.getString(AdventurersGuildConfig.GUILD_WORLD_NAME)))
            return;

        event.setCancelled(true);

    }

}
