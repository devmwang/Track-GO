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
    JPanel playersOverviewMenu;
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

        setLayout(new BorderLayout());
        instantiatePanels();

        add(navbar, BorderLayout.NORTH);
        add(contentContainer);
        contentContainer.add(mainMenu, "mainMenu");
        contentContainer.add(playersOverviewMenu, "playersOverviewMenu");
        contentContainer.add(rostersMenu, "rostersMenu");
        contentContainer.add(loadDataMenu, "loadDataMenu");
        contentContainer.add(saveDataMenu, "saveDataMenu");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1280, 860));
        setResizable(true);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Instantiates JPanels and runs setup methods
    public void instantiatePanels() {
        this.contentContainer = new JPanel(new CardLayout());
        this.navbar = new JPanel(new GridBagLayout());
        this.mainMenu = new JPanel(new GridBagLayout());
        this.playersOverviewMenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.rostersMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        this.rostersOverviewTable = new JPanel(new GridBagLayout());
        this.loadDataMenu = new JPanel(new GridBagLayout());
        this.saveDataMenu = new JPanel(new GridBagLayout());

        setupNavbar();
        setupMainMenu();
    }

    // REQUIRES: e is not null
    // MODIFIES: this
    // EFFECTS: Handles events
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        CardLayout cl = (CardLayout) (contentContainer.getLayout());
        rostersOverviewFilters.clear();

        switch (actionCommand) {
            case "playersOverview":
                setupPlayersOverviewMenu();
                cl.show(contentContainer, "playersOverviewMenu");
                break;
            case "rostersOverview":
                setupRostersMenu();
                cl.show(contentContainer, "rostersMenu");
                break;
            case "loadAppData":
                setupLoadConfirmation();
                cl.show(contentContainer, "loadDataMenu");
                break;
            case "saveAppData":
                setupSaveConfirmation();
                cl.show(contentContainer, "saveDataMenu");
                break;
            case "exit":
                System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: Configures navbar elements
    private void setupNavbar() {
        // Instantiate buttons
        ArrayList<JButton> buttons = new ArrayList<>();
        JButton playersOverviewBtn = new JButton("View Players");
        JButton rostersOverviewBtn = new JButton("View Rosters");
        JButton loadAppDataBtn = new JButton("Load App Data");
        JButton saveAppDataBtn = new JButton("Save App Data");
        JButton exitBtn = new JButton("Exit");

        buttons.add(playersOverviewBtn);
        buttons.add(rostersOverviewBtn);
        buttons.add(loadAppDataBtn);
        buttons.add(saveAppDataBtn);
        buttons.add(exitBtn);

        // Set action commands
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
        JLabel centerText1 = new JLabel("Welcome to Track:GO!");
        JLabel centerText2 = new JLabel("Select an option from the navbar to get started.");

        GridBagConstraints gbConstraints = new GridBagConstraints();

        gbConstraints.fill = GridBagConstraints.VERTICAL;

        gbConstraints.gridy = 0;
        mainMenu.add(centerText1, gbConstraints);

        gbConstraints.gridy = 1;
        gbConstraints.insets = new Insets(15, 0, 0, 0);
        mainMenu.add(centerText2, gbConstraints);
    }

    // MODIFIES: this
    // EFFECTS: Configures players overview menu
    private void setupPlayersOverviewMenu() {
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

    // MODIFIES: this
    // EFFECTS: Configures rosters menu
    private void setupRostersMenu() {
        rostersMenu.removeAll();

        // Add Player to Roster Panel
        JPanel addPlayerToRosterMenu = new JPanel(new GridBagLayout());
        setupAddPlayerToRosterMenu(addPlayerToRosterMenu);
        rostersMenu.add(addPlayerToRosterMenu);

        // Add Roster Overview Panel
        JPanel rostersOverviewMenu = new JPanel(new GridBagLayout());

        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.VERTICAL;

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

        gbConstraints.insets = new Insets(15, 0, 0, 0);
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
        gbConstraints.insets = new Insets(10, 10, 0, 0);

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

    // MODIFIES: this
    // EFFECTS: Configures app data load confirmation
    private void setupLoadConfirmation() {
        loadDataMenu.removeAll();
        JLabel centerText1 = new JLabel("Are you sure you want to load app data?");
        JLabel centerText2 = new JLabel("This will overwrite any unsaved data.");
        centerText2.setForeground(Color.RED);
        JButton loadAppDataBtn = new JButton("Load App Data");

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
        JButton saveAppDataBtn = new JButton("Save App Data");

        GridBagConstraints gbConstraints = new GridBagConstraints();
        gbConstraints.fill = GridBagConstraints.VERTICAL;

        gbConstraints.gridy = 0;
        saveDataMenu.add(centerText1, gbConstraints);

        gbConstraints.gridy = 1;
        gbConstraints.insets = new Insets(15, 0, 0, 0);
        saveDataMenu.add(centerText2, gbConstraints);

        saveAppDataBtn.addActionListener(e -> handleLoadFromFile());
        gbConstraints.gridy = 2;
        saveDataMenu.add(saveAppDataBtn, gbConstraints);
    }

    // EFFECTS: Handles loading data from file into application
    private void handleLoadFromFile() {
        try {
            storeReader.read(appData);
            System.out.println("Data loaded successfully from " + DATA_STORE_PATH + ".");
        } catch (IOException | AppDataInvalidException e) {
            System.out.println("An error occurred while loading data from " + DATA_STORE_PATH + ": " + e);
        }
    }

    // EFFECTS: Handles saving data to file from application
    private void handleSaveToFile() {
        try {
            storeWriter.open();
            storeWriter.write(appData);
            storeWriter.close();
            System.out.println("Data saved successfully to " + DATA_STORE_PATH + ".");
        } catch (IOException e) {
            System.out.println("An error occurred while saving data to " + DATA_STORE_PATH + ": " + e);
        }
    }
}
