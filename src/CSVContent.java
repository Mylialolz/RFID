import java.util.ArrayList;

/**
 * Created by Antoine on 12/12/2016.
 */
public class CSVContent {

    private String recepteur = null;
    private String emetteur = null;
    private String tag = null;

    private ArrayList<String> listRssi = null;
    private double rssiDouble = 0.0;


    public CSVContent(){
    }

    public void fill(String[] list){
        listRssi = new ArrayList<>();
        recepteur = list[0];
        emetteur = list[1];
        tag = list[5];
        listRssi.add(list[8]);
    }

    public void addRssi(String rssi){
        listRssi.add(rssi);
    }

    public String getTag(){
        return tag;
    }

    public void mean(){
        double mean = 0.0;
        for(int i = 0; i < listRssi.size(); i++){
            String s = listRssi.get(i);
            s = s.replace(',', '.');
            double p = Double.valueOf(s);
            mean += p;
        }
        rssiDouble = mean/listRssi.size();
    }

    public double getRSSI(){
        return rssiDouble;
    }



}
