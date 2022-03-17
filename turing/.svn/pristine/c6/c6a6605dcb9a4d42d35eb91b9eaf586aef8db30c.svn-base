package finiteStateMachine;

import finiteStateMachine.state.State;
import finiteStateMachine.state.Transition;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFiniteStateMachine<I,O> {
    private Map<String, State> states = new HashMap<>();

    private String currentState;


    /**
     * add new state name to the list of states
     * create new State with given name
     * @param state
     * @throws IllegalStateException if state with given name exists
     */

    public void addState(String state){

        if(states.containsKey(state)){
            throw new IllegalStateException(state + " already exists");
        }

        states.put(state, new State<I,O>(this, state));

    }

    /**
     *
     * @param state
     * @return
     */

    public State<I, O> getState(String state){


        if(!states.containsKey(state)){
            throw new IllegalStateException(state + " not exists");
        }

        State<I,O> state1 = states.get(state);

        return state1;
    }


    public void addTransition(String startState, I input, String targetState, O output) {
        if(!states.containsKey(startState) || !states.containsKey(targetState)){
            throw new IllegalStateException("start state or target state not exists");
        }

        State<I, O> state1 = getState(startState);
        state1.addTransition(input, targetState, output);

    }

    public void setCurrentState(String state){
        if(!states.containsKey(state)){
            throw new IllegalStateException(state + " not exists!");
        }

        currentState = state;


    }

    public State<I,O> getCurrentState(){
        if(currentState == null){
            throw new NullPointerException("current state is null");
        }

        return getState(currentState);
    }


    public boolean isInAcceptedState(){
        if(currentState == null){
            return false;
        }else {

            return states.get(currentState).isAccepted();
        }
    }


    /**
     *
     * @param input
     */
    public void transit(I input){

        if(currentState == null){
            throw new IllegalStateException("Current state is null");
        }

        O out = getCurrentState().transit(input);
        processOutput(out);


    }

    /** get transition bei currentState with input
     *
     * @param input
     * @return transition or null if there are no transition with input
     * @throws IllegalStateException if no currentState
     */

    public Transition<O> getTransition(I input){

        if(currentState == null){
            throw new IllegalStateException("current State is null");
        }

        Transition<O> transition = getCurrentState().getTransition(input);

        return transition;

    }


    protected abstract void processOutput(O output);


}
