package jpp.tcrush.gui;

import javafx.scene.shape.Rectangle;
import jpp.tcrush.gamelogic.field.GameFieldElement;
import jpp.tcrush.gamelogic.field.GameFieldElementType;
import jpp.tcrush.gamelogic.field.GameFieldItemType;
import jpp.tcrush.gamelogic.utils.Coordinate2D;

import java.lang.annotation.ElementType;

public class CoordFieldElement extends Rectangle {
    private int xCoord;
    private int yCoord;
    private GameFieldElementType elementType;

    public CoordFieldElement(double width, double height, Coordinate2D coordinate2D, GameFieldElementType elementType) {
        super(width, height);
        this.xCoord = coordinate2D.getX();
        this.yCoord = coordinate2D.getY();
        this.elementType = elementType;
    }

    public GameFieldElementType getElementType(){
        return elementType;
    }

    public int getXCoord() {
        return xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }
}
