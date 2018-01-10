package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.db.CentralLocationsDataBase;
import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

public class TestCenteralLocationDataBase {

    @Test
    public void testGetAllCenteralLocations(){
        MainDataBase.openConnection();
        Set<CentralLocation> allCentralLocations = CentralLocationsDataBase.getAllCentralLocations("first");
        Iterator<CentralLocation> iterator = allCentralLocations.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }

    @Test
    public void testGetCentralLocation(){
        //SELECT * FROM dbo.firstPlace WHERE nameID = 'GasStation'
        MainDataBase.openConnection();
        CentralLocation centralLocation = CentralLocationsDataBase.getCentralLocation("GasStation Paz", "first");
        System.out.println(centralLocation.toString());
        CentralLocation centralLocation2 = CentralLocationsDataBase.getCentralLocation("bankHapoalim", "first");
        System.out.println(centralLocation2.toString());
        MainDataBase.closeConnection();
    }
}