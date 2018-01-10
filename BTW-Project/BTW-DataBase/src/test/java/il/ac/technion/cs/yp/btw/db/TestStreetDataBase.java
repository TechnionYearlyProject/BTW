package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.db.StreetsDataBase;
import il.ac.technion.cs.yp.btw.classes.Street;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertNotNull;


public class TestStreetDataBase {

    @Test
    public void testGetAllStreets(){
        MainDataBase.openConnection();
        Set<Street> allStreets = StreetsDataBase.getAllStreets("first");
        Iterator<Street> iterator = allStreets.iterator();
        //System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            assertNotNull(iterator.next());
            //System.out.println(iterator.next().toString());
        }
        MainDataBase.closeConnection();
    }

    @Test
    public void testGetRoad(){

        String mapName = "first";
        MainDataBase.openConnection();
        Street ayalonn = StreetsDataBase.getStreet("Ayalonn", mapName);
        assertNotNull(ayalonn);
        //System.out.println(ayalonn.toString());
        Street road1 = StreetsDataBase.getStreet("Road1", mapName);
        assertNotNull(road1);
        //System.out.println(road1.toString());
        Street road4 = StreetsDataBase.getStreet("Road4", mapName);
        assertNotNull(road4);
        //System.out.println(road4.toString());
        Street road6 = StreetsDataBase.getStreet("Road6", mapName);
        assertNotNull(road6);
        //System.out.println(road6.toString());
        MainDataBase.closeConnection();

    }
}