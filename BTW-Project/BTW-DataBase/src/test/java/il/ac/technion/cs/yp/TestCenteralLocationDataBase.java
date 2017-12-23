package il.ac.technion.cs.yp;

import il.ac.technion.cs.yp.btw.db.CentralLocationsDataBase;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import junit.framework.TestCase;
import java.util.Iterator;
import java.util.Set;

public class TestCenteralLocationDataBase  extends TestCase {

    public void testGetAllCenteralLocations(){
        CentralLocationsDataBase centralLocationsDataBase = new CentralLocationsDataBase("first");
        Set<CentralLocation> allCentralLocations = centralLocationsDataBase.getAllCentralLocations();
        Iterator<CentralLocation> iterator = allCentralLocations.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetCentralLocation(){
        //SELECT * FROM dbo.firstPlace WHERE nameID = 'GasStation'
        CentralLocationsDataBase centralLocationsDataBase = new CentralLocationsDataBase("first");
        CentralLocation centralLocation = centralLocationsDataBase.getCentralLocation("GasStation Paz");
        System.out.println(centralLocation.toString());
        CentralLocation centralLocation2 = centralLocationsDataBase.getCentralLocation("bankHapoalim");
        System.out.println(centralLocation2.toString());
    }
}