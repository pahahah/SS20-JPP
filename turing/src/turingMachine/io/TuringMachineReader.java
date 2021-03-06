package turingMachine.io;

import turingMachine.TuringMachine;
import turingMachine.TuringTransitionOutput;
import turingMachine.tape.Direction;
import turingMachine.tape.MultiTapeReadWriteData;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TuringMachineReader {

    private static String[] getCommendlines (String str){
        String[] lines = str.split("\n");



        for(int i = 0; i < lines.length;){
            if(lines[i].startsWith("#") || lines[i].equals("")) {
                for (int j = i; j < lines.length -1; j++) {
                    lines[j] = lines[j + 1];

                }
                lines = Arrays.copyOf(lines, lines.length -1);
            }
            else {
                i++;
            }

        }
        return lines;
    }

    private static void addTransitions(TuringMachine<Character> machine, List<String> transitions){

        for(String s : transitions){

            int index = s.indexOf(',');
            if(index == -1){
                throw new IllegalArgumentException();
            }
            String startState = s.substring(0, index);
            s = s.substring(index + 1);

            MultiTapeReadWriteData<Character> inputData = new MultiTapeReadWriteData<>(machine.getTapeCount());
            index = s.indexOf(' ');
            if(index == -1){
                throw new IllegalArgumentException();
            }
            String input = s.substring(0, index);
            char[] charInput = input.toCharArray();
            if(charInput.length != machine.getTapeCount()){
                throw new IllegalArgumentException();
            }
            for(int i = 0; i < charInput.length; i++){
                if(charInput[i] != '_'){
                    inputData.set(i, charInput[i]);
                }else {
                    inputData.set(i, null);
                }
            }
            s = s.substring(index + 1);


            index = s.indexOf("-> ");
            if(index == -1)
                throw new IllegalArgumentException();
            s = s.substring(index+3);


            index = s.indexOf(',');
            String targetState = s.substring(0, index);
            s = s.substring(index + 1);

            MultiTapeReadWriteData<Character> outputData = new MultiTapeReadWriteData<>(machine.getTapeCount());

            index = s.indexOf(',');
            if(index == -1){
                throw new IllegalArgumentException();
            }

            String output = s.substring(0, index);
            char[] charOutput = output.toCharArray();

            if(charOutput.length != machine.getTapeCount()){
                throw new IllegalArgumentException();
            }
            for(int i = 0; i < charOutput.length; i++){
                if(charOutput[i] != '_'){
                    outputData.set(i, charOutput[i]);
                }else {
                    outputData.set(i, null);
                }
            }

            s = s.substring(index + 1);


            Direction[] directions = new Direction[machine.getTapeCount()];
            char[] chDir = s.toCharArray();
            if(chDir.length != machine.getTapeCount()){
                throw new IllegalArgumentException();
            }
            for(int i =0; i < chDir.length; i++){

                switch (chDir[i]) {
                    case 'R':
                        directions[i] = Direction.RIGHT;
                        break;
                    case 'L':
                        directions[i] = Direction.LEFT;
                        break;
                    case 'N':
                        directions[i] = Direction.NON;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }


            TuringTransitionOutput<Character> transitionOutput = new TuringTransitionOutput<>(outputData, directions);

            machine.addTransition(startState, inputData, targetState, transitionOutput);
        }
    }


    public static TuringMachine<Character> read(InputStream input) {

        Scanner s = new Scanner(input).useDelimiter("\\A");
        String inputAsString = s.hasNext() ? s.next() : "";

        String[] list = getCommendlines(inputAsString);

        int tapeCount = Integer.parseInt(list[0]);

        if(tapeCount < 1){
            throw new IllegalArgumentException();
        }

        TuringMachine<Character> turingMachine = new TuringMachine<>(tapeCount);

        for(int i = 0; i < tapeCount; i++){
            if(!list[i + 1].contains(":")){
                throw new IllegalArgumentException();
            }
            String[] band = list[i + 1].split(":");
            int position = Integer.parseInt(band[0]) - 1;
            Character[] bandArray = new Character[band[1].length()];
            for(int j = 0; j < band[1].length(); j++){
                bandArray[j] = band[1].charAt(j);
            }
            turingMachine.getTapes().getTapes().get(i).setTape(bandArray,position);
        }

        String state = list[tapeCount + 1];
        String[] states = state.split(",");

        ArrayList<String> statesArray = new ArrayList<>();
        for(String str : states){
            turingMachine.addState(str);
            statesArray.add(str);
        }

        if(!statesArray.contains(list[tapeCount + 3]) || !statesArray.contains(list[tapeCount + 2])){
            throw new IllegalArgumentException();
        }

        turingMachine.setCurrentState(list[tapeCount + 2]);

        turingMachine.getState(list[tapeCount + 3]).setAccepted(true);

        ArrayList<String> transitions = new ArrayList<>();
        for(int i = tapeCount + 4; i < list.length; i++){
            transitions.add(list[i]);
        }

        addTransitions(turingMachine, transitions);

        return turingMachine;
    }

    public static TuringMachine<Character> read(Reader input) throws IOException{

        char[] buffer = new char[4096];
        StringBuilder builder = new StringBuilder();
        int numChars;

        while ((numChars = input.read(buffer)) >=0){
            builder.append(buffer, 0, numChars);
        }


        String inputAsString = builder.toString();


        String[] list = getCommendlines(inputAsString);

        int tapeCount = Integer.parseInt(list[0]);

        if(tapeCount < 1){
            throw new IllegalArgumentException();
        }

        TuringMachine<Character> turingMachine = new TuringMachine<>(tapeCount);

        for(int i = 0; i < tapeCount; i++){
            if(!list[i + 1].contains(":")){
                throw new IllegalArgumentException();
            }
            String[] band = list[i + 1].split(":");
            int position = Integer.parseInt(band[0]) - 1;
            Character[] bandArray = new Character[band[1].length()];
            for(int j = 0; j < band[1].length(); j++){
                bandArray[j] = band[1].charAt(j);
            }
            turingMachine.getTapes().getTapes().get(i).setTape(bandArray,position);
        }

        String state = list[tapeCount + 1];
        String[] states = state.split(",");

        ArrayList<String> statesArray = new ArrayList<>();
        for(String str : states){
            turingMachine.addState(str);
            statesArray.add(str);
        }

        if(!statesArray.contains(list[tapeCount + 3]) || !statesArray.contains(list[tapeCount + 2])){
            throw new IllegalArgumentException();
        }

        turingMachine.setCurrentState(list[tapeCount + 2]);

        turingMachine.getState(list[tapeCount + 3]).setAccepted(true);

        ArrayList<String> transitions = new ArrayList<>();
        for(int i = tapeCount + 4; i < list.length; i++){
            transitions.add(list[i]);
        }

        addTransitions(turingMachine, transitions);

        return turingMachine;
    }

    public static void main(String[] args){

        FileReader fileReader = null;
        try {
            File file = new File("/Users/soohyunan/Documents/turingmachine.txt");
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileInputStream fis = null;
        try{
            fis = new FileInputStream("/Users/soohyunan/Documents/turingmachine.txt");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



        TuringMachine<Character> machine = TuringMachineReader.read(fis);
        System.out.println(machine.getTapes().toString());
        machine.run();
        System.out.println(machine.getTapes().toString());





    }


}

