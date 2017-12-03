package il.ac.technion.cs.yp.btw.objects;

import java.util.List;

public class CrossRoad implements ICrossRoad{

    private int id;
    private String name;
    private Location point;
    private List<ITrafficLight> trafficLights;
    private Map<Road, List<TrafficLight>> turns; //every road has the traffic lights he can go through in the cross roads.(change to road id))

   // (Road through.getFrom, TrafficLight through, Road through.getTo)

    public CrossRoad(int id, String name, Location point, ){
        this.id=id;
        this.name=name;
        this.point=point;
    }

    public int getId(){

        return id;
    }

    public String getName(){

        return name;
    }

    public Location getPoint(){
        return point;
    }

    public List<ITrafficLight> getTrafficLights(IRoad road){

    }
}