
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Antoine on 12/12/2016.
 */
public class Directory {

    public Directory(){

    }

    public static ArrayList<String> listFiles(String chemin) {
        ArrayList<String> list = new ArrayList<>();

        File folder = new File(chemin);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                list.add(listOfFiles[i].getName().toString());
            }
        }
        return list;
    }

}
