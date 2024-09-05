package characterlayer;

import opponentlayer.Opponent;

public class Villager<W> extends Human{
    public Villager(String name, W weapon) {
        super(name, weapon);
    }

    @Override
    public int specialAction(Opponent targetOpponent) {
        return 1;   //has no special action
    }
}