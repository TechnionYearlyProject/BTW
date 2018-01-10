package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.db.CrossRoadsDataBase;
import il.ac.technion.cs.yp.btw.classes.Crossroad;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCrossRoad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import junit.framework.TestCase;
import org.junit.Test;
import java.util.Iterator;
import java.util.Set;
import static org.junit.Assert.assertNotNull;

public class TestCrossRoadsDataBase {

    @Test
    public void testGetCrossRoad(){
        Point position = new PointImpl(1.0,1.0);
        MainDataBase.openConnection();
        Crossroad crossRoad = CrossRoadsDataBase.getCrossRoad(position, "first");
        assertNotNull(crossRoad);
        //System.out.println(crossRoad.toString());
        MainDataBase.closeConnection();
    }

    @Test
    public void testGetAllCrossRoads(){
        MainDataBase.openConnection();
        Set<Crossroad> allCrossRoads = CrossRoadsDataBase.getAllCrossRoads("first");
        Iterator<Crossroad> iterator = allCrossRoads.iterator();
        //System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            assertNotNull(iterator.next());
            //System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }

}