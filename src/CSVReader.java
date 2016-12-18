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


        public ArrayList<CSVContent> getContent(String chemin) {

            ArrayList<CSVContent> list = new ArrayList<>();

            if(chemin != null) {

                String csvFile = chemin;
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ";";

                try {

                    br = new BufferedReader(new FileReader(csvFile));
                    while ((line = br.readLine()) != null) {

                        String[] data = line.split(cvsSplitBy);

                        if(data[0] != "") {

                            for (int i = 0; i < data.length; i++)
                                System.out.print(data[i] + ";");
                            System.out.println();

                            CSVContent c = new CSVContent();
                            c.fill(data);

                            String tag = c.getTag();
                            boolean b = false;
                            int index = 0;

                            for (int i = 0; i < list.size(); i++){
                                if(tag.matches(list.get(i).getTag())){
                                    b = true;
                                    index = i;
                                    break;
                                }
                            }

                            if (b) {
                                list.get(index).addRssi(data[8]);
                            }
                            else {
                                list.add(c);
                            }

                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for(CSVContent c : list)
                c.mean();

            return list;
        }


}
