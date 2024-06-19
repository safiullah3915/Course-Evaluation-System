package course_evaluation_system;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private InstrumentActions instrumentActions;
    private InstrumentAddition instrumentAddition;
    private MarksAdditionUI marksAdditionUI;
    private MarksAdditionDomain marksAdditionDomain;
    private InstrumentDomain instrumentDomain; // Integrated Domain Service
    private LoginDomain loginDomain;
    private String currentUsername; // Store the current username

    public Controller(String username) {
        this.currentUsername = username;
        this.instrumentActions = new InstrumentActions(this, username);
        this.instrumentDomain = new InstrumentDomain();
        this.instrumentAddition = new InstrumentAddition(this, instrumentActions); // Initialize to null for later instantiation
        this.marksAdditionUI = null; // Initialize to null for later instantiation
        this.loginDomain = new LoginDomain();
        this.marksAdditionDomain = new MarksAdditionDomain(); // Initialize the Marks Addition Domain
    }

    public void showInstrumentActions(String username) {
        instrumentActions.getUsername(username);
        instrumentActions.setVisible(true);
        instrumentActions.refreshTable();
        instrumentActions.populateCourseDropdown(); // Populate course dropdown
    }
    public void updateInstrumentDomain() {
        initializeInstrumentDomain();
    }
    public void updateCourseDetails(String course) {
        //instrumentDomain.updateCourseDetails(course);
        // Further actions to update the UI based on the selected course
    }

    public void addInstrumentDataToDB(String major, String weightage, String minor) {
        instrumentDomain.addInstrumentData(major, weightage, minor);
    }

    public void showInstrumentAddition(InstrumentActions instrumentActions) {
        InstrumentAddition instrumentAddition = new InstrumentAddition(this, instrumentActions);
        instrumentAddition.setVisible(true);
    }

    public void showLogin() {
        LoginUI loginFrame = new LoginUI(this);
        loginFrame.setVisible(true);
    }

    public void logout() {
        // TODO: Implement logic to handle user logout
    }

    public void addInstrument() {
        if (instrumentAddition == null) {
            instrumentAddition = new InstrumentAddition(this, instrumentActions);
        }
        instrumentAddition.setVisible(true);
    }

    public void addInstrumentData(String major, String weightage, String minor) {
        instrumentDomain.addInstrumentData(major, weightage, minor);
        instrumentActions.refreshTable(); // Refresh table to show new data
    }

    public void saveStudentMarks(String studentName, String marks) {
        // Pass the student marks data to the Marks Addition Domain for processing
        marksAdditionDomain.saveStudentMarks(studentName, marks);
    }

    public void showMarksAddition(String course, String majorInstrument, String minorInstrument) {
        marksAdditionUI = new MarksAdditionUI(this, marksAdditionDomain);//course, majorInstrument, minorInstrument
        marksAdditionUI.setTitle("Add Marks for " + course);
        marksAdditionUI.setVisible(true);
    }

    /*public List<String[]> getInstrumentData() {
        return instrumentDomain.getInstrumentsFromDB();
    }*/
    
    public List<String> getCourseNames() {
    	InstrumentDomain instrumentDomain = new InstrumentDomain();
        return instrumentDomain.getCourseNamesFromDB();
    }

    public void initializeInstrumentDomain() {
        if (instrumentDomain == null) {
            instrumentDomain = new InstrumentDomain();
        }
    }
    
    public List<String[]> getInstrumentData() {
        if (this.instrumentDomain == null) {
            // Handle the case where InstrumentDomain is not initialized
            System.err.println("InstrumentDomain is not initialized.");
            return new ArrayList<>();
        }

        return this.instrumentDomain.getInstrumentsFromDB();
    }
    
    public InstrumentDomain getInstrumentDomain() {
        return instrumentDomain;
    }
    
    /*public List<String[]> getInstrumentDataForCourse(String course) {
        initializeInstrumentDomain(); // Ensure that the instrument domain is initialized

        if (this.instrumentDomain == null) {
            System.err.println("InstrumentDomain is not initialized.");
            return new ArrayList<>();
        }

        return this.instrumentDomain.getInstrumentsForCourseFromDB(course);
    }*/
    
    public List<String> getCourseNamesFromDB() {
        return instrumentDomain.getCourseNamesFromDB();
    }


    public boolean authenticateUser(String username, String password) throws ClassNotFoundException {
        LoginTechnical initLogin = new LoginTechnical();
        boolean isValid = initLogin.authenticate(username, password);
        if (isValid) {
            return true;
        } else return false;
    }
}
