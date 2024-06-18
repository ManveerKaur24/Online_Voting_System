import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class VoteApp {
    public VoteApp(){
        try {
            // Connect to the MySQL database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_voting_system", "root", "Veer@2024");

            // Retrieve the list of candidates from the database
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM candidates");

            ArrayList<String> candidateNames = new ArrayList<>();
            while (rs.next()) {
                candidateNames.add(rs.getString("candidate_name"));
            }

            // Prompt the user to select a candidate to vote for
            Object[] candidateNamesArray = candidateNames.toArray();
            String selectedCandidate = (String) JOptionPane.showInputDialog(null, "Select a candidate to vote for:", "Vote App", JOptionPane.PLAIN_MESSAGE, null, candidateNamesArray, candidateNamesArray[0]);

            // Update the candidate's vote count in the database
            PreparedStatement updateVotesStmt = connection.prepareStatement("UPDATE candidates SET votes = votes + 1 WHERE candidate_name = ?");
            updateVotesStmt.setString(1, selectedCandidate);
            updateVotesStmt.executeUpdate();

            // Display a confirmation message
            JOptionPane.showMessageDialog(null, "Vote for " + selectedCandidate + " has been recorded");

            // Close the database connection
            connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
       new VoteApp();
    }
	public void setVisible(boolean b) {
	}
}
