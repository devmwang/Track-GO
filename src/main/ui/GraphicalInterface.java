package ui;

import java.util.concurrent.TimeUnit;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.AppData;
import persistence.StoreReader;
import persistence.StoreWriter;
import exceptions.AppDataInvalidException;

// Represents the graphical user interface for Track:GO
public class GraphicalInterface extends JFrame implements ActionListener {
    private static final String DATA_STORE_PATH = "./data/app_data.json";
    private final StoreReader storeReader;
    private final StoreWriter storeWriter;
    private AppData appData;

    JPanel navbar;
    JPanel contentContainer;
    JPanel mainMenu;
    JPanel loadDataMenu;
    JPanel saveDataMenu;

    // EFFECTS: Constructs a new GraphicalInterface
    public GraphicalInterface() {
        super("Track:GO");

        this.storeReader = new StoreReader(DATA_STORE_PATH);
        this.storeWriter = new StoreWriter(DATA_STORE_PATH);

        appData = new AppData();

        setLayout(new BorderLayout());
        instantiatePanels();

        add(navbar, BorderLayout.NORTH);
        add(contentContainer);
        contentContainer.add(mainMenu, "mainMenu");
        contentContainer.add(loadDataMenu, "loadDataMenu");
        contentContainer.add(saveDataMenu, "saveDataMenu");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setResizable(true);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Instantiates JPanels and runs setup methods
    public void instantiatePanels() {
        this.contentContainer = new JPanel(new CardLayout());
        this.navbar =  new JPanel(new GridBagLayout());
        this.mainMenu = new JPanel(new GridBagLayout());
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
        CardLayout cl = (CardLayout)(contentContainer.getLayout());

        switch (actionCommand) {
            case "loadAppData":
                cl.show(contentContainer, "loadDataMenu");
                break;
            case "saveAppData":
                cl.show(contentContainer, "saveDataMenu");
                break;
            case "exit":
                System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: Configures navbar elements
    private void setupNavbar() {
        GridBagConstraints gbConstraints = new GridBagConstraints();

        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbConstraints.weightx = 0.5;
        gbConstraints.insets = new Insets(10, 10, 10, 10);

        // Instantiate buttons
        JButton loadAppDataBtn = new JButton("Load App Data");
        JButton saveAppDataBtn = new JButton("Save App Data");
        JButton exitBtn = new JButton("Exit");

        // Set action commands
        loadAppDataBtn.setActionCommand("loadAppData");
        saveAppDataBtn.setActionCommand("saveAppData");
        exitBtn.setActionCommand("exit");

        // Add action listeners
        loadAppDataBtn.addActionListener(this);
        saveAppDataBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        // Add buttons to navbar
        gbConstraints.gridx = 0;
        navbar.add(loadAppDataBtn, gbConstraints);

        gbConstraints.gridx = 1;
        navbar.add(saveAppDataBtn, gbConstraints);

        gbConstraints.gridx = 2;
        navbar.add(exitBtn, gbConstraints);
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
    // EFFECTS: Configures app data load confirmation
    private void setupLoadConfirmation() {
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
