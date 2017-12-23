package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.CentralLocation;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataCentralLocation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class QueryCentralLocation extends Query {

    public QueryCentralLocation(String mapName, String nameID){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "Place WHERE nameID = '" + nameID + "'";
    }

    @Override
    public CentralLocation arrangeRecievedData(ResultSet resultSet){
        CentralLocation centralLocation = null;
        try {
            resultSet.next();//check if it is necessary.
            String nameID = resultSet.getString("nameID");
            String street = resultSet.getString("street");
            int cord1x = resultSet.getInt("cord1x");
            int cord2x = resultSet.getInt("cord2x");
            int cord3x = resultSet.getInt("cord3x");
            int cord4x = resultSet.getInt("cord4x");
            int cord1y = resultSet.getInt("cord1y");
            int cord2y = resultSet.getInt("cord2y");
            int cord3y = resultSet.getInt("cord3y");
            int cord4y = resultSet.getInt("cord4y");

            Set<Point> points = new HashSet<Point>();
            points.add(new PointImpl(cord1x, cord1y));
            points.add(new PointImpl(cord2x, cord2y));
            points.add(new PointImpl(cord3x, cord3y));
            points.add(new PointImpl(cord4x, cord4y));

            centralLocation = new DataCentralLocation(points, nameID, street,mapName);
        }catch(SQLException e){
            System.out.println("query has failed");
        }

        return centralLocation;
    }
}