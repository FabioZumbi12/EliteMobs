package com.magmaguy.elitemobs.api;

import com.magmaguy.elitemobs.mobconstructor.EliteMobEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EliteMobSpawnEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private Entity original;
    private EliteMobEntity elite;
    private CreatureSpawnEvent.SpawnReason reason;
    private boolean cancelled = false;

    public EliteMobSpawnEvent(Entity entity, EliteMobEntity elite, CreatureSpawnEvent.SpawnReason spawnReason){
        this.original = entity;
        this.elite = elite;
        this.reason = spawnReason;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Entity getEntity(){
        return  this.original;
    }

    public EliteMobEntity getEliteMob(){
        return this.elite;
    }

    public void setEliteMob(EliteMobEntity elite){
        this.elite = elite;
    }

    public CreatureSpawnEvent.SpawnReason getReason(){
        return this.reason;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
