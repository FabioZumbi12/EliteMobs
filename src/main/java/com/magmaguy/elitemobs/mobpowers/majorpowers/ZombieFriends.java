/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.magmaguy.elitemobs.mobpowers.majorpowers;

import com.magmaguy.elitemobs.MetadataHandler;
import com.magmaguy.elitemobs.config.ConfigValues;
import com.magmaguy.elitemobs.mobcustomizer.AggressiveEliteMobConstructor;
import com.magmaguy.elitemobs.mobcustomizer.NameHandler;
import com.magmaguy.elitemobs.powerstances.MajorPowerPowerStance;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Random;

import static com.magmaguy.elitemobs.ChatColorConverter.chatColorConverter;

/**
 * Created by MagmaGuy on 18/05/2017.
 */
public class ZombieFriends extends MajorPowers implements Listener {

    private static Random random = new Random();
    Plugin plugin = Bukkit.getPluginManager().getPlugin(MetadataHandler.ELITE_MOBS);
    String powerMetadata = MetadataHandler.ZOMBIE_FRIENDS_MD;
    private int processID;
    Configuration configuration = ConfigValues.translationConfig;

    @Override
    public void applyPowers(Entity entity) {

        MetadataHandler.registerMetadata(entity, powerMetadata, true);
        MajorPowerPowerStance majorPowerStanceMath = new MajorPowerPowerStance();
        majorPowerStanceMath.itemEffect(entity);

    }

    @Override
    public boolean existingPowers(Entity entity) {

        return entity.hasMetadata(powerMetadata);

    }

    @EventHandler
    public void onHit(EntityDamageEvent event) {

        if (event.getEntity().hasMetadata(powerMetadata) && event.getEntity() instanceof Zombie &&
                !event.getEntity().hasMetadata(MetadataHandler.ZOMBIE_FRIENDS_ACTIVATED) && random.nextDouble() < 0.5) {

            Entity entity = event.getEntity();

            MetadataHandler.registerMetadata(entity, MetadataHandler.ZOMBIE_FRIENDS_ACTIVATED, true);

            int assistMobLevel = (int) Math.floor(entity.getMetadata(MetadataHandler.ELITE_MOB_MD).get(0).asInt() / 4);

            if (assistMobLevel < 1) {

                assistMobLevel = 1;

            }

            Zombie friend1 = (Zombie) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
            Zombie friend2 = (Zombie) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);

            MetadataHandler.registerMetadata(friend1, MetadataHandler.ELITE_MOB_MD, assistMobLevel);
            MetadataHandler.registerMetadata(friend2, MetadataHandler.ELITE_MOB_MD, assistMobLevel);
            MetadataHandler.registerMetadata(friend1, MetadataHandler.CUSTOM_STACK, true);
            MetadataHandler.registerMetadata(friend2, MetadataHandler.CUSTOM_STACK, true);
            MetadataHandler.registerMetadata(friend1, MetadataHandler.CUSTOM_POWERS_MD, true);
            MetadataHandler.registerMetadata(friend2, MetadataHandler.CUSTOM_POWERS_MD, true);
            friend1.setCustomName(chatColorConverter(configuration.getString("ZombieFriends.Friend 1")));
            friend2.setCustomName(chatColorConverter(configuration.getString("ZombieFriends.Friend 2")));
            friend1.setCustomNameVisible(true);
            friend2.setCustomNameVisible(true);
            AggressiveEliteMobConstructor.constructAggressiveEliteMob(friend1);
            AggressiveEliteMobConstructor.constructAggressiveEliteMob(friend2);

            processID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

                @Override
                public void run() {

                    if (!entity.isValid() || !friend1.isValid() && !friend2.isValid()) {

                        if (friend1.isValid()) {

                            nameClearer(friend1);

                            friend1.setCustomName(chatColorConverter(configuration.getStringList("ZombieFriends.DeathMessage").
                                    get(random.nextInt(configuration.getStringList("ZombieFriends.DeathMessage")
                                            .size()))));

                        }

                        if (friend2.isValid()) {


                            nameClearer(friend1);

                            friend2.setCustomName(chatColorConverter(configuration.getStringList("ZombieFriends.DeathMessage").
                                    get(random.nextInt(configuration.getStringList("ZombieFriends.DeathMessage")
                                            .size()))));

                        }

                        Bukkit.getScheduler().cancelTask(processID);
                        return;

                    } else {

                        if (random.nextDouble() < 0.5) {

                            nameClearer(entity);

                            entity.setCustomName(chatColorConverter(configuration.getStringList("ZombieFriends.ZombieDialog").
                                    get(random.nextInt(configuration.getStringList("ZombieFriends.ZombieDialog")
                                            .size()))));

                        }

                        if (random.nextDouble() < 0.5 && friend1.isValid()) {

                            nameClearer(friend1);

                            friend1.setCustomName(chatColorConverter(configuration.getStringList("ZombieFriends.FriendDialog").
                                    get(random.nextInt(configuration.getStringList("ZombieFriends.FriendDialog")
                                            .size()))));

                        }

                        if (random.nextDouble() < 0.5 && friend2.isValid()) {

                            nameClearer(friend2);

                            friend2.setCustomName(chatColorConverter(configuration.getStringList("ZombieFriends.FriendDialog").
                                    get(random.nextInt(configuration.getStringList("ZombieFriends.FriendDialog")
                                            .size()))));

                        }

                    }

                }

            }, 20, 20 * 8);

        }

    }

    private void nameClearer(Entity entity) {

        String originalName = entity.getCustomName();

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

            @Override
            public void run() {

                if (entity.isValid()) {

                    if (entity.hasMetadata(MetadataHandler.CUSTOM_NAME)) {
                        entity.setCustomName(originalName);
                    } else {
                        entity.setCustomName(NameHandler.customAggressiveName(entity));
                    }

                }

            }

        }, 20 * 3);

    }

}
