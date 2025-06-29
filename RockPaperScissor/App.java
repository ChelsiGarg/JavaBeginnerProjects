import javax.swing.*;

public class App {

    public static void main(String[] args){
        // use invokeLater function to run GUI on EDT(Event Dispatch Thread) to avoid weird bugs/crashes
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                // instantiate A RockPaperScissorGUI obj
                RockPaperScissorGUI rockPaperScissorGUI = new RockPaperScissorGUI();

                // display the GUI
                rockPaperScissorGUI.setVisible(true);
            }
        });
    }
    
}
