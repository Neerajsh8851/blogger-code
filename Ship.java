package jgame.model;

import java.awt.*;

public class Ship {
    public Point[] poly;
    public int tx, ty;
    public Ship(Point[] poly, int tx, int ty) {
        this.tx = tx;
        this.ty = ty;
        this.poly = poly;
    }
}        
