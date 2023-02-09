package com.ethan.messaround;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class GlidingUtils {

    public static Vector directionalVelocityChange(Location to, Location from, Vector inputVector){

        double differenceX = to.getX() - from.getX();
        double differenceZ = to.getZ() - from.getZ();
        double xSign = Math.signum(differenceX);
        double zSign = Math.signum(differenceZ);

        double vecX = inputVector.getX();
        double vecZ = inputVector.getZ();

        double newX = updateDirectionSign(xSign, vecX);
        double newZ = updateDirectionSign(zSign, vecZ);

        return new Vector(newX, newZ, inputVector.getY());
    }


    public static double updateDirectionSign(double directionSign, double axisValue){
        double newDirection = Math.abs(axisValue);

        boolean signIsNegative = directionSign < 0;
        // if negative sign return negative
        // otherwise keep as is
        if (signIsNegative){
            newDirection*=-1;
        }

        return newDirection;
    }
}
