# digger
Proof-of-concept for "Digger" game written in Java

This repo contains the code for a proof-of-concept for a simple game called "Digger."

# Overview

In this game, the player controls a "digger," a machine that can move in four directions (up/down/left/right) and shoot a bullet that takes a bit to recharge. The goal of the game is to collect as many points as possible by retrieving emeralds (that have no physics and exist simply to be collected) and gold coins (that come in bags that open and become collectible after falling a minimum of two squares). Although there are two sources of points in the game, a level is completed when all emeralds on the map are collected, making the gold coins a bonus.

The main obstacle is in the form of two enemies that constantly move towards the digger. One of these enemies can dig thorough dirt like the digger, and the other must use pre-existing tunnels. The game combines quick thinking and strategy, as the player must think of the best way to let all the gold bags fall two squares to collect them, while still fending off the repeatedly-spawning enemies.

# Implementation

The game's implementation contains examples of effective/efficient class hierarchy (including abstract classes and interfaces), user imput through action listeners, simple graphics in Java, cycle-based game progression, and level information loaded to external files.

**General Game Structure**

Most classes in this repo are examples of "game components" (not to be confused with JComponent-related classes). Game components are objects that must either change properties when a new cycle occurs, or be interacted with by other game components. Examples include the digger, the bullet, the enemies, the dirt squares, the emeralds, the gold bags, and the gold coins. The game progresses by loading up a list of game components on the field, and executing their progressCycle methods. One-by-one, the game components take in information about their surroundings and update their states accordingly.

**The Digger**

The digger is controlled by several action listeners corresponding to the arrow keys (for movement) and the space bar (for shooting). Because of the large-cell grid system that the game uses to handle positioning, cycles for movement (i.e. almost all cycle timing) must be kept far apart. Because of this, it was found effective to simply call checks on the state of the arrow keys and space bar at each cycle change to determine the action for the digger to take. It follows that the game would take on a sort of "turn-based action" system, in which the player must take the short amount of time between cycles to decide what action to take.

**The Enemies**

The enemies' actions are also taken on the same cycle points as the digger, but unlike the diggers' actions, the enemies' are determined by a simple AI script that varies depending on the type of enemy (and notably, whether or not it can dig through dirt blocks).

**The Collectibles**

The gold bag follows a simple script to determine whether it needs to fall on any given cycle (i.e. if nothing is below it) or whether to break open (i.e. if it has fallen more than one square since it started falling). The gold coins and emeralds follow virtually no scripting, as they take no actions.

**The Levels**

Levels in this game are stored in external files consisting of different characters aligned to represent different game components. For example, a 'd' in the top-left position of the file signifies a dirt block to be placed in the top-left corner of the level. When a new level is loaded, the game reads through the file and places a game component at each square in the grid (or nothing, if the space is an underscore).



This project was completed in CSSE220 - Object-Oriented Software Development at Rose-Hulman Institute of Technology.