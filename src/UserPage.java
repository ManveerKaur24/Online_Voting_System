import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserPage extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private JLabel nameLabel, emailLabel, voterIDLabel, pressLabel, passwordLabel;
    private JTextField nameText, emailText, voterIDText;
    private JButton loginButton;
    private JPasswordField passwordField;

    // Database Connection
    private Connection connection;

    public UserPage() {
        setTitle("UserPage");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        nameLabel = new JLabel("Name:");
        add(nameLabel);

        nameText = new JTextField(20);
        add(nameText);

        emailLabel = new JLabel("Email:");
        add(emailLabel);

        emailText = new JTextField(20);
        add(emailText);

        voterIDLabel = new JLabel("Voter ID:");
        add(voterIDLabel);

        voterIDText = new JTextField(20);
        add(voterIDText);

        passwordLabel = new JLabel("Password:");
        add(passwordLabel);
        passwordField = new JPasswordField(20);
        add(passwordField);

        pressLabel = new JLabel("Next Page");
        add(pressLabel);

        loginButton = new JButton("Login");
        add(loginButton);
        loginButton.addActionListener(this);

        setVisible(true);

        // Set up the database connection
        try {
                         
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/online_voting_system";
            String user = "root";
            String password = "Veer@2024";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            // JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage());
            // System.exit(0);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // First action listener code goes here
            // Do something with the user's name, email, and voter ID
            String name = nameText.getText();
            String email = emailText.getText();
            int voterID = Integer.parseInt(voterIDText.getText());
            String password = new String(passwordField.getPassword());

            try {
                // Insert the data into the database
                PreparedStatement statement = connection
                        .prepareStatement("INSERT INTO users (name, email, voterID,password) VALUES (?, ?, ?, ?)");
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setInt(3, voterID);
                statement.setString(4, password);

                int rows = statement.executeUpdate();

                // Check if any rows were affected
                if (rows > 0) {
                    // Display a message to the user that their data has been updated
                    JOptionPane.showMessageDialog(null, "Login successfull");

                    // Second action listener code goes here
                    InterfaceOne interfaceOne = new InterfaceOne();
                    interfaceOne.setVisible(true);
                    // VotingSystem votingSystem = new VotingSystem();
                    // votingSystem.setVisible(true);
                } else {
                    // Display a message to the user that no rows were updated
                    System.out.println("No rows were updated");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Incorrect password ");
            }

        }

    }
                         
    public static void main(String[] args) {
        new UserPage();
    }
}
