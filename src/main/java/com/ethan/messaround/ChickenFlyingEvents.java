package com.ethan.messaround;

import jdk.javadoc.internal.doclint.HtmlTag;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class ChickenFlyingEvents implements Listener {

    @EventHandler
    public void onPlayerFalling(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location from = e.getFrom();

        boolean hasPassengers = p.getPassengers().size() > 0;
        if (hasPassengers){
            boolean playerFalling = from.getBlock().getType() == Material.AIR;
            boolean hasChickenPassenger = p.getPassengers().get(0).getType() == EntityType.CHICKEN;
            if (playerFalling & hasChickenPassenger){
                Vector pVel =  p.getVelocity();

                // Change player velocity
                // to fall slower
                pVel.setY(pVel.getY() * 0.9);
                p.setVelocity(pVel);

                // Add code to check for
                // jumping vs falling
                // (right now w/ chicken you'll jump 0.25 height)
            }
        }
    }

}
