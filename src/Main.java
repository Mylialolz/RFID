import java.util.ArrayList;

/**
 * Created by Antoine on 12/12/2016.
 */
public class Main {


    public static void main(String[] ags){

        System.out.println("Debut...");

        String directoryPath = "C:\\Users\\Antoine\\Desktop\\RFID\\";
        ArrayList<String> csvFiles = Directory.listFiles(directoryPath);

        RFIDComponent rfid = new RFIDComponent();

        int i = 1;

        for(String s : csvFiles){
            System.out.println("Nouveau fichier...");

            CSVReader csvReader = new CSVReader();
            ArrayList<CSVContent> arrayList = csvReader.getContent(directoryPath + s);
            rfid.set(Integer.toString(i), arrayList);

            ++i;
        }


        ArrayList<Antenna> antennas = rfid.getAntennas();

        for(Antenna a : antennas){
            a.computeFinalMean();
            a.compareAndSort();
        }


        System.out.println("Fin.");
        return;
    }




}
