package il.ac.technion.cs.yp.btw.db;

import il.ac.technion.cs.yp.btw.db.StreetsDataBase;
import il.ac.technion.cs.yp.btw.classes.Street;
import junit.framework.TestCase;
import java.util.Iterator;
import java.util.Set;


public class TestStreetDataBase  extends TestCase {

    public void testGetAllStreets(){
        StreetsDataBase streetsDataBase = new StreetsDataBase("first");
        Set<Street> allStreets = streetsDataBase.getAllStreets();
        Iterator<Street> iterator = allStreets.iterator();
        System.out.println("\n\n\nthe result set is:");
        while(iterator.hasNext()){
            System.out.println(iterator.next().toString());
        }
    }

    public void testGetRoad(){

        StreetsDataBase roadsDataBase = new StreetsDataBase("first");
        Street ayalonn = roadsDataBase.getStreet("Ayalonn");
        System.out.println(ayalonn.toString());
        Street road1 = roadsDataBase.getStreet("Road1");
        System.out.println(road1.toString());
        Street road4 = roadsDataBase.getStreet("Road4");
        System.out.println(road4.toString());
        Street road6 = roadsDataBase.getStreet("Road6");
        System.out.println(road6.toString());

    }
}