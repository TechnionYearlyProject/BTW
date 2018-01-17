package il.ac.technion.cs.yp.btw.db.queries;


import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/*get from the data base all the street names*/
public class QueryAllStreetNames extends Query{

    public QueryAllStreetNames(String mapName){
        super(mapName);
        this.query = "SELECT nameID FROM dbo."+ mapName + "Road";
    }

    @Override
    public Set<String> arrangeRecievedData(ResultSet resultSet){
        Set<String> names = new HashSet();
        try{
            while (resultSet.next()){
                String nameID = resultSet.getString("nameID");
                String myStreet = nameID.split("st")[0];
                names.add(myStreet);
            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return names;
    }
}
