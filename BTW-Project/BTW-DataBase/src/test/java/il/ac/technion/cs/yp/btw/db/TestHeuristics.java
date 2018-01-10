package il.ac.technion.cs.yp.btw.db;

/**
 * Created by shay on 03/01/2018.
 */
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;


public class TestHeuristics {

    @Test
    public void testHeuristics() {
        BTWDataBase btw = new BTWDataBaseImpl("test1");
        assertNotNull(btw.updateHeuristics());
    }
}