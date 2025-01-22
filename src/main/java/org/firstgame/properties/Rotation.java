package org.firstgame.properties;

import java.io.Serializable;

public class Rotation implements Serializable {
    public static final Rotation LEFT = new Rotation(270);
    public static final Rotation UP_LEFT = new Rotation(315);
    public static final Rotation UP = new Rotation(0);
    public static final Rotation UP_RIGHT = new Rotation(45);
    public static final Rotation RIGHT = new Rotation(90);
    public static final Rotation DOWN_RIGHT = new Rotation(135);
    public static final Rotation DOWN = new Rotation(180);
    public static final Rotation DOWN_LEFT = new Rotation(225);

    private double angle;

    public Rotation(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
