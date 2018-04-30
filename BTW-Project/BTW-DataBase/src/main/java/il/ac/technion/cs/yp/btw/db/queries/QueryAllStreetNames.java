package il.ac.technion.cs.yp.btw.db.queries;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/*get from the data base all the street names
* this class is not in use in the current semester*/
public class QueryAllStreetNames extends Query{
    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * */
    public QueryAllStreetNames(String mapName){
        super(mapName);
        this.query = "SELECT nameID FROM dbo."+ mapName + "Road";
    }

    /*
    * @author Sharon Hadar
    * @Date 21/01/2018
    * construct a street by the data recieved from the data base */
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
            logger.error("query has failed");
        }
        return names;
    }
}
