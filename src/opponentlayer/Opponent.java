package opponentlayer;

import characterlayer.Human;

import java.util.Random;

public abstract class Opponent {

    private String opponentID;
    private int points;
    private int attack;
    private int speed;
    private String specialAttackName;
    private boolean guardActivated;
    private int round;                    // Keeps track of how many turns opponent involved in so far
    private int roundToDeactivateGuard;
    private int roundToSkip;

    public Opponent(String opponentID) {
        this.opponentID = opponentID;
        guardActivated = false;
        round = 1;
        initializeStats();
    }

    private void initializeStats(){
        Random rnd = new Random();
        points = rnd.nextInt(50, 151);
        attack = rnd.nextInt(5, 26);
        speed = rnd.nextInt(1, 91);
    }

    public int incomingAttack(int incomingAttack){        // Handles attacks coming from humans to opponents
        if (guardActivated) {
            points -= (incomingAttack / 2);

            if(points < 0)
                points = 0;

            return (incomingAttack / 2);
        }
        else{
            points -= incomingAttack;

            if(points < 0)
                points = 0;

            return incomingAttack;
        }
    }

    public int attack(Human humanToAttack){
        int damageDealt = humanToAttack.incomingAttack(attack * 8 / 10);
        return damageDealt;
    }

    public void guard(){
        guardActivated = true;
        roundToDeactivateGuard = round + 1;
    }

    public abstract void special(Human humanToAttack);

    @Override
    public String toString() {
        return  "ID: " + opponentID + ", Type: " + getClass().getSimpleName() +
                ", Points: " + points + ", Attack: " + attack + ", Speed: " + speed;
    }

    public String getOpponentID() {
        return opponentID;
    }

    public int getSpeed() {
        return speed;
    }

    public String getSpecialAttackName() {
        return specialAttackName;
    }

    public void setSpecialAttackName(String specialAttackName) {
        this.specialAttackName = specialAttackName;
    }

    public int getAttack() {
        return attack;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRoundToDeactivateGuard() {
        return roundToDeactivateGuard;
    }

    public void setGuardActivated(boolean guardActivated) {
        this.guardActivated = guardActivated;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setRoundToSkip(int roundToSkip) {
        this.roundToSkip = roundToSkip;
    }

    public int getRound() {
        return round;
    }

    public int getRoundToSkip() {
        return roundToSkip;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}