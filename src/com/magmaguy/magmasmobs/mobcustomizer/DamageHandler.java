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

package com.magmaguy.magmasmobs.mobcustomizer;

import com.magmaguy.magmasmobs.MagmasMobs;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by MagmaGuy on 18/04/2017.
 */
public class DamageHandler implements Listener{

    private MagmasMobs plugin;

    public DamageHandler(Plugin plugin) {

        this.plugin = (MagmasMobs) plugin;

    }

    public static double damageMath(double initialDamage, int superMobLevel) {

        double finalDamage = PowerFormula.PowerFormula(initialDamage,superMobLevel);

        return finalDamage;

    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        if (event.getDamager().hasMetadata("MagmasSuperMob")) {

            event.setDamage(damageMath(event.getFinalDamage(), event.getDamager().getMetadata("MagmasSuperMob").get(0).asInt()));

        } else if (event.getDamager() instanceof Fireball) {

            Fireball fireball = (Fireball) event.getDamager();

            if (fireball.getShooter() instanceof Blaze && ((Blaze) fireball.getShooter()).hasMetadata("MagmasSuperMob")) {

                event.setDamage(damageMath(event.getFinalDamage(), ((Blaze) fireball.getShooter()).getMetadata("MagmasSuperMob").get(0).asInt()));

            }

        } else if (event.getDamager() instanceof Arrow) {

            Arrow arrow = (Arrow) event.getDamager();

            if (arrow.getShooter() instanceof Skeleton && ((Skeleton) arrow.getShooter()).hasMetadata("MagmasSuperMob")) {

                event.setDamage(damageMath(event.getFinalDamage(), ((Skeleton) arrow.getShooter()).getMetadata("MagmasSuperMob").get(0).asInt()));

            } else if (arrow.getShooter() instanceof Stray && ((Stray) arrow.getShooter()).hasMetadata("MagmasSuperMob")) {

                event.setDamage(damageMath(event.getFinalDamage(), ((Stray) arrow.getShooter()).getMetadata("MagmasSuperMob").get(0).asInt()));

            } else if (arrow.getShooter() instanceof WitherSkeleton && ((WitherSkeleton) arrow.getShooter()).hasMetadata("MagmasSuperMob")) {

                event.setDamage(damageMath(event.getFinalDamage(), ((WitherSkeleton) arrow.getShooter()).getMetadata("MagmasSuperMob").get(0).asInt()));

            }

        }

    }


    @EventHandler
    public void explosionPrimeEvent(ExplosionPrimeEvent event) {

        if (event.getEntity() instanceof Creeper && event.getEntity().hasMetadata("MagmasSuperMob")) {

            event.setRadius((float) damageMath(event.getRadius(), event.getEntity().getMetadata("MagmasSuperMob").get(0).asInt()));

        }

    }

    //TODO: scale witch potion effects, maybe

}