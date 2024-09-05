package presentationlayer;

import characterlayer.*;
import exceptionlayer.*;
import opponentlayer.*;
import turnlayer.Turn;
import weaponlayer.*;
import java.util.*;

public class TBGame {

    Queue<Turn> turnOrderQueue = new LinkedList<Turn>();
    List<Opponent> opponentList = new ArrayList<Opponent>();
    List<Human<Weapon>> humanList = new ArrayList<Human<Weapon>>();
    Random rnd = new Random();
    Scanner keyboard = new Scanner(System.in);
    int numberOfOpponents;

    private class Initializer {
        public void initializeOpponentList(){
            numberOfOpponents = rnd.nextInt(1,5);
            int id = 1;

            for (int i = 0; i < numberOfOpponents; i++){
                int opponentType = rnd.nextInt(1,5);
                String idString = "Opponent " + (i+1);

                if (opponentType == 1)
                    opponentList.add(new Slime(idString));
                else if (opponentType == 2)
                    opponentList.add(new Goblin(idString));
                else if (opponentType == 3)
                    opponentList.add(new Orc(idString));
                else
                    opponentList.add(new Wolf(idString));
                id++;
            }
        }

        private Weapon getRandomWeapon(){
            int weaponType = rnd.nextInt(1,4);

            if (weaponType == 1)
                return new Sword();
            else if (weaponType == 2)
                return new Spear();
            else
                return new Bow();
        }

        public void initializeHumanList(String name){
            int characterType = rnd.nextInt(1,5);

            if (characterType == 1)
                humanList.add(new Knight<Weapon>(name, getRandomWeapon()));
            else if (characterType == 2)
                humanList.add(new Hunter<Weapon>(name, getRandomWeapon()));
            else if (characterType == 3)
                humanList.add(new Squire<Weapon>(name, getRandomWeapon()));
            else
                humanList.add(new Villager<Weapon>(name, getRandomWeapon()));
        }

        public void initializeTurnOrderQueue(List<Human<Weapon>> humanList){
            for (Human<Weapon> human : humanList) {
                Turn turn = new Turn(human.getName(), human.getSpeed());
                turn.setIsHuman(true);
                turnOrderQueue.add(turn);
            }
            for (Opponent opponent : opponentList) {
                turnOrderQueue.add(new Turn(opponent.getOpponentID(), opponent.getSpeed()));
            }

            Comparator<Turn> turnComparator = Comparator.comparingInt(Turn::getSpeed).reversed();
            Collections.sort((LinkedList<Turn>) turnOrderQueue, turnComparator);
        }
    }

    public class Menu {

        private Initializer initializer;

        public Menu(){
            initializer = new Initializer();
        }

        public void mainMenu(){
            menuPart1();
            menuPart2();
            menuPart3();
        }

        private void menuPart1(){
            initializer.initializeOpponentList();

            System.out.println("\nWelcome to TBGame!\n\n" + "These opponents appeared in front of you: ");

            for (Opponent opponent : opponentList){
                System.out.println(opponent.toString());
            }

            System.out.print("\nPlease enter the number of characters to create (min 1, max 3): ");
            String input = keyboard.nextLine();
            int numberOfCharacters = inputChecker(input, new ArrayList<String>(Arrays.asList("1", "2", "3")));

            nameInputChecker(numberOfCharacters);
            System.out.println();

            for (int i = 0; i < humanList.size(); i++) {
                System.out.println("The stats of your " + ordinal(i+1) + " character:");
                System.out.println(humanList.get(i).toString() + "\n");
            }
        }

        private void menuPart2(){
            System.out.println("The battle starts!");
            initializer.initializeTurnOrderQueue(humanList);
            System.out.println();
        }

        private void menuPart3(){
            displayTurnOrderQueue();
            System.out.println();

            int size = turnOrderQueue.size();
            for (int round = 1; round < 50; round++) {     // A round is done when all the turns are played
                for (int move = 1; move < size+1; move++) {
                    System.out.print("\nMove " + move + " - ");

                    if (turnOrderQueue.peek().isHuman()) {
                        humanMove(round, move);
                    } else {
                        opponentMove(round);
                    }
                    turnOrderQueue.add(turnOrderQueue.poll());
                    System.out.println();

                    checkEliminated();    // Removes characters/opponents with 0 health

                    displayTurnOrderQueue();
                    System.out.println();
                    size = turnOrderQueue.size();

                    if(opponentList.size() == 0) {
                        System.out.println("\nYou won! Congratulations!\nThe game ends.");
                        System.exit(0);
                    }
                    else if(humanList.size() == 0) {
                        System.out.println("\nYou lost! Better luck next time.\nThe game ends.");
                        System.exit(0);
                    }
                }
                System.out.println("\nEnd of round " + round + ".");
            }
        }

        private void nameInputChecker(int numberOfCharacters) {      // Checks NotAUniqueNameException
            List<String> nameList = new ArrayList<String>();

            for (int i = 0; i < numberOfCharacters; i++){
                boolean done = false;

                while (!done) {
                    try {
                        System.out.print("Enter name for your " + ordinal(i+1) + " character: ");
                        String name = keyboard.nextLine();

                        if (nameList.contains(name))
                            throw new NotAUniqueNameException();

                        nameList.add(name);
                        initializer.initializeHumanList(name);
                        done = true;
                    }
                    catch (NotAUniqueNameException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

        private String ordinal(int i) {
            String[] suffixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
            switch (i % 100) {
                case 11:
                case 12:
                case 13:
                    return i + "th";
                default:
                    return i + suffixes[i % 10];
            }
        }
    }

    private void checkEliminated(){    // Removes characters/opponents with 0 health
        String id = null;
        for (int k=0; k < opponentList.size(); k++){
            if (opponentList.get(k).getPoints() <= 0) {
                id = opponentList.get(k).getOpponentID();
                opponentList.remove(opponentList.get(k));
            }
        }

        LinkedList<Turn> turnOrderList = new LinkedList<>(turnOrderQueue);

        for (int i=0; i< turnOrderList.size(); i++) {
            if (id == turnOrderList.get(i).getName_id())
                turnOrderList.remove(turnOrderList.get(i));
        }

        String name = null;
        for (int j=0; j < humanList.size(); j++){
            if (humanList.get(j).getPoints() <= 0) {
                name = humanList.get(j).getName();
                humanList.remove(humanList.get(j));
            }
        }

        for (int l=0; l< turnOrderList.size(); l++) {
            if (name == turnOrderList.get(l).getName_id())
                turnOrderList.remove(turnOrderList.get(l));
        }

        turnOrderQueue = new LinkedList<>(turnOrderList);
    }

    private void opponentMove(int round){
        Opponent opponentToMove = null;

        for (Opponent opponent : opponentList){
            if(turnOrderQueue.peek().getName_id().equals(opponent.getOpponentID()))
                opponentToMove = opponent;
        }
        opponentToMove.setRound(round);

        if (opponentToMove.getRoundToSkip() == round)
            System.out.println(opponentToMove.getOpponentID() + " skips the round.");
        else {
            if (round == opponentToMove.getRoundToDeactivateGuard())
                opponentToMove.setGuardActivated(false);

            Human<Weapon> target = humanList.get(rnd.nextInt(humanList.size()));

            System.out.print(opponentToMove.getOpponentID() + " ");

            int actionType = rnd.nextInt(1,4);

            switch (actionType){
                case 1:
                    opponentAttack(opponentToMove, target);
                    break;
                case 2:
                    opponentToMove.guard();
                    System.out.println("starts guarding.");
                    break;
                case 3:
                    opponentSpecial(opponentToMove, target, round);
                    break;
            }
        }
    }

    private void opponentAttack(Opponent opponentToMove, Human<Weapon> target){
        int damageDealt = opponentToMove.attack(target);
        System.out.print("attacks " + target.getName() + ". Deals " + damageDealt + " damage.\n");
        displayHumanInfo(target);
        displayOpponentInfo(opponentToMove);
    }

    private void opponentSpecial(Opponent opponentToMove, Human<Weapon> target, int round){
        opponentToMove.special(target);
        switch (opponentToMove.getSpecialAttackName()){
            case "Rushing attack":
                System.out.println("uses " + opponentToMove.getSpecialAttackName() + " on " + target.getName() + ".");
                displayHumanInfo(target);
                displayOpponentInfo(opponentToMove);
                opponentMove(round);
                opponentToMove.setAttack(opponentToMove.getAttack() * 100 / 70);
                break;

            case "Call friend":
                if (rnd.nextInt(1,11) < 3) {
                    System.out.println("uses " + opponentToMove.getSpecialAttackName() + ".");

                    numberOfOpponents += 1;
                    String idString = "Opponent " + (numberOfOpponents);
                    Opponent friendWolf = new Wolf (idString);
                    friendWolf.setAttack(opponentToMove.getAttack());
                    friendWolf.setSpeed(opponentToMove.getSpeed());
                    friendWolf.setPoints(opponentToMove.getPoints());

                    opponentList.add(friendWolf);
                    insertAtSecondPositionOfQueue(new Turn(friendWolf.getOpponentID(), friendWolf.getSpeed()));
                }
                else
                    System.out.println("does nothing.");
                break;

            default:
                System.out.println("uses " + opponentToMove.getSpecialAttackName() + " on " + target.getName() + ".");
                displayHumanInfo(target);
                displayOpponentInfo(opponentToMove);
                break;
        }
    }

    private void humanMove(int round, int move){
        Human<Weapon> humanToMove = null;

        for (Human<Weapon> human : humanList){
            if(turnOrderQueue.peek().getName_id().equals(human.getName()))
                humanToMove = human;
        }
        humanToMove.setRound(round);

        if (humanToMove.getRoundToSkip() == round) {
            System.out.println(humanToMove.getName() + " skips the round.");
        }
        else if (humanToMove.getRoundToDoubleAttack() == round){
            System.out.println("In this round you have 2 turns.");
            humanToMove.setRoundToDoubleAttack(0);
            humanMove(round, move);
            humanMove(round, move);
        }
        else if (humanToMove.getRoundTo3xAttack() == round){
            humanToMove.setRoundTo3xAttack(0);
            humanToMove.setAttack(humanToMove.getAttack() * 3);
            humanMove(round, move);
            humanToMove.setAttack(humanToMove.getAttack() / 3);
        }
        else {
            normalCase(humanToMove, move, round);
        }
    }

    private void normalCase(Human<Weapon> humanToMove, int move, int round){
        if (round == humanToMove.getRoundToDeactivateGuard())
            humanToMove.setGuardActivated(false);

        System.out.println("It is the turn of " + humanToMove.getName() + ".");
        System.out.println("[1] Punch\n[2] Attack with weapon\n[3] Guard\n[4] Special Action\n[5] Run");

        int choice = exceptionChecker(humanToMove);

        Opponent targetOpponent;
        int damageDealt = 0;

        switch (choice) {

            case 1:
                targetOpponent = getTargetOpponent();
                damageDealt = humanToMove.punch(targetOpponent);
                System.out.println("\nMove " + move + " result: " + humanToMove.getName() + " punches "
                        + targetOpponent.getOpponentID() + ". Deals " + damageDealt + " damage.");
                displayHumanInfo(humanToMove);
                displayOpponentInfo(targetOpponent);
                break;

            case 2:
                weaponAttack(humanToMove, move);
                break;

            case 3:
                humanToMove.guard();
                System.out.println("Starts guarding.");
                break;

            case 4:
                humanSpecial(humanToMove, move);
                humanToMove.setSpecialUsed(true);
                break;

            case 5:
                System.out.print("Remaining points of all opponents: ");
                for (Opponent opponent : opponentList)
                    System.out.print(opponent.getOpponentID() + ": " + opponent.getPoints() + " ");

                System.out.println();
                humanToMove.run();
                break;
        }
    }

    private void humanSpecial(Human<Weapon> humanToMove, int move){
        int damageDealt = 0;
        Opponent targetOpponent = getTargetOpponent();
        switch (humanToMove.getClass().getSimpleName()){
            case "Knight":
                humanToMove.specialAction(targetOpponent);
                System.out.println("Skips this turn.");
                break;
            case "Hunter", "Squire":
                damageDealt = humanToMove.specialAction(targetOpponent);
                System.out.println("\nMove " + move + " result: " + humanToMove.getName() + " attacks "
                        + targetOpponent.getOpponentID() + ". Deals " + damageDealt + " damage.");
                displayHumanInfo(humanToMove);
                displayOpponentInfo(targetOpponent);
                break;
            case "Villager":
                System.out.println("Has no special action.");
                break;
        }
    }

    private void weaponAttack(Human<Weapon> humanToMove, int move){
        int damageDealt = 0;
        Opponent targetOpponent = getTargetOpponent();
        int selectedAttackType;

        switch (humanToMove.getSelectedWeapon().getClass().getSimpleName()){
            case "Sword":
                System.out.print("Please select weapon attack type ([1] Slash [2] Stab):");
                String input2 = keyboard.nextLine();
                selectedAttackType = inputChecker(input2, new ArrayList<String>(Arrays.asList("1", "2")));

                damageDealt = humanToMove.attackWithWeapon(humanToMove.getSelectedWeapon(), selectedAttackType, targetOpponent);
                break;

            case "Spear":
                System.out.print("Please select weapon attack type ([1] Stab [2] Throw):");
                String input3 = keyboard.nextLine();
                selectedAttackType = inputChecker(input3, new ArrayList<String>(Arrays.asList("1", "2")));

                damageDealt = humanToMove.attackWithWeapon(humanToMove.getSelectedWeapon(), selectedAttackType, targetOpponent);
                break;

            case "Bow":
                System.out.print("Please select weapon attack type ([1] Shoot single arrow [2] Shoot double arrow):");
                String input4 = keyboard.nextLine();
                selectedAttackType = inputChecker(input4, new ArrayList<String>(Arrays.asList("1", "2")));

                damageDealt = humanToMove.attackWithWeapon(humanToMove.getSelectedWeapon(), selectedAttackType, targetOpponent);
                break;
        }
        System.out.println("\nMove " + move + " result: " + humanToMove.getName() + " attacks "
                + targetOpponent.getOpponentID() + ". Deals " + damageDealt + " damage.");
        displayHumanInfo(humanToMove);
        displayOpponentInfo(targetOpponent);
    }

    private int exceptionChecker(Human<Weapon> humanToMove){  // Checks for SpecialAlreadyUsedException and
        int choice = 0;                                           // InsufficientStaminaException
        boolean done = false;
        while (!done) {
            try {
                System.out.print("Please select an option: ");
                String input1 = keyboard.nextLine();
                choice = inputChecker(input1, new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5")));

                if (choice == 4 && humanToMove.getSpecialUsed())
                    throw new SpecialAlreadyUsedException();

                int staminaRequired = 0;
                switch (choice){
                    case 1, 2:
                        staminaRequired = 2;
                        break;
                }

                if (humanToMove.getStamina() < staminaRequired)
                    throw new InsufficientStaminaException();

                done = true;
            }
            catch (SpecialAlreadyUsedException e){
                System.out.println(e.getMessage());
            } catch (InsufficientStaminaException e) {
                System.out.println(e.getMessage());
            }

        }
        return choice;
    }

    private Opponent getTargetOpponent(){
        System.out.print("Please enter an opponent ID: ");
        String input2 = "Opponent " + keyboard.nextLine();
        String targetChoiceString = stringOpponentInputChecker(input2, getOpponentStringList());

        Opponent targetOpponent = null;
        for (Opponent element : opponentList){
            if (element.getOpponentID().equals(targetChoiceString))
                targetOpponent = element;
        }
        return targetOpponent;
    }

    private ArrayList<String> getOpponentStringList(){
        ArrayList<String> opponentStringList = new ArrayList<String>();

        for (Opponent opponent : opponentList){
            opponentStringList.add(opponent.getOpponentID());
        }
        return opponentStringList;
    }

    private void insertAtSecondPositionOfQueue(Turn turn){
        LinkedList<Turn> turnOrderList = new LinkedList<>(turnOrderQueue);
        turnOrderList.add(1, turn);
        turnOrderQueue = new LinkedList<>(turnOrderList);
    }

    private void displayHumanInfo(Human<Weapon> target){
        System.out.println(target.getName() + ", Job: " + target.getClass().getSimpleName() + ", Points: " + target.getPoints()
                + ", Stamina: " + target.getStamina());
    }

    private void displayOpponentInfo(Opponent opponentToMove){
        System.out.println(opponentToMove.getOpponentID() + " , Type: " + opponentToMove.getClass().getSimpleName()
                + " , Points: " + opponentToMove.getPoints());
    }

    private void displayTurnOrderQueue(){
        System.out.print("*** Turn Order: ");

        boolean firstTurn = true;
        for (Turn turn : turnOrderQueue){
            if (firstTurn) {
                System.out.print(turn);
                firstTurn = false;
            } else {
                System.out.print(", " + turn);
            }
        }

        System.out.print(" ***");
    }

    private int inputChecker(String input, ArrayList<String> wantedInputList) {    //Checks a string input
        while(!wantedInputList.contains(input)) {                                  // If it is in the desired input list
            System.out.print("Incorrect input! Please reenter another input: ");   // returns as integer
            input = keyboard.nextLine();
        }
        return Integer.parseInt(input);
    }

    private String stringOpponentInputChecker(String input, ArrayList<String> wantedInputList){
        while(!wantedInputList.contains(input)) {
            System.out.print("Incorrect input! Please reenter another input: ");
            input = "Opponent " + keyboard.nextLine();
        }
        return input;
    }

    //keyboard.close();
}