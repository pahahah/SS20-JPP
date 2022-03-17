package jpp.tcrush.gamelogic.field;


import jpp.tcrush.gamelogic.Level;
import jpp.tcrush.gamelogic.utils.Coordinate2D;
import jpp.tcrush.gamelogic.utils.Move;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public interface GameFieldElement {
    public Optional<GameFieldItem> getItem();
    public GameFieldElementType getType();
    public Optional<GameFieldElement> getPredecessor();
    public Optional<GameFieldElement> getSuccessor();
    public Coordinate2D getPos();
    public void setItem(Optional<GameFieldItem> item);
    public void setPredecessor(GameFieldElement field);
    public void setSuccessor(GameFieldElement field);
    public void update(Collection<Move> moves);

    HashMap<Coordinate2D, GameFieldItem> gameFieldItemHashMap = new HashMap<>();

    public static GameFieldElement createCell(GameFieldItem item, Coordinate2D pos) {

        if(pos == null){
            throw new IllegalArgumentException("Position is null");
        }

        gameFieldItemHashMap.put(pos,item);

        return new GameFieldElement_Cell(item, pos);
    }

    public static GameFieldElement createBlock(Coordinate2D pos){


        if(pos == null){
            throw new IllegalArgumentException("Position is null");
        }

        return new GameFieldElement_Block(pos);
    }

    public static GameFieldElement createFallthrough(Coordinate2D pos){
        if(pos == null){
            throw new IllegalArgumentException("Position is null");
        }
        return new GameFieldElement_Fallthrough(pos);
    }



}
