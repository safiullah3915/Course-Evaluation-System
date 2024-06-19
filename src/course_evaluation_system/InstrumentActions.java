package course_evaluation_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class InstrumentActions extends JFrame {
    private JComboBox<String> courseComboBox;
    private JButton addButton, updateButton, deleteButton, logoutButton;
    private Controller controller;
    private JLabel userLabel;
    private JTable instrumentTable;

    public InstrumentActions(Controller controller, String username) {
        this.controller = controller;

        setTitle("Course Evaluation System - Dashboard");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // User label and Logout button
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userLabel = new JLabel("User: Safi Ullah" + username);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> controller.logout());
        userPanel.add(userLabel);
        userPanel.add(logoutButton);
        mainPanel.add(userPanel, BorderLayout.NORTH);

        // Course selection
        String[] courses = {"Course 1", "Course 2", "Course 3"};
        courseComboBox = new JComboBox<>(courses);
        courseComboBox.addActionListener(e -> controller.updateCourseDetails((String) courseComboBox.getSelectedItem()));
        mainPanel.add(courseComboBox, BorderLayout.NORTH);

        // Instrument Table
        String[] columnNames = {"Major Instrument", "Actions", "Minor Instrument", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 3;
            }
        };

        instrumentTable = new JTable(tableModel);

        // Create button renderers for the "Actions" columns
        for (int i = 1; i <= 3; i += 2) {
            instrumentTable.getColumnModel().getColumn(i).setCellRenderer(new ButtonRenderer());
        }

        // Create button editors for the "Actions" columns
        instrumentTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(controller, "Edit", "Button2"));
        instrumentTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(controller, "View", "Button5"));

        // Place the instrument table in the CENTER region
        mainPanel.add(new JScrollPane(instrumentTable), BorderLayout.CENTER);

        // Add, Update, and Delete Instrument Buttons
        addButton = new JButton("Add Major Instrument");
        updateButton = new JButton("Update Major Instrument");
        deleteButton = new JButton("Delete Major Instrument");

        addButton.addActionListener(e -> showInstrumentAddition());
        //updateButton.addActionListener(e -> showInstrumentUpdate());
        //deleteButton.addActionListener(e -> deleteInstrument());

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Add the button panel to the SOUTH region
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Initialize the instrument table with data from the database
        refreshTable();
        add(mainPanel);
    }

    public void updateInstrumentTable(String major, String weightage, String minor) {
        DefaultTableModel model = (DefaultTableModel) instrumentTable.getModel();
        model.addRow(new Object[]{major, "Edit", minor, "View"});
    }
    public void populateCourseDropdown() {
        List<String> courseNames = controller.getCourseNames();
        courseComboBox.setModel(new DefaultComboBoxModel<>(courseNames.toArray(new String[0])));
    }
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) instrumentTable.getModel();
        model.setRowCount(0);

        // Fetch new data from the updated InstrumentDomain and populate the table
        for (String[] instrument : controller.getInstrumentData()) {
            model.addRow(new Object[]{instrument[0], "Edit", instrument[2], "View"});
        }
        populateCourseDropdown();
    }

    private void showInstrumentAddition() {
        InstrumentAddition instrumentAddition = new InstrumentAddition(controller, this);
        instrumentAddition.setVisible(true);
    }

    private void showInstrumentUpdate() {
        int selectedRowIndex = instrumentTable.getSelectedRow();
        if (selectedRowIndex >= 0) {
            String[] selectedInstrument = controller.getInstrumentData().get(selectedRowIndex);
            // Implement the update functionality here
            // You can use selectedInstrument data for updating
            // Refresh the table after updating
            refreshTable();
        }
    }
    
    public void updateInstrumentTable(List<String[]> instrumentData) {
        DefaultTableModel model = (DefaultTableModel) instrumentTable.getModel();
        model.setRowCount(0);

        // Populate the table with the fetched instrument data
        for (String[] instrument : instrumentData) {
            model.addRow(new Object[]{instrument[0], "Edit", instrument[2], "View"});
        }
    }

    public void updateCourseDetails(String course) {
        controller.updateCourseDetails(course);
    }

    /*public void updateCourseDetails(String course) {
        controller.updateInstrumentDomain(); // Ensure that the instrument domain is initialized

        // Fetch the instrument data based on the selected course
        List<String[]> instrumentData = controller.getInstrumentDataForCourse(course);

        // Update the table with the fetched instrument data
        DefaultTableModel model = (DefaultTableModel) instrumentTable.getModel();
        model.setRowCount(0);

        for (String[] instrument : instrumentData) {
            model.addRow(new Object[]{instrument[0], "Edit", instrument[2], "View"});
        }
    }*/

    private void deleteInstrument() {
        int selectedRowIndex = instrumentTable.getSelectedRow();
        if (selectedRowIndex >= 0) {
            String[] selectedInstrument = controller.getInstrumentData().get(selectedRowIndex);
            // Implement the delete functionality here
            // You can use selectedInstrument data for deletion
            // Refresh the table after deletion
            refreshTable();
        }
    }

    // Custom renderer for buttons in the table
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final List<JButton> buttons;

        public ButtonRenderer(String... buttonLabels) {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            buttons = new ArrayList<>();

            for (String label : buttonLabels) {
                JButton button = new JButton(label);
                buttons.add(button);
                add(button);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 40); // Adjust the width and height as needed
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Custom button editor for buttons
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel;
        private final List<JButton> buttons;
        private String clickedButton;

        public ButtonEditor(Controller controller, String... buttonLabels) {
            panel = new JPanel();
            buttons = new ArrayList<>();

            for (String label : buttonLabels) {
                JButton button = new JButton(label);
                button.addActionListener(e -> {
                    clickedButton = label;
                    fireEditingStopped();
                });
                buttons.add(button);
                panel.add(button);
            }
        }

        @Override
        public Object getCellEditorValue() {
            return clickedButton;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return panel;
        }
    }

    public static void main(String[] args) {
        // Example usage
        Controller controller = new Controller("John Doe");
        InstrumentActions instrumentActions = new InstrumentActions(controller, "John Doe");
        instrumentActions.setVisible(true);
    }

    public String getUsername(String username) {
        return username;
    }
}
