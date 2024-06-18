import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class CandidateSection implements ActionListener {
    JFrame frame;
    JPanel panel;
    JLabel titleLabel, nameLabel, partyLabel, candidate_idJLabel, votesJLabel;
    JTextField candidate_nameText, party_nameText, candidate_idText, votesText;
    JButton addButton, updateButton, deleteButton;

    // Database Connection
    private Connection connection;

    public CandidateSection() {
        frame = new JFrame("Candidate Section");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        titleLabel = new JLabel("Candidates");
        titleLabel.setBounds(150, 20, 100, 30);
        panel.add(titleLabel);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 60, 60, 20);
        panel.add(nameLabel);

        candidate_nameText = new JTextField();
        candidate_nameText.setBounds(130, 60, 150, 20);
        panel.add(candidate_nameText);

        candidate_idJLabel = new JLabel("Candidate ID:");
        candidate_idJLabel.setBounds(50, 80, 80, 20);
        panel.add(candidate_idJLabel);

        candidate_idText = new JTextField();
        candidate_idText.setBounds(130, 80, 150, 20);
        panel.add(candidate_idText);

        partyLabel = new JLabel("Party:");
        partyLabel.setBounds(50, 100, 80, 20);
        panel.add(partyLabel);

        party_nameText = new JTextField();
        party_nameText.setBounds(130, 100, 150, 20);
        panel.add(party_nameText);

        votesJLabel = new JLabel("Votes:");
        votesJLabel.setBounds(50, 120, 80, 20);
        panel.add(votesJLabel);

        votesText = new JTextField();
        votesText.setBounds(130, 120, 150, 20);
        panel.add(votesText);

        addButton = new JButton("Add");
        addButton.setBounds(50, 150, 80, 20);
        addButton.addActionListener(this);
        panel.add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(150, 150, 80, 20);
        updateButton.addActionListener(this);
        panel.add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(250, 150, 80, 20);
        deleteButton.addActionListener(this);
        panel.add(deleteButton);

        frame.add(panel);
        frame.setVisible(true);

        // Set up the database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/online_voting_system";
            String user = "root";
            String password = "Veer@2024";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            // JOptionPane.showMessageDialog(null, "Error connecting to database: " +
            //         ex.getMessage());
            // System.exit(0);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Code to add a candidate to the database
            // Do something with the Candidate's name, email, and voter ID
            String candidate_name = candidate_nameText.getText();
            String party_name = party_nameText.getText();
            int candidate_id = Integer.parseInt(candidate_idText.getText());
            int votes = Integer.parseInt(votesText.getText());

            try {
                // Insert the data into the database
                PreparedStatement statement = connection
                        .prepareStatement(
                                "INSERT INTO candidates (candidate_id, candidate_name, party_name, votes) VALUES (?, ?, ?, ?)");
                statement.setInt(1, candidate_id);
                statement.setString(2, candidate_name);
                statement.setString(3, party_name);
                statement.setInt(4, votes);

                // Execute SQL statement
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(null, "Candidate with ID " + candidate_id + " Added successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Candidate with ID " + candidate_id + " not Added.");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error saving data: " + ex.getMessage());
            }

        } else if (e.getSource() == updateButton) {
            // Code to update a candidate in the database
            String candidate_name = candidate_nameText.getText();
            String party_name = party_nameText.getText();

            try {
                int candidate_id = Integer.parseInt(candidate_idText.getText());

                // Prepare SQL statement to delete candidate from candidates table
                String sql = "UPDATE candidates SET candidate_name=?, party_name=? WHERE candidate_id =?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(3, candidate_id);
                stmt.setString(1, candidate_name);
                stmt.setString(2, party_name);

                // Execute SQL statement
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(null, "Candidate with ID " + candidate_id + " updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Candidate with ID " + candidate_id + " not found.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid candidate ID.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == deleteButton) {
            // Code to delete a candidate from the database
            try {
                // Get candidate ID from text field
                int candidateID = Integer.parseInt(candidate_idText.getText());

                // Prepare SQL statement to delete candidate from candidates table
                String sql = "DELETE FROM candidates WHERE candidate_id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, candidateID);

                // Execute SQL statement
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(null, "Candidate with ID " + candidateID + " deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Candidate with ID " + candidateID + " not found.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid candidate ID.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new CandidateSection();
    }

    public void setVisible(boolean b) {
    }
}
