package de.uniwue.jpp.oscidrawing.generation.pathutils;

public class Line {

    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getStart() {
        return p1;
    }

    public Point getEnd() {
        return p2;
    }

    public double length() {
        return p1.distanceTo(p2);
    }

    public Point getPointAt(double percentage) {

        if(length()==0){
            return getStart();
        }

        double x = (getEnd().getX() - getStart().getX()) * percentage + getStart().getX();
        double y = (getEnd().getY() - getStart().getY()) * percentage + getStart().getY();

        return new Point(x, y);






    }

    @Override
    public String toString() {
        return "Line{p1=" + p1 + ", p2=" + p2 + "}";
    }
}
