import java.util.ArrayList;

/**
 * Created by Antoine on 12/12/2016.
 */
public class Antenna {

    private static final double ecart = 4.0;

    private String name = null;
    private ArrayList<CSVContent> csv = null;

    private ArrayList<CSVContent> includedArea = null;
    private ArrayList<CSVContent> unknownArea = null;

    private double meanRssi = 0.0;

    public Antenna(String s, ArrayList<CSVContent> csv){
        name = s;
        this.csv = csv;
    }

    public void computeFinalMean(){
        meanRssi = 0.0;
        for(CSVContent c : csv){
            meanRssi += c.getRSSI();
        }
        meanRssi /= csv.size();
    }

    public void compareAndSort(){

        includedArea = new ArrayList<>();
        unknownArea = new ArrayList<>();

        for(CSVContent c : csv){
            if(c.getRSSI() < meanRssi - ecart){
                unknownArea.add(c);
                System.out.println("Inconnue: " + c.getTag());
            }
            else {
                includedArea.add(c);
                System.out.println("Sure: " + c.getTag());
            }
        }

    }

    public ArrayList<CSVContent> getIncludedArea(){
        return includedArea;
    }

    public ArrayList<CSVContent> getUnknownAreaArea(){
        return unknownArea;
    }

    public ArrayList<CSVContent> getCSVContent(){
        return csv;
    }

    public String getName(){
        return name;
    }

}
