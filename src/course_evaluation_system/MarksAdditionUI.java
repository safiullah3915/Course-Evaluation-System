package course_evaluation_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;

public class MarksAdditionUI extends JFrame {
    private JLabel courseLabel, majorInstrumentLabel, minorInstrumentLabel, marks;
    private JTable marksTable;
    private JButton saveButton, cancelButton;
    private Controller controller;
    private MarksAdditionDomain marksAdditionDomain; // Domain service

    public MarksAdditionUI(Controller controller, MarksAdditionDomain marksAdditionDomain) {
        this.controller = controller;
        this.marksAdditionDomain = marksAdditionDomain;
        setTitle("Add Marks for "); // course
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create a sample table model (replace with actual data)
        String[] columnNames = { "Student Name", "Marks" };
        Object[][] data = { { "Student 1", "" }, { "Student 2", "" }, { "Student 3", "" } };
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        marksTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(marksTable);

        // Save and Cancel Buttons
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Create the main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create a panel for labels in vertical order using BoxLayout
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        // Course Label
        courseLabel = new JLabel("Course: SC"); // + course
        labelPanel.add(courseLabel);

        // Major Instrument Label
        majorInstrumentLabel = new JLabel("Major Instrument: Quiz"); // + majorInstrument
        labelPanel.add(majorInstrumentLabel);

        // Minor Instrument Label
        minorInstrumentLabel = new JLabel("Minor Instrument: Q1"); // + minorInstrument
        labelPanel.add(minorInstrumentLabel);

        // Total Marks Label
        marks = new JLabel("Total Marks: 10");

        // Add the label panel and the table to the main panel
        mainPanel.add(labelPanel, BorderLayout.WEST);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(marks, BorderLayout.NORTH); // Place the "Total Marks" label at the top
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Set up button listeners
        saveButton.addActionListener(e -> saveMarks());
        cancelButton.addActionListener(e -> dispose());
    }

 
   
    
    private void saveMarks() {
        DefaultTableModel model = (DefaultTableModel) marksTable.getModel();
        int rowCount = model.getRowCount();

        for (int row = 0; row < rowCount; row++) {
            String studentName = (String) model.getValueAt(row, 0);
            String marks = (String) model.getValueAt(row, 1);

            if (isValidMarksInput(marks)) {
                // Pass the data to the domain service for processing
                controller.saveStudentMarks(studentName, marks);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid marks input for student: " + studentName,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        dispose();
    }

   

    private boolean isValidMarksInput(String input) {
        // Check if input is numeric and has at most 3 characters
        return input.matches("\\d{1,3}");
    }

    public static void main(String[] args) {
        Controller controller = new Controller("Safi"); //usernameField
        MarksAdditionDomain marksAdditionDomain = new MarksAdditionDomain();
        MarksAdditionUI marksAdditionUI = new MarksAdditionUI(controller, marksAdditionDomain);
        marksAdditionUI.setVisible(true);
    }
}
