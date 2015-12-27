

import java.util.Locale;
import java.util.ResourceBundle;

import java.lang.Float;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DecimalFormat;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import javax.swing.*;

import java.util.prefs.*;

import com.apple.eawt.*;

import au.com.bytecode.opencsv.*;



public class Initialization extends JFrame {


	// J Buttons
	
	protected JButton clearButton;
	protected JButton updateTimeButton;
	protected JButton writeButton;
	
	
	// J Labels
	
    protected JLabel enterText; // do we need this?
	
	protected JLabel dateLabel;
	protected JLabel dateFormat;
	protected JLabel timeLabel;
	protected JLabel timeFormat;
	
	protected JLabel bloodsugarLabel;
	protected JLabel bloodsugarUnits;
	protected JLabel carbohydratesLabel;
	protected JLabel carbohydratesUnits;
	
	protected JLabel bolusLabel;
	protected JLabel bolusUnits;
	protected JLabel basalLabel;
	protected JLabel basalUnits;
	
	protected JLabel predictedBloodsugarLabel;
	protected JLabel predictedBloodsugar;
	protected JLabel predictedBloodsugarUnits;
	protected JLabel predictedCarbohydratesLabel;
	protected JLabel predictedCarbs;
	protected JLabel predictedCarbohydratesUnits;
	protected JLabel predictedBolusLabel;
	protected JLabel predictedBolus;
	protected JLabel predictedBolusUnits;
	
	
	// J Text Fields
	
	protected JTextField editDate;
	protected JTextField editTime;
	protected JTextField editBloodsugar;
	protected JTextField editCarbs;
	protected JTextField editBolus;
	protected JTextField editBasal;
	
	
	// J Panels
	
	protected JPanel enterDataPanel;
	
	
	// Booleans
	
	private boolean dataNotSaved = true;
	public static boolean viewDataOn = false;
	public boolean viewDataReset = false;
	
	
	// Strings
	
	private String pathSeperator = System.getProperty("file.separator");
	
	private String userHome = System.getProperty("user.home");
	
	private String bolusInsulinLabel;
	private String basalInsulinLabel;
	private String predictedBolusInsulinLabel;
	
	private String globalRecordsFile;
	private String localRecordsFile;
	
	private String bloodsugar;
	private String carbohydrates;
	private String timeOfDay;
	
	
	// Integers
	
	private int timeRank;
	
	
	// Floats
	
	private float fCarbRatio;
	private float fCorrectionFactor;
	private float fTarget;
	
	private float mBloodsugar;
	private float mCarbs;
	private float mBolus;
	
	
	// Simple Date Formats
	
	final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	final SimpleDateFormat stf = new SimpleDateFormat("kk:mm");
	
	
	// declare variable for preferences
	
	private Preferences storedPrefs;
	
	
	// Others
	
	private Font font = new Font("serif", Font.ITALIC+Font.BOLD, 36);
	protected ResourceBundle resbundle;
	protected AboutBox aboutBox;
	protected PrefPane prefs;
	private ViewDataTable viewData;
	
	
	private Application fApplication = Application.getApplication();
	protected Action newAction, openAction, closeAction, saveAction, saveAsAction,
	undoAction, cutAction, copyAction, pasteAction, clearAction, selectAllAction, 
	enterDataAction, viewDataAction, graphDataAction, exportDataAction;
	public final JMenuBar mainMenuBar = new JMenuBar();	
	protected JMenu fileMenu, editMenu, toolsMenu; 
	
	
	public Initialization() {
		
	resbundle = ResourceBundle.getBundle ("strings", Locale.getDefault());
		
	storedPrefs = Preferences.userNodeForPackage(this.getClass());
	
	}

// Begin Methods


// Method for Initializing Layout


public void initializeLayouts() {

// Call calendar for current date and time

Calendar c = Calendar.getInstance();

// Initialize Layouts

final JPanel enterDataPanel = new JPanel();

GridLayout enterDataLayout = new GridLayout(0,3,5,5);

enterDataPanel.setLayout(enterDataLayout);




// Create Fields and Labels


// Date

dateLabel = new JLabel("  Date: ");
enterDataPanel.add(dateLabel);

editDate = new JTextField();

//SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
editDate.setText(sdf.format(c.getTime()));

enterDataPanel.add(editDate);

dateFormat = new JLabel("");

enterDataPanel.add(dateFormat);


// Time

timeLabel = new JLabel("  Time: ");
enterDataPanel.add(timeLabel);

editTime = new JTextField();

//SimpleDateFormat stf = new SimpleDateFormat("kk:mm");
editTime.setText(stf.format(c.getTime()));

enterDataPanel.add(editTime);

timeFormat = new JLabel("");

enterDataPanel.add(timeFormat);


// Bloodsugar Entered
bloodsugarLabel = new JLabel("  Bloodsugar: ");
editBloodsugar = new JTextField();
bloodsugarUnits = new JLabel("mg / dL ");

enterDataPanel.add(bloodsugarLabel);
enterDataPanel.add(editBloodsugar);
enterDataPanel.add(bloodsugarUnits);


// Carbohydrates Entered
carbohydratesLabel = new JLabel("  Carbohydrates: ");
editCarbs = new JTextField();
carbohydratesUnits = new JLabel("g ");

enterDataPanel.add(carbohydratesLabel);
enterDataPanel.add(editCarbs);
enterDataPanel.add(carbohydratesUnits);


// Bolus Insulin Dose Entered
bolusLabel = new JLabel(bolusInsulinLabel);
editBolus = new JTextField();
bolusUnits = new JLabel("units ");

enterDataPanel.add(bolusLabel);
enterDataPanel.add(editBolus);
enterDataPanel.add(bolusUnits);


// Basal Insulin Dose Entered
basalLabel = new JLabel(basalInsulinLabel);
editBasal = new JTextField();
basalUnits = new JLabel("units ");

enterDataPanel.add(basalLabel);
enterDataPanel.add(editBasal);
enterDataPanel.add(basalUnits);


// Bloodsugar Predicted
predictedBloodsugarLabel = new JLabel("  Predicted Bloodsugar: ");
predictedBloodsugar = new JLabel();
predictedBloodsugarUnits = new JLabel("mg / dL ");

enterDataPanel.add(predictedBloodsugarLabel);
enterDataPanel.add(predictedBloodsugar);
enterDataPanel.add(predictedBloodsugarUnits);


// Carbohydrates Predicted
predictedCarbohydratesLabel = new JLabel("  Calculated Carbohydrates: ");
predictedCarbs = new JLabel();
predictedCarbohydratesUnits = new JLabel("g ");

enterDataPanel.add(predictedCarbohydratesLabel);
enterDataPanel.add(predictedCarbs);
enterDataPanel.add(predictedCarbohydratesUnits);


// Bolus Insulin Dose Predicted
predictedBolusLabel = new JLabel(predictedBolusInsulinLabel);
predictedBolus = new JLabel();
predictedBolusUnits = new JLabel("units ");

enterDataPanel.add(predictedBolusLabel);
enterDataPanel.add(predictedBolus);
enterDataPanel.add(predictedBolusUnits);


// Add enter Data Panel to Content Pane

this.getContentPane().add(enterDataPanel, BorderLayout.NORTH);

}


// Method for getting preferences

public void getPreferences() {

// Load preferences
String bolusInsulin = storedPrefs.get("BOLUS_INSULIN", "");

String basalInsulin = storedPrefs.get("BASAL_INSULIN", "");

String carbRatio = storedPrefs.get("CARB_RATIO", "");

String correctionFactor = storedPrefs.get("CORRECTION_FACTOR", "");

String bloodsugarTarget = storedPrefs.get("BLOODSUGAR_TARGET", "");

String localRecordsFile = storedPrefs.get("RECORDS_FILE", "");



// check if preferences exist


// Check if Bolus Insulin Name exists

if (bolusInsulin.length()==0) {


// handle default values if preferences do not exist

bolusInsulin = "Bolus Insulin";
bolusInsulinLabel = "  " + bolusInsulin + " Dose: ";
predictedBolusInsulinLabel = "  Calculated " + bolusInsulin + " Dose: ";


}


// Check if Basal Insulin Name exists

if (basalInsulin.length()==0) {


// handle default values if preferences do not exist

basalInsulin = "Basal Insulin";
basalInsulinLabel = "  " + basalInsulin + " Dose: ";

}


// Check if Carb Ratio has been set

if (carbRatio.length()==0) {


// handle default values if preferences do not exist

carbRatio = "5";
fCarbRatio = Float.parseFloat(carbRatio);

}


// Check if Correction Factor has been set

if (correctionFactor.length()==0) {


// handle default values if preferences do not exist

correctionFactor = "30";
fCorrectionFactor = Float.parseFloat(correctionFactor);

}


// Check if Bloodsugar Target has been set

if (bloodsugarTarget.length()==0) {


// handle default values if preferences do not exist

bloodsugarTarget = "120";
fTarget = Float.parseFloat(bloodsugarTarget);

}


// Check if Records File path exists

if (localRecordsFile.length()==0) {


// handle default values if preferences do not exist

localRecordsFile = userHome + pathSeperator + "Bloodsugar" + pathSeperator + "blood_sugar_records.csv";

globalRecordsFile = localRecordsFile;

}




else {


// else load stored preference values into Labels

bolusInsulinLabel = "  " + bolusInsulin + " Dose: ";
basalInsulinLabel = "  " + basalInsulin + " Dose: ";
predictedBolusInsulinLabel = "  Calculated " + bolusInsulin + " Dose: ";


// else load stored preference values into floats

fCarbRatio = Float.parseFloat(carbRatio);
fCorrectionFactor = Float.parseFloat(correctionFactor);
fTarget = Float.parseFloat(bloodsugarTarget);


// else load stored preference values into strings

globalRecordsFile = localRecordsFile;

}


}



}
