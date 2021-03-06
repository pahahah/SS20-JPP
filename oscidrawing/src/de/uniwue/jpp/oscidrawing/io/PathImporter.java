package de.uniwue.jpp.oscidrawing.io;

import de.uniwue.jpp.oscidrawing.generation.pathutils.Point;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PathImporter {
    public static Optional<List<Point>> fromString(List<String> lines) {

        List<Point> points = new ArrayList<>();

        if(lines.isEmpty()){
            return Optional.of(points);
        }

        for(String line : lines){
            if(!line.contains(",")){
                return Optional.empty();
            }
            String[] str = line.split(",");
            if(str.length > 2){
                return Optional.empty();
            }
            double x = Double.parseDouble(str[0]);
            double y = Double.parseDouble(str[1]);
            points.add(new Point(x, y));

        }

        return Optional.of(points);
    }

    public static Optional<List<Point>> fromFile(String path) {
        String content;
        List<Point> points = new ArrayList<>();

        try{
            content = new String(Files.readAllBytes(Paths.get(path)));
        }catch (IOException e) {
            return Optional.empty();
        }
        if(content.isEmpty()){
            return Optional.of(points);
        }

        String[] lines = content.split("\n");

        for (String line : lines) {
            if(!line.contains(",")){
                return Optional.empty();
            }

            String[] str = line.split(",");

            if(str.length > 2){
                return Optional.empty();
            }
            double x = Double.parseDouble(str[0]);
            double y = Double.parseDouble(str[1]);
            points.add(new Point(x, y));

        }

        return Optional.of(points);

    }
}
