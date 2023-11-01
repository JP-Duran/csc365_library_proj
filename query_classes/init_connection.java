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

    /* Queries the Users table for a matching username password combo
     * PARAMS: user_id - the username to query
     *         user_pw - the password to query
     * RETURN: true - if user 'user_id' exists and the password 'user_pw' matches their password
     *         false - if the user does not exist or the password does not match
     */
    public boolean user_login(String user_id, String user_pw) {
        try {
            String user_id_query = "select Users.upassword from Users where uname = ?";
            PreparedStatement prepared_query = connect.prepareStatement(user_id_query);
            prepared_query.setString(1, user_id);
            result = prepared_query.executeQuery();
            // This will throw exception if user does not exist
            result.next();
            // System.out.println(result.getString("upassword"));
            if (user_pw.equals(result.getString("upassword"))) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /* Queries the Employees table for a matching empid password combo
     * PARAMS: emp_id - the empid to query
     *         emp_pw - the password to query
     * RETURN: true - if employee 'emp_id' exists and the password 'emp_pw' matches their password
     *         false - if the employee does not exist or the password does not match
     */
    public boolean employee_login(String emp_id, String emp_pw) {
        try {
            String emp_id_query = "select Employees.emppassword from Employees where empid = ?";
            PreparedStatement prepared_query = connect.prepareStatement(emp_id_query);
            prepared_query.setString(1, emp_id);
            result = prepared_query.executeQuery();
            // This will throw exception if employee does not exist
            result.next();
            // System.out.println(result.getString("emppassword"));
            if (emp_pw.equals(result.getString("emppassword"))) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }
}
