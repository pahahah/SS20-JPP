package jpp.gametheory.rockPaperScissors.strategies;

import jpp.gametheory.generic.IGameRound;
import jpp.gametheory.generic.IPlayer;
import jpp.gametheory.generic.IReward;
import jpp.gametheory.generic.IStrategy;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import java.util.List;

public class MostSuccessful implements IStrategy<RPSChoice> {
    private IStrategy<RPSChoice> alternate;
    private IReward<RPSChoice> reward;

    public MostSuccessful(IStrategy<RPSChoice> alternate, IReward<RPSChoice> reward){
        this.alternate = alternate;
        this.reward = reward;
    }

    @Override
    public String name() {
        return "Most Successful Choice (Alternate: " + alternate.name() + ")";
    }

    @Override
    public RPSChoice getChoice(IPlayer<RPSChoice> player, List<IGameRound<RPSChoice>> previousRounds) {
        int max = 0;

        int rock = 0;
        int scissors = 0;
        int paper = 0;



        for(IGameRound<RPSChoice> previousRound : previousRounds){
            for(IPlayer<RPSChoice> p : previousRound.getPlayers()){
                RPSChoice choice = previousRound.getChoice(p);

                switch (choice){
                    case ROCK:
                        rock += reward.getReward(p, previousRound);
                        break;
                    case SCISSORS:
                        scissors += reward.getReward(p, previousRound);
                        break;
                    case PAPER:
                        paper += reward.getReward(p, previousRound);
                        break;
                }

            }
        }

        if(rock > paper && rock > scissors){
            return RPSChoice.ROCK;
        }else if(scissors > rock && scissors > paper){
            return RPSChoice.SCISSORS;
        }else if(paper > rock && paper > scissors){
            return RPSChoice.PAPER;
        }else {
            return alternate.getChoice(player,previousRounds);
        }
    }

    @Override
    public String toString(){
        return name();
    }
}
