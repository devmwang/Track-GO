package ui;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import exceptions.PlayerNotFoundException;
import exceptions.RosterNotFoundException;
import model.*;
import model.Event;
import persistence.StoreReader;
import persistence.StoreWriter;
import exceptions.AppDataInvalidException;

// Represents the graphical user interface for Track:GO
public class GraphicalInterface extends JFrame implements ActionListener {
    private static final String DATA_STORE_PATH = "./data/app_data.json";
    private final StoreReader storeReader;
    private final StoreWriter storeWriter;
    private AppData appData;

    HashMap<String, Object> rostersOverviewFilters;

    JPanel navbar;
    JPanel contentContainer;
    JPanel mainMenu;
    JPanel playersMenu;
    JPanel rostersMenu;
    JPanel rostersOverviewTable;
    JPanel loadDataMenu;
    JPanel saveDataMenu;

    // EFFECTS: Constructs a new GraphicalInterface
    public GraphicalInterface() {
        super("Track:GO");

        this.storeReader = new StoreReader(DATA_STORE_PATH);
        this.storeWriter = new StoreWriter(DATA_STORE_PATH);

        appData = new AppData();

        this.rostersOverviewFilters = new HashMap<>();

        setupWindowListeners();

        setLayout(new BorderLayout());
        instantiatePanels();

        add(navbar, BorderLayout.NORTH);
        add(contentContainer);
        contentContainer.add(mainMenu, "mainMenu");
        contentContainer.add(playersMenu, "playersMenu");
        contentContainer.add(rostersMenu, "rostersMenu");
        contentContainer.add(loadDataMenu, "loadDataMenu");
        contentContainer.add(saveDataMenu, "saveDataMenu");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 860));
        setResizable(true);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Sets up window listeners (e.g. override default window close behaviour)
    private void setupWindowListeners() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                printEventLog();

                System.exit(0);
            }
        });
    }

    // EFFECTS: Prints events logged in EventLog to console
    private void printEventLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: Instantiates JPanels and runs setup methods
    public void instantiatePanels() {
        this.contentContainer = new JPanel(new CardLayout());
        this.navbar = new JPanel(new GridBagLayout());
        this.mainMenu = new JPanel(new GridBagLayout());
        this.playersMenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.rostersMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        this.rostersOverviewTable = new JPanel(new GridBagLayout());
        this.loadDataMenu = new JPanel(new GridBagLayout());
        this.saveDataMenu = new JPanel(new GridBagLayout());

        setupNavbar();
        setupMainMenu();
        setupLoadConfirmation();
        setupSaveConfirmation();
    }

    // REQUIRES: e is not null
    // MODIFIES: this
    // EFFECTS: Handles events
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        CardLayout cl = (CardLayout) (contentContainer.getLayout());
        rostersOverviewFilters.clear();

        if (actionCommand.equals("mainMenu")) {
            cl.show(contentContainer, "mainMenu");
        } else if (actionCommand.equals("playersOverview")) {
            setupPlayersMenu();
            cl.show(contentContainer, "playersMenu");
        } else if (actionCommand.equals("rostersOverview")) {
            setupRostersMenu();
            cl.show(contentContainer, "rostersMenu");
        } else if (actionCommand.equals("loadAppData")) {
            cl.show(contentContainer, "loadDataMenu");
        } else if (actionCommand.equals("saveAppData")) {
            cl.show(contentContainer, "saveDataMenu");
        } else if (actionCommand.equals("exit")) {
            printEventLog();
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: Configures navbar elements
    private void setupNavbar() {
        // Instantiate buttons
        ArrayList<JButton> buttons = new ArrayList<>();
        JButton mainMenuBtn = new JButton("Main Menu");
        JButton playersOverviewBtn = new JButton("View Players");
        JButton rostersOverviewBtn = new JButton("View Rosters");
        JButton loadAppDataBtn = new JButton("Load App Data");
        JButton saveAppDataBtn = new JButton("Save App Data");
        JButton exitBtn = new JButton("Exit");

        buttons.add(mainMenuBtn);
        buttons.add(playersOverviewBtn);
        buttons.add(rostersOverviewBtn);
        buttons.add(loadAppDataBtn);
        buttons.add(saveAppDataBtn);
        buttons.add(exitBtn);

        // Set action commands
        mainMenuBtn.setActionCommand("mainMenu");
        playersOverviewBtn.setActionCommand("playersOverview");
        rostersOverviewBtn.setActionCommand("rostersOverview");
        loadAppDataBtn.setActionCommand("loadAppData");
        saveAppDataBtn.setActionCommand("saveAppData");
        exitBtn.setActionCommand("exit");

        // Final configuration for all buttons on navbar
        allNavbarBtnConfig(buttons);
    }

    // REQUIRES: buttons is not null
    // MODIFIES: this
    // EFFECTS: Configures all buttons on navbar
    private void allNavbarBtnConfig(ArrayList<JButton> buttons) {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbConstraints.weightx = 0.5;
        gbConstraints.insets = new Insets(10, 10, 50, 10);

        int gridIndex = 0;

        for (JButton button : buttons) {
            button.addActionListener(this);

            gbConstraints.gridx = gridIndex;
            navbar.add(button, gbConstraints);

            gridIndex++;
        }
    }

    // MODIFIES: this
    // EFFECTS: Configures main menu
    private void setupMainMenu() {
        ImageIcon icon = new ImageIcon("./assets/TrackGO.png");
        Image scaledIcon = icon.getImage().getScaledInstance(-1, 200, Image.SCALE_DEFAULT);
        JLabel appIcon = new JLabel(new ImageIcon(scaledIcon));
        JLabel centerText1 = new JLabel("Welcome to Track:GO!");
        centerText1.setFont(new Font(centerText1.getFont().getName(), Font.PLAIN, 24));
        JLabel centerText2 = new JLabel("Select an option from the navbar to get started.");
        centerText2.setFont(new Font(centerText2.getFont().getName(), Font.PLAIN, 20));

        GridBagConstraints gbConstraints = new GridBagConstraints();

        gbConstraints.fill = GridBagConstraints.VERTICAL;

        gbConstraints.gridy = 0;
        mainMenu.add(appIcon, gbConstraints);

        gbConstraints.insets = new Insets(15, 0, 0, 0);
        gbConstraints.gridy = 1;
        mainMenu.add(centerText1, gbConstraints);

        gbConstraints.gridy = 2;
        mainMenu.add(centerText2, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Configures players menu
    private void setupPlayersMenu() {
        playersMenu.removeAll();

        JPanel addPlayerMenu = new JPanel(new GridBagLayout());
        setupAddPlayerMenu(addPlayerMenu);
        playersMenu.add(addPlayerMenu);

        JPanel playersOverviewMenu = new JPanel(new GridBagLayout());
        setupPlayersOverviewMenu(playersOverviewMenu);
        playersMenu.add(playersOverviewMenu);
    }

    // REQUIRES: playersOverviewMenu is not null
    // MODIFIES: this
    // EFFECTS: Configures players overview menu
    private void setupPlayersOverviewMenu(JPanel playersOverviewMenu) {
        playersOverviewMenu.removeAll();
        String[] columnTitles = {"Player", "Games Played", "Games Won", "Games Lost", "Games Tied", "MVPs"};

        ArrayList<Player> playerList = appData.getPlayers();
        Object[][] tableData = new Object[playerList.size()][6];

        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            Object[] playerData = {player.getUsername(), player.getGamesPlayed(), player.getWins(),
                    player.getLosses(), (player.getGamesPlayed() - player.getWins() - player.getLosses()),
                    player.getMostValuablePlayerAwards()};
            tableData[i] = playerData;
        }

        JTable playersTable = new JTable(tableData, columnTitles);

        playersOverviewMenu.add(new JScrollPane(playersTable));
    }

    // REQUIRES: addPlayerMenu is not null
    // MODIFIES: this
    // EFFECTS: Configures menu for adding player to roster
    private void setupAddPlayerMenu(JPanel addPlayerMenu) {
        addPlayerMenu.setBorder(BorderFactory.createTitledBorder("Add Player to Roster"));
        JTextField specifiedUsername = new JTextField(15);

        JButton confirmAndAddBtn = new JButton("Create Player");

        setupAddPlayerMenuElements(addPlayerMenu, specifiedUsername, confirmAndAddBtn);

        confirmAndAddBtn.addActionListener(event -> addPlayerEventListener(specifiedUsername));
    }

    // REQUIRES: addPlayerMenu, specifiedUsername, confirmAndAddBtn are not null
    // MODIFIES: this
    // EFFECTS: Creates menu UI elements for adding player to roster
    private void setupAddPlayerMenuElements(JPanel addPlayerMenu, JTextField specifiedUsername,
                                            JButton confirmAndAddBtn) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.VERTICAL;
        gbConstraints.insets = new Insets(10, 10, 10, 10);

        gbConstraints.gridy = 0;
        addPlayerMenu.add(new JLabel("Player Username: "), gbConstraints);
        addPlayerMenu.add(specifiedUsername, gbConstraints);

        gbConstraints.gridy = 1;
        gbConstraints.gridwidth = 2;
        addPlayerMenu.add(confirmAndAddBtn, gbConstraints);
    }

    // REQUIRES: specifiedUsername is not null
    // MODIFIES: this
    // EFFECTS: Defines an event listener for the confirmation button to add a player to a roster
    private void addPlayerEventListener(JTextField specifiedUsername) {
        if (specifiedUsername.getText().equals("")) {
            JOptionPane.showMessageDialog(contentContainer, "Please enter a valid username.", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        appData.addPlayer(specifiedUsername.getText());

        setupPlayersMenu();
        ((CardLayout) (contentContainer.getLayout())).show(contentContainer, "loadDataMenu");
        ((CardLayout) (contentContainer.getLayout())).show(contentContainer, "playersMenu");
    }

    // MODIFIES: this
    // EFFECTS: Configures rosters menu
    private void setupRostersMenu() {
        rostersMenu.removeAll();

        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.VERTICAL;
        gbConstraints.insets = new Insets(20, 0, 20, 0);

        // Roster Edit System
        JPanel rosterEditMenu = new JPanel(new GridBagLayout());

        // Add Player to Roster Panel
        JPanel addPlayerToRosterMenu = new JPanel(new GridBagLayout());
        setupAddPlayerToRosterMenu(addPlayerToRosterMenu);
        gbConstraints.gridy = 0;
        rosterEditMenu.add(addPlayerToRosterMenu, gbConstraints);

        JPanel removePlayerFromRosterMenu = new JPanel(new GridBagLayout());
        setupRemovePlayerFromRosterMenu(removePlayerFromRosterMenu);
        gbConstraints.gridy = 1;
        rosterEditMenu.add(removePlayerFromRosterMenu, gbConstraints);

        rostersMenu.add(rosterEditMenu);

        // Add Roster Overview Panel
        JPanel rostersOverviewMenu = new JPanel(new GridBagLayout());

        JPanel rostersOverviewControlPanel = new JPanel(new GridBagLayout());
        setupRostersOverviewControlPanel(rostersOverviewControlPanel);

        gbConstraints.gridy = 0;
        rostersOverviewMenu.add(rostersOverviewControlPanel, gbConstraints);

        setupRostersOverviewTable();

        gbConstraints.gridy = 1;
        rostersOverviewMenu.add(rostersOverviewTable, gbConstraints);

        rostersMenu.add(rostersOverviewMenu);
    }

    // MODIFIES: this
    // EFFECTS: Reloads rosters overview table
    private void reloadRostersOverviewTable() {
        setupRostersOverviewTable();
        ((CardLayout) (contentContainer.getLayout())).show(contentContainer, "loadDataMenu");
        ((CardLayout) (contentContainer.getLayout())).show(contentContainer, "rostersMenu");
    }

    // MODIFIES: this
    // EFFECTS: Configures rosters overview table
    private void setupRostersOverviewTable() {
        rostersOverviewTable.removeAll();
        GridBagConstraints gbConstraints = new GridBagConstraints();

        // Add rosters overview table
        String[] columnTitles = {"Roster ID", "Win Rate", "Players"};
        Object[][] tableData = populateRostersOverviewTableData(rostersOverviewFilters);

        JTable rostersTable = new JTable(tableData, columnTitles);
        PlayerListCellRenderer renderer = new PlayerListCellRenderer();
        rostersTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
        rostersTable.setRowHeight(100);

        gbConstraints.gridy = 1;
        rostersOverviewTable.add(new JScrollPane(rostersTable), gbConstraints);
    }

    // REQUIRES: rostersOverviewFilters is not null
    // EFFECTS: Gets and puts roster data that fulfills any active filters into tableData 2D array
    private Object[][] populateRostersOverviewTableData(HashMap<String, Object> rostersOverviewFilters) {
        ArrayList<Roster> rostersList = (ArrayList<Roster>) appData.getRosters().clone();

        // Filter rostersList for required player
        if (rostersOverviewFilters.get("requiredPlayer") != null) {
            rostersList.removeIf(r -> !r.getPlayers().contains((Player) rostersOverviewFilters.get("requiredPlayer")));
        }

        Object[][] tableData = new Object[rostersList.size()][3];

        for (int i = 0; i < rostersList.size(); i++) {
            Roster roster = rostersList.get(i);
            Object[] playerData = {roster.getId(), roster.getWinRate(), roster.getPlayers()};
            tableData[i] = playerData;
        }

        return tableData;
    }

    // REQUIRES: rostersOverviewControlPanel is not null
    // MODIFIES: this
    // EFFECTS: Configures rosters overview control panel and returns a list of filters/control parameters
    private void setupRostersOverviewControlPanel(JPanel rostersOverviewControlPanel) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;

        JComboBox requiredPlayerSelect = new JComboBox();
        requiredPlayerSelect.addItem("N/A");

        for (Player player : appData.getPlayers()) {
            requiredPlayerSelect.addItem(player.getUsername());
        }

        gbConstraints.gridx = 0;
        JButton confirmBtn = new JButton("Confirm Filters");
        confirmBtn.addActionListener(event -> rostersFilterEventListener(requiredPlayerSelect));
        rostersOverviewControlPanel.add(requiredPlayerSelect, gbConstraints);

        gbConstraints.gridx = 1;
        rostersOverviewControlPanel.add(confirmBtn, gbConstraints);
    }

    // REQUIRES: requiredPlayerSelect is not null
    // MODIFIES: this
    // EFFECTS: Defines an event listener for the overview filter confirmation button
    private void rostersFilterEventListener(JComboBox requiredPlayerSelect) {
        try {
            rostersOverviewFilters.put("requiredPlayer",
                    appData.getPlayerByUsername((String) requiredPlayerSelect.getSelectedItem()));
        } catch (PlayerNotFoundException e) {
            rostersOverviewFilters.remove("requiredPlayer");
        }

        reloadRostersOverviewTable();
    }

    // REQUIRES: addPlayerToRosterMenu is not null
    // MODIFIES: this
    // EFFECTS: Configures menu for adding player to roster
    private void setupAddPlayerToRosterMenu(JPanel addPlayerToRosterMenu) {
        addPlayerToRosterMenu.setBorder(BorderFactory.createTitledBorder("Add Player to Roster"));
        JComboBox playerSelect = new JComboBox();

        for (Player player : appData.getPlayers()) {
            playerSelect.addItem(player.getUsername());
        }

        JComboBox rosterSelect = new JComboBox();

        for (Roster roster : appData.getRosters()) {
            rosterSelect.addItem(roster.getId());
        }

        JButton confirmAndAddBtn = new JButton("Confirm Selections");

        setupAddPlayerToRosterMenuElements(addPlayerToRosterMenu, playerSelect, rosterSelect, confirmAndAddBtn);

        confirmAndAddBtn.addActionListener(event -> addPlayerToRosterEventListener(playerSelect, rosterSelect));
    }

    // REQUIRES: addPlayerToRosterMenu, playerSelect, rosterSelect, confirmAndAddBtn are not null
    // MODIFIES: this
    // EFFECTS: Creates menu UI elements for adding player to roster
    private void setupAddPlayerToRosterMenuElements(JPanel addPlayerToRosterMenu, JComboBox playerSelect,
                                                    JComboBox rosterSelect, JButton confirmAndAddBtn) {
        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.VERTICAL;
        gbConstraints.insets = new Insets(10, 10, 10, 10);

        gbConstraints.gridy = 0;
        addPlayerToRosterMenu.add(new JLabel("Select Player: "), gbConstraints);
        addPlayerToRosterMenu.add(playerSelect, gbConstraints);

        gbConstraints.gridy = 1;
        addPlayerToRosterMenu.add(new JLabel("Select Roster: "), gbConstraints);
        addPlayerToRosterMenu.add(rosterSelect, gbConstraints);

        gbConstraints.gridy = 2;
        gbConstraints.gridwidth = 2;
        addPlayerToRosterMenu.add(confirmAndAddBtn, gbConstraints);
    }

    // REQUIRES: playerSelect, rosterSelect are not null
    // MODIFIES: this
    // EFFECTS: Defines an event listener for the confirmation button to add a player to a roster
    private void addPlayerToRosterEventListener(JComboBox playerSelect, JComboBox rosterSelect) {
        Player selectedPlayer;
        Roster selectedRoster;

        try {
            selectedPlayer = appData.getPlayerByUsername((String) playerSelect.getSelectedItem());
            selectedRoster = appData.getRosterById((String) rosterSelect.getSelectedItem());
        } catch (PlayerNotFoundException | RosterNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (selectedRoster.getPlayers().contains(selectedPlayer)) {
            JOptionPane.showMessageDialog(contentContainer, selectedPlayer.getUsername() + " is already in "
                    + selectedRoster.getId() + ".", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        selectedRoster.addPlayer(selectedPlayer);

        reloadRostersOverviewTable();
    }

    // REQUIRES: addPlayerToRosterMenu is not null
    // MODIFIES: this
    // EFFECTS: Configures menu for removing a player from a roster
    private void setupRemovePlayerFromRosterMenu(JPanel removePlayerFromRosterMenu) {
        removePlayerFromRosterMenu.setBorder(BorderFactory.createTitledBorder("Remove Player from Roster"));
        JComboBox playerSelect = new JComboBox();

        for (Player player : appData.getPlayers()) {
            playerSelect.addItem(player.getUsername());
        }

        JComboBox rosterSelect = new JComboBox();

        for (Roster roster : appData.getRosters()) {
            rosterSelect.addItem(roster.getId());
        }

        JButton confirmAndAddBtn = new JButton("Confirm Selections");

        setupAddPlayerToRosterMenuElements(removePlayerFromRosterMenu, playerSelect, rosterSelect, confirmAndAddBtn);

        confirmAndAddBtn.addActionListener(event -> removePlayerFromRosterEventListener(playerSelect, rosterSelect));
    }

    // REQUIRES: playerSelect, rosterSelect are not null
    // MODIFIES: this
    // EFFECTS: Defines an event listener for the confirmation button to remove a player from a roster
    private void removePlayerFromRosterEventListener(JComboBox playerSelect, JComboBox rosterSelect) {
        Player selectedPlayer;
        Roster selectedRoster;

        try {
            selectedPlayer = appData.getPlayerByUsername((String) playerSelect.getSelectedItem());
            selectedRoster = appData.getRosterById((String) rosterSelect.getSelectedItem());
        } catch (PlayerNotFoundException | RosterNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!selectedRoster.getPlayers().contains(selectedPlayer)) {
            JOptionPane.showMessageDialog(contentContainer, selectedPlayer.getUsername() + " is not in "
                    + selectedRoster.getId() + ".", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        selectedRoster.removePlayer(selectedPlayer);

        reloadRostersOverviewTable();
    }

    // MODIFIES: this
    // EFFECTS: Configures app data load confirmation
    private void setupLoadConfirmation() {
        loadDataMenu.removeAll();
        JLabel centerText1 = new JLabel("Are you sure you want to load app data?");
        JLabel centerText2 = new JLabel("This will overwrite any unsaved data.");
        centerText2.setForeground(Color.RED);
        JButton loadAppDataBtn = new JButton("Confirm and Load App Data");

        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.VERTICAL;

        gbConstraints.gridy = 0;
        loadDataMenu.add(centerText1, gbConstraints);

        gbConstraints.gridy = 1;
        gbConstraints.insets = new Insets(15, 0, 0, 0);
        loadDataMenu.add(centerText2, gbConstraints);

        loadAppDataBtn.addActionListener(e -> handleLoadFromFile());
        gbConstraints.gridy = 2;
        loadDataMenu.add(loadAppDataBtn, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Configures app data save confirmation
    private void setupSaveConfirmation() {
        saveDataMenu.removeAll();
        JLabel centerText1 = new JLabel("Are you sure you want to save the active app data?");
        JLabel centerText2 = new JLabel("This will overwrite your currently saved app data.");
        centerText2.setForeground(Color.RED);
        JButton saveAppDataBtn = new JButton("Confirm and Save App Data");

        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.VERTICAL;

        gbConstraints.gridy = 0;
        saveDataMenu.add(centerText1, gbConstraints);

        gbConstraints.gridy = 1;
        gbConstraints.insets = new Insets(15, 0, 0, 0);
        saveDataMenu.add(centerText2, gbConstraints);

        saveAppDataBtn.addActionListener(e -> handleSaveToFile());
        gbConstraints.gridy = 2;
        saveDataMenu.add(saveAppDataBtn, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Handles loading data from file into application
    private void handleLoadFromFile() {
        try {
            storeReader.read(appData);
            JOptionPane.showMessageDialog(contentContainer,
                    "Data loaded successfully from " + DATA_STORE_PATH + ".",
                    "Load Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            ((CardLayout) (contentContainer.getLayout())).show(contentContainer, "mainMenu");
        } catch (IOException | AppDataInvalidException e) {
            JOptionPane.showMessageDialog(contentContainer,
                    "An error occurred while loading data from " + DATA_STORE_PATH + ": " + e,
                    "Load Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: Handles saving data to file from application
    private void handleSaveToFile() {
        try {
            storeWriter.open();
            storeWriter.write(appData);
            storeWriter.close();
            JOptionPane.showMessageDialog(contentContainer,
                    "Data saved successfully to " + DATA_STORE_PATH + ".",
                    "Save Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            ((CardLayout) (contentContainer.getLayout())).show(contentContainer, "mainMenu");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(contentContainer,
                    "An error occurred while saving data to " + DATA_STORE_PATH + ": " + e,
                    "Save Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
