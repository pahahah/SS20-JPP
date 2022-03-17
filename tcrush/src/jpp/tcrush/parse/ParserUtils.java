package jpp.tcrush.parse;

import jpp.tcrush.gamelogic.Shape;
import jpp.tcrush.gamelogic.field.*;
import jpp.tcrush.gamelogic.utils.Coordinate2D;

import java.util.*;

import static jpp.tcrush.gamelogic.Shape.*;

public class ParserUtils {

    public static Coordinate2D parseStringToCoordinate(String s){

        int x = 0;
        int y = 0;

        if(s.startsWith("(") && s.endsWith(")") && s.contains(",")){
            String strX = s.substring(s.indexOf("(") + 1, s.indexOf(","));
            String strY = s.substring(s.indexOf(",") + 1, s.indexOf(")"));

            if(strX.contains(".") || strY.contains(".")){
                throw new InputMismatchException();
            }else {
                x = Integer.parseInt(strX);
                y = Integer.parseInt(strY);
            }


        }else {
            throw new InputMismatchException();
        }


        return new Coordinate2D(x , y);

    }

    public static Shape parseStringToShape(String s){

        if(!s.contains(":")){
            throw new InputMismatchException();
        }

        int amount = Integer.parseInt(s.substring(0,s.indexOf(":")));


        String name = s.substring(s.indexOf(":") + 1);

        if(name.startsWith("(") && name.endsWith(";")){
            String[] cList = name.split(";");
            Collection<Coordinate2D> points = new ArrayList<>();

            for(String str : cList){
                points.add(parseStringToCoordinate(str));
            }
            return new Shape(points, amount);

        }else {
            switch (name){
                case "point":
                    return getPointShape(amount);
                case "sRow":
                    return getsRowShape(amount);
                case "sColumn":
                    return getsColumnShape(amount);
                case "row":
                    return getRowShape(amount);
                case "column":
                    return getColumnShape(amount);
                case "upT":
                    return getUpTShape(amount);
                case "downT":
                    return getDownTShape(amount);
                case "rightT":
                    return getRightTShape(amount);
                case "leftT":
                    return getLeftTShape(amount);
                default:
                    throw new InputMismatchException();
            }
        }


    }





    public static Map<Coordinate2D, GameFieldElement> parseStringToFieldMap(String s){


        HashMap<Coordinate2D, GameFieldElement> fieldMap = new HashMap<>();
        String[] elements = s.split("\n");

        if(elements.length <= 1 || elements[0].length() <= 1){
            throw new InputMismatchException();
        }

        for(int i = 0; i < elements.length;){
            if(elements[i].equals("")) {
                for (int j = i; j < elements.length - 1; j++) {
                    elements[j] = elements[j + 1];
                }
            }
            else {
                i++;
            }

        }

        int width = elements[0].length();


        for(int i = 0; i < elements.length; i++) {

            if (elements[i].length() != width) {

                throw new InputMismatchException("kein Rechteck");
            }
        }

        for(int x = 0; x < width; x++){
            for(int y = 0; y < elements.length; y++){
                Coordinate2D position = new Coordinate2D(x, y);

                GameFieldItem item;
                char symbol = elements[y].charAt(x);




                switch (symbol){
                    case '#':
                        fieldMap.put(position, new GameFieldElement_Block(position));
                        break;
                    case '+':

                        fieldMap.put(position, new GameFieldElement_Fallthrough(position));
                        break;
                    case 'b':
                        item = new GameFieldItem(GameFieldItemType.BLUE);
                        fieldMap.put(position,new GameFieldElement_Cell(item,position));
                        break;
                    case 'g':
                        item = new GameFieldItem(GameFieldItemType.GREEN);
                        fieldMap.put(position,new GameFieldElement_Cell(item,position));
                        break;
                    case 'r':
                        item = new GameFieldItem(GameFieldItemType.RED);
                        fieldMap.put(position,new GameFieldElement_Cell(item,position));
                        break;
                    case 'y':
                        item = new GameFieldItem(GameFieldItemType.YELLOW);
                        fieldMap.put(position,new GameFieldElement_Cell(item,position));
                        break;
                    case 'p':
                        item = new GameFieldItem(GameFieldItemType.PURPLE);
                        fieldMap.put(position,new GameFieldElement_Cell(item,position));
                        break;
                    case 'B':
                        item = new GameFieldItem(GameFieldItemType.BLACK);
                        fieldMap.put(position,new GameFieldElement_Cell(item,position));
                        break;
                    case 'o':
                        item = new GameFieldItem(GameFieldItemType.ORANGE);
                        fieldMap.put(position,new GameFieldElement_Cell(item,position));
                        break;
                    case 'n':
                        fieldMap.put(position,new GameFieldElement_Cell(null,position));
                        break;
                    default:
                        throw new InputMismatchException("String enthält ein unerlaubtes Symbol");
                }

            }



        }


        for(int x = 0; x < width; x++) {
            ArrayList<GameFieldElement> cbList = new ArrayList<>();
            ArrayList<GameFieldElement> cList = new ArrayList<>();
            for (int y = 0; y < elements.length; y++) {
                Coordinate2D position = new Coordinate2D(x, y);

                if(fieldMap.get(position).getType() == GameFieldElementType.CELL){
                    cList.add(fieldMap.get(position));
                }

                if(fieldMap.get(position).getType() == GameFieldElementType.CELL || fieldMap.get(position).getType() == GameFieldElementType.BLOCK){
                    cbList.add(fieldMap.get(position));
                }


            }

            if(cList.size() <= 1){
                throw new InputMismatchException();
            }
            for(int i = 0; i < cbList.size() -1 ; i++){
                if(cbList.get(i).getType() == GameFieldElementType.CELL){
                    if(cbList.get(i + 1).getType() != GameFieldElementType.BLOCK){
                        cbList.get(i).setSuccessor(cbList.get(i + 1));
                    }
                }


            }
            for(int i = cbList.size() - 1; i > 0; i--){
                if(cbList.get(i).getType() == GameFieldElementType.CELL){
                    if(cbList.get(i - 1).getType() != GameFieldElementType.BLOCK){
                        cbList.get(i).setPredecessor(cbList.get(i - 1));
                    }
                }


            }




        }
        return fieldMap;

    }
}
