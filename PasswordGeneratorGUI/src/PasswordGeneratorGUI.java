// this class will render GUI components (frontend) & will inherit from JFrame class

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class PasswordGeneratorGUI extends JFrame{
    // to call generatePassword() method in other class (that takes care of backend of this application), create object of that class
    private PasswordGenerator passwordGenerator;

    public PasswordGeneratorGUI(){
        // render frame & add a title
        super("Password Generator");

        // set the size of GUI
        setSize(540, 570);

        // prevent GUI from being able to resize
        setResizable(false);

        // set layout to null so that we have the control over position & size of our components
        setLayout(null);

        // terminate the program when GUI is closed (ends the process)
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // center the GUI to the screen (null will set Location Relative to screen)
        setLocationRelativeTo(null);

        // initalize password generator object
        passwordGenerator = new PasswordGenerator();

        // render GUI components (GUI components are those which you see on the frame)
        addGuiComponents();
    }

    private void addGuiComponents(){
        // create title text 
        JLabel titleLabel = new JLabel("Password Generator");

        // increase font size & make it bold
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // center the title to the screen
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // set x,y co-ordinates & height-width values
        titleLabel.setBounds(0,10,540,39);

        // add to GUI
        add(titleLabel);


        // create result text area
        JTextArea passwordOutput = new JTextArea();

        // prevent editing the text area
        passwordOutput.setEditable(false);
        passwordOutput.setFont(new Font("Dialog", Font.BOLD, 32));

        // add scrollability in case output becomes too big
        JScrollPane passwordOutputPane = new JScrollPane(passwordOutput);
        passwordOutputPane.setBounds(25,97,479,70);

        // create a black border around text area
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(passwordOutputPane);

        // create password length label
        JLabel passwordLengthLabel = new JLabel("Password Length: ");
        passwordLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthLabel.setBounds(25,215,272,39);
        add(passwordLengthLabel);

        // create password length input
        JTextArea passwordLengthInputArea = new JTextArea();
        passwordLengthInputArea.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthInputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLengthInputArea.setBounds(310, 215, 192, 39);
        add(passwordLengthInputArea);

        // create toggle buttons
        // uppercase toggle button
        JToggleButton uppercaseToggle = new JToggleButton("Uppercase");
        uppercaseToggle.setBounds(25,302,225,56);
        uppercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(uppercaseToggle);

        // lowercase toggle button
        JToggleButton lowercaseToggle = new JToggleButton("Lowercase");
        lowercaseToggle.setBounds(282,302,225,56);
        lowercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(lowercaseToggle);

        // numbers toggle button
        JToggleButton numbersToggle = new JToggleButton("Numbers");
        numbersToggle.setBounds(25,373,225,56);
        numbersToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(numbersToggle);

        // symbols toggle button
        JToggleButton symbolsToggle = new JToggleButton("Symbols");
        symbolsToggle.setBounds(282, 373, 225, 56);
        symbolsToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        add(symbolsToggle);

        // create generate button
        JButton generateButton = new JButton("Generate");
        generateButton.setFont(new Font("Dialog", Font.PLAIN, 32));
        generateButton.setBounds(155, 477, 222, 41);

        // to listen to button presses, we use ActionListener. The code inside action performed will run if the button was pressed
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // validation: generate password only when length>0 & one of the toggle buttons is pressed
                if(passwordLengthInputArea.getText().length()<=0) return;

                // isSelected will return boolean depending on if the user pressed the button or not
                boolean anyToggleSelected = uppercaseToggle.isSelected() || lowercaseToggle.isSelected() || 
                                            numbersToggle.isSelected() || symbolsToggle.isSelected();
                
                // generate password
                // convert the text into integer value
                int passwordLength = Integer.parseInt(passwordLengthInputArea.getText());
                if(anyToggleSelected){
                    String generatedPassword = passwordGenerator.generatePassword(passwordLength, 
                                               uppercaseToggle.isSelected(),     
                                               lowercaseToggle.isSelected(), 
                                               numbersToggle.isSelected(), 
                                               symbolsToggle.isSelected());

                    // display output to user
                    passwordOutput.setText(generatedPassword);
                }
            }
        });
        add(generateButton);
    }

}
