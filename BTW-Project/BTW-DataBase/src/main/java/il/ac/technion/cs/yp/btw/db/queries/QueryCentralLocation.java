package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCentralLocation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/*get from the data base a centeral location by its name
* this class is not in use in the current semester*/
public class QueryCentralLocation extends Query {
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * */
    public QueryCentralLocation(String mapName, String nameID){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "Place WHERE nameID = '" + nameID + "'";
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct a centeral location from the data base results*/
    @Override
    public CentralLocation arrangeRecievedData(ResultSet resultSet){
        CentralLocation centralLocation = null;
        try {
            resultSet.next();//check if it is necessary.
            String nameID = resultSet.getString("nameID");
            String street = resultSet.getString("street");
            double cord1x = resultSet.getDouble("cord1x");
            double cord2x = resultSet.getDouble("cord2x");
            double cord3x = resultSet.getDouble("cord3x");
            double cord4x = resultSet.getDouble("cord4x");
            double cord1y = resultSet.getDouble("cord1y");
            double cord2y = resultSet.getDouble("cord2y");
            double cord3y = resultSet.getDouble("cord3y");
            double cord4y = resultSet.getDouble("cord4y");

            Set<Point> points = new HashSet<Point>();
            points.add(new PointImpl(cord1x, cord1y));
            points.add(new PointImpl(cord2x, cord2y));
            points.add(new PointImpl(cord3x, cord3y));
            points.add(new PointImpl(cord4x, cord4y));

            centralLocation = new DataCentralLocation(points, nameID, street,mapName);
        }catch(SQLException e){
            logger.error("query central location has failed");
        }

        return centralLocation;
    }
}