package turingMachine;

import turingMachine.tape.Direction;
import turingMachine.tape.MultiTape;
import finiteStateMachine.AbstractFiniteStateMachine;
import turingMachine.tape.MultiTapeReadWriteData;
import turingMachine.tape.Tape;

public class TuringMachine<T> extends AbstractFiniteStateMachine<MultiTapeReadWriteData<T>, TuringTransitionOutput<T>> {

    private int tapeCount;
    private MultiTape<T> tapes;

    public TuringMachine(int tapeCount){
        this.tapeCount = tapeCount;
        tapes = new MultiTape<>(tapeCount);
    }

    public void run(){
        while (!isInAcceptedState()){
            try{
                transit();
            }catch (IllegalArgumentException e){
                break;
            }
        }
    }

    public void transit(){
        if(isInAcceptedState()){
            throw new IllegalStateException();
        }
        transit(tapes.read());

    }

    public int getTapeCount(){
        return tapeCount;
    }

    public MultiTape<T> getTapes(){
        return tapes;
    }


    @Override
    public void processOutput(TuringTransitionOutput<T> output) {
        tapes.write(output.getToWrite());
        tapes.move(output.getDirections());
    }



    public static void main(String[] args){
        TuringMachine<Character> turingMachine = new TuringMachine<>(1);


        turingMachine.getTapes().getTapes().get(0).setTape(new Character[]{'a', 'b', 'c'},2);
        turingMachine.addState("s1");
        turingMachine.addState("s2");
        turingMachine.addState("s3");

        turingMachine.setCurrentState("s1");
        turingMachine.getState("s3").setAccepted(true);


        turingMachine.addTransition1(turingMachine,"s1", "s1", 'a', 'a', Direction.LEFT);
        turingMachine.addTransition1(turingMachine,"s1", "s1", 'b', 'b', Direction.LEFT);
        turingMachine.addTransition1(turingMachine,"s1", "s1", 'c', 'c', Direction.LEFT);
        turingMachine.addTransition1(turingMachine,"s1", "s2", '_', '_', Direction.RIGHT);
        turingMachine.addTransition1(turingMachine,"s2", "s2", 'a', 'b', Direction.RIGHT);
        turingMachine.addTransition1(turingMachine,"s2", "s2", 'b', 'c', Direction.RIGHT);
        turingMachine.addTransition1(turingMachine,"s2", "s2", 'c', 'd', Direction.RIGHT);
        turingMachine.addTransition1(turingMachine,"s2", "s3", '_', '_', Direction.NON);


        System.out.println(turingMachine.getCurrentState());
        System.out.println(turingMachine.getTapes().read());
        System.out.println(turingMachine.getTapes().toString());
        turingMachine.run();
        System.out.println(turingMachine.getTapes().toString());
        System.out.println(turingMachine.getCurrentState());


    }
    void addTransition1(TuringMachine<Character> machine, String startState, String targetState, Character input, Character output, Direction direction){
        MultiTapeReadWriteData<Character> inputData = new MultiTapeReadWriteData<>(1);
        inputData.set(0,input);
        MultiTapeReadWriteData<Character> outputData = new MultiTapeReadWriteData<>(1);
        outputData.set(0,output);
        TuringTransitionOutput<Character> transitionOutput = new TuringTransitionOutput<>(outputData, direction);
        machine.addTransition(startState,inputData,targetState,transitionOutput);
    }


}

