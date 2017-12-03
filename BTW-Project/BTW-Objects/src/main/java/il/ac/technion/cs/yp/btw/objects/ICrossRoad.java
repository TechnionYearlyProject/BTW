package il.ac.technion.cs.yp.btw.objects;

import java.util.List;

interface ICrossRoad{

    public int getId();
    public String getName();
    public Location getPoint();
    public List<ITrafficLight> getTrafficLights(IRoad road);
}