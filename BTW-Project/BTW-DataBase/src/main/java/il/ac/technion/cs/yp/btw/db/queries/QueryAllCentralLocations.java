package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCentralLocation;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/*get from the data base all the central locations
* this class is not in use in the current semester*/
public class QueryAllCentralLocations extends Query{
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * */
    public QueryAllCentralLocations(String mapName){
        super(mapName);
        query=this.query = "SELECT * FROM dbo."+ mapName + "Place";
    }
    /*
    * @author Sharon Hadar
     * @Date 21/01/2018
     * get the results fro the data base and construct the match centeral locations
     * */
    @Override
    public Set<CentralLocation> arrangeRecievedData(ResultSet resultSet){
        Set<CentralLocation> centralLocations = new HashSet();
        try{
            while (resultSet.next()){
                String nameID = resultSet.getString("nameID");
                String street = resultSet.getString("street");
                int cord1x =  resultSet.getInt("cord1x");
                int cord2x =  resultSet.getInt("cord2x");
                int cord3x =  resultSet.getInt("cord3x");
                int cord4x =  resultSet.getInt("cord4x");
                int cord1y =  resultSet.getInt("cord1y");
                int cord2y =  resultSet.getInt("cord2y");
                int cord3y =  resultSet.getInt("cord3y");
                int cord4y =  resultSet.getInt("cord4y");

                Set<Point> points = new HashSet<Point>();
                points.add(new PointImpl(cord1x, cord1y));
                points.add(new PointImpl(cord2x, cord2y));
                points.add(new PointImpl(cord3x, cord3y));
                points.add(new PointImpl(cord4x, cord4y));

                CentralLocation centralLocation = new DataCentralLocation(points, nameID, street, mapName);
                centralLocations.add(centralLocation);

            }
        }catch(SQLException e){
            logger.error("query has failed");
        }
        return centralLocations;

    }
}