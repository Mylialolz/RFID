import javax.net.ssl.SSLContext;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Antoine on 12/12/2016.
 */
public class Tag {

    private String tag = null;
    private double rssi = 0.0;

    public ArrayList<Zone> Li = new ArrayList<>();
    public String classification = "";
    public String zonePlacement = "";

    public Tag(){
    }

    public void fill(String[] list){

        tag = list[5];
        String s = list[8];
        s = s.replace(',', '.');
        rssi = Double.valueOf(s);
    }

    public String getTag(){
        return tag;
    }

    public double getRSSI(){
        return rssi;
    }

    public void deleteDoublons(){

        Map<String, Zone> map = new LinkedHashMap<>();
        for (Zone ays : Li) {
            map.put(ays.nom, ays);
        }
        Li.clear();
        Li.addAll(map.values());

        if(Li.size() == 1){
            zonePlacement = Li.get(0).nom;
            classification = "OK";
        }
        else {
            String s = "";
            for(int i = 0; i < Li.size(); i++){
                if(i == Li.size() - 1)
                    s += Li.get(i).nom ;
                else
                    s += Li.get(i).nom +"," ;
            }
            zonePlacement = s;
            classification = "KO";
        }

        return;
    }

    public void print(){
        System.out.println("Tag: " + tag + "; Rssi: " + rssi + "; Zone: " + zonePlacement + "; classification: " + classification);
    }


}
