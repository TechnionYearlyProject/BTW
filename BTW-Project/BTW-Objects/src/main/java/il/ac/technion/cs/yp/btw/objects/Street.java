package il.ac.technion.cs.yp.btw.objects;

import java.util.List;
import java.util.Map;

/*a street includes an ordered sequence of roads*/
public class Street implements IStreet {

    private int id;
    private String name;
    private Map<Integer, Road> roads;
    private List<Integer> rangesStarts; //ArrayList find by binary search

    public Street(int id, String name, List<Roads> roads){
        this.id=id;
        this.name=name;
        this.roads=roads;
    }

    public List<Road> getRoads(){
        return roads;
    }
}