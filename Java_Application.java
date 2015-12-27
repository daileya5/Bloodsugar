//
//  Bloodsugar_java
//
//  Created by __Abraham Dailey__ on 02/22/2014.
//  Copyright (c) 2014 __MyCompanyName__. All rights reserved.
//
//	For information on setting Java configuration information, including 
//	setting Java properties, refer to the documentation at
//		http://developer.apple.com/techpubs/java/java.html
//

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



public class Java_Application extends JFrame {
	
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
	
	private String bolusInsulin;
	private String basalInsulin;
	
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
	
	
	public Java_Application() {
		
		super("Bloodsugar: Enter Data");
		// The ResourceBundle below contains all of the strings used in this
		// application.  ResourceBundles are useful for localizing applications.
		// New localities can be added by adding additional properties files.
		resbundle = ResourceBundle.getBundle ("strings", Locale.getDefault());


		LoadMenus();

		
		// paste code
		
		// Load Preferences for labels
		
		storedPrefs = Preferences.userNodeForPackage(this.getClass());
		
		//Initialization c = new Initialization();
		
		//c.getPreferences();

		//c.initializeLayouts();
		
		getPreferences();
		
		

		
		
		// Initialize Layouts
		
		initializeLayouts();
		
		
		
		
		
		// Create button panel and add buttons
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		
		clearButton = new JButton("Clear Record");
		
		updateTimeButton = new JButton("Update Time");
		
		writeButton = new JButton("Write Record");
		
		buttonPanel.add (clearButton);
		
		buttonPanel.add (updateTimeButton);

		buttonPanel.add (writeButton);
		
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
	
		
		// Create action listeners
		
		
		// Clear Record Button
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent newEvent) {
				
				clearFunction();

				
			}	
		});
		
		
		
		// Bloodsugar Entered Change Listener
		
		editBloodsugar.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				//predictedBloodsugar.setText(editBloodsugar.getText());
				
				//dataNotSaved = true;
				
				//String no1 = editBloodsugar.getText().toString();
				
				//if (no1.length()>0) {
				//mBloodsugar = Integer.parseInt(no1);
				//Log.i(TAG,no1);
				//displayCounts(); //this should ultimately be uncommented
				//predictedBloodsugar.setText(String.valueOf(mBloodsugar));
				//}
			}
			
			public void insertUpdate(DocumentEvent e) {
				//predictedBloodsugar.setText(editBloodsugar.getText());
				
				dataNotSaved = true;
				
				String no1 = editBloodsugar.getText().toString();

				
				if (no1.length()>0) {
					mBloodsugar = Integer.parseInt(no1);

					//Log.i(TAG,no1);
					displayCounts(); //this should ultimately be uncommented
					//predictedBloodsugar.setText(String.valueOf(mBloodsugar));
				}
			}
			
			public void changedUpdate(DocumentEvent e) {
				//predictedBloodsugar.setText(editBloodsugar.getText());
				
				dataNotSaved = true;
				
				String no1 = editBloodsugar.getText().toString();

				
				if (no1.length()>0) {
					mBloodsugar = Integer.parseInt(no1);

					//Log.i(TAG,no1);
					displayCounts(); //this should ultimately be uncommented
					//predictedBloodsugar.setText(String.valueOf(mBloodsugar));
				}
			}

		});
		
		
		
		// Carbohydrates Entered Change Listener
		
		editCarbs.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				//predictedCarbs.setText(editCarbs.getText());
				
				//dataNotSaved = true;
				
				//String no2 = editCarbs.getText().toString();
				
				//if (no2.length()>0) {
				//mCarbs = Integer.parseInt(no2);
				//Log.i(TAG,no2);
				//displayCounts(); //this should ultimately be uncommented
				//predictedCarbs.setText(String.valueOf(mCarbs));
				//}
			}
			
			public void insertUpdate(DocumentEvent e) {
				//predictedCarbs.setText(editCarbs.getText());
				
				dataNotSaved = true;
				

				String no2 = editCarbs.getText().toString();

				
				if (no2.length()>0) {

					mCarbs = Integer.parseInt(no2);

					//Log.i(TAG,no2);
					displayCounts(); //this should ultimately be uncommented
					//predictedCarbs.setText(String.valueOf(mCarbs));
				}
			}
			
			public void changedUpdate(DocumentEvent e) {
				//predictedCarbs.setText(editCarbs.getText());
				
				dataNotSaved = true;
				

				String no2 = editCarbs.getText().toString();

				
				if (no2.length()>0) {

					mCarbs = Integer.parseInt(no2);

					//Log.i(TAG,no2);
					displayCounts(); //this should ultimately be uncommented
					//predictedCarbs.setText(String.valueOf(mCarbs));
				}
			}
			
		});
		
		
		
		// Bolus Entered Change Listener
		
		editBolus.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				//predictedBolus.setText(editBolus.getText());
				
				//dataNotSaved = true;
				
				//String no3 = editBloodsugar.getText().toString();
				
				//if (no3.length()>0) {
				//mBolus = Integer.parseInt(no3);
				//Log.i(TAG,no3);
				//displayCounts(); //this should ultimately be uncommented
				//predictedBolus.setText(String.valueOf(mBolus));
				//}
			}
			
			public void insertUpdate(DocumentEvent e) {
				//predictedBolus.setText(editBolus.getText());
				
				dataNotSaved = true;
				

				String no3 = editBolus.getText().toString();
				
				if (no3.length()>0) {

					mBolus = Integer.parseInt(no3);
					//Log.i(TAG,no3);
					displayCounts(); //this should ultimately be uncommented
					//predictedBolus.setText(String.valueOf(mBolus));
				}
			}
			
			public void changedUpdate(DocumentEvent e) {
				//predictedBolus.setText(editBolus.getText());
				
				dataNotSaved = true;
				

				String no3 = editBolus.getText().toString();
				
				if (no3.length()>0) {

					mBolus = Integer.parseInt(no3);
					//Log.i(TAG,no3);
					displayCounts(); //this should ultimately be uncommented
					//predictedBolus.setText(String.valueOf(mBolus));
				}
			}
			
		});
		
	
		
		// Basal Entered Change Listener
		
		editBasal.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {

				//dataNotSaved = true;
				

			}
			
			public void insertUpdate(DocumentEvent e) {

				dataNotSaved = true;
				

			}
			
			public void changedUpdate(DocumentEvent e) {
				
				dataNotSaved = true;
			
			}
			
		});
		
		

		
		
		// Update Time Button
		
		updateTimeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent newEvent) {
				

				// Update time and date fields
				
				Calendar d2 = Calendar.getInstance();
				
				editDate.setText(sdf.format(d2.getTime()));
				editTime.setText(stf.format(d2.getTime()));
				
				
			}	
		});
		
		
		// Write Record Button
		
		writeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent newEvent) {
				
				// Check to see if a records file already exists.
				checkRecords();
				
				writeRecords();
				
				clearFunction();
				
				dataNotSaved = false;
				
				//ViewDataTable viewData = new ViewDataTable();
				//viewData.setVisible(true);
				
				
				if (viewDataOn == true) {
					
					//viewData.setVisible(false);
					viewData.dispose();
					
					viewData = new ViewDataTable();
					viewData.setVisible(true);
					viewDataOn = true;
					
					
				}
				

				
				//if (viewDataOn == true && viewDataReset == false) {
					
					//viewData.setVisible(false);
					viewData.dispose();
					
					viewDataReset = true;
					
			//	}
				
				//if (viewDataReset == true && viewDataOn == true) {
					
					//ViewDataTable viewData = new ViewDataTable();
					
					viewData = new ViewDataTable();
					viewData.setVisible(true);
					viewDataOn = true;
					viewDataReset = false;
				//}
					
			}	
		});


		
		// end paste code

		fApplication.setEnabledPreferencesMenu(true);
		fApplication.addApplicationListener(new com.apple.eawt.ApplicationAdapter() {
			public void handleAbout(ApplicationEvent e) {
                                if (aboutBox == null) {
                                    aboutBox = new AboutBox();
                                }
                                about(e);
                                e.setHandled(true);
			}
			public void handleOpenApplication(ApplicationEvent e) {
			}
			public void handleOpenFile(ApplicationEvent e) {
			}
			public void handlePreferences(ApplicationEvent e) {
                                if (prefs == null) {
                                    prefs = new PrefPane();
									
									
								
                                }
				preferences(e);
				
				//getPreferences();
				//enterDataPanel.removeAll();
				//enterDataPanel.getRootPane().validate();
				//enterDataPanel.updateUI();
				//enterDataPanel.repaint();
				
				//initializeLayouts();
				
				
				
				
			}
			
			
			public void handlePrintFile(ApplicationEvent e) {
			}
			public void handleQuit(ApplicationEvent e) {
				
				if (dataNotSaved) {
					
				checkRecords();
				
				writeRecords();
				}
				
				quit(e);
			}
		});
		
		
		
		setSize(650, 400);
		setLocation(20, 40);
		
		//setSize(310, 150);
		setVisible(true);
	}

	public void about(ApplicationEvent e) {
		aboutBox.setResizable(false);
		aboutBox.setVisible(true);
	}

	public void preferences(ApplicationEvent e) {
		prefs.setResizable(false);
		prefs.setVisible(true);
		
		//prefs.addWindowListener(new WindowAdapter() {
			//@Override
			
			//public void windowClosing(WindowEvent we) {
				
				//getPreferences();
				//enterDataPanel.removeAll();
				//enterDataPanel.revalidate();
				//enterDataPanel.updateUI();
				//enterDataPanel.repaint();
				
				//initializeLayouts();
				
			//}
		//});
		
		//initializeLayouts();
		
	}


	public void quit(ApplicationEvent e) {	
		System.exit(0);
	}

		
	
	
	public void clearFunction() {
		
		// Clear fields
		
		editBloodsugar.setText("");
		editCarbs.setText("");
		editBolus.setText("");
		editBasal.setText("");
		
		predictedBloodsugar.setText("");
		predictedCarbs.setText("");
		predictedBolus.setText("");
		
		
		// Clear Variables
		
		mBloodsugar = 0;
		mCarbs = 0;
		mBolus = 0;
		
		
		// Update time and date fields
		
		Calendar d = Calendar.getInstance();
		
		editDate.setText(sdf.format(d.getTime()));
		editTime.setText(stf.format(d.getTime()));
		
		
		// Set dataNotSaved to true
		
		dataNotSaved = true;
		
	}

	//Initialize Menus
	
	
	public void LoadMenus() {
		
		int shortcutKeyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		
		// Create File Menu
		
		fileMenu = new JMenu(resbundle.getString("fileMenu"));
		
		
		// File New
		
		JMenuItem fileNew = new JMenuItem("New", null); // replace null with an icon if desired
		fileNew.setMnemonic(KeyEvent.VK_N);
		//fileNew.setToolTipText("New  Window"); implement if desired
		fileNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcutKeyMask));
		//fileNew.setEnabled(false);
		
		fileNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// File Open
		
		JMenuItem fileOpen = new JMenuItem("Open", null); // replace null with an icon if desired
		fileOpen.setMnemonic(KeyEvent.VK_O);
		//fileOpen.setToolTipText("Open File"); implement if desired
		fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, shortcutKeyMask));
		//fileOpen.setEnabled(false);
		
		fileOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// File Close
		
		JMenuItem fileClose = new JMenuItem("Close", null); // replace null with an icon if desired
		fileClose.setMnemonic(KeyEvent.VK_W);
		//fileClose.setToolTipText("Close  Window"); implement if desired
		fileClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, shortcutKeyMask));
		//fileClose.setEnabled(false);
		
		fileClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// File Save
		
		JMenuItem fileSave = new JMenuItem("Save", null); // replace null with an icon if desired
		fileSave.setMnemonic(KeyEvent.VK_S);
		//fileSave.setToolTipText("Save"); implement if desired
		fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, shortcutKeyMask));
		//fileSave.setEnabled(false);
		
		fileSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// File Save As
		
		JMenuItem fileSaveAs = new JMenuItem("Save As...", null); // replace null with an icon if desired
		//fileSaveAs.setMnemonic(KeyEvent.VK_N);
		//fileSaveAs.setToolTipText("Save As"); implement if desired
		fileSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, (java.awt.event.InputEvent.SHIFT_MASK | (Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()))));
		//fileSaveAs.setEnabled(false);
		
		fileSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		// Add File Menu Items to File Menu
		fileMenu.add(fileNew);
		fileMenu.add(fileOpen);
		fileMenu.add(fileClose);
		fileMenu.add(fileSave);
		fileMenu.add(fileSaveAs);
		
		// Add File Menu to Menu Bar
		mainMenuBar.add(fileMenu);
		
		
		
		// Create Edit Menu
		
		editMenu = new JMenu(resbundle.getString("editMenu"));
		
		
		// Edit Undo
		
		JMenuItem editUndo = new JMenuItem("Undo", null); // replace null with an icon if desired
		editUndo.setMnemonic(KeyEvent.VK_Z);
		//editUndo.setToolTipText("Oops"); implement if desired
		editUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, shortcutKeyMask));
		//editUndo.setEnabled(false);
		
		editUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// Edit Cut
		
		JMenuItem editCut = new JMenuItem("Cut", null); // replace null with an icon if desired
		editCut.setMnemonic(KeyEvent.VK_X);
		//editCut.setToolTipText("Cut"); implement if desired
		editCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, shortcutKeyMask));
		//editCut.setEnabled(false);
		
		editCut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// Edit Copy
		
		JMenuItem editCopy = new JMenuItem("Copy", null); // replace null with an icon if desired
		editCopy.setMnemonic(KeyEvent.VK_C);
		//editCopy.setToolTipText("Copy"); implement if desired
		editCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, shortcutKeyMask));
		//editCopy.setEnabled(false);
		
		editCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// Edit Paste
		
		JMenuItem editPaste = new JMenuItem("Paste", null); // replace null with an icon if desired
		editPaste.setMnemonic(KeyEvent.VK_V);
		//editPaste.setToolTipText("Paste"); implement if desired
		editPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, shortcutKeyMask));
		//editPaste.setEnabled(false);
		
		editPaste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// Edit Clear
		
		JMenuItem editClear = new JMenuItem("Clear", null); // replace null with an icon if desired
		//editClear.setMnemonic(KeyEvent.VK_Z);
		//editClear.setToolTipText("Clear"); implement if desired
		//editClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, shortcutKeyMask));
		//editClear.setEnabled(false);
		
		editClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				
				clearFunction();
			}
		});
		
		
		// Edit Select All
		
		JMenuItem selectAll = new JMenuItem("Select All", null); // replace null with an icon if desired
		selectAll.setMnemonic(KeyEvent.VK_A);
		//selectAll.setToolTipText("Select All"); implement if desired
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, shortcutKeyMask));
		//selectAll.setEnabled(false);
		
		editUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		
		// Add Edit Menu Items to Edit Menu
		
		editMenu.add(editUndo);
		editMenu.addSeparator();
		editMenu.add(editCut);
		editMenu.add(editCopy);
		editMenu.add(editPaste);
		editMenu.add(editClear);
		editMenu.addSeparator();
		editMenu.add(selectAll);
		
		// Add Edit Menu to Menu Bar
		
		mainMenuBar.add(editMenu);
		
		
		
		
		// Create Tools Menu
		
		toolsMenu = new JMenu(resbundle.getString("toolsMenu"));
		
		
		// Tools Enter Data
		
		JMenuItem enterDataAction = new JMenuItem("Enter Data", null);
		//enterDataAction.setToolTipText("Enter Data"); implement if desired
		enterDataAction.setEnabled(false);
		
		enterDataAction.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				EnterDataActivity enterData = new EnterDataActivity();
				enterData.setVisible(true);
			}
		});
		
		
		// Tools View Data
		
		JMenuItem viewDataAction = new JMenuItem("View Data", null);
		//viewDataAction.setToolTipText("View Data"); implement if desired
		//viewDataAction.setEnabled(false);
		
		viewDataAction.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				//ViewDataTable 
				

				
				viewData = new ViewDataTable();
				

				
				viewData.setVisible(true);
				
				viewDataOn = true;
			}
		});
		
		
		// Tools Graph Data
		
		JMenuItem graphDataAction = new JMenuItem("Graph Data", null);
		//graphDataAction.setToolTipText("Graph Data"); implement if desired
		//graphDataAction.setEnabled(false);
		
		graphDataAction.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// Tools Export Data
		
		JMenuItem exportDataAction = new JMenuItem("Export Data", null);
		//exportDataAction.setToolTipText("Export Data"); implement if desired
		//exportDataAction.setEnabled(false);
		
		exportDataAction.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				//EnterDataActivity enterData = new EnterDataActivity();
				//enterData.setVisible(true);
			}
		});
		
		
		// Add Tools Menu Items to Tools Menu
		
		toolsMenu.add(enterDataAction);
		toolsMenu.add(viewDataAction);
		toolsMenu.add(graphDataAction);
		toolsMenu.add(exportDataAction);
		
		
		// Add Tools Menu to Menu Bar
		
		mainMenuBar.add(toolsMenu);					  
		
		setJMenuBar (mainMenuBar);
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
		bolusInsulin = storedPrefs.get("BOLUS_INSULIN", "");
		
		basalInsulin = storedPrefs.get("BASAL_INSULIN", "");
		
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
	
	
	
	// Method for Updating Labels
	
	//private void updateLabels() {
		
		//bolusLabel = bolusInsulinLabel;
		//basalLabel = basalInsulinLabel;
		//predictedBolusLabel = predictedBolusInsulinLabel;
	
	//}
	
	
	// Begin Methods from Android Code
	
	
	
	//classify time of day
	public String[] classifyTime(String time) {
		
		String[] timeRankString = new String[2];
		
		String[] parts = time.split(":");
		String hour = parts[0];
		String minutes = parts[1];
		int mHour = Integer.parseInt(hour);
		int mMinutes = Integer.parseInt(minutes);
		
		if (mHour == 24 || (mHour >= 0 && mHour<=5)) {
			timeOfDay = "Night";
			timeRank = 1;
		}
		if (mHour > 5 && mHour <= 11) {
			
			timeOfDay = "Breakfast";
			timeRank = 2;
		}
		
		if (mHour > 11 && mHour <= 14) {
			
			timeOfDay = "Lunch";
			timeRank = 3;
		}
		
		if (mHour > 14 && mHour <= 17) {
			
			timeOfDay = "Afternoon";
			timeRank = 4;
		}
		
		if (mHour > 17 && mHour <= 20) {
			
			timeOfDay = "Dinner";
			timeRank = 5;
		}
		
		if (mHour > 20 && mHour <= 23) {
			
			timeOfDay = "Evening";
			timeRank = 6;
		}
		
		timeRankString[0] = timeOfDay;
		timeRankString[1] = String.valueOf(timeRank);
		
		return timeRankString;
		
	}
	
	
	// Check to see if records file exists, create it if not
	public void checkRecords() {
		
		try{
			
			
			String filePath = globalRecordsFile;
			
			
			CSVReader reader = new CSVReader(new FileReader(filePath));
			
			reader.close();
			
			
		} catch(IOException e) {
			
			try{	
				
				
				String filePath = globalRecordsFile;
				
				File f = new File(filePath);
				
				String csv = filePath;
				CSVWriter writer = new CSVWriter(new FileWriter(csv));
				
				
				String[] header = new String[18];
				header[0] = "Date";
				header[1] = "Time";
				header[2] = "Bloodsugar";
				header[3] = "Carbohydrates";
				header[4] = "Bolus Dose";
				header[5] = "Bolus Insulin";
				header[6] = "Basal Dose";
				header[7] = "Basal Insulin";
				header[8] = "Predicted Bloodsugar";
				header[9] = "Calculated Carbs";
				header[10] = "Calculated Bolus";
				header[11] = "Detailed Carbs";
				header[12] = "Notes";
				header[13] = "Time of Day Category";
				header[14] = "Time of Day Rank";
				header[15] = "Carb Ratio";
				header[16] = "Correction Factor";
				header[17] = "Target";
				
				// Add these in eventually, will need to change String[11] to String[13]
				// header[11] = "Detailed Carbs";
				// header[12] = "Notes";
				
				writer.writeNext(header);
				
				writer.close();
				
			} catch(IOException e2) {
				
				//Log.i(TAG,"IOException");
				//Log.e("Error", e2.toString());
				
			}
			
		}
		
	}
	
	// write entered data to records file
	public void writeRecords() {
		
		//getPreferences();
		
		String date = editDate.getText().toString();
		String time = editTime.getText().toString();
		
		String bloodsugar = editBloodsugar.getText().toString();
		if(bloodsugar.length()==0) {
			bloodsugar = "0";
		}
		
		String carbohydrates = editCarbs.getText().toString();
		if(carbohydrates.length()==0) {
			carbohydrates = "0";
		}
		
		String bolus_dose = editBolus.getText().toString();
		if(bolus_dose.length()==0) {
			bolus_dose = "0";
		}
		
		String basal_dose = editBasal.getText().toString();
		if(basal_dose.length()==0) {
			basal_dose = "0";
		}
		
		String predicted_bloodsugar = predictedBloodsugar.getText().toString();
		if(predicted_bloodsugar.length()==0) {
			predicted_bloodsugar = "0";
		}
		
		String calculated_carbs = predictedCarbs.getText().toString();
		if(calculated_carbs.length()==0) {
			calculated_carbs = "0";
		}
		
		String calculated_bolus = predictedBolus.getText().toString();
		if(calculated_bolus.length()==0) {
			calculated_bolus = "0";
		}
		
		String carb_ratio = Float.toString(fCarbRatio);
		String correction_factor = Float.toString(fCorrectionFactor);
		String target = Float.toString(fTarget);
		
		
	
		
		try{
			
			
			
			
			String filePath = globalRecordsFile;
			
			
			String csv = filePath;

			CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
			
			
			String[] timeRank = new String[2];
			timeRank = classifyTime(time);
			
			String[] record = new String[18];
			record[0] = date;
			record[1] = time;
			record[2] = bloodsugar;
			record[3] = carbohydrates;
			record[4] = bolus_dose;
			record[5] = bolusInsulin;
			record[6] = basal_dose;
			record[7] = basalInsulin;
			record[8] = predicted_bloodsugar;
			record[9] = calculated_carbs;
			record[10] = calculated_bolus;
			record[11] = null;
			record[12] = null;
			record[13] = timeRank[0];
			record[14] = timeRank[1];
			record[15] = carb_ratio; 
			record[16] = correction_factor;
			record[17] = target;
			
			
			writer.writeNext(record);
			
			
			writer.close();
			
		} catch(IOException e) {
			
			//Log.i(TAG,"IOException");
			//Log.e("Error", e.toString());
			
		}
	}
	
	// Calculate new bloodsugar based on carbs and bolus insulin dose
	
	public void calcBloodsugar(float bloodsugar, float carbs, float bolus) {
		
		float carbUnits;
		float correctionAmount;
		float correctionUnits;
		float calcBloodsugar;
		
		// calculate bloodsugar
		// logically, does not make sense
		// return to EditText bloodsugar
		
		// Calculate the units of bolus insulin to take to cover carbohydrates; assumes carb ratio of 1 unit / 5 g carbs
		// TODO add variable for carb ratio from preferences
		carbUnits = carbs / fCarbRatio;
		
		// Calculate the units of bolus insulin to correct for bloodsugar above target level
		// Total units of bolus insulin = carb units + correction units
		correctionUnits = bolus - carbUnits;
		
		correctionAmount = correctionUnits * fCorrectionFactor;
		
		// The calculated bloodsugar after this dose of bolus insulin
		// Assumes carbUnits will cover carbs and correction units will cover units above target level
		// TODO add variable for correction factor from preferences
		calcBloodsugar = -(correctionAmount - bloodsugar);
		
		// Display the calculated bloodsugar in the predictedBloodsugar field in the layout
		predictedBloodsugar.setText(String.valueOf(calcBloodsugar));
		
	}
	
	
	
	// Calculate carbs based on bloodsugar and bolus insulin dose
	public void calcCarbs(float bloodsugar, float bolus) {
		
		float correctionAmount;
		float correctionUnits;
		float carbUnits;
		float calcCarbs;
		
		// calculate Carbs
		// return to predicted carbs
		
		// Calculate the amount bloodsugar is below / above target level
		
		correctionAmount = bloodsugar - fTarget;
		
		// Calculate the bolus units to correct for bloodsugars above target level
		// target is 120 mg / dL; correction factor is 1 unit for every 30 mg/dL above target
		// TODO: add variables for these later
		
		if (correctionAmount == 0) {
			correctionUnits = 0;
		} else {
			
			correctionUnits = correctionAmount/fCorrectionFactor;
		}
		// Since bolus dose = correction units + carb units,
		// subtracting above correction dose from input Bolus dose gives the units needed to cover carbs
		carbUnits = bolus - correctionUnits;
		
		
		// Multiplying the number of bolus units for carbs by the carb ratio will give the grams of carbs
		// needed to cover this bolus dose
		// correction factor is 1 unit for every 5 g of carbohydrates
		// add variables for these later
		calcCarbs = fCarbRatio * carbUnits;
		
		// Display the calculated carbohydrates in the predictedCarbs field in the layout				
		predictedCarbs.setText(String.valueOf(calcCarbs));
		
	}			
	
	// Calculate bolus insulin dose based on bloodsugar and carbs
	
	public void calcBolus(float bloodsugar, float carbs) {
		
		float correctionUnits;
		float carbUnits;
		float calculatedBolus;
		
		// calculate Bolus dose
		// return to predicted Bolus dose
		
		// Calculate the bolus units to correct for bloodsugars above target level		
		// target is 120 mg / dL; correction factor is 1 unit for every 30 mg/dL above target
		// TODO add variables from preferences for these later
		correctionUnits = ((bloodsugar - fTarget)/fCorrectionFactor);
		
		// Calculate the units of bolus insulin to take to cover carbohydrates; assumes carb ratio of 1 unit / 5 g carbs
		// TODO add variable for carb ratio from preferences
		carbUnits = (carbs/fCarbRatio);
		
		// Total units of bolus insulin = carb units + correction units		
		calculatedBolus = correctionUnits + carbUnits;
		
		// Display the calculated bolus dose in the predictedBolus field in the layout			
		predictedBolus.setText(String.valueOf(calculatedBolus));
		
	}
	
	
	
	// Update text view fields
	public void displayCounts() {
		
		

		
		// Update predicted bloodsugar field
		if(mBloodsugar != 0 && mCarbs != 0 && mBolus!= 0) {
			calcBloodsugar(mBloodsugar, mCarbs, mBolus);
		}
		
		// Update calculated carbohydrate field
		if(mBloodsugar != 0 && mBolus != 0) {
			calcCarbs(mBloodsugar, mBolus);
		}
		
		// Update calculated bolus insulin dose field
		if(mBloodsugar != 0 && mCarbs != 0) {
			calcBolus(mBloodsugar, mCarbs);
		}
		
		
	}
	
	
	
	
	
	// End Methods from Android Code
	
	 public static void main(String args[]) {
		new Java_Application();
	 }

}
