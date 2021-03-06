package jpp.gametheory.generic;

import java.util.ArrayList;
import java.util.List;

public class Player <C extends IChoice> implements IPlayer<C> {
    private String name;
    private IStrategy<C> strategy;

    public Player(String name, IStrategy<C> strategy){
        if(name == null | strategy == null){
            throw new NullPointerException();
        }
        this.name = name;
        this. strategy = strategy;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name + "(" + this.strategy.name() + ")";
    }

    @Override
    public IStrategy<C> getStrategy() {
        return this.strategy;
    }

    @Override
    public C getChoice(List<IGameRound<C>> previousRounds) {
        if(previousRounds == null){
            throw new NullPointerException();
        }
        return strategy.getChoice(this, previousRounds);
    }

    @Override
    public int compareTo(IPlayer<C> ciPlayer) {
        return this.name.compareTo(ciPlayer.getName());
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(this.getClass() != obj.getClass()){
            return false;
        }
        if(this == obj){
            return true;
        }
        Player that = (Player) obj;
        if(this.name == null && that.name != null){
            return false;
        }
        if(this.name.equals(that.name)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((name == null) ? 0 : name.hashCode());

        return hashCode;
    }
}
