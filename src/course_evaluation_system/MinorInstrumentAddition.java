package course_evaluation_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinorInstrumentAddition extends JFrame {
    private JTextField minorInstrumentNameField, weightageField;
    private JButton saveButton, cancelButton;
    private Controller controller;
    private InstrumentActions parentWindow; // Reference to the parent window

    public MinorInstrumentAddition(Controller controller, InstrumentActions parentWindow) {
        this.controller = controller;
        this.parentWindow = parentWindow; // Initialize the reference

        setTitle("Add Minor Instrument");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Dispose the window on close

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));

        // Minor Instrument Name Field
        inputPanel.add(new JLabel("Minor Instrument Name:"));
        minorInstrumentNameField = new JTextField();
        inputPanel.add(minorInstrumentNameField);

        // Weightage Field
        inputPanel.add(new JLabel("Weightage/Marks:"));
        weightageField = new JTextField();
        inputPanel.add(weightageField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Save Button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveMinorInstrumentData());
        buttonPanel.add(saveButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> this.dispose()); // Close the window on cancel
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void saveMinorInstrumentData() {
        String minorInstrumentName = minorInstrumentNameField.getText();
        String weightage = weightageField.getText();

        if (!minorInstrumentName.isEmpty() && !weightage.isEmpty()) {
            // Pass the data to the parent window (InstrumentActions) to update the table
            // parentWindow.updateMinorInstrumentTable(minorInstrumentName, weightage);
            this.dispose(); // Close the window after saving
        } else {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Initialize the controller and parentWindow as needed
        Controller controller = new Controller("usernameField");
        InstrumentActions parentWindow = new InstrumentActions(controller, "username");
        MinorInstrumentAddition minor = new MinorInstrumentAddition(controller, parentWindow);
        minor.setVisible(true);
    }
}
