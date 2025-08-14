import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args){
        // invoke later ensures that GUI is created & updated in thread safe manner
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run(){
                new MorseCodeTranslatorGUI().setVisible(true);
            }
        });
    }
}
