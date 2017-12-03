package il.ac.technion.cs.yp.btw.db;

/*need to be tested with our server*/

java.lang.reflect.Method method;

/*this class is responsible for the connection to ths SQL server*/
public class MainDataBase{


    /*making a connection is explained in the link: https://docs.microsoft.com/en-us/sql/connect/jdbc/retrieving-result-set-data-sample*/
    public MainDataBase() {


    }

    /*for every query from the SQL server we will need to establish a connection and close it afterwards.
    * for that purpose we will use that generic method to call the query.
    * the query will be implemented in a private function. for every query there will be a sell function
    * that calls to this function with the name of the querry functions and the arguments.
    *
    * this is an experiment and might be changed.*/
    private Object connectToDataBaseServer(String methodName, Object[] orderedArguments){


        Object res=null;
        // Create a variable for the connection string.
        String connectionUrl =  "jdbc:sqlserver://btwserver.database.windows.net:1433;" +
                                "databaseName=BTW" +
                                "integratedSecurity=true;";

        // Declare the JDBC objects.
        Connection con = null; //this variable is responsible for the connection to the server.
        Statement stmt = null;
        ResultSet rs = null;

        try{
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            int classArgSize = orderedArguments.size();
            if(orderedArguments != null) {
                Class[] classArg = new Class[classArgSize];

                for (int i = 0; i < orderedArguments.size(); i++) {//get all arguments types;
                    classArg[i] = argumentsIterator[i]. class ;
                }
            }

            //public Method getMethod(String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
            try {//find a method by name
                if(orderedArguments != null) {
                    method = this.getClass().getMethod(methodName, classArg);
                }else{
                    method = this.getClass().getMethod(methodName);
                }
            } catch (SecurityException e) { ... }
            catch (NoSuchMethodException e) { ... }

            try {//invoke the found method with the current instace.
                if(orderedArguments != null) {
                    res = method.invoke(this, orderedArguments);
                }else{
                    res = method.invoke(this);
                }
            } catch (IllegalArgumentException e) { ... }
            catch (IllegalAccessException e) { ... }
            catch (InvocationTargetException e) { ... }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (rs != null) try { rs.close(); } catch(Exception e) {}
            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
            if (con != null) try { con.close(); } catch(Exception e) {}
        }

        return res;

    }


    private ResultSet queryExampleAux(){
        // Create and execute an SQL statement that returns a
        // set of data and then display it.

        Statement stmt = null;  //from the original example
        ResultSet rs = null;   //from the original example
        String SQL = "SELECT * FROM Production.Product;";
        stmt = con.createStatement();
        rs = stmt.executeQuery(SQL);
        displayRow("PRODUCTS", rs);
        return rs;
    }
    /*the sell for the query example*/
    public ResultSet queryExample(){
       return  connectToDataBaseServer("queryExampleAux", null);
    }

}


