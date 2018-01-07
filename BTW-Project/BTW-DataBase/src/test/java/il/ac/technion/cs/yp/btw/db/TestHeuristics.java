package il.ac.technion.cs.yp.btw.db;

/**
 * Created by shay on 03/01/2018.
 */
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import junit.framework.TestCase;



public class TestHeuristics extends TestCase {

    public void testHeuristics() {
        BTWDataBase btw = new BTWDataBaseImpl("first");
        btw.updateHeuristics();
    }
}