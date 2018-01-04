package il.ac.technion.cs.yp.btw.db;
import il.ac.technion.cs.yp.btw.classes.BTWDataBase;
import junit.framework.TestCase;
/**
 * Created by shay on 04/01/2018.
 */
public class TestSaveMap extends TestCase {
    public void testSaveMap() {
        BTWDataBase btw = new BTWDataBaseImpl("third");
        String json = "{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [0,0],\n" +
                "\t\t\"name\": \"T245-bb-aa\",\n" +
                "\t\t\"overload\": 435345445\n" +
                "      }\n" +
                "    },\n" +
                "\t\t\n" +
                "\t{\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [3,0],\n" +
                "\t\t\"name\": \"T246-cc-bb\",\n" +
                "\t\t\"overload\": 4647\n" +
                "      }\n" +
                "    },\n" +
                "\t\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[0,0],[-3,0]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"aa\",\n" +
                "\t\t\"sector_start\": \"1\",\n" +
                "\t\t\"sector_end\": \"10\",\n" +
                "\t\t\"length\": 3,\n" +
                "\t\t\"overload\": 68\n" +
                "      }\n" +
                "    },\n" +
                "\t\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[0,0],[0,3]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"bbe\",\n" +
                "\t\t\"sector_start\": \"1\",\n" +
                "\t\t\"sector_end\": \"10\",\n" +
                "\t\t\"length\": 3,\n" +
                "\t\t\"overload\": 957\n" +
                "      }\n" +
                "    },  \n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"LineString\",\n" +
                "        \"coordinates\": [[0,3],[3,3]]\n" +
                "      },\n" +
                "      \"properties\": {\n" +
                "        \"name\": \"bb\",\n" +
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

    }
}
