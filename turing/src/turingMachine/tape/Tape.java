package turingMachine.tape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Tape <T> {

    private LinkedList<T> band = new LinkedList<>();
    private int position = 0;
    private ArrayList<TapeChangeListener<T>> listeners = new ArrayList<>();


    public Tape(){
        band.add(null);
    }

    public void setTape(T[] data, int position){
        for(int i = 0; i < data.length; i++){
            write(data[i]);
            if(i != data.length-1){
                move(Direction.RIGHT);
            }
        }
        this.position = position;
    }

    public void move(Direction direction){
        if(direction.equals(Direction.LEFT)){
            position -= 1;
        }else if(direction.equals(Direction.RIGHT)){
            position += 1;
        }

        if(position < 0){
            band.addFirst(null);
            position = 0;
        }else if(position >= band.size()){
            band.addLast(null);
            position = band.size() - 1;
        }

    }

    public T read(){
        return band.get(position);
    }

    public List<T> getContents(){
        return Collections.unmodifiableList(band);
    }

    public int getPosition(){
        return position;
    }

    public void write(T content){
        band.remove(position);
        band.add(position, content);
        for(int i = 0; i < listeners.size(); i++){
            listeners.get(i).onWrite(content);
        }
    }
    public void addListener(TapeChangeListener<T> listener){
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }
    public String getCurrent(){
        if(band.get(position) == null){
            return "_";
        }else {
            return band.get(position).toString();
        }
    }

    public String getLeft(){
        StringBuilder sb = new StringBuilder();
        String leftContent="";
        for(int i = 0; i < position; i++){

            if(band.get(i) == null){
                leftContent += "_";
            }else {
                leftContent += band.get(i).toString();
            }

        }
        while (leftContent.startsWith("_")){
            leftContent = leftContent.substring(1);
        }

        sb.append(leftContent);
        return sb.toString();
    }
    public String getRight(){
        StringBuilder sb = new StringBuilder();
        String rightContent="";
        for(int i = position + 1; i < band.size(); i++){

            if(band.get(i) == null){
                rightContent += "_";
            }else {
                rightContent += band.get(i).toString();
            }

        }

        while (rightContent.endsWith("_")){
            rightContent = rightContent.substring(0, rightContent.length()-1);
        }

        sb.append(rightContent);

        return sb.toString();

    }
    public String toString(){

        StringBuilder sb = new StringBuilder();

        String left = getLeft();
        while (left.startsWith("_")){
            left = left.substring(1);
        }
        String right = getRight();
        while (right.endsWith("_")){
            right = right.substring(0, right.length()-1);
        }

        sb.append(left + getCurrent() + right + "\n");
        for(int i = 0; i < left.length(); i++){
            sb.append(" ");
        }
        sb.append("^");

        return sb.toString();

        /*
        String cell1 = getLeft() + getCurrent() + getRight();

        while (cell1.startsWith("_")){
            cell1 = cell1.substring(1);
        }
        while(cell1.endsWith("_")){
            cell1 = cell1.substring(0, cell1.length()-1);
        }

        sb.append(cell1 + "\n");

        for(int i = 0; i < getLeft().length(); i++){
            sb.append(" ");
        }
        sb.append("^");
        for(int i = 0; i < getRight().length(); i++){
            sb.append(" ");
        }
        return sb.toString();

         */
    }
}
