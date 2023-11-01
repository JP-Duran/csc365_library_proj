import java.sql.*;

class PlayWithMySQL {
  static   Connection connect;
  public static void main(String[] args) {
   try {
     Class.forName("com.mysql.cj.jdbc.Driver");
     connect = DriverManager.getConnection(
"jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/thegoats?user=thegoats&password=someSecurePassword");
      Statement statement = connect.createStatement();
      statement.executeUpdate("CREATE TABLE Test (a integer primary key);");
        } catch (Exception e) {
               e.printStackTrace();
        } finally {
        //try { rs.close(); } catch (Exception e) { /* Ignored */ }
        //try { statement.close(); } catch (Exception e) { /* Ignored */ }
        try { connect.close(); } catch (Exception e) { /* Ignored */ }
        }
    }
}
