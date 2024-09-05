package weaponlayer;

public class Spear extends Weapon{

    public Spear() {
        super();
    }

    public int stab(int attack){
        return (attack + getAdditionalAttack()) * 11 / 10;
    }

    public int thrown(int attack){
        return (attack + getAdditionalAttack()) * 2;
    }
}
