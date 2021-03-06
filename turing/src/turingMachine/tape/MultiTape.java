package turingMachine.tape;

import java.util.ArrayList;
import java.util.List;

public class MultiTape <T> {
    private int tapeCount;
    private ArrayList<Tape<T>> tapes = new ArrayList<>();
    private MultiTapeReadWriteData<T> data;

    public MultiTape(int tapeCount){
        this.tapeCount = tapeCount;
        for(int i = 0; i < tapeCount; i++){
            tapes.add(new Tape<T>());
        }
        data = new MultiTapeReadWriteData<>(tapeCount);
    }

    public MultiTapeReadWriteData<T> read(){
        data = new MultiTapeReadWriteData<>(tapeCount);
        for(int i = 0; i < tapeCount; i++){
            data.set(i, tapes.get(i).read());
        }
        return data;
    }

    public void write(MultiTapeReadWriteData<T> values){
        if(values.getLength() != tapeCount){
            throw new IllegalArgumentException("values are too long or too short");
        }
        for(int i = 0; i < tapeCount; i++){
            tapes.get(i).write(values.get(i));
        }
    }

    public void move(Direction[] directions){
        if(directions.length != tapeCount){
            throw new IllegalArgumentException("directions too long or too short");
        }
        for(int i = 0; i < tapeCount; i++){
            tapes.get(i).move(directions[i]);
        }
    }

    public List<Tape<T>> getTapes(){
        return tapes;
    }

    public int getTapeCount(){
        return tapeCount;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tapeCount; i++){
            sb.append(tapes.get(i).toString() + "\n");
        }
        return sb.toString();
    }

}

