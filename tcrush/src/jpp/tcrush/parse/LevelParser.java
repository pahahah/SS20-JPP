package jpp.tcrush.parse;


import jpp.tcrush.gamelogic.Level;
import jpp.tcrush.gamelogic.Shape;
import jpp.tcrush.gamelogic.field.GameFieldElement;
import jpp.tcrush.gamelogic.utils.Coordinate2D;

import java.io.*;
import java.util.*;

import static jpp.tcrush.parse.ParserUtils.parseStringToFieldMap;
import static jpp.tcrush.parse.ParserUtils.parseStringToShape;

public class LevelParser {
    private static String getFieldMap(String input){
        String[] lines = input.split("\n");
        String fieldMapStr = "";

        int index1 = Arrays.asList(lines).indexOf("TCrush-LevelDefinition:");
        int index2 = Arrays.asList(lines).indexOf("Shapes:");


        for(int i = index1 + 1; i < index2; i++){
            fieldMapStr += lines[i] + "\n";
        }

        if(!lines[index2 - 1].equals("")){
            throw new InputMismatchException();
        }


        return fieldMapStr;


    }

    private static String[] getShapes(String input){
        String[] lines = input.split("\n");
        String shapes = "";

        int index = Arrays.asList(lines).indexOf("Shapes:");

        for(int i = index + 1; i < lines.length; i++){
            shapes += lines[i] + "\n";
        }


        String[] shapesline = shapes.split("\n");

/*

        for(int i = 0; i < shapesline.length;){
            if(shapesline[i].equals("")) {
                for (int j = i; j < shapesline.length - 1; j++) {
                    shapesline[j] = shapesline[j + 1];
                }
            }
            else {
                i++;
            }

        }

 */

        return shapesline;
    }

    public static Level parseStreamToLevel(InputStream inputStream){

        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String inputAsString = s.hasNext() ? s.next() : "";

        System.out.println(inputAsString);



        if(!inputAsString.startsWith("TCrush-LevelDefinition:") || !inputAsString.contains("Shapes:")){
            throw new InputMismatchException();
        }



        ArrayList<Shape> allowedShapes = new ArrayList<>();

        HashMap<Coordinate2D, GameFieldElement> fieldMap = new HashMap<>(parseStringToFieldMap(getFieldMap(inputAsString)));


        String[] shapes = getShapes(inputAsString);
        for(int i = 0; i < shapes.length; i++){
            allowedShapes.add(parseStringToShape(shapes[i]));
        }

        return new Level(fieldMap, allowedShapes);


    }

    public static void writeLevelToStream(Level level, OutputStream outputStream){

        String levelStr = level.toString();

        try {
            byte[] content = levelStr.getBytes();

            outputStream.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
