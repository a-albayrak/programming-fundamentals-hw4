package weaponlayer;

public class Bow extends Weapon{

    public Bow() {
        super();
    }

    public int singleArrow(int attack){
        return (attack + getAdditionalAttack()) * 8 / 10;
    }

    public int doubleArrow(int attack){
        return (attack + getAdditionalAttack()) * 25 / 10;
    }
}