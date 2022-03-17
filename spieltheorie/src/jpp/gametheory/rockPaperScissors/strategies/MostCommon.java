package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;

public class MostCommon implements IStrategy<RPSChoice> {
    IStrategy<RPSChoice> alternate;

    public MostCommon(IStrategy<RPSChoice> alternate){
        this.alternate = alternate;
    }

    @Override
    public String name() {
        return "Most Common Choice (Alternate: " + alternate.name() + ")";
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        int rock = 0;
        int paper = 0;
        int scissors = 0;

        for(IGameRound<RPSChoice> previousRound : previousRounds){
            for(RPSChoice choice : previousRound.getPlayerChoices().values()){
                switch (choice){
                    case ROCK:
                        rock++;
                        break;
                    case SCISSORS:
                        scissors++;
                        break;
                    case PAPER:
                        paper++;
                        break;
                }
            }


        }

        if(rock > paper && rock > scissors){
            return RPSChoice.ROCK;
        }else if(paper > rock && paper > scissors){
            return RPSChoice.PAPER;
        }else if(scissors > rock && scissors > paper){
            return RPSChoice.SCISSORS;
        }else {
            return alternate.getChoice(player,previousRounds);
        }

    }

    @Override
    public String toString(){
        return name();
    }
}
