package de.uniwue.jpp.oscidrawing.generation.pathutils;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(Point p) {
        double p_x = p.getX();
        double p_y = p.getY();

        double distanceX = this.getX() - p_x;
        double distanceY = this.getY() - p_y;

        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    @Override
    public String toString() {
        return "Point{x=" + x + ", y=" + y + "}";
    }

    public Point interpolateTo(Point p, double factor) {

        if(factor == 0){
            return this;
        }else if(factor == 1){
            return p;
        }else {

            double x = (p.getX() - this.getX()) * factor + this.getX();
            double y = (p.getY() - getY()) * factor + getY();

            return new Point(x, y);
        }


    }
}
