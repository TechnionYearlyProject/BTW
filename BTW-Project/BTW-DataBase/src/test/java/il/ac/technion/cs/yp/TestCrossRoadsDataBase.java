package il.ac.technion.cs.yp;

import il.ac.technion.cs.yp.btw.db.CrossRoadsDataBase;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import junit.framework.TestCase;
import java.util.Iterator;
import java.util.Set;

public class TestCrossRoadsDataBase  extends TestCase {

    public void testGetCrossRoad(){
        Point position = new PointImpl(1.0,1.0);
        CrossRoadsDataBase crossRoadsDataBase = new CrossRoadsDataBase("first");
        Crossroad crossRoad = crossRoadsDataBase.getCrossRoad(position);
        System.out.println(crossRoad.toString());
    }

    public void testGetAllCrossRoads(){
        CrossRoadsDataBase crossRoadsDataBase = new CrossRoadsDataBase("first");
        //Set<Crossroad> getAllCrossRoads(){
        Set<Crossroad> allCrossRoads = crossRoadsDataBase.getAllCrossRoads();
        Iterator<Crossroad> iterator = allCrossRoads.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

}