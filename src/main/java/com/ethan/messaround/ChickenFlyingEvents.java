package com.ethan.messaround;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ChickenFlyingEvents implements Listener {


    @EventHandler
    public void onFallDamage(EntityDamageEvent e){
        EntityDamageEvent.DamageCause dCause = e.getCause();

        boolean dmgByFall = dCause == EntityDamageEvent.DamageCause.FALL;


    }

    @EventHandler
    public void onPlayerFalling(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location from = e.getFrom();

        // get block below the player
        from.setY(from.getY()-1);

        boolean hasPassengers = p.getPassengers().size() > 0;
        if (hasPassengers){
            boolean playerFalling = from.getBlock().getType() == Material.AIR;
            boolean hasChickenPassenger = p.getPassengers().get(0).getType() == EntityType.CHICKEN;
            if (playerFalling & hasChickenPassenger){
                Vector pVel =  p.getVelocity();

                // Change player velocity
                // to fall slower
                pVel.setY(pVel.getY() * 0.8);
                pVel.setX(pVel.getX() * 1.1);
                pVel.setZ(pVel.getZ() * 1.1);
                p.setVelocity(pVel);

                // Add code to check for
                // jumping vs falling
                // (right now w/ chicken you'll jump 0.25 height)
            }
        }
    }

}
