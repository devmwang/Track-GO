# CPSC 210 Term Project (Track:GO)

## CS:GO Player/Roster Statistics Tracker and Visualizer

### What will this project (Track:GO) do?

Track:GO will allow users to enter CS:GO match data in order to analyze and track specifics
with greater ease. The user will be able to enter data from matches they play. Then, they
can view data from each prior match, view overall data (like overall win rate or average
damage per round), or perform trend analysis with the program to see how their performance
may be improving or declining.

### Who will use it?

-   General/regular players of CS:GO to track their personal and friend stats.
-   CS:GO team coaches/data analysts (to help them analyze historical data more effectively).

### Why this project is of interest to me

I play CS:GO quite often (with a group of friends). Often, we want to be able to look at
and analyze historical data, or view personalized stats. Additionally, we find it
interesting to view data on group data, like map win rate as a group, or overall win rate
depending on who is playing.

## User Stories

-   As a user, I want to be able to create new rosters.
-   As a user, I want to be able to create new players.
-   As a user, I want to be able to add a player to a roster.
-   As a user, I want to be able to add data from a match to a roster.
-   As a user, I want to be able to add match data to a player for a specific match.
-   As a user, I want to be able to select a roster and view overall stats for that roster.
-   As a user, when I select the quit option from the main menu, I want to be given the option to save all the entered data to a file.
-   As a user, when the application is launched, I want to be given the option to load player/roster/match data from a file.

## Instructions for Grader (Phase 3)

-   You can generate the first required action: adding a player to a roster by navigating to the Rosters page (`View Rosters` button in the navbar), then selecting a player and a roster with the selection boxes in the `Add Player to Roster` panel, then confirm the add with the `Confirm Selections` button.
-   You can generate the second required action: remove a player from a roster by navigating to the Rosters page (`View Rosters` button in the navbar), then selecting a player and a roster with the selection boxes in the `Remove Player from Roster` panel, then confirm the removal with the `Confirm Selections` button.
-   You can generate a third action: filtering the displayed rosters to only ones with a certain player by navigating to the Rosters page (`View Rosters` button in the navbar), then selecting a required player (only show rosters with the required player), then confirm the filter with the `Confirm Filters` button.
-   You can locate my visual component in the main menu, shown by default (or accessible through the navbar with the `Main Menu` button).
-   You can save app data by selecting `Save App Data` in the navbar, then confirm the load with the `Confirm and Save App Data` button.
-   You can load app data by selecting `Load App Data` in the navbar, then confirm the load with the `Confirm and Load App Data` button.

## Phase 4: Task 2

Sample of logged events printed on exit:

Wed Apr 12 14:08:11 PDT 2023  
Player created with username "Player4"

Wed Apr 12 14:08:16 PDT 2023  
Player created with username "Player5"

Wed Apr 12 14:08:31 PDT 2023  
Player with username "Player3" added to roster with id "Roster1"

Wed Apr 12 14:08:32 PDT 2023  
Player with username "Player4" added to roster with id "Roster1"

Wed Apr 12 14:08:35 PDT 2023  
Player with username "Player4" added to roster with id "Roster2"

Wed Apr 12 14:08:39 PDT 2023  
Player with username "Player5" added to roster with id "Roster2"

Wed Apr 12 14:08:46 PDT 2023  
Player with username "Player1" removed from roster with id "Roster1"

Wed Apr 12 14:08:49 PDT 2023  
Player with username "Player3" removed from roster with id "Roster2"

## Phase 4: Task 3

One of the main issues I would tackle with a refactor is the current unnecessarily complex intertwining of the different models even within the AppData class. As seen in the UML class diagram, the AppData, Roster, and Match classes all hold lists or maps of the Player class. Complicating things further is that AppData holds lists of the Roster and Match classes. This especially complicated things when implementing the persistence system for multiple reasons. When implementing the write system, I had to be careful to choose where to store player data and where to store references to players using the unique usernames. When implementing the read system, I had to be extremely careful to properly instantiate and store the correct references to the different objects on load, (like making sure that AppData, Roster, and Match all stored references to the same Player object for each individual player). To fix these issues, I would have AppData be the single source of truth for the Player, Roster and Match objects. Then, since Roster and Match still need to know about the players in a given roster or match, I would give every new Player a hidden unique id (using uuid or cuid) that can be used as a reference to a player, rather than storing the object reference/address directly.

There are a few other minor changes that could clean up the code. AppData could be turned into a singleton, since only one should ever be instantiated (though in the current implementation, only one is instantiated anyways). Another change would be to make the GUI much more modular. Currently, the GUI is extremely janky and somewhat cobbled-together mostly due to inexperience with Swing and what design pattern would work well with it. If I were to overhaul it, I'd abstract common functionality (like repainting or reloading a page) and potentially use a specific design pattern like MVC.

### Additional Info

Code pertaining to persistence system adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

Code pertaining to EventLog system adapted from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
