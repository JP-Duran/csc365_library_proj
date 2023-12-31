package query_classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.PreparedStatement;

/* Current placeholder class for JDBC SQL Server connection
 * +++ DOCUMENTATION NEEDED +++
 */
public class init_connection {

    ArrayList<String> validGenres = new ArrayList<>(Arrays.asList("fiction", "nonfiction", "mystery", "romance", "fantasy"));
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
            ResultSet result = prepared_query.executeQuery();
            // This will throw exception if employee does not exist
            result.next();
            // System.out.println(result.getString("emppassword"));
            if (emp_pw.equals(result.getString("emppassword"))) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /*
    * update user password
    * PARAMS: cardNum, newPassword
    * RETURN: true if update successful, false otherwise
    */
    public boolean update_user_password(String card_number, String new_pw) {
        try {
            String update_password_query = "update Users set upassword = ? where cardNum = ?";
            PreparedStatement prepared_query = connect.prepareStatement(update_password_query);
            prepared_query.setString(1, new_pw);
            prepared_query.setString(2, card_number);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /*
     * search book
     * PARAMS: genre, book_title, isbn, lib_id
     * ENUM GENRE: fiction, nonfiction, horror
     * RETURN: ResultSet containing query results
     */
    public ResultSet search_book(String genre, String book_title, String isbn, Integer lib_id) {
        try {
            String book_query = "select * from Books where genre like ? and bname like ? and isbn like ? and lid like ?";
            PreparedStatement prepared_query = connect.prepareStatement(book_query);
            prepared_query.setString(1, genre.isEmpty() ? "%" : genre);
            prepared_query.setString(2, book_title.isEmpty() ? "%" : "%" + book_title + "%");
            prepared_query.setString(3, isbn.isEmpty() ? "%" : isbn);
            prepared_query.setString(4, lib_id == null ? "%" : lib_id.toString());
            ResultSet result = prepared_query.executeQuery();
            return result;
        } catch (Exception e) { /*Ignore */ }
        return null;
    }

    /*
    * add book
    * PARAMS: genre, book_title, isbn, lib_id
    * RETURN: true if addition successful, false if failure
    */
    public boolean add_book(String genre, String book_title, String isbn, Integer lid) {
        try {
            genre = genre.toLowerCase();
            if (!validGenres.contains(genre) || genre == null || book_title == null || isbn == null || lid == null) {
                return false;
            }
            String add_book_query = "insert into Books (genre, bname, isbn, lid) values (?, ?, ?, ?)";
            PreparedStatement prepared_query = connect.prepareStatement(add_book_query);
            prepared_query.setString(1, genre);
            prepared_query.setString(2, book_title);
            prepared_query.setString(3, isbn);
            prepared_query.setInt(4, lid);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /*
    * remove book
    * PARAMS: isbn
    * RETURN: true if removal successful, false if failure
    */
    public boolean remove_book(String isbn) {
        try {
            String remove_book_query = "delete from Books where isbn = ?";
            PreparedStatement prepared_query = connect.prepareStatement(remove_book_query);
            prepared_query.setString(1, isbn);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /*
    * check out a book
    * PARAMS: card_number, isbn
    * RETURN: true if book is checked out, false if book doesn't exist or is already checked out
    */
    public boolean check_out_book(String card_number, String isbn) {
        try {
            String check_out_query = "update Books set cardnum = ?, available = 0 where isbn = ? and available = 1";
            PreparedStatement prepared_query = connect.prepareStatement(check_out_query);
            prepared_query.setString(1, card_number);
            prepared_query.setString(2, isbn);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /*
    * return a book
    * PARAMS: isbn
    * RETURN: true if book is returned, false if book doesn't exist or other error
    */
    public boolean return_book(String isbn) {
        try {
            String return_book_query = "update Books set cardnum = null, available = 1 where isbn = ? and available = 0";
            PreparedStatement prepared_query = connect.prepareStatement(return_book_query);
            prepared_query.setString(1, isbn);
            int rows_affected = prepared_query.executeUpdate();
            if (rows_affected > 0) return true;
        } catch (Exception e) { /*Ignore */ }
        return false;
    }

    /*
    * personal info query
    * PARAMS: empid
    * RETURN: ArrayList of Strings containing [llocation, emppassword], or null if employee not found
    */
    public ArrayList<String> personal_info_query(String empid) {
        ArrayList<String> info = new ArrayList<>();
        try {
            String info_query = "select Libraries.llocation, Employees.emppassword from Employees join Libraries on Employees.lid = Libraries.lid where Employees.empid = ?";
            PreparedStatement prepared_query = connect.prepareStatement(info_query);
            prepared_query.setString(1, empid);
            ResultSet result = prepared_query.executeQuery();
            if (!result.next()) { return null; }
            do {
                info.add(result.getString("llocation"));
                info.add(result.getString("emppassword"));
            } while (result.next());
        } catch (Exception e) { /*Ignore */ }
        return info;
    }

    /*
    * get library name
    * PARAMS: lid
    * RETURN: library name as a String, or null if library not found
    */
    public String get_library_name(Integer lid) {
        String libraryName = null;
        try {
            String library_query = "select llocation from Libraries where lid = ?";
            PreparedStatement prepared_query = connect.prepareStatement(library_query);
            prepared_query.setInt(1, lid);
            ResultSet result = prepared_query.executeQuery();
            if (result.next()) {
                libraryName = result.getString("llocation");
            }
        } catch (Exception e) { /*Ignore */ }
        return libraryName;
    }

    /*
    * get library id
    * PARAMS: llocation
    * RETURN: library id as an Integer, or null if library not found
    */
    public Integer get_library_id(String llocation) {
        Integer libraryId = null;
        try {
            String library_query = "select lid from Libraries where llocation = ?";
            PreparedStatement prepared_query = connect.prepareStatement(library_query);
            prepared_query.setString(1, llocation);
            ResultSet result = prepared_query.executeQuery();
            if (result.next()) {
                libraryId = result.getInt("lid");
            }
        } catch (Exception e) { /*Ignore */ }
        return libraryId;
    }
}

