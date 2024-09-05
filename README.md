# Programming Fundamentals Homework 4: Turn-Based Video Game in Java

## Overview
In this project, I implemented a **Turn-Based Video Game Application** using Java. The application showcases a battle between player-controlled characters and randomly generated opponents, utilizing object-oriented principles and Java-specific concepts such as inheritance, interfaces, abstract classes, inner classes, exception handling, generics, and collections.

## Key Features
- **Player-Character Creation**: Players can create up to 3 characters with randomly generated stats, each assigned one of four possible jobs: Knight, Hunter, Squire, or Villager.
- **Opponent Generation**: Opponents are randomly generated from a pool of 1 to 4 opponents with distinct types: Slime, Goblin, Orc, or Wolf.
- **Turn-Based Combat System**: The game features a queue-based turn order system, where each character and opponent takes turns performing actions like attacking, guarding, or executing special moves.
- **Exception Handling**: Custom exceptions such as `NotAUniqueNameException`, `InsufficientStaminaException`, and `SpecialAlreadyUsedException` are used to manage game logic.

## Game Mechanics
1. **Randomly Generated Stats**:
   - **Opponents**: Opponents have randomly assigned stats for health, attack, and speed. Each opponent type has a unique special action.
   - **Characters**: Player characters have randomly generated stats, including points, attack, and speed, and are equipped with randomly assigned weapons (Sword, Spear, Bow).

2. **Combat Actions**:
   - **Attack**: Characters can attack with either punches or their assigned weapon, which has two distinct attack types.
   - **Guard**: Reduce damage received by a significant percentage and recover stamina.
   - **Special Actions**: Each job class has a special action that can only be used once per game, providing strategic advantages in battle.

3. **Turn Order**: The turn order is determined by the speed stat. A queue holds the order, and turns cycle through the queue until a side is defeated or the player runs away.

### Example Game Flow

```text
Welcome to TBGame!

These opponents appeared in front of you:
Id: 1, Type: Slime, Points: 62, Attack: 9, Speed: 78
Id: 2, Type: Wolf, Points: 121, Attack: 16, Speed: 65

Please enter the number of characters to create: 1
Enter name for your 1st character: Abcde

The stats of your 1st character:
Abcde, Job: Hunter, Points: 136, Stamina: 10, Attack: 33, Speed: 80, Weapon: Sword with +12 attack

The battle starts!

Turn Order: Opponent 3, Abcde, Opponent 1, Opponent 2

Move 1
Opponent 3 attacks Abcde. Deals 24 damage.
Abcde, Job: Hunter,
Points : 112, Stamina: 10

Move 2
It is the turn of Abcde.
[1] Punch
[2] Attack with weapon
[3] Guard
[4] Special Action
[5] Run
Please select an option:
2
Please select weapon attack type ([1] Slash [2] Stab):
1
Please enter an opponent id: 1
Move 2 Result: Abcde attacks Opponent 1. Deals 45 damage.
Abcde, Job: Hunter,
Points : 112, Stamina: 8
Opponent 1, Type: Slime, Points : 17

Turn Order:
Opponent 1, Opponent 2, Opponent 3, Abcde

Move 3
Opponent 1 uses Absorb on Abcde. Deals 9 damage.
Abcde, Job: Hunter,
Points : 103, Stamina: 8
Opponent 1, Type: Slime, Points : 26

Move 4
Opponent 2 starts guarding.

Move 5
Opponent 3 uses Absorb on Abcde. Deals 24 damage.
Abcde, Job: Hunter,
Points : 79, Stamina: 8
Opponent 3, Type: Wolf, Points : 128

Move 6
It is the turn of Abcde.
[1] Punch
[2] Attack with weapon
[3] Guard
[4] Special Action
[5] Run
Please select an option:
5
Your character(s) started running away. The battle ends!
Thanks for playing!
