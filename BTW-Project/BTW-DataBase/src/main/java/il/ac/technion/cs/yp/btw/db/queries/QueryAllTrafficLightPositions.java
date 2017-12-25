package il.ac.technion.cs.yp.btw.db.queries;

import il.ac.technion.cs.yp.btw.classes.PointImpl;
import il.ac.technion.cs.yp.btw.classes.Point;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

public class QueryAllTrafficLightPositions extends Query{

    public QueryAllTrafficLightPositions(String mapName){
        super(mapName);
        this.query = "SELECT * FROM dbo."+ mapName + "TrafficLight";
    }

    @Override
    public Set<Point> arrangeRecievedData(ResultSet resultSet){
        Set<Point> positions = new HashSet();
        try{
            while (resultSet.next()){

                int cordx =  resultSet.getInt("cordx");
                int cordy =  resultSet.getInt("cordy");
                Point position = new PointImpl(cordx, cordy);

                positions.add(position);

            }
        }catch(SQLException e){
            System.out.println("query has failed");
        }
        return positions;

    }
}