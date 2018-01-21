package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/*get from the data base all the roads*/
public class QueryAllRoads extends Query{

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * */
    public QueryAllRoads(String mapName){
        super(mapName);
        query = "SELECT * FROM dbo."+ mapName + "Road";
    }
    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct a road by the information recieved from the data base
     * */
    @Override
    public Set<Road> arrangeRecievedData(ResultSet resultSet){
        Set<Road> roads = new HashSet();
        try{
            while (resultSet.next()){
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
                Road road = new DataRoad(nameID, length,myStreet, sourceCrossroadId, destinationCrossroadId, secStart, secEnd, overload, mapName);
                roads.add(road);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return roads;

    }
}
