//
//  Java_Application.java
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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.*;
import java.util.*;

import java.util.List;
import java.util.ArrayList;

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
import javax.swing.table.TableModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.Timer;

import javax.swing.*;

import com.apple.eawt.*;

import au.com.bytecode.opencsv.*;

import java.util.prefs.*;

//implements TableModelListener implements ActionListener

public class ViewDataTable extends JFrame  {

	private String[] columnNames;
	
	private Object[][] data;
	
	protected static int aboutWidth = 1500;
    protected static int aboutHeight = 350;
    protected static int aboutTop = 500;
    protected static int aboutLeft = 20;
	
	// Strings
	
	private String pathSeperator = System.getProperty("file.separator");
	
	private String userHome = System.getProperty("user.home");
	
	private String bolusInsulinLabel;
	private String basalInsulinLabel;
	private String predictedBolusInsulinLabel;
	
	private String globalRecordsFile;
	private String localRecordsFile;
	
	
	//Timer time = new Timer(300,this);
	
	private long timeModified;
	
	private long actualLastModified;
	
	// declare variable for preferences
	
	private Preferences storedPrefs;

		

	//@Override
	//public void actionPerformed(ActionEvent e) {
		
		//String fileName = "/Users/daileya/Bloodsugar/blood_sugar_records.csv";
	
		//String filePath = "/Users/daileya/Bloodsugar/blood_sugar_records.csv";
	
		//File fileName = new File(filePath);
		//long actualLastModified = fileName.lastModified();
		//if (timeModified != actualLastModified) {
		
			//super.repaint();
			
			//data = readRecords();
			
			//loadTable();
			
			
			//model.fireTableDataChanged();
			
			//timeModified = actualLastModified;
		
		
		//}
		

	//}
	
	//@Override
	//public void tableChanged(TableModelEvent e) {
		
		//int row = e.getFirstRow();
		//int column = e.getColumn();
		//TableModel model = (TableModel)e.getSource();
		//String columnName = model.getColumnName(column);
		//Object data = model.getValueAt(row, column);
		

		
		//fireTableDataChanged();
		
	//}
	
	
	
	public ViewDataTable() {
		
		super("Bloodsugar: View Data");
		
		

		// Load Preferences for labels
		
		storedPrefs = Preferences.userNodeForPackage(this.getClass());
		
		
		getPreferences();
		
		
		
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		String filePath = globalRecordsFile;
		
		File fileName = new File(filePath);
		
		timeModified = fileName.lastModified();
		//time.start();
		
		columnNames = readColumnHeadings();
		
		data = readRecords();
	
		loadTable();		
		
	}
	

	
	// Method for getting preferences
	
	public void getPreferences() {
		
		// Load preferences
		String bolusInsulin = storedPrefs.get("BOLUS_INSULIN", "");
		
		String basalInsulin = storedPrefs.get("BASAL_INSULIN", "");
		
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
			
			
			// else load stored preference values into strings
			
			globalRecordsFile = localRecordsFile;
			
		}
		
		
	}
	
		
public void loadTable() {
			
	//columnNames = readColumnHeadings();
	
	//String file = "/Users/daileya/Bloodsugar/blood_sugar_records.csv";
	
	//data = readRecords();
	
	
	final JTable table = new JTable(data, columnNames);
	table.setPreferredScrollableViewportSize(new Dimension(500,70));
	table.setFillsViewportHeight(true);
	
	JScrollPane scrollPane = new JScrollPane(table, 22, 32);
	

	
	final JScrollBar horizontalScroller = 
	new JScrollBar(JScrollBar.HORIZONTAL);
	final JScrollBar verticalScroller = new JScrollBar();
	verticalScroller.setOrientation(JScrollBar.VERTICAL);
	horizontalScroller.setMaximum (100);
	horizontalScroller.setMinimum (1);
	verticalScroller.setMaximum (100);
	verticalScroller.setMinimum (1);
	
	//table.getModel().addTableModelListener(this);
	
	
	
	scrollPane.add(horizontalScroller);
	scrollPane.add(verticalScroller);
	this.add(scrollPane);
	
	WindowListener listener = new WindowAdapter(){
		
		@Override
		
		public void windowClosing(WindowEvent w) {
			
			Java_Application.viewDataOn = false;
			
			//JFrame windowCloseNotification = new JFrame;
			
			//EnterDataActivity enterData = new EnterDataActivity();
			//enterData.setVisible(true);
			
			
		}
		
	};
	
	this.addWindowListener(listener);
	
	this.setLocation(aboutLeft, aboutTop);
	this.setSize(aboutWidth, aboutHeight);
	
}
	

	
	public String[] readColumnHeadings() {
		
		String[] columnHeader = new String[18];
		
		try{
			
			String filePath = globalRecordsFile;
		
			CSVReader reader = new CSVReader(new FileReader(filePath));
			
			//String[] header = new String[];
			
			String[] header = reader.readNext();
			
			columnHeader = header;
		
			reader.close();
			
			
			
		} catch(IOException e) {
			// do something here
			//Log.i(TAG,"IOException");
			//Log.e("Error", e.toString());
		}
			
		return columnHeader;
	}
	
	
	
	
	// Read bloodsugar records line by line from csv file
	// Based on example from:http://opencsv.sourceforge.net/
	public Object[][] readRecords() {
		
		//Object[][] data = new Object[][];

			
			try{
				

				
				String filePath = globalRecordsFile;
				
				//lastModified = filePath.lastModified();
				
				CSVReader reader = new CSVReader(new FileReader(filePath));
				
				//columnNames = reader.readLine();
				
				String [] nextLine;
				
				List<String[]> dataList = new ArrayList<String[]>();
				
				reader.readNext();

				while ((nextLine = reader.readNext()) != null) {
					
					dataList.add(nextLine);
					//nextLine() is an array of values from the line
					//use nextLine[] for every column in the data file
					
					// pass this info to something that can handle it
					// could use this if it makes sense to pass records to file line by line
					//System.out.println(nextLine[0] + nextLine[1]);
					
					//Log.i("CSV_READER",nextLine[0]);
					//Log.i("CSV_READER",nextLine[1]);
					//Log.i("CSV_READER",nextLine[2]);
					//Log.i("CSV_READER",nextLine[3]);
					//Log.i("CSV_READER",nextLine[4]);
					//Log.i("CSV_READER",nextLine[5]);
					//Log.i("CSV_READER",nextLine[6]);
					//Log.i("CSV_READER",nextLine[7]);
					//Log.i("CSV_READER",nextLine[8]);
					//Log.i("CSV_READER",nextLine[9]);
					//Log.i("CSV_READER",nextLine[10]);
					
				}
				
				reader.close();
				
				data = dataList.toArray(new String[dataList.size()][]);
				
				
				
				
			} catch(IOException e) {
				// do something here
				//Log.i(TAG,"IOException");
				//Log.e("Error", e.toString());
			}
		
		return data;
		
	}


}
