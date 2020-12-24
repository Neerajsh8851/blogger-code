package jgame.model;

import java.awt.*;

public class Ship {
    public Point[] poly;
    public float tx, ty;
    public float velocity = 60.0f; // px/sec

    public Ship(Point[] poly, int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        this.poly = poly;
    }
}
