package query_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/* Current placeholder class for JDBC SQL Server connection
 * +++ DOCUMENTATION NEEDED +++
 */
public class init_connection {

    private Connection connect;
    public ResultSet result;
    private static String jdbc_link = "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/thegoats?user=thegoats&password=someSecurePassword";

    /* Initializes a connection to the SQL server
     * +++ Connection must be closed when finished +++
     */
    public init_connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connect = DriverManager.getConnection(jdbc_link);
        } catch (Exception e) {
            e.printStackTrace();
        } // finally {
        //try { rs.close(); } catch (Exception e) { /* Ignored */ }
        //try { statement.close(); } catch (Exception e) { /* Ignored */ }
        // try { this.connect.close(); } catch (Exception e) { /* Ignored */ }
        // }
    }

    /* MUST BE CALLED AT END OF MAIN
    * Closes the connection to the SQL server
    */
    public void close() {
        try { result.close(); } catch (Exception e) { /* Ignored */ }
        try { this.connect.close(); } catch (Exception e) { /* Ignored */ }
        // try { statement.close(); } catch (Exception e) { /* Ignored */ }

    }

    /* Queries the user table for a password matching the given username
    * PARAM: A String containing the username to be queried
    * RETURN: A ResultSet with 1 attribute 'upassword' which contains 1 password if the
    *         user is found or null if the user is not found
    */
    public ResultSet user_login(String user_id) {
        try{    
            String user_id_query = "select Users.upassword from Users where uname = ?";
            PreparedStatement prepared_query = connect.prepareStatement(user_id_query);
            prepared_query.setString(1, user_id);
            return prepared_query.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
