package course_evaluation_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorMessageLabel;
    private Controller controller;

    public LoginUI(Controller controller) {
        this.controller = controller; // Initialize the controller
        setTitle("Course Evaluation System - Login");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(createContentPane());
        setUpListeners(); // Set up the button listeners
    }

    private JPanel createContentPane() {
        JPanel contentPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Username label and field
        c.gridx = 0;
        c.gridy = 0;
        contentPane.add(new JLabel("Username:"), c);
        c.gridx = 1;
        usernameField = new JTextField(15);
        contentPane.add(usernameField, c);

        // Password label and field
        c.gridx = 0;
        c.gridy = 1;
        contentPane.add(new JLabel("Password:"), c);
        c.gridx = 1;
        passwordField = new JPasswordField(15);
        contentPane.add(passwordField, c);

        // Login button
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.insets = new Insets(10, 0, 0, 0);
        loginButton = new JButton("Login");
        contentPane.add(loginButton, c);

        // Error message label (initially hidden)
        c.gridy = 3;
        errorMessageLabel = new JLabel("", JLabel.CENTER);
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setVisible(false);
        contentPane.add(errorMessageLabel, c);

        return contentPane;
    }

    private void setUpListeners() {
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					handleLogin();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    private void handleLogin() throws ClassNotFoundException {
        // Get the entered username and password
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Input validation
        if (username.isEmpty() || password.isEmpty()) {
            errorMessageLabel.setText("Username and password are required.");
            errorMessageLabel.setVisible(true);
        } else if (containsInvalidCharacters(username) || containsInvalidCharacters(password)) {
            errorMessageLabel.setText("Invalid characters detected in username or password.");
            errorMessageLabel.setVisible(true);
        } else {
            // Authentication logic (replace with actual authentication)
            boolean isAuthenticated = controller.authenticateUser(username, password);

            if (isAuthenticated) { //isAuthenticated
                // Successful login, navigate to InstrumentActions
            	System.out.println("Login Succesfully");
                dispose();
                controller.showInstrumentActions(username);
            } else {
                // Authentication failed, display an error message
            	System.out.println("not Login");
                errorMessageLabel.setText("Invalid username or password.");
                errorMessageLabel.setVisible(true);
            }
        }
    }

    private boolean containsInvalidCharacters(String input) {
        // Define a list of characters that are not allowed (customize as needed)
        String[] invalidCharacters = {"<", ">", "&", "$"};

        // Check if the input contains any of the invalid characters
        for (String character : invalidCharacters) {
            if (input.contains(character)) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        Controller controller = new Controller("Tariq");
        LoginUI loginFrame = new LoginUI(controller);
        loginFrame.setVisible(true);
    }
}
