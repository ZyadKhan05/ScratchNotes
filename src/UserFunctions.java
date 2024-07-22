import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/*
 * This class provides different functions for the users to manage 
 * their card sets. Data is stored in SQL databases, and data is
 * accessed and modified using JDBC and mySQL.
 */
public class UserFunctions {

    // Parameters for JBDC connection
    private final String url = "jdbc:mysql://localhost:3306/scratch_notes";
    private final String usernameAcc = "root";
    private final String passwordAcc = "1234";

    // JBDC required fields to connect to database server
    protected Connection connection;
    protected Statement statement;

    // Constructor
    public UserFunctions() {

        // Try to establish a connection
        try {

            // Try to establish a connection to database
            connection = DriverManager.getConnection
            (url, usernameAcc, passwordAcc);

            // This allows us to use java for query statements
            statement = connection.createStatement();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // User functions

    // Create account and store it in SQL database
    public boolean createAccount(String username, String password){

        // MySQL query
        String query = "INSERT INTO users (Username, Password) " +
                       "VALUES (?, ?)";

        // Inserting and executing query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            System.out.println("Account created successfully.");
            return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    }

    // Delete account and update it in SQL database
    public boolean deleteAccount(String username){

        // MySQL query
        String query = "DELETE FROM users WHERE Username = ?";

        // Inserting and executing query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            // Make sure that table is updated
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account deleted successfully.");
                return true;
            } else {
                System.out.println("Account not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Change old username to new username
    public boolean changeUsername(String oldUsername, String newUsername){

        // MySQL query
        String query = "UPDATE users SET Username = ? WHERE Username = ?";

        // Inserting and executing query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, oldUsername);

            // Make sure that table is updated
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Username changed successfully.");
                return true;
            } else {
                System.out.println("Account not found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Change password based on username
    public boolean changePassword(String username, String oldPassword, String newPassword){

        // MySQL query
        String query = "UPDATE users SET Password = ? WHERE Username = ? " +
            "AND Password = ?";

        // Inserting and executing query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, oldPassword);

            // Make sure that table is updated
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Password changed successfully.");
                return true;
            } else {
                System.out.println("Account not found or old password incorrect.");
                return false;
            }
        }   catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // User is signed in and placed in active_users table
    public boolean signIn(String username, String password) {

        // Query to check if the user exists from users table
        String checkUserQuery = "SELECT 1 FROM users WHERE Username = ? " +
            "AND Password = ?";

        // Query to insert user into the list of active users
        String insertActiveUserQuery = "INSERT INTO active_users (Username, Password) " +
            "VALUES (?, ?)";

        // Inserting and executing first query to check existence of user
        try (PreparedStatement checkUserStatement = connection.prepareStatement(checkUserQuery)){
            checkUserStatement.setString(1, username);
            checkUserStatement.setString(2, password);

            /*
             * Store executed statment in a ResultSet. If users exist from updating pointers,
             * prepare the insert statement to be executed and prompt user with success
             * message. Otherwise, promt user with invalid message
             */
            try (ResultSet rs = checkUserStatement.executeQuery()){
                if (rs.next()){
                    try (PreparedStatement insertActiveUserStmt = connection.prepareStatement(insertActiveUserQuery)) {
                        insertActiveUserStmt.setString(1, username);
                        insertActiveUserStmt.setString(2, password);
                        insertActiveUserStmt.executeUpdate();
                        System.out.println("User logged in successfully.");
                        return true;
                    }
                } else {
                    System.out.println("Invalid username or password.");
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }     
    }

    // Remove user from active_users table/signs them out
    public boolean signOut(String username) {

        // MySQL query
        String query = "DELETE FROM active_users WHERE Username = ?";

        // Inserting and executing query
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);

            // Make sure that table is updated
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account signed out successfully.");
                return true;
            } else {
                System.out.println("User not found in active users.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Close the connections
    public void closeConnection() {
        try {
            if (statement != null) {
                statement.close();
            } 
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }  
}