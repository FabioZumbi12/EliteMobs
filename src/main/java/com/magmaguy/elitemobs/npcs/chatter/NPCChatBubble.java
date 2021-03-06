package com.magmaguy.elitemobs.npcs.chatter;

import com.magmaguy.elitemobs.ChatColorConverter;
import com.magmaguy.elitemobs.EntityTracker;
import com.magmaguy.elitemobs.MetadataHandler;
import com.magmaguy.elitemobs.npcs.NPCEntity;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NPCChatBubble {

    public NPCChatBubble(String message, NPCEntity npcEntity, Player player) {

        if (npcEntity.getVillager().hasPotionEffect(PotionEffectType.INVISIBILITY)) return;
        if (npcEntity.getIsTalking()) return;
        npcEntity.startTalkingCooldown();

        int lineCounter = 0;

        for (String substring : message.split("\\\\n")) {

            Location newLocation = npcEntity.getVillager().getEyeLocation().clone()
                    .add(player.getLocation().clone().subtract(npcEntity.getVillager().getLocation()).toVector().normalize().multiply(0.5))
                    .add(new Vector(0, -50 - (0.2 * lineCounter), 0));

            ArmorStand messageBubble = (ArmorStand) newLocation.getWorld().spawnEntity(newLocation, EntityType.ARMOR_STAND);
            EntityTracker.registerArmorStands(messageBubble);
            messageBubble.setVisible(false);
            messageBubble.setMarker(true);
            messageBubble.setCustomName(ChatColorConverter.convert(substring));
            messageBubble.setCustomNameVisible(true);
            messageBubble.setGravity(false);

            new BukkitRunnable() {
                int counter = 0;

                @Override
                public void run() {
                    if (counter > 20 * 3) {
                        messageBubble.remove();
                        cancel();
                        return;
                    }

                    if (counter == 1)
                        messageBubble.teleport(messageBubble.getLocation().add(new Vector(0, 49.2, 0)));

                    if (counter > 1)
                        messageBubble.teleport(messageBubble.getLocation().clone().add(new Vector(0, 0.01, 0)));

                    counter++;
                }
            }.runTaskTimer(MetadataHandler.PLUGIN, 0, 1);

            lineCounter++;

        }

    }

    public NPCChatBubble(List<String> message, NPCEntity npcEntity, Player player) {

        String selectedMessage = message.get(ThreadLocalRandom.current().nextInt(message.size()));

        new NPCChatBubble(selectedMessage, npcEntity, player);

    }


}
