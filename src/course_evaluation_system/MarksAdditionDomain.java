package course_evaluation_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MarksAdditionDomain {
    // Method to save student marks (replace with actual logic)
	 public void saveStudentMarks(String studentName, String marks) {
	        try (Connection connection = DatabaseConnection.getConnection()) {
	            if (connection != null) {
	                String query = "INSERT INTO marks (student_name, marks) VALUES (?, ?)";
	                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                    preparedStatement.setString(1, studentName);
	                    preparedStatement.setString(2, marks);
	                    int rowsAffected = preparedStatement.executeUpdate();

	                    System.out.println(rowsAffected + " row(s) affected.");

	                    if (rowsAffected > 0) {
	                        System.out.println("Marks saved successfully for student: " + studentName);
	                    } else {
	                        System.out.println("Failed to save marks for student: " + studentName);
	                    }
	                }
	            } else {
	                System.err.println("Failed to establish a database connection.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
    // Add other domain functionalities as needed
}
