package jpp.tcrush.gamelogic.utils;

public class Move {
    private Coordinate2D from;
    private Coordinate2D to;

    public Move(Coordinate2D from, Coordinate2D to){
        if(from == null){
            throw new IllegalArgumentException("start coordination is null");
        }
        if(to == null){
            throw new IllegalArgumentException("end coordination is null");
        }

        this.from = from;
        this.to = to;
    }
    public Coordinate2D getFrom(){
        return from;
    }
    public Coordinate2D getTo(){
        return to;
    }

    @Override
    public String toString() {
        return from.toString() + " -> " + to.toString();
    }
}
