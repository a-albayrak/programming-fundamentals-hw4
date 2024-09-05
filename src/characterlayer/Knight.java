package characterlayer;

import opponentlayer.Opponent;

public class Knight<W> extends Human{
    public Knight(String name, W weapon) {
        super(name, weapon);
    }

    @Override
    public int specialAction(Opponent targetOpponent) {
        setRoundTo3xAttack(getRound()+1);
        return 0;
    }
}