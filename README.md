# WerewolfGame
Java 3 Final Project
Game rules:
Werewolf is a game of deceit, where an informed minority, the werewolves, must remain hidden from the uninformed majority, the villagers. 
The werewolves know each other and, during the night, murder one villager. During the day the players discuss who they think are werewolves and, at the end of the day, someone will be lynched, based on a voting system. If the werewolves reach parity (there are the same number of werewolves as there are of good players) they win the game. If all werewolves are lynched, the villagers win the game.
Instructions:
Create the database and tables using the .sql file.
Put the badwords.txt file in the tomcat8.0/bin directory(or your version of tomcat)
To log in, use the username Joao or Jamie and the password root and create a game.
In order for the game to run, open an icognito browser window and login with the user "SYSTEM" password "system" and leave that window open. This runs a servlet that keeps checking the active games to count votes, kill players, check for end game and increase the game rounds. 
Play the game using any of the Players (Player1, Player2, Player 3, etc) with the password root.
Living players that are on the good side have a main chat and the ability to vote in other (alive) players.
Living players that are on the werewolf, or evil, side have the main chat, the ability to vote and also the ability to choose a victim for the night. 
Dead players can see the main chat, but not enter any messages, and also have access to a dead chat, where they can discuss the game with other dead players.
