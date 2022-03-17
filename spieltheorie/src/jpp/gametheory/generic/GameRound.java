package jpp.gametheory.generic;

import java.util.*;

public class GameRound<C extends IChoice> implements IGameRound<C> {
    private Map<IPlayer<C>, C> playerChoices;

    public GameRound(Map<IPlayer<C>, C> playerChoices){
        this.playerChoices = playerChoices;
        if(playerChoices.isEmpty()){
            throw new IllegalArgumentException("playerChoices is empty");
        }

    }

    @Override
    public Map<IPlayer<C>, C> getPlayerChoices() {
        return Collections.unmodifiableMap(playerChoices);
    }

    @Override
    public C getChoice(IPlayer<C> player) {
        if(player == null){
            throw new NullPointerException();
        }
        if(!playerChoices.containsKey(player)){
            throw new IllegalArgumentException("This player plays not this round");
        }
        return playerChoices.get(player);
    }

    @Override
    public Set<IPlayer<C>> getPlayers() {
        return Collections.unmodifiableSet(playerChoices.keySet());
    }

    @Override
    public Set<IPlayer<C>> getOtherPlayers(IPlayer<C> player) {
        if(player == null){
            throw new NullPointerException();
        }
        if(!playerChoices.containsKey(player)){
            throw new IllegalArgumentException("This player plays not this round");
        }
        Map<IPlayer<C>, C> copyMap = new HashMap<>(playerChoices);
        copyMap.remove(player);
        return Collections.unmodifiableSet(copyMap.keySet());
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Set<IPlayer<C>> set = playerChoices.keySet();
        TreeSet treeSet = new TreeSet();
        treeSet.addAll(set);
        /*
        List setList = new ArrayList(set);
        Collections.sort(setList);
        for(Object player : setList){
            sb.append(player);
            sb.append(" -> ");
            C choice = playerChoices.get(player);
            sb.append(choice.name());
            sb.append(", ");
        }

         */

        Iterator<IPlayer<C>> iterator = treeSet.iterator();
        sb.append("(");
        while (iterator.hasNext()){
            IPlayer<C> player = iterator.next();
            sb.append(player.getName());
            sb.append(" -> ");
            C choice = playerChoices.get(player);
            sb.append(choice.name());
            if(iterator.hasNext()){
                sb.append(", ");
            }
        }

        sb.append(")");
        return String.valueOf(sb);
    }
}
