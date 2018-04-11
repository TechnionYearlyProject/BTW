package il.ac.technion.cs.yp.btw.db;

/*need to be tested with our server*/


import il.ac.technion.cs.yp.btw.db.queries.Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*this class is responsible for the connection to ths SQL server*/
public class MainDataBase{

    private static Connection connection;

    /*making a connection is explained in the link: https://docs.microsoft.com/en-us/sql/connect/jdbc/retrieving-result-set-data-sample*/
    public MainDataBase() {


    }

    /**
     * @author: shay
     * @date: 20/1/18
     * @author Sharon Hadar
     * @Date 21/01/2018
     * open connection to the server
     */
    public static void openConnection(){
        // Connect to database
        String url = "jdbc:sqlserver://btwservernew.database.windows.net:1433;" +
                "database=BTW;" +
                "user=shay@btwservernew;" +
                "password=S123456!;" +
                "encrypt=true;" +
                "trustServerCertificate=false;" +
                "hostNameInCertificate=*.database.windows.net;" +
                "loginTimeout=30;" +
                "resource_limit=false;";
        //Connection connection = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url);
            //System.out.println("successfuly connected to the server");
        }catch (Exception e) {

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e1) {
                    System.out.println("failed to connect the server ");
                }
            }
            connection = null;
        }
    }

    public static void closeConnection(){
        if (connection != null) {
            try {
                connection.close();
                //System.out.println("successfuly closed connection to the server");
                connection = null;
            } catch (Exception e1) {
                System.out.println("failed to close connection to the server");
            }
        }

    }

    /*for every query from the SQL server we will need to establish a connection and close it afterwards.
    * for that purpose we will use that generic method to call the query.
    * for every query there will be a sell function that calls to this function.
    */


    /*
     * @author Sharon Hadar
     * @Date 21/01/2018
     * @author: shay
     * @date: 20/1/18
     *execute query and return the result as an instance of the matching object
     *the return value must be closed after the use is done*/
    private static Object queryDataBaseServer(Query query){

        Statement statement = null;
        ResultSet resultSet = null;
        Object result = null;
        try {
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
            if (statement != null){
                try {
                    statement.close();
                } catch(Exception e) {
                    System.out.println("failed to close statement for query " + query.getClass().getName());
                }
            }
            if (resultSet != null){
                try {
                } catch(Exception e) {
                    System.out.println("failed to close result set for query " + query.getClass().getName());
                }
            }
        }

        return result;
    }

    /*
     * @author: shay
     * @date: 20/1/18
     * @author Sharon Hadar
     * @Date 21/01/2018
     *create an instance of the object by the query's result
     * the activate the  the query on the data base*/
    public static Object queryDataBase(Query query){

        Object result = null;
        try{
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

    /**
     * * @author: shay
     * @date: 20/1/18
     * @param sqlQuery query in string just to save
     */
    public static void saveDataFromQuery(String sqlQuery) {
        /*String url = "jdbc:sqlserver://btwserver.database.windows.net:1433;" +
                "database=BTW;" +
                "user=shay@btwserver;" +
                "password=S123456!;" +
                "encrypt=true;" +
                "trustServerCertificate=false;" +
                "hostNameInCertificate=*.database.windows.net;" +
                "loginTimeout=30;";*/
        //Connection connection = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            }
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
    }
}


