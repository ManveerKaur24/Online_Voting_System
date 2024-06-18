import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceOne extends JFrame {
    private JButton votebutton;
    private JButton resultbutton;

    public InterfaceOne() {
        // Disable or grey out vote button if voting is disabled

        votebutton = new JButton("Click to enter Voting Page");
        votebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VoteApp voteApp = new VoteApp();
                voteApp.setVisible(true);
            }
        });
        resultbutton = new JButton("Show Result Page");
        resultbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VotingResultUI votingResultUI = new VotingResultUI();
                votingResultUI.setVisible(true);
            }
        });

        setLayout(new FlowLayout());
        add(votebutton);
        add(resultbutton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new InterfaceOne();
    }
}