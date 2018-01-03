package il.ac.technion.cs.yp.btw.db.queries;


import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryRoad extends Query {

    public QueryRoad(String mapName, String nameID) {
        super(mapName);
        this.query = "SELECT * FROM dbo." + mapName + "Road WHERE nameID = '" + nameID + "'";
    }

    @Override
    public Road arrangeRecievedData(ResultSet resultSet){
        Road road = null;
        try{
            resultSet.next();
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

                road = new DataRoad (nameID, length,myStreet, sourceCrossroadId, destinationCrossroadId, secStart, secEnd, overload, mapName);
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return road;

    }
}