# Group Project: Chip v. Chap (Marco v. Craig)

This is Team 26's group project for SWEN225 2022.

## Installation 

Clone the repo to your machine.

```bash
git clone ......
```

## Usage

To run our program, run the main class from *nz.ac.vuw.ecs.swen225.gp22.app.Main* (Requires JAVA 17).

## Controls and Gameplay
In this game, you control a character (Marco) and guide him through levels. You will come across gold treasures which all need to be picked up in order to proceed in-game. Coloured keys are present in the levels, which need to be picked up in order to unlock the corresponding doors. Watch out for Craig! If he walks onto a tile occupied by your character, the level will reset. Reach the exits to win the game.

Arrow keys: control your character (re-bindable in GUI)

Control + S: save the game

## Replay Functions
To save a replay you must complete a game, once completed there will be a button to save replay.
To load a replay from the main menu either select "Replay" button for automatic replay or Play by Play
for a Play by play Replay. For a play by play replay the right arrow key will advance the game 1/20th of a second so holding down the button will play through the game at a good speed, but to go slowley simply tap the right arrow key button.
(the play by play may look as though its not moving as its only advancing 1/20 of a second but just keep tapping and you will be away !). In a automatic Replay you will have a check box at the top to increase replay speed to 2x and toggle it off to go back to normal speed.

Games can only be saved when you have completed a game.

##render
tiles include:
wall tile: a purple slab unable to be crossed.

lock tile: diifrfent coulor tiles which ave keys holes waiting for the macthing key inorder to be entered.

keys: the keys which are required to unlock the lock tiles. 

coins: collect theses in oder to complete levels, be sure you havent forgotten any.

info tile: a point of information for when your lost, stuck or confused. 

rainbow lock: can only be unlcoked once all teh coins are collected. 

escape tile: the way out or onto the next level, found behind the ranibow lock. 

## App
Game starts by creating a ChipVsChap object in the Main class.
In the ChipVsChap constructor, we ensure we are in the EDT by assertions and we call the menu function which brings up the menu containing the required buttons to play the game. Every frame contains a closePhase runnable that removes it's components to be ready for the next set of components. JLabels were used as background images using the setIcon() method and these acted as a container in which other labels and buttons could sit on. A setLevel method was created to reset the closePhase variable and call the run() method using a Phase as an argument depending on the level. Each level contained a win or loss runnable. When the level is finished setLevel() is called again causing run to be called again but this time with either the win or loss as the phase argument. Eventually when the player finishes the final level will the win screen appear. The movement in this game was controlled by KeyListeners that were constantly waiting for key presses. If a movement key was pressed, it would move chap through a method from the domain module. The timer ticks 20 times a second and every tick checks for a win or a loss. Every second the timer would check if the time is 0 and if it is the level will be restarted. This timer was also used to draw the rendering module on the window every two ticks. Pause functionality was made through making a JDialog appear visible and invisible and stopping and starting the timer accordingly. 

## Team Members

| **Module**  	| **Member Name** 	| **GitLab User** 	|
|-------------	|-----------------	|-----------------	|
| Persistency 	| Luke Gulliver   	| @gullivluke     	|
| Recorder    	| Milan Kriletich 	| @kriletmila     	|
| App         	| Calvin Li       	| @licalv         	|
| Renderer    	| Jack Grunfeld   	| @grunfejack / jack grunfeld    	|
| Domain      	| Leo Gaynor      	| @gaynorleo      	|
| Fuzz        	| Ilya Mashkov    	| @mashkoilya     	|

## Gource Video
https://myvuwac-my.sharepoint.com/:v:/g/personal/gullivluke_myvuw_ac_nz/EWzFKP73PtpInKveXhKjjBcBrSYAeVLz6tnYU6oLq6gCvQ?e=YV50xH
