package com.ethan.messaround;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.entity.Entity;
public class PassengerEvents implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        Entity ent = e.getRightClicked();

        boolean hasPassenger = p.getPassengers().size() > 0;
        if (!hasPassenger){
            p.addPassenger(ent);
        }
    }

}
