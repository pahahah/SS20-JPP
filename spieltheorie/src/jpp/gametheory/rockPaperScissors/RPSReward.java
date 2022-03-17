package jpp.gametheory.rockPaperScissors;

import jpp.gametheory.generic.*;

import java.util.Set;

public class RPSReward implements IReward<RPSChoice> {

    @Override
    public int getReward(IPlayer<RPSChoice> player, IGameRound<RPSChoice> gameRound) {
        int score = 0;

        Set<IPlayer<RPSChoice>> otherPlayers = gameRound.getOtherPlayers(player);
        RPSChoice playerChoice = gameRound.getChoice(player);

        for(IPlayer<RPSChoice> otherPlayer : otherPlayers){
            score += scoreCalculator(playerChoice, gameRound.getChoice(otherPlayer));
        }
        return score;

    }

    private int scoreCalculator(RPSChoice playerChoice, RPSChoice oPChoice) {
        if(playerChoice == oPChoice){
            return 0;
        }
        switch (playerChoice){
            case ROCK:
                switch (oPChoice){
                    case PAPER:
                        return -1;
                    case SCISSORS:
                        return 2;
                }break;
            case SCISSORS:
                switch (oPChoice){
                    case PAPER:
                        return 2;
                    case ROCK:
                        return -1;
                }break;
            case PAPER:
                switch (oPChoice){
                    case ROCK:
                        return 2;
                    case SCISSORS:
                        return -1;
                }break;
        }
        return 0;
    }

}
