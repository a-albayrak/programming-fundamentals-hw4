package characterlayer;

import opponentlayer.Opponent;

public class Hunter<W> extends Human{
    public Hunter(String name, W weapon) {
        super(name, weapon);
    }

    @Override
    public int specialAction(Opponent targetOpponent) {
        setAttack(getAttack() / 2);
        int damageDealt = targetOpponent.incomingAttack(getAttack() * 5 / 10);
        setAttack(getAttack() * 2);
        setRoundToDoubleAttack(getRound()+1);
        return damageDealt;
    }
}