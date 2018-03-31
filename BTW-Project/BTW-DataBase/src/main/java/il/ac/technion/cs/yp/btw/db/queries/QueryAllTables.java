package il.ac.technion.cs.yp.btw.db.queries;
import il.ac.technion.cs.yp.btw.classes.Point;
import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Road;
import il.ac.technion.cs.yp.btw.db.DataObjects.DataRoad;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
/**
 * Created by shay on 31/03/2018.
 */
public class QueryAllTables extends Query {

    /**
     * @author: shay
     * @date: 31/03/2018.
     * creates query for selecting table names.
     */
    public QueryAllTables(String mapName){
        super("AdminTables");
        query = "SELECT * FROM dbo.AdminTables;";
    }

    /**
     * @author: shay
     * @date: 31/03/2018.
     * implements the virtual function.
     */
    @Override
    public Set<String> arrangeRecievedData(ResultSet resultSet){
        Set<String> mapNames = new HashSet();
        try{
            while (resultSet.next()){
                String nameID = resultSet.getString("mapName");
                mapNames.add(nameID);
            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return mapNames;

    }

}
