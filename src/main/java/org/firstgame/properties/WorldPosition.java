package org.firstgame.properties;

import java.io.Serializable;

public class WorldPosition implements Serializable {
    private double x;
    private double y;
    public WorldPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
