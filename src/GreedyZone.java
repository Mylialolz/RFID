import java.util.ArrayList;

/**
 * Created by Antoine on 15/01/2017.
 */
public class GreedyZone {

    public int zone = -1;
    public int quantite = -1;


    public ArrayList<Integer> d = new ArrayList<>();

    public GreedyZone(){
        for(int i = 0; i < 5; i++){
            d.add(0);
        }
    }
}
