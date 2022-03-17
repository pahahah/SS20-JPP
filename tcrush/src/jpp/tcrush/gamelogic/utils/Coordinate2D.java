package jpp.tcrush.gamelogic.utils;

public class Coordinate2D {
    private int x;
    private int y;

    public Coordinate2D(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        }
        if(other == null){
            return false;
        }
        if(getClass() != other.getClass()){
            return false;
        }
        Coordinate2D that = (Coordinate2D) other;
        if(x != that.x){
            return false;
        }
        if(y != that.y){
            return false;
        }
        return true;
    }
    public int hashCode(){
        final int prime = 31;
        int hashCode = 1;
        hashCode = prime * hashCode + x;
        hashCode = prime * hashCode + y;
        return hashCode;
    }
}
