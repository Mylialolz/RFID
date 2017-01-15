import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Antoine on 12/12/2016.
 */
public class Main {


    public static void main(String[] ags){

        System.out.println("Debut...");

        GUI gui = new GUI();

        JFrame frame = new JFrame("Tagsys project");
        frame.setContentPane(gui.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            }
        });
        frame.pack();
        frame.setVisible(true);



        System.out.println("Fin.");
        return;
    }




}
