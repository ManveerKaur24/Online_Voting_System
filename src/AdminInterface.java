import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminInterface extends JFrame implements ActionListener {
    private JButton enableVotingButton;
    private JButton terminateVotingButton;
    private JTable votersTable;
    private JTable candidatesTable;
    private JScrollPane votersScrollPane;
    private JScrollPane candidatesScrollPane;
    private JLabel statusLabel;

    public AdminInterface() {
        super("Admin Interface");

        // create GUI components
        enableVotingButton = new JButton("Enable Voting");
        enableVotingButton.addActionListener(this);

        terminateVotingButton = new JButton("Terminate Voting");
        terminateVotingButton.addActionListener(this);

        votersTable = new JTable();
        candidatesTable = new JTable();
        votersScrollPane = new JScrollPane(votersTable);
        candidatesScrollPane = new JScrollPane(candidatesTable);

        // add components to the frame
        JPanel panel = new JPanel();
        // panel.add(enableVotingButton);
        // panel.add(terminateVotingButton);
        panel.add(votersScrollPane);
        panel.add(candidatesScrollPane);
        add(panel);

        // Create the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(enableVotingButton);
        buttonPanel.add(terminateVotingButton);
        add(buttonPanel);

        statusLabel = new JLabel("Voting is currently disabled.");
        add(statusLabel);

        // set frame properties
        setSize(1100, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        // pack();

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(panel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // load data from database
        loadVotersData();
        loadCandidatesData();
    }

    private void loadVotersData() {
        try {
            // connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/online_voting_system", "root",
                    "Veer@2024");

            // execute query to retrieve voters data
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM registration");

            // create table model to display voters data
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("name");
            tableModel.addColumn("email");
            tableModel.addColumn("voterID");
            tableModel.addColumn("password");

            // populate table model with data
            while (resultSet.next()) {
                Object[] row = new Object[4];
                row[0] = resultSet.getString("name");
                row[1] = resultSet.getString("email");
                row[2] = resultSet.getInt("voterID");
                row[3] = resultSet.getString("password");
                tableModel.addRow(row);
            }

            // set table model for voters table
            votersTable.setModel(tableModel);

            // close database connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if voting is enabled or not
    public static boolean isVotingEnabled() {
        boolean votingEnabled = false;
        try {
            // Open a connection
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/online_voting_system", "root",
                    "Veer@2024");

            // Execute a query
            System.out.println("Checking voting status...");
            Statement statement = connection.createStatement();

            String sql = "SELECT is_voting_enabled FROM admin";
            ResultSet rs = statement.executeQuery(sql);

            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                votingEnabled = rs.getBoolean("is_voting_enabled");
            }

            // Clean-up environment
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
        }
        return votingEnabled;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enableVotingButton) {
            statusLabel.setText("Voting is enabled.");
            // TODO: Enable voting logic here

            try {
                // Open a connection
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/online_voting_system",
                        "root",
                        "Veer@2024");
                Statement statement = connection.createStatement();

                // Execute a query
                System.out.println("Updating voting status...");
                statement = connection.createStatement();
                String sql = "UPDATE admin SET is_voting_enabled = " + enableVotingButton.isSelected();
                statement.executeUpdate(sql);

                // Clean-up environment
                statement.close();
                connection.close();
            } catch (SQLException se) {

            }

        } else if (e.getSource() == terminateVotingButton) {
            statusLabel.setText("Voting is terminated.");
            // TODO: Terminate voting logic here
        }
    }

    private void loadCandidatesData() {
        try {
            // connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/online_voting_system", "root",
                    "Veer@2024");

            // execute query to retrieve candidates data
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM candidates");

            // create table model to display candidates data
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("candidate_id");
            tableModel.addColumn("candidate_name");
            tableModel.addColumn("party_name");

            // populate table model with data
            while (resultSet.next()) {
                Object[] row = new Object[3];
                row[0] = resultSet.getInt("candidate_id");
                row[1] = resultSet.getString("candidate_name");
                row[2] = resultSet.getString("party_name");
                tableModel.addRow(row);
            }

            // set table model for candidates table
            candidatesTable.setModel(tableModel);

            // close database connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // create admin interface
        new AdminInterface();
    }
}
