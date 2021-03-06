package jpp.gametheory.generic;

import com.sun.source.tree.Tree;
import jpp.gametheory.rockPaperScissors.RPSChoice;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.InflaterInputStream;

public class Game<C extends IChoice> {
    private Set<IPlayer<C>> players;
    private IReward<C> reward;
    private List<IGameRound<C>> gameRounds = new ArrayList<>();
    private Map<IPlayer<C>, C> playerChoices;
    private Map<IPlayer<C>, Integer> playerRewards;

    public Game(Set<IPlayer<C>> players, IReward<C> reward){
        if(players == null || reward == null){
            throw new NullPointerException();
        }
        if(players.isEmpty()){
            throw new IllegalArgumentException("Player List is empty");
        }
        this.players = players;
        this.reward = reward;

        playerRewards = new HashMap<>();
        for(IPlayer<C> player : players){
            playerRewards.put(player, 0);
        }

    }

    public Set<IPlayer<C>> getPlayers(){
        return players;
    }

    public IGameRound<C> playRound(){
        playerChoices = new HashMap<>();
        for(IPlayer<C> player : players){
            playerChoices.put(player, player.getChoice(gameRounds));
        }

        GameRound<C> gameRound = new GameRound<>(playerChoices);
        gameRounds.add(gameRound);

        for(IPlayer<C> player : players){
            if(playerRewards.containsKey(player)){
                int score = playerRewards.get(player);
                playerRewards.put(player,score + reward.getReward(player,gameRound));
            }else {
                playerRewards.put(player,reward.getReward(player,gameRound));
            }

        }

        return gameRound;
    }
    public void playNRounds(int n){
        if(n < 1){
            throw new IllegalArgumentException("n is smaller than 1");
        }

        for(int i = 0; i < n; i++){
            playRound();
        }
    }

    public Optional<IGameRound<C>> undoRound(){
        if(gameRounds.isEmpty()){
            return Optional.empty();
        }else {
            IGameRound<C> gameRound = gameRounds.remove(gameRounds.size()-1);
            for(IPlayer<C> player : players){
                if(!(playerRewards.containsKey(player))){
                    playerRewards.put(player,playerRewards.get(player) - reward.getReward(player,gameRound));
                }
            }
            return Optional.of(gameRound);
        }
    }

    public void undoNRounds(int n){
        if(n < 1){
            throw new IllegalArgumentException("n is smaller than 1");
        }
        for(int i = 0; i < n; i++){
            if(gameRounds.isEmpty()){
                break;
            }
            undoRound();
        }
    }

    public List<IGameRound<C>> getPlayedRounds(){
        return Collections.unmodifiableList(gameRounds);
    }

    public int getPlayerProfit(IPlayer<C> player){

        if(player == null){
            throw new NullPointerException();
        }
        if(!(players.contains(player))){
            throw new IllegalArgumentException("This player plays not this round");
        }

        if(!(playerRewards.containsKey(player))){
            playerRewards.put(player, 0);
        }

        return playerRewards.get(player);
    }
    public Optional<IPlayer<C>> getBestPlayer(){
        int max = 0;

        List<IPlayer<C>> bestPlayer = new ArrayList<>();

        for(Integer score : playerRewards.values()){
            if(score > max){
                max = score;
            }
        }
        for(IPlayer<C> player : players){
            if(playerRewards.get(player) == max){
                bestPlayer.add(player);
            }
        }
        if(bestPlayer.size() != 1){
            return Optional.empty();
        }else{
            return Optional.of(bestPlayer.get(0));
        }
    }
    public String toString(){
        StringBuilder sb = new StringBuilder("Spiel nach " + gameRounds.size() + " Runden:\n");
        sb.append("Profit : Spieler\n");

        TreeMap<IPlayer<C>, Integer> rewardsMap = new TreeMap<>(playerRewards);

        List<Map.Entry<IPlayer<C>, Integer>> entries = rewardsMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).collect(Collectors.toList());
        for(Map.Entry<IPlayer<C>, Integer> entry : entries){
            sb.append(entry.getValue() + " : " + entry.getKey().toString() + "\n");
        }

        return sb.toString().trim();
    }
}
