import java.util.ArrayList;

/**
 * Created by Antoine on 12/12/2016.
 */
public class Main {


    public static void main(String[] ags){

        System.out.println("Debut...");

        String directoryPath = "C:\\Users\\Antoine\\Desktop\\MASTER INDUS\\cours\\RFID\\csv\\";
        ArrayList<String> csvFiles = Directory.listFiles(directoryPath);

        int i = 1;

        ArrayList<Tag> tag = new ArrayList<>();

        for(String s : csvFiles){
            System.out.println("Nouveau fichier...");

            CSVReader csvReader = new CSVReader();
            csvReader.getContent(directoryPath + s, "" + i, tag);

            ++i;
        }



        for(Tag t : tag){

            t.deleteDoublons();
            if(t.Li.size() == 1){
                t.zonePlacement = t.Li.get(0).nom;
                t.classification = "Ok";
            }
            else {
                t.zonePlacement = "Inconnue";
                t.classification = "KO";
            }

        }


        System.out.println("Fin.");
        return;
    }




}
