import java.util.ArrayList;

/**
 * Created by Antoine on 12/12/2016.
 */
public class RFIDComponent {

    private ArrayList<Antenna> antenna = null;

    public RFIDComponent(){
        antenna = new ArrayList<>();
    }

    public void set(String s, ArrayList<CSVContent> csv){
        antenna.add(new Antenna(s, csv));
    }


    public ArrayList<Antenna> getAntennas(){
        return antenna;
    }


}
