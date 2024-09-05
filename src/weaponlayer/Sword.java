package weaponlayer;

public class Sword extends Weapon{

    public Sword() {
        super();
    }

    public int slash(int attack){
        return attack + getAdditionalAttack();
    }

    public int stab(int attack){
        return (attack + getAdditionalAttack()) * 2;
    }
}