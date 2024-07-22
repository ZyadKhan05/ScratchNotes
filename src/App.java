import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/scratch_notes", "root", "1234");
            statement = con.createStatement();
            String query = "SELECT Username FROM users WHERE UserID = 1";
            rs = statement.executeQuery(query);

            if (rs.next()) { // Move the cursor to the first row
                String name = rs.getString("Username");
                System.out.println(name);
            } else {
                System.out.println("No user found with UserID = 1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
    }
}
