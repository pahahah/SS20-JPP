package turingMachine.tape;

import java.util.ArrayList;

public class MultiTapeReadWriteData<T> {
    private int length;
    private ArrayList<T> band = new ArrayList<>();

    public MultiTapeReadWriteData(int length){
        this.length = length;
        for(int i = 0; i < length; i++){
            band.add(null);
        }
    }

    public T get(int i){
        return band.get(i);
    }

    public void set(int i, T value){
        if(i < 0 || i >= length){
            throw new IndexOutOfBoundsException();
        }
        band.set(i, value);
    }

    public int getLength(){
        return length;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        T t;
        for(int i = 0; i < length; i++){
            t = band.get(i);
            if(t == null)
                sb.append("_");
            else
                sb.append(t.toString());
        }
        return sb.toString();
    }

    @Override

    public boolean equals(Object other){
        if(other == null){
            return false;
        }
        if(this.getClass() != other.getClass()){
            return false;
        }
        if(this == other){
            return true;
        }
        MultiTapeReadWriteData<?> that = (MultiTapeReadWriteData<?>) other;
        if(this.band == null && that.band != null){
            return false;
        }
        if(this.band.equals(that.band)){
            return true;
        }

        return false;
    }


    @Override
    public int hashCode(){
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((band == null) ? 0 : band.hashCode());

        return hashCode;
    }

}
