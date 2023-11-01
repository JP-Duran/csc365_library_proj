package query_classes;
import java.sql.*;

class Main {
    public static void main(String[] args) {
        init_connection con = new init_connection();
        ResultSet rs = con.user_login("jonsnow");
        try {
            while (rs.next()) {
                String pw = rs.getString("upassword");
                System.out.println(pw);
            }
        } catch (Exception e) { e.printStackTrace(); }
        con.close();
    }
}
