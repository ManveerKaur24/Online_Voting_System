import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class VotingResultUI1 extends JFrame {
    private JPanel Panel;
    private JLabel winnerLabel;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public VotingResultUI1() {
        super("Voting Result");

        JLabel titleLabel = new JLabel("Welcome to Result Page");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        Panel = new JPanel(new BorderLayout());

        winnerLabel = new JLabel("Winner: ");
        Panel.add(winnerLabel, BorderLayout.CENTER);

        // Create an ImageIcon object to hold the image
        ImageIcon icon = new ImageIcon("D:\\OnlineVoting\\12983106.jpg");
        Image image = icon.getImage();
        // Resize the image
        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newimg);

        // Create a JLabel and set its icon to the ImageIcon
        JLabel label = new JLabel(icon);
        label.setIcon(newImageIcon);
        JPanel imgPanel = new JPanel();
        imgPanel.add(label);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(Panel, BorderLayout.EAST);
        mainPanel.add(titleLabel, BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.add(imgPanel, BorderLayout.WEST);

        // Add the main panel to the frame
        add(mainPanel);

        try {
            // Check if the voting process has been terminated
            PreparedStatement statement = conn.prepareStatement("SELECT terminated FROM settings WHERE id = 1");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getBoolean("terminated")) {
                // Connect to the database
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_voting_system", "root",
                        "Veer@2024");

                // Retrieve the winning candidate from the database
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT candidate_name FROM candidates ORDER BY votes DESC LIMIT 1");

                // Set the winner label text
                if (rs.next()) {
                    winnerLabel.setText("Winner is : " + rs.getString("candidate_name"));
                    winnerLabel.setFont(new Font("Arial", Font.BOLD, 20));
                }
            } else {
                JOptionPane.showMessageDialog(this, "Voting is still in progress.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // Close the database connection
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Set up the window
        setTitle("Results Page");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new VotingResultUI1();
    }
}
