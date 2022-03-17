package jpp.tcrush.gamelogic.field;

public enum GameFieldItemType {
    BLUE("b" ),
    GREEN("g" ),
    RED("r"),
    YELLOW("y"),
    PURPLE("p"),
    BLACK("B"),
    ORANGE("o");

    private final String name;


    GameFieldItemType(String name){
        this.name = name;

    }

    public String getName(){return name;}



}
