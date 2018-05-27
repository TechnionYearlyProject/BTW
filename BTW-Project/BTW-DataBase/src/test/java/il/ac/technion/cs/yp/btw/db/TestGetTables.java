package il.ac.technion.cs.yp.btw.db;
import il.ac.technion.cs.yp.btw.classes.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shay on 31/03/2018.
 */
public class TestGetTables {
    @Test
    public void testGetTablesName() {
        BTWDataBaseImpl db = BTWDataBaseImpl.getInstance("tablesTest");
        Assert.assertNotNull(db.getTablesNames());
    }
}
