package opponentlayer;

import characterlayer.Human;

public class Goblin extends Opponent{
    public Goblin(String opponentID) {
        super(opponentID);
        setSpecialAttackName("Rushing attack");
    }

    @Override
    public void special(Human humanToAttack) {
        setAttack(getAttack() * 70 / 100);
        attack(humanToAttack);
    }
}