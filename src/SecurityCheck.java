import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SecurityCheck extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    private String adminUsername = "admin";
    private String adminPassword = "password123";

    public SecurityCheck() {
        setTitle("Admin Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);

    }

    public void actionPerformed(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            JOptionPane.showMessageDialog(null, "Login successfull.");
            // Open the admin page here
            AdminInterface1 adminInterface1 = new AdminInterface1();
            adminInterface1.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect username or password " );
        }
    }

    public static void main(String[] args) {
        SecurityCheck securityCheck = new SecurityCheck();
        securityCheck.setVisible(true);
    }
}
