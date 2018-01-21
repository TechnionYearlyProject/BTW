package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Point;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

/*get from the data base all the coordinates which has traffic lights on them. retuen them in a form of a point.
* this class is not used in the current semester*/
public class QueryAllTrafficLightPositions extends Query{

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018*/
    public QueryAllTrafficLightPositions(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "TrafficLight";
    }

    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * construct a traffic light by results from the data base*/
    @Override
    public Set<Point> arrangeRecievedData(ResultSet resultSet){
        Set<Point> positions = new HashSet();
        try{
            while (resultSet.next()){

                double cordx =  resultSet.getDouble("cordx");
                double cordy =  resultSet.getDouble("cordy");
                Point position = new PointImpl(cordx, cordy);

                positions.add(position);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return positions;

    }
}