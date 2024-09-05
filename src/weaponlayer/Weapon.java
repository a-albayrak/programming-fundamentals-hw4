package weaponlayer;

import java.util.Random;

public abstract class Weapon {
    private int additionalAttack;

    public Weapon(){
        initializeStats();
    }

    private void initializeStats() {
        Random rnd = new Random();
        additionalAttack = rnd.nextInt(10, 21);
    }

    public int getAdditionalAttack() {
        return additionalAttack;
    }
}