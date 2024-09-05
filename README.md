# Programming Fundamentals Homework 4: Turn-Based Video Game in Java

## Overview
In this project, I implemented a **Turn-Based Video Game Application** using Java. The application showcases a battle between player-controlled characters and randomly generated opponents, utilizing object-oriented principles and Java-specific concepts such as inheritance, interfaces, abstract classes, inner classes, exception handling, generics, and collections.

## Features
- **Player-Character Creation**: Players can create up to 3 characters with randomly generated stats, each assigned one of four possible jobs: Knight, Hunter, Squire, or Villager.
- **Opponent Generation**: Opponents are randomly generated from a pool of 1 to 4 opponents with distinct types: Slime, Goblin, Orc, or Wolf.
- **Turn-Based Combat System**: The game features a queue-based turn order system, where each character and opponent takes turns performing actions like attacking, guarding, or executing special moves.
- **Exception Handling**: Custom exceptions such as `NotAUniqueNameException`, `InsufficientStaminaException`, and `SpecialAlreadyUsedException` are used to manage game logic.

## Game Mechanics
An **opponent could be a Slime, Goblin, Orc or Wolf**. An opponent cannot exist without having a specific type like these. Randomly generated opponents can be made of any possible combination. Each opponent has the following stats: 

- opponentId: A unique id per opponent. (E.g., these can simply be “1, 2, 3, 4” if there are 4 opponents.) 
- 50 <= points <= 150 
- 5 <= attack <=25 
- 1<= speed <= 90 

The “opponent ids” exist to be used while displaying the TurnOrder information. An opponent is considered defeated when their “points” stat is reduced to 0 or below. The “attack” stat determines how much damage (to a player character) an opponent deals after performing the “Attack” action. The “speed” stat is used for determining the initial turn order.  

When an opponent’s turn comes, they perform one of the following actions randomly: 

- Attack: Randomly select a specific character and deal damage to them a value that is equal to the opponent’s attack stat. 
- Guard: Don’t do anything this turn and receive %50 less damage until the start of the next turn. 
- Special: Perform a special action unique to the opponent’s type: 
- ***Slime***: absorb (It attacks normally and increases its points equal to the damage dealt. Its points cannot go past 150.) 
- ***Goblin***: rushingAttack (It attacks normally and gets another turn immediately after the current one. It deals damage 0.7 × attack stat for both of these turns.) 
- ***Orc***: heavyHit (It deals damage for 2 × attack stat and skips its next turn.) 
- ***Wolf***:  callFriend  (It  does  not  attack.  This  action  has  20%  chance  of  success.  If successful,  an  identical  wolf  is  created,  and  it  joins  the  opponents.  If  this  is unsuccessful, nothing happens.) 

When the application starts, the player should **enter the number of player-controlled-characters** to create as input. There could be **3 characters at most**. Then, the stats of the characters should also be  **randomly  generated**.  The  newly  created  “Human<W>”  characters  should  implement  the “Character<W>” generic interface.  

A human could have one of the following jobs: “Knight”, “Hunter”, “Squire”, “Villager”. A human cannot exist without having one of these jobs. The jobs are also determined randomly. Each **human** has the following stats: 

- name: A unique name for the character that is taken as an input. If the name already exists after taking the input, a **NotAUniqueNameException** should be thrown. 
- 100 <= points <= 150 
- stamina (This stat always starts as 10.) 
- 20 <= attack <=40 
- 10 <= speed <= 99 
- weapon:  The  weapon  of  the  character  that  is  randomly  assigned.  Each  weapon  has  an additionalAttack stat (from 10 to 20). This stat’s value is added to the character’s attack stat when an action with the weapon is performed. Each weapon has 2 attack types and one of these must be selected when a character attacks with a weapon. A weapon could be one of the following: 
- ***Sword***: It can slash a selected opponent and directly deal the combined attack stat of the character and the sword. It can also stab an opponent with 25% chance of failure. In case of success, the character deals 2 × combined attack damage. 
- ***Spear***: It can stab an opponent for 1.1 × combined attack damage. It can also be thrown to deal 2 × combined attack damage, but the character skips his next turn as a result.  
- ***Bow***: It can shoot a single arrow to deal an opponent 0.8 × combined attack damage. Also, it can shoot two arrows at the same time to deal an opponent 2.5 × combined attack damage. 

A **Character<W>** should be able to do the following: 

- punch: The character deals “0.8 × attack stat” to a selected opponent. This action reduces stamina stat by 1. 
- attackWithWeapon<W>: The character selects an opponent and one the two attack types of his weapons. The selected opponent is damaged according to the rules of weapons described above. All attacks with sword and spear reduce stamina by 2. Bow’s single arrow attack uses 1 stamina, and its double arrow attack reduces stamina by 3. 
- guard: The character guards until his next turn and receives 75% reduced damage. This action increases stamina by 3. 
- run: All characters run away and leave the battle. The menu displays the remaining points of all opponents. Then, the application terminates. This action can be performed without any limitations. 
- specialAction: The character performs a special action on a selected opponent according to his job. Each character can use their **special action once per game**. When they try to use it again a **SpecialAlreadyUsedException** should be thrown. 
- ***Knight*** can skip the current turn and deals 3 × attack on his next turn.  
- ***Hunter*** can attack for 0.5 × attack in the current turn and have two turns back-to- back for his next turn. Note that this does not mean he gets a turn immediately after the current one. 
- ***Squire*** can attack for 0.5 × attack in the current turn and increase his stamina to 10. 
- ***Villager*** has no special action. 

Note  that,  when  trying  to  attack  with  insufficient  stamina,  an  **InsufficientStaminaException** should be thrown. 

TBGame class also has **a queue that contains the turn order**. A Turn has its *owner’s id or name*. It also has a *AttackModifier* attribute that can be used for some specific actions described above.  

These Turns are ordered inside a queue named TurnOrder. The **order is determined according to the speed stat** of the characters and opponents. The ones with higher speed stat should come first in this order.  

Normally, after a character or opponent’s turn is over, **they are removed from the queue and added to its tail as their next turn**. According to the special action situations described above, there can be certain changes to the way the queue works. If a character or an opponent completely loses their points, they are completely removed from the queue. 

### Example Game Flow

```text
Welcome to TBGame! 

These opponents appeared in front of you: 

Id: 1, Type: Slime, Points: 62, Attack: 9, Speed: 78 Id: 2, Type: Wolf, Points: 121, Attack: 16, Speed: 65 Id: 3, Type: Slime, Points: 104, Attack: 24, Speed: 83 

Please enter the number of characters to create: 1 Enter name for your 1st character: Abcde 

The stats of your 1st character:  

Abcde, Job: Hunter, Points: 136, Stamina: 10, Attack: 33, Speed: 80, Weapon: Sword with +12 attack The battle starts! 

\*\*\* Turn Order: Opponent 3, Abcde, Opponent 1, Opponent 2 \*\*\* 

Move 1 – Opponent 3 attacks Abcde. Deals 24 damage.  Abcde, Job: Hunter, Points: 112, Stamina: 10 

Move 2 – It is the turn of Abcde.  

1. Punch 
1. Attack with weapon 
1. Guard 
1. Special Action 
1. Run 

Please select an option: 2 

Please select weapon attack type ([1] Slash [2] Stab): 1 Please enter an opponent id: 1 

Move 2 Result: Abcde attacks Opponent 1. Deals 45 damage. Abcde, Job: Hunter, Points: 112, Stamina: 8 

Opponent 1, Type: Slime, Points: 17 

\*\*\* Turn Order: Opponent 1, Opponent 2, Opponent 3, Abcde \*\*\* 

Move 3 – Opponent 1 uses Absorb on Abcde. Deals 9 damage. Abcde, Job: Hunter, Points: 103, Stamina: 8 

Opponent 1, Type: Slime, Points: 26 

Move 4 – Opponent 2 starts guarding. 

Move 5 – Opponent 3 uses Absorb on Abcde. Deals 24 damage. Abcde, Job: Hunter, Points: 79, Stamina: 8 

Opponent 3, Type: Slime, Points: 128 

Move 6 – It is the turn of Abcde.  

1. Punch 
1. Attack with weapon 
1. Guard 
1. Special Action 
1. Run 

Please select an option: 5 

Your character(s) started running away. The battle ends! Thanks for playing! 
