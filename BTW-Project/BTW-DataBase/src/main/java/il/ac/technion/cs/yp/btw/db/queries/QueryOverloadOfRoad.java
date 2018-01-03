package il.ac.technion.cs.yp.btw.db.queries;


import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryOverloadOfRoad extends Query {

    public QueryOverloadOfRoad(String mapName, String nameID) {
        super(mapName);
        query = "SELECT * FROM dbo." + mapName + "Road WHERE nameID = '" + nameID + "'";
    }

    @Override
    public Long arrangeRecievedData(ResultSet resultSet){
        long overloadOfRoad = 0;
        try{
            resultSet.next();
            overloadOfRoad =  resultSet.getLong("overload");
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return overloadOfRoad;

    }
}