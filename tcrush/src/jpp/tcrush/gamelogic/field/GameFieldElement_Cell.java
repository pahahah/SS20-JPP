package jpp.tcrush.gamelogic.field;

import jpp.tcrush.gamelogic.utils.Coordinate2D;
import jpp.tcrush.gamelogic.utils.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;

public class GameFieldElement_Cell implements GameFieldElement {


    private Coordinate2D position;
    private GameFieldItem item;
    private GameFieldElementType elementType = GameFieldElementType.CELL;
    private GameFieldElement predecessor;
    private GameFieldElement successor;


    public GameFieldElement_Cell(GameFieldItem item, Coordinate2D pos){
        this.item = item;
        this.position = pos;
    }

    /**
     *
     * @return GameFieldItem or Optional.empty(), if there is no Item
     */
    @Override
    public Optional<GameFieldItem> getItem() {
        if(item == null){
            return Optional.empty();
        }

        return Optional.of(this.item);
    }

    @Override
    public GameFieldElementType getType() {
        return elementType;
    }

    @Override
    public Optional<GameFieldElement> getPredecessor() {
        if(predecessor == null){
            return Optional.empty();
        }
        return Optional.of(predecessor);
    }

    @Override
    public Optional<GameFieldElement> getSuccessor() {
        if(successor == null){
            return Optional.empty();
        }
        return Optional.of(successor);
    }

    @Override
    public Coordinate2D getPos() {
        return position;
    }

    @Override
    public void setItem(Optional<GameFieldItem> item) {
        if(item.equals(Optional.empty())){
            this.item = null;
        }else {
            this.item = item.get();
        }

    }

    @Override
    public void setPredecessor(GameFieldElement field) {
        if(field == null){
            throw new IllegalArgumentException("field is null");
        }
        this.predecessor = field;
    }

    @Override
    public void setSuccessor(GameFieldElement field) {
        if(field == null){
            throw new IllegalArgumentException("field is null");
        }
        this.successor = field;
    }







    @Override
    public void update(Collection<Move> moves) {

        GameFieldElement current = this;


        if (current.getItem().isPresent()){


            if(current.getSuccessor().isPresent() && current.getSuccessor().get().getItem().isEmpty()){
                if(!current.getItem().get().isOnMove()){
                    current.getItem().get().startMove(current.getPos());
                }

                current.getSuccessor().get().setItem(getItem());
                current.setItem(Optional.empty());
                current.getSuccessor().get().update(moves);


                if(current.getPredecessor().isPresent()){
                    current.getPredecessor().get().update(moves);
                }


            }else if(current.getSuccessor().isEmpty()){
                if(current.getItem().get().isOnMove()){
                    moves.add(current.getItem().get().endMove(current.getPos()));

                }
            }else if(current.getSuccessor().isPresent() && current.getSuccessor().get().getItem().isPresent()){
                if(current.getItem().get().isOnMove()){
                    moves.add(current.getItem().get().endMove(current.getPos()));

                }
            }

        }


    }

    @Override
    public String toString() {
        if(item != null){
            return this.item.getType().getName();
        }else {
            return "n";
        }
    }









}

