package jpp.tcrush.gamelogic;

import jpp.tcrush.gamelogic.field.*;
import jpp.tcrush.gamelogic.utils.Coordinate2D;
import jpp.tcrush.gamelogic.utils.Move;

import java.util.*;

public class Level {

    private HashMap<Coordinate2D, GameFieldElement> fieldMap = new HashMap<>();
    private ArrayList<Shape> allowedShapes = new ArrayList<>();
    private int height;
    private int width;


    public Level(Map<Coordinate2D, GameFieldElement> fieldMap, Collection<Shape> allowedShapes){

        if(allowedShapes == null || fieldMap == null){
            throw new IllegalArgumentException();
        }
        if(allowedShapes.isEmpty() || fieldMap.size() < 4){
            throw new IllegalArgumentException();
        }

        this.fieldMap.putAll(fieldMap);
        this.allowedShapes.addAll(allowedShapes);
    }

    public int getHeight(){
        height = 0;
        for(Coordinate2D key : fieldMap.keySet()){
            int valY = key.getY();
            if(valY >= height){
                height = valY;
            }
        }
        height++;
        return height;
    }

    public int getWidth(){
        width = 0;
        for(Coordinate2D key : fieldMap.keySet()){
            int valX = key.getX();
            if(valX >= width){
                width = valX;
            }
        }
        width++;
        return width;
    }

    public Map<Coordinate2D, GameFieldElement> getField(){
        return Collections.unmodifiableMap(fieldMap);
    }

    public Collection<Shape> getAllowedShapes(){
        return Collections.unmodifiableCollection(allowedShapes);
    }


    public Optional<Collection<Coordinate2D>> fit(Shape shape, Coordinate2D position){
        if(shape == null || position == null){
            throw new IllegalArgumentException("shape or position is null");
        }

        if(!fieldMap.containsKey(position)){
            return Optional.empty();

        }else {
            if(fieldMap.get(position).getType() != GameFieldElementType.CELL){
                return Optional.empty();
            }

            GameFieldItemType itemType;
            if(fieldMap.get(position).getItem().isPresent()){
                itemType = fieldMap.get(position).getItem().get().getType();
            }else {
                return Optional.empty();
            }


            ArrayList<Coordinate2D> list = new ArrayList<>(shape.getPoints());
            ArrayList<Coordinate2D> list1 = new ArrayList<>();

            for (Coordinate2D coordinate2D : list) {
                int x;
                int y;
                if(coordinate2D.getY() == 0){
                    x = coordinate2D.getX() + position.getX();
                    y = coordinate2D.getY() + position.getY();
                }else {
                    x = coordinate2D.getX() + position.getX();
                    y = -coordinate2D.getY() + position.getY();
                }

                Coordinate2D shapePos = new Coordinate2D(x, y);

                if(!fieldMap.containsKey(shapePos)){
                    return Optional.empty();
                }else {
                    if(fieldMap.get(shapePos).getType() == GameFieldElementType.CELL){
                        if (fieldMap.get(shapePos).getItem().isPresent()) {
                            if (fieldMap.get(shapePos).getItem().get().getType() == itemType) {
                                list1.add(shapePos);
                            } else {
                                return Optional.empty();
                            }
                        } else {
                            return Optional.empty();
                        }
                    }
                }




            }
            return Optional.of(list1);
        }





    }

    public Collection<Move> setShapeAndUpdate(Shape shape, Coordinate2D position){
        if(fit(shape,position).isEmpty()){
            throw new IllegalArgumentException("shape ist not fit in given position");
        }

        Collection<Coordinate2D> list = fit(shape, position).get();

        if(shape == null || position == null){
            throw new IllegalArgumentException("parameter is null");
        }

        if(!allowedShapes.contains(shape)){
            throw new IllegalArgumentException("shape is not available");
        }

        ArrayList<Move> moves = new ArrayList<>();



        for(Coordinate2D c : list){
            fieldMap.get(c).setItem(Optional.empty());
        }
        for(Coordinate2D c : list){
            if(fieldMap.get(c).getPredecessor().isPresent()){
                fieldMap.get(c).getPredecessor().get().update(moves);
            }

        }

        if(allowedShapes.contains(shape)){
            int index = allowedShapes.indexOf(shape);
            Shape shape1 = allowedShapes.get(index);
            if(shape1.reduceAmount()){
                allowedShapes.set(index, shape1);
            }else {
                allowedShapes.remove(shape1);
            }
        }

        return moves;



    }

    public boolean isWon(){
        for(Coordinate2D c : fieldMap.keySet()){
            if(fieldMap.get(c).getType() == GameFieldElementType.CELL){
                if(fieldMap.get(c).getItem().isPresent()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMakeAnyMove(){

        for(Shape s : allowedShapes){
            for(Coordinate2D c : fieldMap.keySet()){
                if(fit(s, c).isPresent()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder("TCrush-LevelDefinition:\n");

        for(int i = 0; i < getHeight(); i++){
            for(int j = 0; j < getWidth(); j++){
                Coordinate2D c = new Coordinate2D(j,i);
                str.append(fieldMap.get(c).toString());
            }
            str.append("\n");
        }

        str.append("\nShapes:");
        for(Shape s : allowedShapes){
            str.append("\n");
            str.append(s.toString());
        }


        return str.toString();
    }




}
