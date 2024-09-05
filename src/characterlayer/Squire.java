package characterlayer;

import opponentlayer.Opponent;

public class Squire<W> extends Human{
    public Squire(String name, W weapon) {
        super(name, weapon);
    }

    @Override
    public int specialAction(Opponent targetOpponent) {
        setAttack(getAttack() / 2);
        setStamina(10);
        int damageDealt = targetOpponent.incomingAttack(getAttack() * 5 / 10);
        return damageDealt;
    }
}