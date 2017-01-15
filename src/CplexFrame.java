
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Antoine on 15/01/2017.
 */
public class CplexFrame {

    private JPanel panel1;
    private JLabel schema;
    private JTable data;
    private JSpinner spinner1;
    private JButton ButtonSolve;
    DefaultTableModel model;

    public ArrayList<String> actions = new ArrayList<>();

    // algorithme glouton
    final String[] instructions = {""};

    ArrayList<GreedyZone> m_list = null;
    int totalDemand = 0;
    int m_capa = 0;

    public CplexFrame(ArrayList<GreedyZone> list, int totaldemand) {
        this.m_list = list;
        totalDemand = totaldemand;
        JFrame frame = new JFrame("Cplex");
        frame.setContentPane(panel1);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            }
        });
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(600, 300);

        ButtonSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_capa = (int) spinner1.getValue();
                calculerCouts();
                System.out.println(solve(m_list, totalDemand, m_capa));
            }
        });

    }

    public String solve(ArrayList<GreedyZone> list, int totaldemand, int capa) {

        for(int i = 0; i < list.size(); i++)
            System.out.println("Quantité : " + list.get(i).quantite);



        Thread t = new Thread() {
            @Override
            public void run() {
                int _totaldemand = totaldemand;
                boolean stop = false;
                int demandeComplete = 0;
                int capaciteCourante = 0;
                GreedyZone temp = null;
                String s = "";
                while (demandeComplete < _totaldemand) {
                    capaciteCourante = 0;
                    temp = list.get(0);
                    s += "(0,0)";
                    while (true) {
                        GreedyZone temp2 = zoneLaPlusProche(list, temp);
                        if (temp2 != null) {
                            if (capaciteCourante + temp2.quantite < capa) {
                                int diff = capaciteCourante;
                                capaciteCourante += temp2.quantite;
                                diff = capaciteCourante - diff;
                                temp2.quantite -= diff;
                                s += "-(" + temp2.zone + "," + diff + ")";
                            }
                            else {
                                int diff = capa - capaciteCourante;
                                temp2.quantite -= diff;
                                capaciteCourante = capaciteCourante + diff;
                                s += "-(" + temp2.zone + "," + diff + ")";
                                break;
                            }
                            temp = temp2;
                        }
                        else {
                            stop = true;
                            break;
                        }
                    }
                    demandeComplete += capaciteCourante;
                    s += "-(0,0)\n";
                    if(stop)
                        break;

                }
                instructions[0] = s;
                System.out.println(instructions[0]);
                mesTournees();
            }
        };
        t.start();
        return null;
    }


    public void calculerCouts(){

        TableModel dtm = data.getModel();
        int nRow = dtm.getRowCount();
        System.out.println("" + nRow);
        int[] tableData = new int[nRow-1];
        for (int i = 1 ; i < nRow ; i++) {
            System.out.println("" + (String) dtm.getValueAt(i, 2));
            tableData[i - 1] = Integer.valueOf((String) dtm.getValueAt(i, 2));
        }

        for(int i = 0 ; i < 5; i++){
            m_list.get(i).d.set(i, 0);
        }

        for(int i = 0; i < nRow-1; i++){
            if(i <= 3){
                m_list.get(0).d.set(0+i, tableData[i]);
                m_list.get(0+i).d.set(0, tableData[i]);
            }
        }

        m_list.get(1).d.set(2, tableData[4]);
        m_list.get(2).d.set(1, tableData[4]);

        m_list.get(1).d.set(3, tableData[5]);
        m_list.get(3).d.set(1, tableData[5]);

        m_list.get(1).d.set(4, tableData[6]);
        m_list.get(4).d.set(1, tableData[6]);

        m_list.get(2).d.set(3, tableData[7]);
        m_list.get(3).d.set(2, tableData[7]);

        m_list.get(2).d.set(3, tableData[7]);
        m_list.get(3).d.set(2, tableData[7]);

        m_list.get(2).d.set(4, tableData[8]);
        m_list.get(4).d.set(2, tableData[8]);

        m_list.get(3).d.set(4, tableData[9]);
        m_list.get(4).d.set(3, tableData[9]);

    }


    public GreedyZone zoneLaPlusProche(ArrayList<GreedyZone> list, GreedyZone gz){
        int distance = 10000000;
        GreedyZone r = null;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).zone != gz.zone){
                if(distance > list.get(i).d.get(gz.zone) && list.get(i).quantite > 0){
                    distance = list.get(i).d.get(gz.zone);
                    r = list.get(i);
                }
            }
        }
        return r;
    }

    public void mesTournees(){
        try{
            PrintWriter writer = new PrintWriter("./mesTournees.txt", "UTF-8");
            writer.println(instructions[0]);
            writer.close();
        } catch (IOException e) {
            // do something
        }
    }

    public void setUpZone(){

        Vector h = new Vector();
        h.add("Zone i");
        h.add("Zone j");
        h.add("Distance entre i et j");
        model.addRow(h);

        Vector r0 = new Vector();
        r0.add("0");
        r0.add("1");
        r0.add("");
        model.addRow(r0);

        Vector r1 = new Vector();
        r1.add("0");
        r1.add("2");
        r1.add("");
        model.addRow(r1);

        Vector r2 = new Vector();
        r2.add("0");
        r2.add("3");
        r2.add("");
        model.addRow(r2);

        Vector r3 = new Vector();
        r3.add("0");
        r3.add("4");
        r3.add("");
        model.addRow(r3);

        Vector row = new Vector();
        row.add("1");
        row.add("2");
        row.add("");
        model.addRow(row);

        Vector row2 = new Vector();
        row2.add("1");
        row2.add("3");
        row2.add("");
        model.addRow(row2);

        Vector row3 = new Vector();
        row3.add("1");
        row3.add("4");
        row3.add("");
        model.addRow(row3);

        Vector row4 = new Vector();
        row4.add("2");
        row4.add("3");
        row4.add("");
        model.addRow(row4);

        Vector row5 = new Vector();
        row5.add("2");
        row5.add("4");
        row5.add("");
        model.addRow(row5);

        Vector row6 = new Vector();
        row6.add("3");
        row6.add("4");
        row6.add("");
        model.addRow(row6);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        schema = new JLabel();
        schema.setIcon(new ImageIcon());

        model = new DefaultTableModel();
        model.addColumn("Zone i");
        model.addColumn("Zone j");
        model.addColumn("Distance entre zone i et zone j");
        data = new JTable(model);
    }
}
