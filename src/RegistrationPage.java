import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class RegistrationPage extends JFrame implements ActionListener {
    // Database Connection
    private Connection connection;

    private static final long serialVersionUID = 1L;

    private JLabel nameLabel, emailLabel, voterIDLabel, passwordLabel, confirmPasswordLabel;
    private JTextField nameText, emailText, voterIDText;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton submitButton;

    public RegistrationPage() {
        setTitle("New Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

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

        confirmPasswordLabel = new JLabel("Confirm Password:");
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField(20);
        add(confirmPasswordField);

        submitButton = new JButton("Submit");
        add(submitButton);
        submitButton.addActionListener(this);

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
        if (e.getSource() == submitButton) {
            String name = nameText.getText();
            String email = emailText.getText();
            int voterID = Integer.parseInt(voterIDText.getText());
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            // Save the new user registration information in a database or file
            // and perform any additional processing as needed
            try {
                // Insert the data into the database
                PreparedStatement statement = connection
                        .prepareStatement(
                                "INSERT INTO registration (name, email, voterID, password, confirmPassword) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setInt(3, voterID);
                statement.setString(4, password);
                statement.setString(5, confirmPassword);

                int rows = statement.executeUpdate();

                // Check if any rows were affected
                if (rows > 0) {
                    // Display a message to the user that their data has been updated
                    JOptionPane.showMessageDialog(null, "Registration successfull");

                } else {
                    // Display a message to the user that no rows were updated
                    System.out.println("No rows were updated");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }

        }
    }

    public static void main(String[] args) {
        new RegistrationPage();
    }
}
