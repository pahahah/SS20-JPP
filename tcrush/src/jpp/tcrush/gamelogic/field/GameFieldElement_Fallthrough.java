package jpp.tcrush.gamelogic.field;

import jpp.tcrush.gamelogic.utils.Coordinate2D;
import jpp.tcrush.gamelogic.utils.Move;

import java.util.Collection;
import java.util.Optional;

public class GameFieldElement_Fallthrough implements GameFieldElement {

    private Coordinate2D position;
    private GameFieldElementType elementType = GameFieldElementType.FALLTHROUGH;


    public GameFieldElement_Fallthrough(Coordinate2D position){
        this.position = position;
    }

    @Override
    public Optional<GameFieldItem> getItem() {
        throw new UnsupportedOperationException("Fallthrough has no item");
    }

    @Override
    public GameFieldElementType getType() {
        return elementType;
    }

    @Override
    public Optional<GameFieldElement> getPredecessor() {
        throw new UnsupportedOperationException("Fallthrough has no item");

    }

    @Override
    public Optional<GameFieldElement> getSuccessor() {
        throw new UnsupportedOperationException("Fallthrough has no item");

    }

    @Override
    public Coordinate2D getPos() {
        return position;
    }

    @Override
    public void setItem(Optional<GameFieldItem> item) {
        throw new UnsupportedOperationException("Fallthrough has no item");
    }

    @Override
    public void setPredecessor(GameFieldElement field) {
        throw new UnsupportedOperationException("Fallthrough has no item");
    }

    @Override
    public void setSuccessor(GameFieldElement field) {
        throw new UnsupportedOperationException("Fallthrough has no item");
    }

    @Override
    public void update(Collection<Move> moves) {

        throw new UnsupportedOperationException("Fallthrough");
    }

    @Override
    public String toString() {
        return "+";
    }
}
