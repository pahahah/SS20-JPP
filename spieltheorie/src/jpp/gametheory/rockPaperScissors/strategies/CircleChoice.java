package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;

public class CircleChoice implements IStrategy<RPSChoice> {

    @Override
    public String name() {
        return "Circle Choice";
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        if(previousRounds.isEmpty()){
            return RPSChoice.ROCK;
        }else {
            RPSChoice choice = previousRounds.get(previousRounds.size()-1).getChoice(player);
            if(choice.equals(RPSChoice.ROCK)){
                return RPSChoice.PAPER;
            }else if(choice.equals(RPSChoice.PAPER)){
                return RPSChoice.SCISSORS;
            }else{
                return RPSChoice.ROCK;
            }
        }

    }

    @Override
    public String toString(){
        return name();
    }
}
