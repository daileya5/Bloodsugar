//
//	File:	PrefPane.java
//

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.prefs.*;

import java.awt.FileDialog;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

import java.awt.event.ActionListener;

//import Java_Application;

public class PrefPane extends JFrame{
	
	// JFrames
	
	protected JFrame saveRecordsJFrame;
	
	// Buttons
	protected JButton recordsButton;
	protected JButton restoreDefaultsButton;
    protected JButton okButton;
	
	
	// Labels
	
	// what is this used for?
    protected JLabel prefsText;
	
	protected JLabel bolusLabel;
	protected JLabel basalLabel;
	protected JLabel carbRatioLabel;
	protected JLabel correctionFactorLabel;
	protected JLabel targetLabel;
	protected JLabel recordsLabel;
	
	
	// Text Fields
	
	protected JTextField editBolus;
	protected JTextField editBasal;
	protected JTextField editCarbRatio;
	protected JTextField editCorrectionFactor;
	protected JTextField editTarget;
	protected JTextField editRecords;
	
	
	// Integers
	
	protected static int labelCount = 8;
    protected static int aboutWidth = 600;
    protected static int aboutHeight = 300;
    protected static int aboutTop = 200;
    protected static int aboutLeft = 350;
	
	
	// strings
	
	private String pathSeperator = System.getProperty("file.separator");
	
	private String userHome = System.getProperty("user.home");
	
	private String recordsFile;
	
	
	// boleans
	
	private boolean editBolusChanged = false;
	private boolean editBasalChanged = false;
	private boolean editCarbRatioChanged = false;
	private boolean editCorrectionFactorChanged = false;
	private boolean editTargetChanged = false;
	private boolean editRecordsChanged = false;
	

	// declare variable for preferences
	
	private Preferences storedPrefs;

    public PrefPane()
    {
		
		
		
		super("Bloodsugar Preferences");
        this.setResizable(true);
        //resbundle = ResourceBundle.getBundle ("strings", Locale.getDefault());
        //SymWindow aSymWindow = new SymWindow();
        //this.addWindowListener(aSymWindow);	

        //this.getContentPane().setLayout(new BorderLayout(100, 100));
        //prefsText = new JLabel ("Bloodsugar Preferences...");
        //JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        //textPanel.add(prefsText);
        //this.getContentPane().add (textPanel, BorderLayout.NORTH);
		
		//bolusLabel = new JLabel ("Bolus Insulin: ");
		
		//editBolus = new JTextField();
		
		//editBolus.setText("Humalog");
		
		
		storedPrefs = Preferences.userNodeForPackage(this.getClass());
		
		
		final JPanel preferencesPanel = new JPanel();
		
        GridLayout preferencesLayout = new GridLayout(0,2,0,5);
		
		preferencesPanel.setLayout(preferencesLayout);
		
		
		
		
		// Create Fields and Labels
		
		
		// Bolus Insulin
		
		bolusLabel = new JLabel("  Bolus Insulin: ");
		editBolus = new JTextField();

		preferencesPanel.add(bolusLabel);
		preferencesPanel.add(editBolus);
		

		
		
		// Basal Insulin
		
		basalLabel = new JLabel("  Basal Insulin: ");
		editBasal = new JTextField();
		
		preferencesPanel.add(basalLabel);
		preferencesPanel.add(editBasal);
		


		
		// Carbohydrate Ratio
		carbRatioLabel = new JLabel("  Carbohydrate Ratio: ");
		editCarbRatio = new JTextField();

		
		preferencesPanel.add(carbRatioLabel);
		preferencesPanel.add(editCarbRatio);

		
		
		// Correction Factor
		correctionFactorLabel = new JLabel("  Correction Factor: ");
		editCorrectionFactor = new JTextField();

		
		preferencesPanel.add(correctionFactorLabel);
		preferencesPanel.add(editCorrectionFactor);

		
		
		// Target
		targetLabel = new JLabel("  Target Bloodsugar: ");
		editTarget = new JTextField();


		preferencesPanel.add(targetLabel);
		preferencesPanel.add(editTarget);
		
		
		// Bloodsugar records file
		
		recordsLabel = new JLabel("  Bloodsugar Records File: ");
		editRecords = new JTextField();
		
		
		preferencesPanel.add(recordsLabel);
		preferencesPanel.add(editRecords);
		
		
		// Add Preferences Panel to Content Pane
		
		this.getContentPane().add(preferencesPanel, BorderLayout.NORTH);
		
		

        this.setLocation(aboutLeft, aboutTop);
        this.setSize(aboutWidth, aboutHeight);
		
		
		
		
		// Create button panel and add buttons
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
	
		
		recordsButton = new JButton("Records File Path...");
		
		restoreDefaultsButton = new JButton("Restore Defaults");
  
        okButton = new JButton("OK");
		
		
		buttonPanel.add (recordsButton);
		
		buttonPanel.add (restoreDefaultsButton);
		
        buttonPanel.add (okButton);
		

		
		// Action Listeners
		
		
		
		// Edit Fields Action Listener
		
		
		// Bolus Entered Change Listener
		
		editBolus.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {

			}
			
			public void insertUpdate(DocumentEvent e) {
		
				
				editBolusChanged = true;
				

			}
			
			public void changedUpdate(DocumentEvent e) {
			
				
				editBolusChanged = true;
				
	
			}
			
		});
		
		
		
		// Basal Entered Change Listener
	
		editBasal.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				
			}
			
			public void insertUpdate(DocumentEvent e) {
				
				
				editBasalChanged = true;
				
				
			}
			
			public void changedUpdate(DocumentEvent e) {
				
				
				editBasalChanged = true;
				

			}
			
		});
		
		
		
		
		// Carb Ratio Entered Change Listener
		
		editCarbRatio.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				
			}
			
			public void insertUpdate(DocumentEvent e) {
				
				
				editCarbRatioChanged = true;
				
				
			}
			
			public void changedUpdate(DocumentEvent e) {
				
				
				editCarbRatioChanged = true;
				
				
			}
			
		});
		
		
		
		// Correction Factor Entered Change Listener
		
		editCorrectionFactor.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				
			}
			
			public void insertUpdate(DocumentEvent e) {
				
				
				editCorrectionFactorChanged = true;
				
				
			}
			
			public void changedUpdate(DocumentEvent e) {
				
				
				editCorrectionFactorChanged = true;
				
				
			}
			
		});
		
		
		
		// Target Entered Change Listener
		
		editTarget.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				
			}
			
			public void insertUpdate(DocumentEvent e) {
				
				
				editTargetChanged = true;
				
				
			}
			
			public void changedUpdate(DocumentEvent e) {
				
				
				editTargetChanged = true;
				
				
			}
			
		});
		
		
		
		// Records File Entered Change Listener
		
		editRecords.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				
			}
			
			public void insertUpdate(DocumentEvent e) {
				
				
				editRecordsChanged = true;
				
				
			}
			
			public void changedUpdate(DocumentEvent e) {
				
				
				editRecordsChanged = true;
				
				
			}
			
		});
		
		
		
		// Buttons Action Listeners
		
		
		// Records Button Action Listener
		
		recordsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent newEvent) {
				
				// set path for records file here
				
				editRecords.setText(selectRecordsFile());
				
				
			}	
		});
		
		
		
		// Restore Defaults Buton Action Listener
		
		restoreDefaultsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent newEvent) {
				
				// restore defaults
				
				restoreDefaults();
				
				
			}	
		});
		
		
		// OK Button Action Listener
		
        okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent newEvent) {
				
				// set preferences here

					
				setPreferences();
				
				setVisible(false);
				
				//Java_Application.setVisible(false);
				
				//Initialization b = new Initialization();
				
				//b.getPreferences();
				//b.enterDataPanel.removeAll();
				//b.enterDataPanel.revalidate();
				//b.enterDataPanel.updateUI();
				//b.enterDataPanel.repaint();
				//b.initializeLayouts();
					
				
				
			}	
		});
		
		
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		//setSize(390, 129);
		//setLocation(20, 40);
		
		
		
		// Load saved preferences if they exist
		
		getPreferences();
    }
	
	
	
	// Method for restoring default preferences
	
	public void restoreDefaults() {
		
		
		// Restore Default Values
		editBolus.setText("Bolus Insulin");
		editBasal.setText("Basal Insulin");
		editCarbRatio.setText("5");
		editCorrectionFactor.setText("30");
		editTarget.setText("120");
		editRecords.setText(userHome + pathSeperator + "Bloodsugar" + pathSeperator + "blood_sugar_records.csv");
		
		// Set Edit Fields Changed Flags
		
		editBolusChanged = true;
		editBasalChanged = true;
		editCarbRatioChanged = true;
		editCorrectionFactorChanged = true;
		editTargetChanged = true;
		editRecordsChanged = true;
		
	}
	
	
	// Method for getting preferences
	
	public void getPreferences() {
		
		// Load preferences
		String bolusInsulin = storedPrefs.get("BOLUS_INSULIN", "");
		
		String basalInsulin = storedPrefs.get("BASAL_INSULIN", "");
		
		String carbRatio = storedPrefs.get("CARB_RATIO", "");
		
		String correctionFactor = storedPrefs.get("CORRECTION_FACTOR", "");
		
		String bloodsugarTarget = storedPrefs.get("BLOODSUGAR_TARGET", "");
		
		String recordsFile = storedPrefs.get("RECORDS_FILE", "");
		
		
		
		// check if preferences exist
		
		
		// Check if Bolus Insulin Name exists
		
		if (bolusInsulin.length()==0) {
			
		
		// handle default values if preferences do not exist
		
			editBolus.setText("Bolus Insulin");
			
		}
		
		
		// Check if Basal Insulin Name exists
		
		if (basalInsulin.length()==0) {
			
			
			// handle default values if preferences do not exist
			
			editBasal.setText("Basal Insulin");
			
		}
		
		
		// Check if Carb Ratio has been set
		
		if (carbRatio.length()==0) {
			
			
			// handle default values if preferences do not exist
			
			editCarbRatio.setText("5");
			
		}
		
		
		// Check if Correction Factor has been set
		
		if (correctionFactor.length()==0) {
			
			
			// handle default values if preferences do not exist
			
			editCorrectionFactor.setText("30");
			
		}
		
		
		// Check if Bloodsugar Target has been set
		
		if (bloodsugarTarget.length()==0) {
			
			
			// handle default values if preferences do not exist
			
			editTarget.setText("120");
			
		}
		
		
		// Check if Records File path exists
		
		if (recordsFile.length()==0) {
			
			
			// handle default values if preferences do not exist
			
			editRecords.setText(userHome + pathSeperator + "Bloodsugar" + pathSeperator + "blood_sugar_records.csv");
			
			
		}
		
			
		
		
		else {
			
		// else load stored preference values into edit fields
		
		editBolus.setText(bolusInsulin);
		

		editBasal.setText(basalInsulin);
		

		editCarbRatio.setText(carbRatio);
		

		editCorrectionFactor.setText(correctionFactor);
		

		editTarget.setText(bloodsugarTarget);
		

		editRecords.setText(recordsFile);
		
		}
		
		
	}
	
	
	
	
	// Method for setting preferences
	
	public void setPreferences() {
		
		// Check to see if Bolus Insulin name has changed
		
		if (editBolusChanged = true) {
			
			// Get new Bolus Insulin name
			String bolusInsulin = editBolus.getText().toString();
			
			// Write new Bolus Insulin name to preferences
			storedPrefs.put("BOLUS_INSULIN",bolusInsulin);
			
			editBolusChanged = false;
		
		}
		
		// Check to see if Basal Insulin name has changed
		
		if (editBasalChanged = true) {
			
			String basalInsulin = editBasal.getText().toString();
			storedPrefs.put("BASAL_INSULIN",basalInsulin);
			
			editBasalChanged = false;
			
		}
		
		// Check to see if Carb Ratio has changed
		
		if (editCarbRatioChanged = true) {
			
			String carbRatio = editCarbRatio.getText().toString();
			storedPrefs.put("CARB_RATIO",carbRatio);
			
			editCarbRatioChanged = false;
			
		}
		
		// Check to see if Basal Insulin name has changed
		
		if (editCorrectionFactorChanged = true) {
			
			String correctionFactor = editCorrectionFactor.getText().toString();
			storedPrefs.put("CORRECTION_FACTOR",correctionFactor);
			
			editCorrectionFactorChanged = false;
			
		}
		
		// Check to see if Basal Insulin name has changed
		
		if (editTargetChanged = true) {
			
			String bloodsugarTarget = editTarget.getText().toString();
			storedPrefs.put("BLOODSUGAR_TARGET",bloodsugarTarget);
			
			editTargetChanged = false;
			
		}
		
		// Check to see if Records File path has changed
		
		if (editRecordsChanged = true) {
			
			String recordsFile = editRecords.getText().toString();
			storedPrefs.put("RECORDS_FILE",recordsFile);
			
			editRecordsChanged = false;
			
		}


		
		
		
	}
	
	// Method for saving output file
	
	public String selectRecordsFile() {
		
		FileDialog fs = new FileDialog(saveRecordsJFrame, "Select location for the records file...", FileDialog.SAVE);
		fs.setDirectory(userHome);
		fs.setVisible(true);
		
		String recordsFileName = fs.getFile();
		
		File output = new File(fs.getDirectory(),fs.getFile());
		
		String recordsFilePath = output.getAbsolutePath();
		
		
		if (recordsFileName == null)
			System.out.println("You cancelled the choice");
		else 
			System.out.println("You chose " + recordsFileName);
		
		recordsFile = recordsFilePath;
		
		editRecordsChanged = true;
		
		return recordsFilePath;
		
	}
	
	
	
	
	
}
