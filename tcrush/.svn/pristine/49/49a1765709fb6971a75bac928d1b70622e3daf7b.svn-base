package jpp.tcrush.gui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableNumberValue;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jpp.tcrush.gamelogic.Level;
import jpp.tcrush.gamelogic.Shape;
import jpp.tcrush.gamelogic.field.GameFieldElement;
import jpp.tcrush.gamelogic.field.GameFieldElementType;
import jpp.tcrush.gamelogic.field.GameFieldItem;
import jpp.tcrush.gamelogic.field.GameFieldItemType;
import jpp.tcrush.gamelogic.utils.Coordinate2D;
import jpp.tcrush.parse.LevelParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class TCrushGui extends Application {

    private File file;

    private Level currentLevel;


    private HashMap<Coordinate2D, CoordFieldElement> elementMap = new HashMap<>();
    private HashMap<Coordinate2D, CoordItem> itemMap = new HashMap<>();
    private HashMap<Shape, SampleShapes> shapeMap = new HashMap<>();
    private Shape selectedShape = null;



    private BorderPane borderPane = new BorderPane();
    private HBox topBox = new HBox();
    private HBox midBox = new HBox();
    private Pane field = new Pane();
    //private VBox botBox = new VBox();
    private ScrollPane botBox = new ScrollPane();
    private HBox shapeBox = new HBox();
    private Button btnLoad = new Button("Level laden");
    private Button btnSave = new Button("Level speichern");
    private Button btnReset = new Button("Level zurücksetzen");
    private ScrollBar sc = new ScrollBar();






    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        final Node block = (Node) borderPane;


        //----------------TopBox-------------------
        topBox.getChildren().add(btnLoad);
        btnLoad.setPrefWidth(100);
        btnLoad.setOnMouseClicked(e -> loadFile(block));

        topBox.getChildren().add(btnSave);
        btnSave.setPrefWidth(120);
        btnSave.setOnMouseClicked(e -> saveLevel(block));

        topBox.getChildren().add(btnReset);
        btnReset.setPrefWidth(150);
        btnReset.setOnMouseClicked(e -> initGame());

        topBox.setAlignment(Pos.CENTER);
        topBox.setSpacing(20);
        topBox.setPrefHeight(30);
        topBox.setStyle("-fx-background-color: silver; -fx-padding: 20;");
        borderPane.setTop(topBox);


        //-----------matchField---------------------
        midBox.getChildren().add(field);
        field.prefWidthProperty().bind(midBox.widthProperty());
        field.prefHeightProperty().bind(midBox.heightProperty());
        midBox.setAlignment(Pos.CENTER);
        midBox.setStyle("-fx-background-color: dimgray; -fx-padding: 60;");



        borderPane.setCenter(midBox);


        //----------ShapeBox---------------------

        botBox.setContent(shapeBox);
        botBox.setPrefHeight(150);
        //botBox.getChildren().add(exampleBox);

        shapeBox.setPrefHeight(130);
        shapeBox.setStyle("-fx-padding: 20, 20, 0, 20;");
        shapeBox.setAlignment(Pos.CENTER_LEFT);
        shapeBox.setSpacing(10);



/*
        sc.setMin(0);
        sc.setOrientation(Orientation.HORIZONTAL);
        sc.setMax(3);

        sc.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number old_val, Number new_val) {
                exampleBox.setLayoutX(-new_val.doubleValue());
            }
        });
        botBox.getChildren().add(sc);

 */
        //botBox.setStyle("-fx-background-color: silver;");

        borderPane.setBottom(botBox);








        Scene mainScene = new Scene(borderPane, 800, 800);
        stage.setScene(mainScene);
        stage.setTitle("TCrush");
        stage.show();




    }



    private void newShapeExamples(Level level){

        shapeMap.clear();
        shapeBox.getChildren().clear();

        for(Shape s: level.getAllowedShapes()){

            shapeMap.put(s, new SampleShapes(90,90, s));
            SampleShapes canvas = shapeMap.get(s);
            GraphicsContext gc = canvas.getGraphicsContext2D();


            gc.setFill(Color.DIMGRAY);
            gc.setStroke(Color.WHITE);

            ArrayList<Coordinate2D> sPoints = new ArrayList<>(s.getPoints());
            int minX = 0;
            int minY = 0;
            int maxX = 0;
            int maxY = 0;
            for(int i = 0; i < sPoints.size(); i++){
                if(minX >= sPoints.get(i).getX()){
                    minX = sPoints.get(i).getX();
                }
                if(maxX <= sPoints.get(i).getX()){
                    maxX = sPoints.get(i).getX();
                }
                if(minY >= sPoints.get(i).getY()){
                    minY = sPoints.get(i).getY();
                }
                if(maxY <= sPoints.get(i).getY()){
                    maxY = sPoints.get(i).getY();
                }

            }


            for(Coordinate2D c : s.getPoints()){
                double x;
                double y;
                if( (minX < -1 || maxX > 1) && maxX - minX > 1){
                    x = c.getX() * 20 + 35 - ((maxX - minX) * 10);
                }else {
                    x = c.getX() * 20 + 35;
                }
                if((minY < -1 || maxY > 1) && maxY - minY > 1){
                    if(c.getY() == 0){
                        y = c.getY() * 20 + 35 + ((maxY - minY) * 10);
                    }else {
                        y = -c.getY() * 20 + 35 + ((maxY - minY) * 10);
                    }
                }else {
                    if(c.getY() == 0){
                        y = c.getY() * 20 + 35;
                    }else {
                        y = -c.getY() * 20 + 35;
                    }

                }

                gc.fillRect(x, y, 20, 20);
                gc.strokeRect(x, y, 20,20);

            }


            String amount = "x" + s.getAmount();
            gc.setFill(Color.BLACK);
            gc.fillText(amount, 40,45);





            canvas.setPickOnBounds(true);
            canvas.setOnMouseClicked(e ->  shapeClickEvent(e));
            shapeBox.getChildren().add(canvas);
        }


    }

    private void previewEnter(MouseEvent e){
        try {

            CoordFieldElement element = (CoordFieldElement) e.getSource();
            for (Coordinate2D sc : selectedShape.getPoints()) {
                int x = sc.getX() + element.getXCoord();
                int y;
                if (sc.getY() == 0) {
                    y = sc.getY() + element.getYCoord();
                } else {
                    y = -sc.getY() + element.getYCoord();
                }
                Coordinate2D newCoordinate = new Coordinate2D(x, y);
                if (elementMap.containsKey(newCoordinate)) {
                    CoordFieldElement element1 = elementMap.get(newCoordinate);
                    element1.setFill(Color.color(1, 0, 0, 0.2));
                }

            }
        }catch (Exception exception) {
            CoordItem item = (CoordItem) e.getSource();
            for (Coordinate2D sc : selectedShape.getPoints()) {
                int x = sc.getX() + item.getXCoord();
                int y;
                if (sc.getY() == 0) {
                    y = sc.getY() + item.getYCoord();
                } else {
                    y = -sc.getY() + item.getYCoord();
                }
                Coordinate2D newCoordinate = new Coordinate2D(x, y);
                if (elementMap.containsKey(newCoordinate)) {
                    CoordFieldElement element1 = elementMap.get(newCoordinate);
                    element1.setFill(Color.color(1, 0, 0, 0.2));
                }
            }
        }
    }

    private void previewExit(MouseEvent e){
        try{
            CoordFieldElement element = (CoordFieldElement) e.getSource();
            for(Coordinate2D sc : selectedShape.getPoints()){
                int x = sc.getX() + element.getXCoord();
                int y;
                if(sc.getY() == 0){
                    y = sc.getY() + element.getYCoord();
                }else {
                    y = -sc.getY() + element.getYCoord();
                }
                Coordinate2D newCoordinate = new Coordinate2D(x,y);
                if(elementMap.containsKey(newCoordinate)) {
                    CoordFieldElement element1 = elementMap.get(newCoordinate);
                    if (element1.getElementType().equals(GameFieldElementType.CELL)) {
                        element1.setFill(Color.SILVER);
                    } else if (element1.getElementType().equals(GameFieldElementType.FALLTHROUGH)) {

                        element1.setFill(Color.DARKGRAY);

                    } else {

                        element1.setFill(Color.DIMGRAY);
                    }
                }

            }
        }catch (Exception exception){
            CoordItem item = (CoordItem) e.getSource();
            for(Coordinate2D sc : selectedShape.getPoints()){
                int x = sc.getX() + item.getXCoord();
                int y;
                if(sc.getY() == 0){
                    y = sc.getY() + item.getYCoord();
                }else {
                    y = -sc.getY() + item.getYCoord();
                }
                Coordinate2D newCoordinate = new Coordinate2D(x,y);
                if(elementMap.containsKey(newCoordinate)) {
                    CoordFieldElement element1 = elementMap.get(newCoordinate);
                    if (element1.getElementType().equals(GameFieldElementType.CELL)) {
                        element1.setFill(Color.SILVER);
                    } else if (element1.getElementType().equals(GameFieldElementType.FALLTHROUGH)) {

                        element1.setFill(Color.DARKGRAY);

                    } else {

                        element1.setFill(Color.DIMGRAY);
                    }
                }

            }
        }


    }

    private void shapeClickEvent(MouseEvent e){
        newShapeExamples(currentLevel);
        SampleShapes sampleShape = (SampleShapes) e.getSource();
        selectedShape = sampleShape.getShape();
        sampleShape.setSelected(true);
        shapeMap.replace(sampleShape.getShape(), sampleShape);
        getSampleBox();

        for(Coordinate2D c : elementMap.keySet()){
            CoordFieldElement element = elementMap.get(c);
            element.setOnMouseEntered(event -> previewEnter(event));
            element.setOnMouseExited(event -> previewExit(event));
        }
        for(Coordinate2D c : itemMap.keySet()){
            CoordItem item = itemMap.get(c);
            item.setOnMouseEntered(event -> previewEnter(event));
            item.setOnMouseExited(event -> previewExit(event));
        }

    }


    private void getSampleBox(){
        shapeBox.getChildren().clear();

        for(Shape s : currentLevel.getAllowedShapes()){
            if(s.getAmount() > 0){
                SampleShapes canvas = shapeMap.get(s);
                GraphicsContext gc = canvas.getGraphicsContext2D();
                if(shapeMap.get(s).isSelected){
                    gc.setFill(Color.color(1,0,0,0.2));
                    gc.fillRect(0,0,90,90);
                }
                gc.setFill(Color.DIMGRAY);
                gc.setStroke(Color.WHITE);


                ArrayList<Coordinate2D> sPoints = new ArrayList<>(s.getPoints());
                int minX = 0;
                int minY = 0;
                int maxX = 0;
                int maxY = 0;
                for(int i = 0; i < sPoints.size(); i++){
                    if(minX >= sPoints.get(i).getX()){
                        minX = sPoints.get(i).getX();
                    }
                    if(maxX <= sPoints.get(i).getX()){
                        maxX = sPoints.get(i).getX();
                    }
                    if(minY >= sPoints.get(i).getY()){
                        minY = sPoints.get(i).getY();
                    }
                    if(maxY <= sPoints.get(i).getY()){
                        maxY = sPoints.get(i).getY();
                    }

                }

                for(Coordinate2D c : s.getPoints()){
                    double x;
                    double y;
                    if( (minX < -1 || maxX > 1) && maxX - minX > 1){
                        x = c.getX() * 20 + 35 - ((maxX - minX) * 10);
                    }else {
                        x = c.getX() * 20 + 35;
                    }
                    if((minY < -1 || maxY > 1) && maxY - minY > 1){
                        if(c.getY() == 0){
                            y = c.getY() * 20 + 35 + ((maxY - minY) * 10);
                        }else {
                            y = -c.getY() * 20 + 35 + ((maxY - minY) * 10);
                        }
                    }else {
                        if(c.getY() == 0){
                            y = c.getY() * 20 + 35;
                        }else {
                            y = -c.getY() * 20 + 35;
                        }

                    }

                    gc.fillRect(x, y, 20, 20);
                    gc.strokeRect(x, y, 20,20);

                }

                String amount = "x" + s.getAmount();
                gc.setFill(Color.BLACK);
                gc.fillText(amount, 40,45);





                canvas.setPickOnBounds(true);
                canvas.setOnMouseClicked(e -> shapeClickEvent(e));
                shapeBox.getChildren().add(canvas);
            }


        }

    }







    private void newField(Level level){

        HashMap<Coordinate2D, GameFieldElement> fieldMap = new HashMap<>(level.getField());

        elementMap.clear();
        itemMap.clear();
        field.getChildren().clear();

        ObservableNumberValue bWidth = Bindings.min(field.heightProperty().divide(level.getHeight()), field.widthProperty().divide(level.getWidth()));
        ObservableNumberValue bHeight = Bindings.min(field.heightProperty().divide(level.getHeight()), field.widthProperty().divide(level.getWidth()));
        ObservableNumberValue xStart = Bindings.subtract(field.widthProperty(), Bindings.multiply(bWidth, level.getWidth())).divide(2);
        ObservableNumberValue yStart = Bindings.subtract(field.heightProperty(), Bindings.multiply(bHeight, level.getHeight())).divide(2);

        ObservableNumberValue bRadius = Bindings.min(field.heightProperty().divide(level.getHeight()).divide(2.5), field.widthProperty().divide(level.getWidth()).divide(2.5));
        ObservableNumberValue bRadius1 = Bindings.min(field.heightProperty().divide(level.getHeight()).divide(2), field.widthProperty().divide(level.getWidth()).divide(2));

        ObservableNumberValue cxStart = Bindings.add(xStart, bRadius1);
        ObservableNumberValue cyStart = Bindings.add(yStart, bRadius1);

        for(Coordinate2D c : fieldMap.keySet()){
            elementMap.put(c, new CoordFieldElement(50,50, c, fieldMap.get(c).getType()));
            CoordFieldElement element = elementMap.get(c);
            element.widthProperty().bind(bWidth);
            element.heightProperty().bind(bHeight);
            element.layoutXProperty().bind(Bindings.add(xStart, element.widthProperty().multiply(c.getX())));
            element.layoutYProperty().bind(Bindings.add(yStart, element.heightProperty().multiply(c.getY())));

            element.setStroke(Color.DIMGRAY);

            if (fieldMap.get(c).getType().equals(GameFieldElementType.CELL)) {

                element.setFill(Color.SILVER);

            } else if(fieldMap.get(c).getType().equals(GameFieldElementType.FALLTHROUGH)){

                element.setFill(Color.DARKGRAY);

            }else {

                element.setFill(Color.DIMGRAY);
            }


            field.getChildren().add(element);



            if(fieldMap.get(c).getType().equals(GameFieldElementType.CELL) && fieldMap.get(c).getItem().isPresent()){
                itemMap.put(c, new CoordItem(15, c, fieldMap.get(c)));
                CoordItem item = itemMap.get(c);
                item.setPickOnBounds(true);
                item.radiusProperty().bind(bRadius);
                item.layoutXProperty().bind(Bindings.add(cxStart, element.widthProperty().multiply(c.getX())));
                item.layoutYProperty().bind(Bindings.add(cyStart, element.heightProperty().multiply(c.getY())));

                item.setOnMouseClicked(e -> itemClickEvent(e));


                GameFieldItemType itemType = fieldMap.get(c).getItem().get().getType();
                switch (itemType){
                    case RED:
                        item.setFill(Color.RED);
                        break;
                    case BLUE:
                        item.setFill(Color.BLUE);
                        break;
                    case BLACK:
                        item.setFill(Color.BLACK);
                        break;
                    case GREEN:
                        item.setFill(Color.GREEN);

                        break;
                    case ORANGE:
                        item.setFill(Color.ORANGE);
                        break;
                    case PURPLE:
                        item.setFill(Color.PURPLE);
                        break;
                    case YELLOW:
                        item.setFill(Color.YELLOW);

                        break;
                }
                field.getChildren().add(item);
            }


        }





    }

    private void itemClickEvent(MouseEvent e){
        /*
        level.setShapeAndUpdate하고 새로운 item리스트 만들어서 업데이트
        새로운레벨 currentLevel에 저장.
        getField(새로운 레벨)
         */
        CoordItem cItem = (CoordItem) e.getSource();
        Coordinate2D point = cItem.getPoint();
        GameFieldElement element = cItem.getElement();
        Alert alertInvData = new Alert(Alert.AlertType.ERROR);
        alertInvData.setTitle("Invalid Item");
        alertInvData.setContentText("the Item is not fit on the shape. Try again.");

        try{

            element.update(currentLevel.setShapeAndUpdate(selectedShape, point));

        } catch (Exception exception) {
            alertInvData.showAndWait();

        }
        newShapeExamples(currentLevel);

        if(currentLevel.isWon()){

            field.getChildren().clear();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setText("Level gelöst!");
            text.prefWidth(300);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setLayoutX(field.getWidth()/2 - 150);
            text.setLayoutY(field.getHeight()/2);
            field.getChildren().add(text);

        }else if(currentLevel.canMakeAnyMove()){

            newField(currentLevel);
            getSampleBox();

        }else {

            field.getChildren().clear();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setText("Level fehlgeschlagen! Kein Zug mehr möglich!");
            text.prefWidth(300);
            text.setTextAlignment(TextAlignment.CENTER);
            text.setLayoutX(field.getWidth()/2 - 150);
            text.setLayoutY(field.getHeight()/2);
            field.getChildren().add(text);
        }


        selectedShape = null;

    }



    public void loadFile(Node block){
        FileChooser chooser = new FileChooser();

        file = chooser.showOpenDialog(block.getScene().getWindow());

        Alert alertInvData = new Alert(Alert.AlertType.ERROR);
        alertInvData.setTitle("Invalid File");
        alertInvData.setContentText("The File you tried to load is not a valid TCrush file.");
        try{

            InputStream fileStream = new FileInputStream(file);

            currentLevel = LevelParser.parseStreamToLevel(fileStream);

        } catch (Exception e) {
            if(file != null){
                alertInvData.showAndWait();
            }
        }



        newField(currentLevel);
        newShapeExamples(currentLevel);

    }

    public void saveLevel(Node block){
        FileChooser chooser = new FileChooser();

        File file = chooser.showSaveDialog(block.getScene().getWindow());

        try {
            FileOutputStream fos = new FileOutputStream(file + ".txt");
            LevelParser.writeLevelToStream(currentLevel, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initGame(){
        try{

            InputStream fileStream = new FileInputStream(file);

            currentLevel = LevelParser.parseStreamToLevel(fileStream);

        } catch (Exception e) {

        }

        newField(currentLevel);
        newShapeExamples(currentLevel);
    }


}



