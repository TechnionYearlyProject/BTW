package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.classes.Street;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataStreet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class QueryStreet extends Query{

    private String streetName;

    public QueryStreet(String mapName, String streetName) {
        super(mapName);
        query = "SELECT * FROM dbo." + mapName + "Road WHERE nameID LIKE '" + streetName + "%'";
        this.streetName = streetName;
    }

    @Override
    public Street arrangeRecievedData(ResultSet resultSet){

        Set<Road> roads = new HashSet();
        try{
            while (resultSet.next()){
                String nameID = resultSet.getString("nameID");
                int cord1x =  resultSet.getInt("cord1x");
                int cord2x =  resultSet.getInt("cord2x");
                int cord1y =  resultSet.getInt("cord1y");
                int cord2y =  resultSet.getInt("cord2y");
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
        return new DataStreet(streetName, roads, mapName);
    }
}