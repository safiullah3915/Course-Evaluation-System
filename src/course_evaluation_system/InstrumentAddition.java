package course_evaluation_system;

import javax.swing.*;
import java.awt.*;

public class InstrumentAddition extends JFrame {
    private JTextField majorInstrumentField, weightageField, minorInstrumentField;
    private JButton saveButton, cancelButton;
    private Controller controller;
    private InstrumentActions instrumentActions; // Reference to the previous window

    public InstrumentAddition(Controller controller, InstrumentActions instrumentActions) {
        this.controller = controller;
        this.instrumentActions = instrumentActions; // Initialize the reference
        setTitle("Add Major Instrument");
        setSize(300, 200);
        setLayout(new GridLayout(4, 2, 10, 10));
        setLocationRelativeTo(null);

        // Major Instrument Field
        add(new JLabel("Major Instrument Name:"));
        majorInstrumentField = new JTextField();
        add(majorInstrumentField);

        // Weightage Field
        add(new JLabel("Weightage:"));
        weightageField = new JTextField();
        add(weightageField);

        // Minor Instrument Field
        add(new JLabel("Minor Instrument:"));
        minorInstrumentField = new JTextField();
        add(minorInstrumentField);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveInstrumentData());
        add(saveButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> this.dispose());
        add(cancelButton);
    }

    private void saveInstrumentData() {
        String major = majorInstrumentField.getText();
        String weightage = weightageField.getText();
        String minor = minorInstrumentField.getText();
        System.out.println("The Statement is: " + major + "" + minor + "" + weightage);
        if (!major.isEmpty() && !weightage.isEmpty() && !minor.isEmpty()) {
            controller.addInstrumentDataToDB(major, weightage, minor);
            instrumentActions.refreshTable(); // Refresh the table on the previous window
            this.dispose(); // Close the window after saving
        } else {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
