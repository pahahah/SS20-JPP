package finiteStateMachine.state;

import finiteStateMachine.AbstractFiniteStateMachine;

import java.util.HashMap;
import java.util.Map;

public class State<I, O> {
    private String name;
    private AbstractFiniteStateMachine<I, O> machine;
    private boolean accepted;
    private Map<I, Transition<O>> transitions = new HashMap<>();


    public State(AbstractFiniteStateMachine<I, O> machine, String name){
        this.name = name;
        this.machine = machine;
    }


    public void setAccepted(boolean accepted){
        this.accepted = accepted;
    }
    public boolean isAccepted(){
        return this.accepted;
    }

    public void addTransition(I input, String targetState, O output){

        Transition<O> transition = new Transition<>(targetState, output);

        if(transitions.containsKey(input)){
            transitions.replace(input,transition);
        }else {
            transitions.put(input, transition);
        }
    }

    public Transition<O> getTransition(I input){
        if(transitions.containsKey(input)){
            return transitions.get(input);
        }else {
            return null;
        }
    }

    public O transit(I input){
        Transition<O> transition = transitions.get(input);
        if(transition == null){
            throw new IllegalArgumentException();
        }
        machine.setCurrentState(transition.getNextState());
        return transition.getOutput();
    }

    public AbstractFiniteStateMachine<I, O> getMachine(){return this.machine;}

    public String getName(){return this.name;}

    public String toString(){
        return this.name;
    }
}
