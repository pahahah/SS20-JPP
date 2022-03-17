package jpp.tcrush.gui;

import javafx.scene.shape.Circle;
import jpp.tcrush.gamelogic.field.GameFieldElement;
import jpp.tcrush.gamelogic.utils.Coordinate2D;

public class CoordItem extends Circle {

    private int xCoord;
    private int yCoord;
    private Coordinate2D coordinate2D;
    private GameFieldElement element;

    public CoordItem(double size, Coordinate2D coordinate2D, GameFieldElement element) {
        super(size);
        this.xCoord = coordinate2D.getX();
        this.yCoord = coordinate2D.getY();
        this.coordinate2D = coordinate2D;
        this.element = element;
    }

    public Coordinate2D getPoint(){return coordinate2D;}

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public GameFieldElement getElement(){
        return element;
    }
}
