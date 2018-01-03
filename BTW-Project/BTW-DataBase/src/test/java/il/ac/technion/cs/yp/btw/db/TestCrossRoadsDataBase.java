package il.ac.technion.cs.yp.btw.db;

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
        MainDataBase.openConnection();
        Crossroad crossRoad = CrossRoadsDataBase.getCrossRoad(position, "first");
        System.out.println(crossRoad.toString());
        MainDataBase.closeConnection();
    }

    public void testGetAllCrossRoads(){
        MainDataBase.openConnection();
        Set<Crossroad> allCrossRoads = CrossRoadsDataBase.getAllCrossRoads("first");
        Iterator<Crossroad> iterator = allCrossRoads.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }

}