# CENG211 – Programming Fundamentals

## Homework #4

In this homework, you are expected to implement a simple “Turn-Based Video Game Application” in Java.  
The submitted homework should fulfill the following concepts of:  

- Inheritance  
- Interfaces  
- Abstract Classes  
- Inner Classes  
- Exception Handling  
- Generics  
- Collections  

In this application, you will create a simple turn-based video game that plays out a single battle using player inputs. You should create a `TBGame` class that will contain necessary objects, display the menu, and play the game according to inputs.  

`TBGame` consists of your characters, randomly generated opponents, and a queue that holds the necessary `TurnOrder` information.  

During the game, each character and opponent perform an action during their turns while targeting someone from the other side. Their goal is to reduce the score points of all members of the other side to 0.  

`TBGame` should randomly initialize opponents and store them inside an `ArrayList`. Total number of opponents should also be randomly initialized. This number can vary from 1 to 4.  

An opponent could be a Slime, Goblin, Orc or Wolf. An opponent cannot exist without having a specific type like these. Randomly generated opponents can be made of any possible combination.  
Each opponent has the following stats:  
- `opponentId`: A unique id per opponent (e.g., “1, 2, 3, 4”)  
- `50 <= points <= 150`  
- `5 <= attack <= 25`  
- `1 <= speed <= 90`  

The `opponentId`s exist to be used while displaying the TurnOrder information. An opponent is considered defeated when their `points` stat is reduced to 0 or below.  
The `attack` stat determines how much damage an opponent deals after performing the `Attack` action.  
The `speed` stat is used for determining the initial turn order.  

When an opponent’s turn comes, they perform one of the following actions randomly:
- **Attack**: Randomly select a character and deal damage equal to the opponent’s attack stat.  
- **Guard**: Don’t do anything this turn and receive 50% less damage until next turn.  
- **Special**: Unique to opponent type:
  - **Slime**: `absorb` – Attacks normally and increases its points by damage dealt (max 150).  
  - **Goblin**: `rushingAttack` – Attacks normally and gets another turn immediately. Deals 0.7 × attack damage both turns.  
  - **Orc**: `heavyHit` – Deals 2 × attack damage and skips next turn.  
  - **Wolf**: `callFriend` – 20% chance to add an identical wolf to opponents.  

---

When the application starts, the player should enter the number of player-controlled characters (max 3).  
Stats of characters are randomly generated.  
Newly created `Human<W>` characters should implement the `Character<W>` generic interface.  

Each human can be:
- Knight  
- Hunter  
- Squire  
- Villager  

Each human has:
- `name`: Must be unique. If duplicate, throw `NotAUniqueNameException`.  
- `100 <= points <= 150`  
- `stamina` (starts at 10)  
- `20 <= attack <= 40`  
- `10 <= speed <= 99`  
- `weapon`: Randomly assigned, with `additionalAttack (10–20)`

### Weapon Types:
- **Sword**:  
  - `slash`: Deal combined attack stat.  
  - `stab`: 25% fail chance. On success, deal 2 × attack.  

- **Spear**:  
  - `stab`: 1.1 × attack  
  - `throw`: 2 × attack, skip next turn  

- **Bow**:  
  - `shoot`: 0.8 × attack  
  - `double shot`: 2.5 × attack  

### Actions:
- `punch`: 0.8 × attack, -1 stamina  
- `attackWithWeapon<W>`: attack with selected type. Sword/Spear = -2 stamina, Bow = -1/-3 stamina  
- `guard`: reduce damage by 75%, +3 stamina  
- `run`: end game, display remaining opponent points  
- `specialAction` (once per game, throws `SpecialAlreadyUsedException` if reused):
  - Knight: skip turn → next turn: 3 × attack  
  - Hunter: 0.5 × attack now, 2 turns next  
  - Squire: 0.5 × attack now, stamina to 10  
  - Villager: none  

If insufficient stamina → `InsufficientStaminaException`.

---

`TBGame` also uses a `TurnOrder` queue:  
- Each `Turn` contains owner id or name, and optional `AttackModifier`  
- Queue ordered by `speed` (descending)  
- After each turn, move to end of queue  
- Special actions may modify turn behavior  
- Characters/opponents with 0 points are removed from queue  

---

Inner Classes:
- `Menu`: Handles menu display and input  
- `Initializer`: Handles setup of lists and queue  

Menu displays points, stamina, and turn order. Affected values must be shown after every action.  
Game ends when all opponents or all characters are defeated, or someone runs.

---

### Example Output:

```
Welcome to TBGame!

These opponents appeared in front of you:  
Id: 1, Type: Slime, Points: 62, Attack: 9, Speed: 78  
Id: 2, Type: Wolf, Points: 121, Attack: 16, Speed: 65  
Id: 3, Type: Slime, Points: 104, Attack: 24, Speed: 83  

Please enter the number of characters to create: 1  
Enter name for your 1st character: Abcde  

The stats of your 1st character:  
Abcde, Job: Hunter, Points: 136, Stamina: 10, Attack: 33, Speed: 80, Weapon: Sword with +12 attack  

The battle starts!  
*** Turn Order: Opponent 3, Abcde, Opponent 1, Opponent 2 ***  

Move 1 – Opponent 3 attacks Abcde. Deals 24 damage.  
Abcde, Job: Hunter, Points: 112, Stamina: 10  

Move 2 – It is the turn of Abcde.  
[1] Punch  
[2] Attack with weapon  
[3] Guard  
[4] Special Action  
[5] Run  
Please select an option: 2  
Please select weapon attack type ([1] Slash [2] Stab): 1  
Please enter an opponent id: 1  

Move 2 Result: Abcde attacks Opponent 1. Deals 45 damage.  
Abcde, Job: Hunter, Points: 112, Stamina: 8  
Opponent 1, Type: Slime, Points: 17  

*** Turn Order: Opponent 1, Opponent 2, Opponent 3, Abcde ***  

Move 3 – Opponent 1 uses Absorb on Abcde. Deals 9 damage.  
Abcde, Job: Hunter, Points: 103, Stamina: 8  
Opponent 1, Type: Slime, Points: 26  

Move 4 – Opponent 2 starts guarding.  

Move 5 – Opponent 3 uses Absorb on Abcde. Deals 24 damage.  
Abcde, Job: Hunter, Points: 79, Stamina: 8  
Opponent 3, Type: Slime, Points: 128  

Move 6 – It is the turn of Abcde.  
[1] Punch  
[2] Attack with weapon  
[3] Guard  
[4] Special Action  
[5] Run  
Please select an option: 5  

Your character(s) started running away. The battle ends!  
Thanks for playing!
```

> Note that the outputs and results are provided randomly as simple examples.  
> Also, you should not forget about handling incorrect inputs.  

---

## Important Notes:

1. You must use `List`, `ArrayList`, and `Queue` interfaces.  
2. All exceptions must be **handled**; the app must never crash.  
3. You may use `java.io` and `java.util`. Do NOT use third-party libraries.  
4. To support Turkish characters, change project encoding to UTF8.  
5. Write clean, readable, and tester-friendly code.  
6. Comment your code thoroughly.  
7. Use proper package structure. Don’t place everything in one package.  

---
