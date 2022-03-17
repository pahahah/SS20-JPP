package jpp.tcrush.gamelogic;

import jpp.tcrush.gamelogic.utils.Coordinate2D;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Shape {

    private Collection<Coordinate2D> points;
    private String name;
    private  int amount;

    public Shape(Collection<Coordinate2D> points, String name, int amount){
        if(points == null|| points.isEmpty() || name == null || amount <= 0){
            throw new IllegalArgumentException();
        }
        this.points = points;
        this.name = name;
        this.amount = amount;
    }
    public Shape(Collection<Coordinate2D> points, int amount){
        if(points == null|| points.isEmpty() || amount <= 0){
            throw new IllegalArgumentException();
        }
        this.points = points;
        this.amount = amount;
    }

    public Collection<Coordinate2D> getPoints(){
        return Collections.unmodifiableCollection(points);
    }
    public int getAmount(){
        return amount;
    }

    public boolean reduceAmount(){
        amount--;
        if(amount > 0){

            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }

        Shape that = (Shape) obj;

        if(points.containsAll(that.points) && that.points.containsAll(points)){
            return true;
        }
        if(points == null && that.points != null || points.size() != that.points.size()){
            return false;
        }
        return false;

    }

    @Override
    public String toString() {
        if(name != null){
            return amount + ":" + name;
        }else {
            String str = amount + ":";
            ArrayList<Coordinate2D> po = new ArrayList<>();
            po.addAll(points);
            for(int i = 0; i < po.size(); i++){
                str += po.get(i).toString() + ";";
            }
            return str;
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((points == null) ? 0 : points.hashCode());

        return hashCode;
    }


    public static Shape getPointShape(int amount){
        Coordinate2D coord = new Coordinate2D(0,0);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord);
        Shape point = new Shape(coords,"point", amount);
        return point;
    }

    public static Shape getsRowShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(-1, 0);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        return new Shape(coords, "sRow", amount);
    }

    public static Shape getsColumnShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(0, 1);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        return new Shape(coords, "sColumn", amount);
    }

    public static Shape getRowShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(-1, 0);
        Coordinate2D coord3 = new Coordinate2D(1, 0);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        coords.add(coord3);
        return new Shape(coords, "row", amount);
    }

    public static Shape getColumnShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(0, 1);
        Coordinate2D coord3 = new Coordinate2D(0, 2);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        coords.add(coord3);
        return new Shape(coords, "column", amount);
    }

    public static Shape getUpTShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(-1, 0);
        Coordinate2D coord3 = new Coordinate2D(1, 0);
        Coordinate2D coord4 = new Coordinate2D(0, 1);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        coords.add(coord3);
        coords.add(coord4);
        return new Shape(coords, "upT", amount);
    }

    public static Shape getDownTShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(-1, 1);
        Coordinate2D coord3 = new Coordinate2D(1, 1);
        Coordinate2D coord4 = new Coordinate2D(0, 1);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        coords.add(coord3);
        coords.add(coord4);
        return new Shape(coords, "downT", amount);
    }

    public static Shape getRightTShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(0, 1);
        Coordinate2D coord3 = new Coordinate2D(0, 2);
        Coordinate2D coord4 = new Coordinate2D(1, 1);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        coords.add(coord3);
        coords.add(coord4);
        return new Shape(coords, "rightT", amount);
    }
    public static Shape getLeftTShape(int amount){
        Coordinate2D coord1 = new Coordinate2D(0,0);
        Coordinate2D coord2 = new Coordinate2D(0, 1);
        Coordinate2D coord3 = new Coordinate2D(0, 2);
        Coordinate2D coord4 = new Coordinate2D(-1, 1);
        ArrayList<Coordinate2D> coords = new ArrayList<>();
        coords.add(coord1);
        coords.add(coord2);
        coords.add(coord3);
        coords.add(coord4);
        return new Shape(coords, "leftT", amount);
    }

}
