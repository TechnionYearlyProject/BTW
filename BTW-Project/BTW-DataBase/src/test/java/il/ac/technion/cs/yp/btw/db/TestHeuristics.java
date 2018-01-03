package il.ac.technion.cs.yp.btw.db;

/**
 * Created by shay on 03/01/2018.
 */
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import il.ac.technion.cs.yp.btw.db.RoadsDataBase;
import il.ac.technion.cs.yp.btw.classes.Road;
import junit.framework.TestCase;
import java.util.Iterator;
import java.util.Set;


public class TestHeuristics extends TestCase {

    public void testHeuristics() {
        BTWDataBase btw = new BTWDataBaseImpl("first");
        btw.updateHeuristics();
    }
}