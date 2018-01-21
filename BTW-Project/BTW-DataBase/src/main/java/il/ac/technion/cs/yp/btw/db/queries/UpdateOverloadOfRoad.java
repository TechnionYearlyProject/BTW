package il.ac.technion.cs.yp.btw.db.queries;


import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;

import java.sql.ResultSet;
import java.sql.SQLException;

/*update a road's overload in the data base*/
public class UpdateOverloadOfRoad extends Query {

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public UpdateOverloadOfRoad(String mapName, String nameID, long overload) {
        super(mapName);
        query = "UPDATE * dbo." + mapName + "Road SET overload = " + overload + " WHERE nameID = '" + nameID + "'";
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     *no informationn was received from the data base*/
    @Override
    public Road arrangeRecievedData(ResultSet resultSet){
        return null;
    }
}