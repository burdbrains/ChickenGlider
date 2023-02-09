package com.ethan.messaround;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;


public class ChickenFlyingEvents implements Listener {

    Map<Player, Vector> chickenVectors = new HashMap<>();

    @EventHandler
    public void onFallDamage(EntityDamageEvent e){
        boolean entityIsPlayer = e.getEntity() instanceof Player;

        if (entityIsPlayer){
            Player p = (Player) e.getEntity();

            boolean hasPassengers = p.getPassengers().size() > 0;
            if (hasPassengers){
                EntityDamageEvent.DamageCause dCause = e.getCause();
                boolean dmgByFall = dCause == EntityDamageEvent.DamageCause.FALL;

                boolean passengerIsChicken = p.getPassengers().get(0).getType() == EntityType.CHICKEN;
                if(passengerIsChicken && dmgByFall){
                    e.setCancelled(true);

                    boolean playerHasKey = chickenVectors.get(p) != null;

                    if (playerHasKey) {
                        chickenVectors.remove(p);
                    }
                }
            }
        }
    }

    //onPlayerFalling velocity multipliers
    float yMultiplier = 0.8f;
    float xzMultiplier = 2f;
    @EventHandler
    public void onPlayerFalling(PlayerMoveEvent e){
        Player p = e.getPlayer();

        Location from = e.getFrom();
        Location to = e.getTo();

        //p.sendMessage(ChatColor.AQUA + "X: " + p.getVelocity().getX() + " /  Z: " + p.getVelocity().getZ());

        // get block below the player
        from.setY(from.getY()-1);

        boolean hasPassengers = p.getPassengers().size() > 0;
        if (hasPassengers){
            boolean playerVelocityNegative = p.getVelocity().getY() < 0;

            if (playerVelocityNegative) {
                boolean blockBelowAir = from.getBlock().getType() == Material.AIR;
                boolean hasChickenPassenger = p.getPassengers().get(0).getType() == EntityType.CHICKEN;
                // Checks the above conditions and
                // if player has reached their peak Y velocity
                // with given conditions so then we can set
                // their chicken glide velocity once and it doesn't
                // update every movement event
                // (setX(getX() * multiplier^xxx))
                //
                // Y velocity never hits EXACTLY 0
                // so we have to check yVel < 0.08 && yVel > -0.08
                double yVel = p.getVelocity().getY();
                boolean reachedPeakY = yVel > -0.1 && yVel < 0.1;

                // Have to use this point as a baseline to
                // store a vector (w/ multipliers) relative
                // to given vector, then constantly update
                // the players velocity with the stored vector
                // else the players gravity/velocity will behave
                // normally

                // break up into two events
                //      1. find baseline vector
                //              * when player reaches peak
                //              * w/ chicken and air block
                //      2. constantly update vector to baseline
                //              * so falling is constant
                //              * w/ chicken and air block

                boolean playerHasKey = chickenVectors.get(p) != null;

                p.sendMessage(ChatColor.GREEN + "yVel=" + yVel);
                if (blockBelowAir && hasChickenPassenger && reachedPeakY) {

                    p.sendMessage(ChatColor.RED + "FIRED... yVel=" + yVel);

                    Vector pVel = p.getVelocity();
                    double pY = pVel.getY();
                    double pX = pVel.getX();
                    double pZ = pVel.getZ();

                    // Change player velocity
                    // to fall slower
                    pVel.setY(pY * yMultiplier);
                    pVel.setX(pX * xzMultiplier);
                    pVel.setZ(pZ * xzMultiplier);

                    chickenVectors.put(p, pVel);

                    p.setVelocity(pVel);

                    // Add code to check for
                    // jumping vs falling
                    // (right now w/ chicken you'll jump 0.25 height)
                }
                else if (blockBelowAir && hasChickenPassenger && playerHasKey)
                {
                    // uses chicken vector when gliding
                    // and updates xz vector to players inputs

                    // Gets the sign of the players attempted vectors
                    // and applies that to gliding vectors
                    // which changes vector direction according
                    // to player moves

                    // VECTORS DON'T CONTAIN THE PLAYERS DIRECTION
                    // THAT WILL HAVE TO BE DETERMINED WITH FROM & TO LOCATION

                    // direction doesn't work because if ur going in neg direction
                    // and try to move in that direction -1 * -1 = 1
                    // so you start moving in the opposite direction
                    // we need to just set new x & z velocity to the signs
                    // we retrieve, can create util function for this

                    Vector updatedVector = GlidingUtils.directionalVelocityChange(to, from, chickenVectors.get(p));

                    p.setVelocity(updatedVector);

                    chickenVectors.put(p, updatedVector);
                }
            }
        }
    }

}
