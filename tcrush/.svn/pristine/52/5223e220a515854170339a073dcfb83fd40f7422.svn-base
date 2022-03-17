package jpp.tcrush.gamelogic.field;

import jpp.tcrush.gamelogic.utils.Coordinate2D;
import jpp.tcrush.gamelogic.utils.Move;

import java.time.MonthDay;

public class GameFieldItem {
    private GameFieldItemType type;
    private Coordinate2D startPosition;
    private boolean onMove = false;

    public GameFieldItem(GameFieldItemType type){
        this.type = type;
    }



    public GameFieldItemType getType(){
        return type;
    }

    public void startMove(Coordinate2D startPosition){
        if(startPosition == null){
            throw new IllegalArgumentException("start position is null");
        }
        if(isOnMove()== true){
            throw new IllegalStateException("this item is already moving");
        }

        this.startPosition = startPosition;
        onMove = true;

    }

    public Move endMove(Coordinate2D endPosition){
        if(endPosition == null){
            throw new IllegalArgumentException();
        }
        if(isOnMove() == false){
            throw new IllegalStateException();
        }
        Move move = new Move(startPosition, endPosition);
        onMove = false;

        return move;
    }
    public boolean isOnMove(){
        return onMove;
    }

}
