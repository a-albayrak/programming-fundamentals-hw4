package opponentlayer;

import characterlayer.Human;

public class Orc extends Opponent{
    public Orc(String opponentID) {
        super(opponentID);
        setSpecialAttackName("Heavy hit");
    }

    @Override
    public void special(Human humanToAttack) {
        setAttack(getAttack() * 2);
        attack(humanToAttack);
        setAttack(getAttack() / 2);
        setRoundToSkip(getRound() + 1);
    }
}