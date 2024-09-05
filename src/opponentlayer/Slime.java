package opponentlayer;

import characterlayer.Human;

public class Slime extends Opponent{

    public Slime(String opponentID) {
        super(opponentID);
        setSpecialAttackName("Absorb");
    }

    @Override
    public void special(Human humanToAttack) {
        int damageDealt = attack(humanToAttack);

        if (getPoints() + damageDealt > 150)
            setPoints(150);
        else
            setPoints(getPoints() + damageDealt);
    }
}