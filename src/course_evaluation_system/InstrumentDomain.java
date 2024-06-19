package course_evaluation_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDomain {
    private String JDBC_URL = "jdbc:mysql://localhost:3306/ces";
    private String USERNAME = "root";
    private String PASSWORD = "safimughal123#";

    public List<String[]> getInstrumentsFromDB() {
        List<String[]> instruments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT major_instrument_name, weightage, minor_instrument_name FROM instrument";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String major = resultSet.getString("major_instrument_name");
                    String weightage = resultSet.getString("weightage");
                    String minor = resultSet.getString("minor_instrument_name");
                    instruments.add(new String[]{major, weightage, minor});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instruments;
    }
    
    public List<String> getCourseNamesFromDB() {
        List<String> courses = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT course_name FROM course";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String courseName = resultSet.getString("course_name");
                    courses.add(courseName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public void addInstrumentData(String major, String weightage, String minor) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "INSERT INTO instrument (major_instrument_name, weightage, minor_instrument_name) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, major);
                preparedStatement.setString(2, weightage);
                preparedStatement.setString(3, minor);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
