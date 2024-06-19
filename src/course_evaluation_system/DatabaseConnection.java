package course_evaluation_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ces";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "safimughal123#";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
}



/*public static void main(String[] args) {
try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ces","root","safimughal123#");
	Statement stmt = con.createStatement();
	System.out.println("Inserting Records");
	
}catch( Exception e) {
	System.out.println(e); 
}	
}*/