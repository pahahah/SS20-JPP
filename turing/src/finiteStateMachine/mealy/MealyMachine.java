package finiteStateMachine.mealy;

import finiteStateMachine.AbstractFiniteStateMachine;

public class MealyMachine <I, O> extends AbstractFiniteStateMachine {

    public static void main(String[] args){
        MealyMachine<Integer, String> mealyMachine = new MealyMachine<>();

        mealyMachine.addState("S0");
        mealyMachine.addState("S1");
        mealyMachine.addState("S2");

        mealyMachine.addTransition("S0", 1, "S1", "0->1");
        mealyMachine.addTransition("S0", 2, "S2", "0->2");
        mealyMachine.addTransition("S1", 2, "S2", "1->2");
        mealyMachine.addTransition("S1", 1, "S1", "1->1");
        mealyMachine.addTransition("S2", 1, "S1", "2->1" );
        mealyMachine.addTransition("S2", 2, "S2", "2->2");

        mealyMachine.setCurrentState("S0");

        mealyMachine.transit(2);
        mealyMachine.transit(1);
        mealyMachine.transit(1);
        //mealyMachine.transit(0);


    }
    @Override
    protected void processOutput(Object output) {
        System.out.print(output.toString());
    }
}
