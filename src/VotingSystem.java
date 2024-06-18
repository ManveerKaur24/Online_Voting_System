import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class VotingSystem extends JFrame {
   private Connection conn;
   private Statement stmt;
   ResultSet rs = null;
   private JLabel statusLabel;
   private JButton voteButton;

   public VotingSystem() {
      // Initialize the GUI components
      setTitle("Online Voting System");
      setSize(400, 300);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(new FlowLayout());
      statusLabel = new JLabel("Check Voting Status.");
      voteButton = new JButton("Vote");

      add(statusLabel); 
      add(voteButton);

      // Check if voting is currently enabled or disabled
      try {
         // Connect to the MySQL database
         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_voting_system", "root", "Veer@2024");

         // Check if voting is enabled
         stmt = conn.createStatement();
         rs = stmt.executeQuery("SELECT is_voting_enabled FROM admin WHERE id = 1");
         if (rs.next() && rs.getBoolean("is_voting_enabled")) {
            // Voting is enabled, allow user to vote
            System.out.println("Voting is enabled. You can vote now.");

            // TODO: Code to allow user to vote
            voteButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  // Perform the voting process here
                  try {
                     // Connect to the MySQL database
                     Connection connection = DriverManager
                           .getConnection("jdbc:mysql://localhost:3306/online_voting_system", "root", "Veer@2024");

                     // Retrieve the list of candidates from the database
                     Statement stmt = connection.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT * FROM candidates");

                     ArrayList<String> candidateNames = new ArrayList<>();
                     while (rs.next()) {
                        candidateNames.add(rs.getString("candidate_name"));
                     }

                     // Prompt the user to select a candidate to vote for
                     Object[] candidateNamesArray = candidateNames.toArray();
                     String selectedCandidate = (String) JOptionPane.showInputDialog(null,
                           "Select a candidate to vote for:", "Vote App", JOptionPane.PLAIN_MESSAGE, null,
                           candidateNamesArray, candidateNamesArray[0]);

                     // Update the candidate's vote count in the database
                     PreparedStatement updateVotesStmt = connection
                           .prepareStatement("UPDATE candidates SET votes = votes + 1 WHERE candidate_name = ?");
                     updateVotesStmt.setString(1, selectedCandidate);
                     updateVotesStmt.executeUpdate();

                     // Display a confirmation message
                     JOptionPane.showMessageDialog(null, "Vote for " + selectedCandidate + " has been recorded");

                     // Close the database connection
                     connection.close();
                  } catch (SQLException e1) {
                     JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
                  }
                  JOptionPane.showMessageDialog(null, "Thanks for voting!");

               }
            });

         } else {
            JOptionPane.showMessageDialog(null, "Voting is currently disabled.");
         }

      } catch (SQLException e1) {
         e1.printStackTrace();
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
            if (conn != null)
               conn.close();
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
      }
   }

   public static void main(String[] args) {
      VotingSystem votingSystem = new VotingSystem();
      votingSystem.setVisible(true);
   }
}
