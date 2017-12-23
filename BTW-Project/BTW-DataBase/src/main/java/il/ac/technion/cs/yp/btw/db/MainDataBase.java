package il.ac.technion.cs.yp.btw.db;

/*need to be tested with our server*/


import il.ac.technion.cs.yp.btw.db.queries.Query;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import com.microsoft.sqlserver.jdbc.*;

/*this class is responsible for the connection to ths SQL server*/
public class MainDataBase{


    /*making a connection is explained in the link: https://docs.microsoft.com/en-us/sql/connect/jdbc/retrieving-result-set-data-sample*/
    public MainDataBase() {


    }

    /*for every query from the SQL server we will need to establish a connection and close it afterwards.
    * for that purpose we will use that generic method to call the query.
    * for every query there will be a sell function that calls to this function.
    */

    /*the return value must be closed after the use is done*/
    private static Object queryDataBaseServer(Query query){

        // Connect to database
        String url = String.format("jdbc:sqlserver://btwserver.database.windows.net:1433;" +
                                    "database=BTW;" +
                                    "user=shay@btwserver;" +
                                    "password=S123456!;" +
                                    "encrypt=true;" +
                                    "trustServerCertificate=false;" +
                                    "hostNameInCertificate=*.database.windows.net;" +
                                    "loginTimeout=30;");
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Object result = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url);
            System.out.println("successfuly connected to the server");
            statement = connection.createStatement();
            try {
                resultSet = statement.executeQuery(query.getQuery());
                result = query.arrangeRecievedData(resultSet);
            }catch(Exception e){
                System.out.println("failed to to execute query" + query.getClass().getName());
            }
        }
        catch (Exception e) {
            System.out.println("failed to connect the server for query " + query.getClass().getName());
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    System.out.println("failed to close connection for query " + query.getClass().getName());
                }
            }
            if (statement != null){
                try {
                    statement.close();
                } catch(Exception e) {
                    System.out.println("failed to close statement for query " + query.getClass().getName());
                }
            }
            if (resultSet != null){
                try {
                    //  resultSet.close();
                } catch(Exception e) {
                    System.out.println("failed to close result set for query " + query.getClass().getName());
                }
            }
        }

        return result;
    }

    /*the sell for the query example*/
    public static Object queryDataBase(Query query){
       // ResultSet resultSet= null;
        Object result = null;
        //String query = "SELECT * FROM Production.Product;";
        try{
            //resultSet = connectToDataBaseServer(query);
            //result = query.arrangeRecievedData(resultSet);
            result = queryDataBaseServer(query);

        }catch (Exception e) {
            e.printStackTrace();
        }/*finally{
            if (resultSet != null){
                try {
                  //  resultSet.close();
                } catch(Exception e) {
                    System.out.println("failed to close result set for query " + query.getClass().getName());
                }
            }
        }*/
        return result;
    }

}


