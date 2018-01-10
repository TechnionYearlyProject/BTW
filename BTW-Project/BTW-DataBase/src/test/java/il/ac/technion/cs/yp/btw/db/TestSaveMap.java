package il.ac.technion.cs.yp.btw.db;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shay on 04/01/2018.
 */
public class TestSaveMap {
    @Test
    public void testSaveMap() {
        BTWDataBase btw = new BTWDataBaseImpl("test1");
        String json = "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [0,0],\n" +
                "\t\t\"name\": \"T1-aa-bb\",\n" +
                "\t\t\"overload\": 435345445\n" +
                "      }\n" +
                "    },\n" +
                "\t\t\n" +
                "\t{\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [0,0],\n" +
                "\t\t\"name\": \"T2-cc-dd\",\n" +
                "\t\t\"overload\": 4647\n" +
                "      }\n" +
                "    },\n" +
                "\t\n" +
                "\t{\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [2,0],\n" +
                "\t\t\"name\": \"T3-bb-ee\",\n" +
                "\t\t\"overload\": 4647\n" +
                "      }\n" +
                "    },\n" +
                "\t\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[0,0],[-6,0]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"aa\",\n" +
                "\t\t\"sector_start\": \"1\",\n" +
                "\t\t\"sector_end\": \"10\",\n" +
                "\t\t\"length\": 6,\n" +
                "\t\t\"overload\": 68\n" +
                "      }\n" +
                "    },\n" +
                "\t\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[0,0],[2,0]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"bb\",\n" +
                "\t\t\"sector_start\": \"1\",\n" +
                "\t\t\"sector_end\": \"10\",\n" +
                "\t\t\"length\": 2,\n" +
                "\t\t\"overload\": 957\n" +
                "      }\n" +
                "    },  \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[0,5],[0,0]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"cc\",\n" +
                "\t\t\"sector_start\": \"1\",\n" +
                "\t\t\"sector_end\": \"10\",\n" +
                "\t\t\"length\": 3,\n" +
                "\t\t\"overload\": 856\n" +
                "      }\n" +
                "    },\t\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[0,0],[0,-3]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"dd\",\n" +
                "\t\t\"sector_start\": \"1\",\n" +
                "\t\t\"sector_end\": \"10\",\n" +
                "\t\t\"length\": 3,\n" +
                "\t\t\"overload\": 856\n" +
                "      }\n" +
                "    },\t\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[2,0],[2,-2]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"ee\",\n" +
                "\t\t\"sector_start\": \"1\",\n" +
                "\t\t\"sector_end\": \"10\",\n" +
                "\t\t\"length\": 3,\n" +
                "\t\t\"overload\": 856\n" +
                "      }\n" +
                "    },\t\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Poligon\",\n" +
                "        \"coordinates\": [[0,0],[4,0],[4,1],[1,0]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"MailWay\",\n" +
                "\t\t\"street\": \"street chukchuk\"\n" +
                "      }\n" +
                "    }\n" +
                "    \n" +
                "  ]\n" +
                "}";
        btw.saveMap(json);
        Assert.assertTrue(true); // if we reach here it mean no errors in map saving

    }
}
