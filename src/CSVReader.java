import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Antoine on 12/12/2016.
 */
public class CSVReader {

        public CSVReader(){

        }

        public void getContent(String chemin, String nomAntenne, ArrayList<Tag> listTag) {

            if(chemin != null) {

                String csvFile = chemin;
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ";";

                try {

                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {

                        String[] data = line.split(cvsSplitBy);

                        Tag c = null;
                        if(data[0] != "") {
                            c = new Tag();
                            c.fill(data);
                        }

                        if(c != null){
                            boolean tagExistant = false;
                            for(Tag t : listTag) {
                                if (t.getTag().matches(c.getTag())) {
                                    tagExistant = true;
                                    Zone zone = new Zone();
                                    zone.nom = nomAntenne;
                                    zone.Rssi = c.getRSSI();
                                    t.Li.add(zone);
                                    break;
                                }
                            }
                            if(!tagExistant){
                                Zone zone = new Zone();
                                zone.nom = nomAntenne;
                                zone.Rssi = c.getRSSI();
                                c.Li.add(zone);
                                listTag.add(c);
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return;
        }


}
