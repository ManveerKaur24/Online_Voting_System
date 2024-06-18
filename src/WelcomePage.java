import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;

public class WelcomePage extends JFrame implements ActionListener {

    // UI components
    private JLabel titleLabel;
    private JButton loginButton, registerButton, candidateButton, adminButton;
    private JLabel label;

    // Constructor
    public WelcomePage() {
        // Set up UI components
        titleLabel = new JLabel("Welcome to Online Voting System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        candidateButton = new JButton("Candidate Section");
        adminButton= new JButton("Admin Section");

        // Add action listeners to buttons
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
        candidateButton.addActionListener(this);
        adminButton.addActionListener(this);

        // Create the panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(candidateButton);
        buttonPanel.add(adminButton);

        // Create an ImageIcon object to hold the image
        ImageIcon icon = new ImageIcon("D:\\OnlineVoting\\indianvoting.jpg");
        Image image = icon.getImage();
        // Resize the image
        Image newimg = image.getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(newimg);

        // Create a JLabel and set its icon to the ImageIcon
        label = new JLabel(icon);
        label.setIcon(newImageIcon);
        JPanel imgPanel = new JPanel();
        imgPanel.add(label);

        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(titleLabel, BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.add(imgPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        add(mainPanel);

        // Set up JFrame properties
        setTitle("Welcome");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    // ActionListener method for button clicks
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Open the second page in a new window
            UserPage userPage = new UserPage();
            userPage.setVisible(true);

        } else if (e.getSource() == registerButton) {
            RegistrationPage registrationPage = new RegistrationPage();
            registrationPage.setVisible(true);
        }
         else if (e.getSource() == candidateButton) {
            CandidateSection candidateSection = new CandidateSection();
            candidateSection.setVisible(true);
        }
         else if (e.getSource() ==  adminButton) {
            SecurityCheck securityCheck = new SecurityCheck();
            securityCheck.setVisible(true);
        }

    }

    // Main method to run the program
    public static void main(String[] args) {
        new WelcomePage();
    }
}
