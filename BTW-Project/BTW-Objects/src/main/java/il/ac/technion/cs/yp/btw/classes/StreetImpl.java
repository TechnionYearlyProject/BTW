package il.ac.technion.cs.yp.btw.classes;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * default implementation for the interface Street
 */
public class StreetImpl implements Street {
    private String name;
    private Set<String> roadIds;

    public StreetImpl (String name){
        this(name,new HashSet<String>());
    }

    public StreetImpl (String name, Set<String> roadIds){
        this.name = name;
        this.roadIds = roadIds;
    }

    @Override
    public Set<Road> getAllRoadsInStreet() {
        return null;
    }

    @Override
    public String getStreetName() {
        return this.name;
    }

    //@Override
    public String toStringStreetFull() {
        String roadsNames = "";
        for(String roadName: roadIds){
            roadsNames+=roadName;
        }
        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+
                "\"properties\":{"+"\"name\":"+"\""+this.getStreetName()+"\","+
                "\"included_streets\":"+"\""+roadsNames+"\"},";
    }

    //@Override
    public String toStringStreet() {

        return "{\"type\""+":\"Feature\","+"\"geometry\""+":{\"type\""+":\"LineString\","+
                "\"properties\":{"+"\"name\":"+"\""+this.getStreetName()+"\"},";
    }
}
