package opponentlayer;

import characterlayer.Human;

public class Wolf extends Opponent{


    public Wolf(String opponentID) {
        super(opponentID);
        setSpecialAttackName("Call friend");
    }

    @Override
    public void special(Human humanToAttack) {

    }
}