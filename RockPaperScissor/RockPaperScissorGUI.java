import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RockPaperScissorGUI extends JFrame implements ActionListener{
    // player buttons
    JButton rockButton, paperButton, scissorButton;

    // will display choice of computer
    JLabel computerChoice;

    // will display the scores of computer and player
    JLabel computerScoreLabel, playerScoreLabel;

    // backend obj
    RockPaperScissor rockPaperScissor;

    public RockPaperScissorGUI(){

        // invoke JFrame constructor & give title to it
        super("Rock Paper Scissor");

        // set the size of GUI
        setSize(450, 574);

        // set Layout to null to disable layout management, so that we can do the positioning of elements & GUI like setting height/width etc.
        setLayout(null);

        // terminate java virtual machine on closing the GUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // pass null to LocationRelativeTo so that GUIis centered in the screen every time we run the application
        setLocationRelativeTo(null);

        // add GUI components
        addGUIComponents();

        // initialize backend obj
        rockPaperScissor = new RockPaperScissor();
    }

    private void addGUIComponents(){
        // create computer score label
        computerScoreLabel = new JLabel("Computer: 0");

        // set x/y co-ordinates & give height/weight to this component
        computerScoreLabel.setBounds(0, 43, 450, 30 );

        // Assign font family: dialog, weight: bold & size: 26
        computerScoreLabel.setFont(new Font("Dialog", Font.BOLD, 26));

        // place the text in center
        computerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(computerScoreLabel);

        // create computer choice
        computerChoice = new JLabel("?");
        computerChoice.setBounds(175, 118, 98, 81);
        computerChoice.setFont(new Font("Dialog", Font.PLAIN, 18));
        computerChoice.setHorizontalAlignment(SwingConstants.CENTER);

        // create black border around computer choice
        computerChoice.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(computerChoice);

        // create player score label
        playerScoreLabel = new JLabel("Player: 0");
        playerScoreLabel.setBounds(0, 317, 450, 30);
        playerScoreLabel.setFont(new Font("Dialog", Font.BOLD, 26));
        playerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(playerScoreLabel);

        // player buttons

        // rock button
        rockButton = new JButton("Rock");
        rockButton.setBounds(40, 387, 105, 81);
        rockButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        rockButton.addActionListener(this);
        add(rockButton);

        // paper button
        paperButton = new JButton("Paper");
        paperButton.setBounds(165, 387, 105, 81);
        paperButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        paperButton.addActionListener(this);
        add(paperButton);

        // scissor button
        scissorButton = new JButton("Scissors");
        scissorButton.setBounds(290, 387, 105, 81);
        scissorButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        scissorButton.addActionListener(this);
        add(scissorButton);
    }

    // displays a message dialog which will show the winner and a try again buttpn to play again
    private void showDialog(String message){
        JDialog resultDialog = new JDialog(this, "Result", true);
        resultDialog.setSize(227,124);
        resultDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        resultDialog.setResizable(false);

        // message label
        JLabel resultLabel = new JLabel(message);
        resultLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultDialog.add(resultLabel, BorderLayout.CENTER);

        // try again button
        JButton tryAgainButton = new JButton("Try Again?");
        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // reset computer choice
                computerChoice.setText("?");

                // close dialog box
                resultDialog.dispose();
            }
        });
        resultDialog.add(tryAgainButton, BorderLayout.SOUTH);
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // get player choice
        String playerChoice = e.getActionCommand();

        // play rock paper scissor & store its result
        String result = rockPaperScissor.playRockPaperScissor(playerChoice);

        computerChoice.setText(rockPaperScissor.getComputerChoice());

        // update score
        computerScoreLabel.setText("Computer: " + rockPaperScissor.getComputerScore());
        playerScoreLabel.setText("Player: " + rockPaperScissor.getPlayerScore());

        // display result dialog
        showDialog(result);
    }
}
