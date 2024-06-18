import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class OnlineVotingSystem extends JFrame {
    private Connection connection;
    private JLabel resultLabel;

    public OnlineVotingSystem() {
        // Create UI components
        JLabel titleLabel = new JLabel("Online Voting System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel = new JLabel();
        JButton showResultButton = new JButton("Show Results");
        showResultButton.addActionListener(e -> showResults());

        // Add components to the frame
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(showResultButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(resultLabel);
        add(panel);

        // Set frame properties
        setTitle("Online Voting System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        // Connect to the MySQL database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showResults() {
        try {
            // Check if the voting process has been terminated
            PreparedStatement statement = connection.prepareStatement("SELECT terminated FROM settings WHERE id = 1");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getBoolean("terminated")) {
                // Display voting results
                statement = connection.prepareStatement("SELECT candidate, COUNT(*) AS votes FROM votes GROUP BY candidate ORDER BY votes DESC");
                resultSet = statement.executeQuery();
                StringBuilder builder = new StringBuilder("<html><b>Voting Results</b><br>");
                while (resultSet.next()) {
                    builder.append(resultSet.getString("candidate")).append(": ").append(resultSet.getInt("votes")).append("<br>");
                }
                builder.append("</html>");
                resultLabel.setText(builder.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Voting is still in progress.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OnlineVotingSystem().setVisible(true);
        });
    }
}

