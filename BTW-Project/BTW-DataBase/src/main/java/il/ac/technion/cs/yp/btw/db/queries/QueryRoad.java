package il.ac.technion.cs.yp.btw.db.queries;


import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

/*get from data base a road by its name
* this class is not in use in the current semester*/
public class QueryRoad extends Query {

    /** @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryRoad(String mapName, String nameID) {
        super(mapName);
        query = "SELECT * FROM dbo." + mapName + "Road WHERE nameID = '" + nameID + "'";
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct a road by the results from the data base*/
    @Override
    public Road arrangeRecievedData(ResultSet resultSet){
        Road road = null;
        try{
            resultSet.next();
                String nameID = resultSet.getString("nameID");
                double cord1x =  resultSet.getDouble("cord1x");
                double cord2x =  resultSet.getDouble("cord2x");
                double cord1y =  resultSet.getDouble("cord1y");
                double cord2y =  resultSet.getDouble("cord2y");
                int length =  resultSet.getInt("length");
                int secStart =  resultSet.getInt("secStart");
                int secEnd =  resultSet.getInt("secEnd");
                long overload =  resultSet.getLong("overload");

                String myStreet = nameID.split("st")[0];
                Point sourceCrossroadId  = new PointImpl(cord1x, cord1y);
                Point destinationCrossroadId = new PointImpl(cord2x, cord2y);

                road = new DataRoad (nameID, length,myStreet, sourceCrossroadId, destinationCrossroadId, secStart, secEnd, overload, mapName);
        }catch(SQLException e){
            logger.error("query Road has failed");
        }
        return road;

    }
}