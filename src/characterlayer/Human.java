package characterlayer;

import opponentlayer.Opponent;
import weaponlayer.*;

import java.util.Random;

public abstract class Human<W> implements Character<W>{

    private String name;
    private int points;
    private int stamina;
    private int attack;
    private int speed;
    private W weapon;
    private int round;         // Keeps track of how many turns this character involved in so far
    private int roundToSkip;
    private boolean guardActivated;
    private int roundToDeactivateGuard;
    Weapon selectedWeapon;
    Random rnd = new Random();
    private int roundToDoubleAttack;
    private int roundTo3xAttack;
    private boolean specialUsed;

    public Human(String name, W weapon){
        this.name = name;
        stamina = 10;
        initializeStats();
        this.weapon = weapon;
        this.selectedWeapon = (Weapon) weapon;
        guardActivated = false;
        specialUsed = false;
    }

    private void initializeStats(){
        Random rnd = new Random();
        points = rnd.nextInt(100, 151);
        attack = rnd.nextInt(20, 41);
        speed = rnd.nextInt(10, 100);
    }

    public int incomingAttack(int incomingAttack){      // Handles attacks coming from opponents to humans
        if (guardActivated) {
            points -= (incomingAttack * 25 / 100);

            if(points < 0)
                points = 0;

            return (incomingAttack * 25 / 100);
        }
        else{
            points -= incomingAttack;

            if(points < 0)
                points = 0;

            return incomingAttack;
        }
    }

    @Override
    public int punch(Opponent targetOpponent) {
        int damageDealt = targetOpponent.incomingAttack(attack * 8 / 10);
        stamina -= 1;
        return damageDealt;
    }

    @Override
    public <W> int attackWithWeapon(W weapon, int option, Opponent targetOpponent) {
        Weapon weaponToUse = (Weapon) weapon;
        int staminaChange = 0;
        int damageDealt = 0;

        switch (weapon.getClass().getSimpleName()){
            case "Sword":
                if (option == 1) {
                    damageDealt = targetOpponent.incomingAttack(((Sword) weaponToUse).slash(attack));
                }
                else {
                    if (rnd.nextInt(1,101) > 25)
                        damageDealt = targetOpponent.incomingAttack(((Sword) weaponToUse).stab(attack));
                }
                staminaChange = 2;
                break;

            case "Spear":
                if (option == 1) {
                    damageDealt = targetOpponent.incomingAttack(((Spear) weaponToUse).stab(attack));
                }
                else {
                    damageDealt = targetOpponent.incomingAttack(((Spear) weaponToUse).thrown(attack));
                    roundToSkip = (round + 1);
                }
                staminaChange = 2;
                break;

            case "Bow":
                if (option == 1) {
                    damageDealt = targetOpponent.incomingAttack(((Bow) weaponToUse).singleArrow(attack));
                    staminaChange = 1;
                }
                else {
                    damageDealt = targetOpponent.incomingAttack(((Bow) weaponToUse).doubleArrow(attack));
                    staminaChange = 3;
                }
                break;
        }
        stamina -= staminaChange;
        return damageDealt;
    }

    @Override
    public void guard() {
        guardActivated = true;
        roundToDeactivateGuard = round + 1;
        stamina += 3;
    }

    @Override
    public void run() {
        System.out.println("Your character(s) started running away. The battle ends!\nThanks for playing!");
        System.exit(0);
    }

    @Override
    public abstract int specialAction(Opponent targetOpponent);

    public String toString() {
        return  name +
                ", Job: " + getClass().getSimpleName() +
                ", Points: " + points +
                ", Attack: " + attack +
                ", Speed: " + speed +
                ", Weapon: " + selectedWeapon.getClass().getSimpleName() +
                " with +" + selectedWeapon.getAdditionalAttack() + " attack";
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public int getStamina() {
        return stamina;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getRoundToSkip() {
        return roundToSkip;
    }

    public void setRoundToSkip(int roundToSkip) {
        this.roundToSkip = roundToSkip;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setWeapon(W weapon) {
        this.weapon = weapon;
    }

    public int getRoundToDeactivateGuard() {
        return roundToDeactivateGuard;
    }

    public void setRoundToDeactivateGuard(int roundToDeactivateGuard) {
        this.roundToDeactivateGuard = roundToDeactivateGuard;
    }

    public void setGuardActivated(boolean guardActivated) {
        this.guardActivated = guardActivated;
    }

    public Weapon getSelectedWeapon() {
        return selectedWeapon;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return attack;
    }

    public int getRoundToDoubleAttack() {
        return roundToDoubleAttack;
    }

    public void setRoundToDoubleAttack(int roundToDoubleAttack) {
        this.roundToDoubleAttack = roundToDoubleAttack;
    }

    public int getRoundTo3xAttack() {
        return roundTo3xAttack;
    }

    public void setRoundTo3xAttack(int roundTo3xAttack) {
        this.roundTo3xAttack = roundTo3xAttack;
    }

    public boolean getSpecialUsed() {
        return specialUsed;
    }

    public void setSpecialUsed(boolean specialUsed) {
        this.specialUsed = specialUsed;
    }
}