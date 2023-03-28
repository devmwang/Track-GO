# CPSC 210 Term Project (Track:GO)

## CS:GO Player/Roster Statistics Tracker and Visualizer

### What will this project (Track:GO) do?

Track:GO will allow users to enter CS:GO match data in order to analyze and track specifics
with greater ease. The user will be able to enter data from matches they play. Then, they
can view data from each prior match, view overall data (like overall win rate or average 
damage per round), or perform trend analysis with the program to see how their performance
may be improving or declining.

### Who will use it?

- General/regular players of CS:GO to track their personal and friend stats.
- CS:GO team coaches/data analysts (to help them analyze historical data more effectively).

### Why this project is of interest to me

I play CS:GO quite often (with a group of friends). Often, we want to be able to look at 
and analyze historical data, or view personalized stats. Additionally, we find it
interesting to view data on group data, like map win rate as a group, or overall win rate
depending on who is playing.

## User Stories

- As a user, I want to be able to create new rosters.
- As a user, I want to be able to create new players.
- As a user, I want to be able to add a player to a roster.
- As a user, I want to be able to add data from a match to a roster.
- As a user, I want to be able to add match data to a player for a specific match.
- As a user, I want to be able to select a roster and view overall stats for that roster.
- As a user, when I select the quit option from the main menu, I want to be given the option to save all the entered data to a file.
- As a user, when the application is launched, I want to be given the option to load player/roster/match data from a file.

## Instructions for Grader (Phase 3)

- You can generate the first required action: adding a player to a roster by navigating to the Rosters page (`View Rosters` button in the navbar), then selecting a player and a roster with the selection boxes in the `Add Player to Roster` panel, then confirm the add with the `Confirm Selections` button.
- You can generate the second required action: filtering the displayed rosters to only ones with a certain player by navigating to the Rosters page (`View Rosters` button in the navbar), then selecting a required player (only show rosters with the required player), then confirm the filter with the `Confirm Filters` button.
- You can locate my visual component in the main menu, shown by default (or accessible through the navbar with the `Main Menu` button.
- You can save app data by selecting `Save App Data` in the navbar, then confirm the load with the `Confirm and Save App Data` button.
- You can load app data by selecting `Load App Data` in the navbar, then confirm the load with the `Confirm and Load App Data` button.

### Additional Info
Code pertaining to persistence system adapted from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo