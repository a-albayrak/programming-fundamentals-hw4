package turnlayer;

public class Turn {

    private String name_id;
    private int attackModifier;
    private int speed;
    private boolean isHuman;     //Shows if human or opponent turn

    public Turn(String name_id, int speed) {
        this.name_id = name_id;
        this.speed = speed;
        isHuman = false;
        attackModifier = 0;
    }

    public void setIsHuman(boolean isHuman) {
        this.isHuman = isHuman;
    }

    public boolean isHuman() {
        return isHuman;
    }

    @Override
    public String toString() {
        return name_id;
    }

    public String getName_id() {
        return name_id;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public void setAttackModifier(int attackModifier) {
        this.attackModifier = attackModifier;
    }
}