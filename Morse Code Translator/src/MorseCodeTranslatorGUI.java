import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

// key listener is used here so that we can listen to the key presses (user tying or giving input)
public class MorseCodeTranslatorGUI extends JFrame implements KeyListener{
    private MorseCodeController morseCodeController;

    // textInputArea - user input (text to be translated)
    // morseCodeArea - text translated to morse code
    private JTextArea textInputArea, morseCodeArea;

    public MorseCodeTranslatorGUI(){
        // adds text to the title bar
        super("Morse Code Translator");

        // sets the size of the frame to be 540x760 pixels
        setSize(new Dimension(540, 760));

        // prevents GUI from being able to be resized
        setResizable(false);

        // setting the layout to be null allows us to manually position & set the size of the components in our GUI
        setLayout(null);

        // exits the program when closing GUI
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // sets the background color
        getContentPane().setBackground(Color.decode("#264653"));

        // places the GUI in the center of screen when ran
        setLocationRelativeTo(null);

        morseCodeController = new MorseCodeController();
        addGuiComponents();
    }

    private void addGuiComponents(){
        // title label
        JLabel titleLabel = new JLabel("Morse Code Translator");

        // sets the font size & font weight for the label
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));

        // sets the font color of the text to be white
        titleLabel.setForeground(Color.WHITE);

        // centers text (relative to its container's width)
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // sets x,y position and width, height of title label
        // to make sure that the title aligns to the center of our GUI, we made it the same width 
        titleLabel.setBounds(0,0,540,100);

        // text input
        JLabel textInputLabel = new JLabel("Text: ");
        textInputLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        textInputLabel.setForeground(Color.WHITE);
        textInputLabel.setBounds(20,100,200,30);

        // text input area
        textInputArea = new JTextArea();
        textInputArea.setFont(new Font("Dialog", Font.PLAIN, 18));

        // make it so that we can listen to key presses whenever we are typing in this text area
        textInputArea.addKeyListener(this);

        // gives 10px padding in all directions in text area
        textInputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // words wrap up to next line after reaching the end of text area. 
        // If it is set to false, words will continue in same line & hence need to scroll horizontally to read entire line
        textInputArea.setLineWrap(true);

        // boundary words get wrapped up & doesn't split off.
        textInputArea.setWrapStyleWord(true);

        // add scrolling ability to input text area
        JScrollPane textInputScroll = new JScrollPane(textInputArea);
        textInputScroll.setBounds(20, 132, 484, 236);

        // morse code input
        JLabel morseCodeInputLabel = new JLabel("Morse Code: ");
        morseCodeInputLabel.setFont(new Font("Dialog", Font.PLAIN, 18));
        morseCodeInputLabel.setForeground(Color.WHITE);
        morseCodeInputLabel.setBounds(20, 390, 200, 30);

        morseCodeArea = new JTextArea();
        morseCodeArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        morseCodeArea.setEditable(false);
        morseCodeArea.setLineWrap(true);
        morseCodeArea.setWrapStyleWord(true);
        morseCodeArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // add scrolling ability to morse code text area
        JScrollPane morseCodeScroll = new JScrollPane(morseCodeArea);
        morseCodeScroll.setBounds(20, 430, 484, 236);

        // play sound button
        JButton playSoundButton = new JButton("Play Sound");
        playSoundButton.setBounds(210, 680, 100, 30);
        playSoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // disable the play sound button (prevents the sound from getting interrupted)
                playSoundButton.setEnabled(false);

                Thread playMorseCodeThread = new Thread(new Runnable() {
                    @Override
                    public void run(){
                        // attempt to play the morse code sound
                        try{
                            String[] morseCodeMessage = morseCodeArea.getText().split(" ");
                            morseCodeController.playSound(morseCodeMessage); 
                        }catch(LineUnavailableException lineUnavailableException){
                            lineUnavailableException.printStackTrace();
                        }catch(InterruptedException interruptedException){
                            interruptedException.printStackTrace();
                        }finally{
                            // enable play sound button
                            playSoundButton.setEnabled(true);
                        }
                    }
                });
                playMorseCodeThread.start();
            }
        });

        // add to GUI
        add(titleLabel);
        add(textInputLabel);
        add(textInputScroll);
        add(morseCodeInputLabel);
        add(morseCodeScroll);
        add(playSoundButton);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ignore shift key press as these keys are pressed to change the case but doesn't directly change the case
        if(e.getKeyCode() != KeyEvent.VK_SHIFT){
            // retrieve text input
            String inputText = textInputArea.getText();

            // update the GUI with translated text
            morseCodeArea.setText(morseCodeController.translateToMorse(inputText));
        }
    }
}
