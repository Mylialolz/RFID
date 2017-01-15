import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Antoine on 15/01/2017.
 */
public class GUI {
    private JPanel panel1;
    private JButton JButtonResolution;
    private JTextField répertoireFichiersCsvTextField;
    private JLabel LogoTagSys;
    private JButton folderCSV;
    private JScrollPane scrollPane;
    private JLabel JLabelScore;
    private JTable table;

    public ArrayList<Tag> tags = new ArrayList<>();
    DefaultTableModel model;


    public GUI(){
        JButtonResolution.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = model.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    model.removeRow(i);
                }
                preProcessing();
                affichageCentrale();
            }
        });
        folderCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chercherFolder();
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void chercherFolder(){
        JFileChooser dialogue = new JFileChooser(new File("."));
        dialogue.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (dialogue.showOpenDialog(null)==
                JFileChooser.APPROVE_OPTION) {
            File fichier = dialogue.getSelectedFile();
            répertoireFichiersCsvTextField.setText(fichier.getAbsolutePath()+"\\");
        }
    }

    public void affichageCentrale(){
        for(Tag t : tags){
            Vector row = new Vector();
            row.add(t.getTag());
            row.add(t.getRSSI());
            row.add(t.zonePlacement);
            row.add(t.classification);
            model.addRow(row);
        }
    }

    public void preProcessing(){
        JLabelScore.setText("Score : 0");
        String directoryPath = répertoireFichiersCsvTextField.getText();
        if(directoryPath != null) {
            ArrayList<String> csvFiles = Directory.listFiles(directoryPath);
            int i = 1;
            ArrayList<Tag> tag = new ArrayList<>();
            for (String s : csvFiles) {
                System.out.println("Nouveau fichier...");
                CSVReader csvReader = new CSVReader();
                csvReader.getContent(directoryPath + s, "" + i, tag);
                ++i;
            }
            for (Tag t : tag) {
                t.deleteDoublons();
            }

            for (Tag t : tag) {
                t.print();
            }
            tags = (ArrayList<Tag>) tag.clone();
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        LogoTagSys = new JLabel();
        LogoTagSys.setIcon(new ImageIcon("./tagsys_logo2.jpg"));
        JLabelScore = new JLabel();
        JLabelScore.setText("Score : 0");
        model = new DefaultTableModel();
        model.addColumn("Tag");
        model.addColumn("Rssi");
        model.addColumn("Zone");
        model.addColumn("Classification");
        table = new JTable(model);
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    }
}
