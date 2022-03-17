package finiteStateMachine.state;

public class Transition<O> {
    private String targetState;
    private O output;

    public Transition(String targetState, O output){
        this.targetState = targetState;
        this.output = output;

    }

    public String getNextState(){
        return this.targetState;
    }


    public O getOutput() {
        return this.output;
    }

    public String toString(){
        String state;
        String output;

        if(targetState == null){
            state = "null";
        }else {
            state = targetState;
        }

        if(this.output == null){
            output = "null";
        }else {
            output = this.output.toString();
        }

        return state + "," + output;
    }
}
