package turingMachine;

import turingMachine.tape.Direction;
import turingMachine.tape.MultiTapeReadWriteData;

public class TuringTransitionOutput <T> {
    private MultiTapeReadWriteData<T> toWrite;
    private Direction[] directions;

    public TuringTransitionOutput(MultiTapeReadWriteData<T> toWrite, Direction... directions){
        if(toWrite.getLength() != directions.length){
            throw new IllegalArgumentException("amount of data and directions are not same");
        }

        this.toWrite = toWrite;
        this.directions = directions;
    }

    public MultiTapeReadWriteData<T> getToWrite(){
        return toWrite;
    }

    public Direction[] getDirections(){
        return directions;
    }

}
