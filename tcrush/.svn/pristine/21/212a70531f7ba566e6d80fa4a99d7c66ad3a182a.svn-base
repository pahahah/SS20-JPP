package jpp.tcrush.gamelogic.field;

import jpp.tcrush.gamelogic.utils.Coordinate2D;
import jpp.tcrush.gamelogic.utils.Move;

import java.util.Collection;
import java.util.Optional;

public class GameFieldElement_Block implements GameFieldElement {

    private Coordinate2D position;


    public GameFieldElement_Block(Coordinate2D position){
        this.position = position;
    }


    @Override
    public Optional<GameFieldItem> getItem() {
        throw new UnsupportedOperationException("Block has no item");
    }

    @Override
    public GameFieldElementType getType() {
        return GameFieldElementType.BLOCK;
    }

    @Override
    public Optional<GameFieldElement> getPredecessor() {
        throw new UnsupportedOperationException("this element is Block");
    }

    @Override
    public Optional<GameFieldElement> getSuccessor() {
        throw new UnsupportedOperationException("this element is Block");
    }

    @Override
    public Coordinate2D getPos() {
        return position;
    }

    @Override
    public void setItem(Optional<GameFieldItem> item) {
        throw new UnsupportedOperationException("this element is Block");
    }

    @Override
    public void setPredecessor(GameFieldElement field) {
        throw new UnsupportedOperationException("this element is Block");
    }

    @Override
    public void setSuccessor(GameFieldElement field) {
        throw new UnsupportedOperationException("this element is Block");
    }

    @Override
    public void update(Collection<Move> moves) {
        throw new UnsupportedOperationException("Block can not move");
    }

    @Override
    public String toString() {
        return "#";
    }
}
