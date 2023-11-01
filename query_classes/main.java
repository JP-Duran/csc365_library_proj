package query_classes;
import java.sql.*;

class Main {
    public static void main(String[] args) {
        init_connection con = new init_connection();
        System.out.println(con.user_login("teddy", "pw2"));
        System.out.println(con.employee_login("employee1jac", "emppw2"));
        con.close();
    }
}
