
package com.ihsinformatics.endtb.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static final String FILE_NAME = "elims.txt";
	public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


	public static void log(Exception e) {
		try {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String toWrite = sw.toString();
			File externalStorageDir = Environment.getExternalStorageDirectory();
			File myFile = new File(externalStorageDir, FILE_NAME);

			if (!myFile.exists()) {
				myFile.createNewFile();
			} 
			
			FileOutputStream fOut = new FileOutputStream(myFile, true);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append("\n\n\n\n\n------------------------------------------------------------------------------------\n");
			myOutWriter.append(TIMESTAMP_FORMAT.format(new Date()));
			myOutWriter.append("\n------------------------------------------------------------------------------------\n");
			myOutWriter.append(toWrite);
			myOutWriter.close();
			fOut.close();
			
		} catch (IOException ee) {
			ee.printStackTrace();
		}
	}
	
	public static void log(String s) {
		try {
			File externalStorageDir = Environment.getExternalStorageDirectory();
			File myFile = new File(externalStorageDir, FILE_NAME);

			if (!myFile.exists()) {
				myFile.createNewFile();
			} 
			
			FileOutputStream fOut = new FileOutputStream(myFile, true);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append("\n\n\n\n\n------------------------------------------------------------------------------------\n");
			myOutWriter.append(TIMESTAMP_FORMAT.format(new Date()));
			myOutWriter.append("\n------------------------------------------------------------------------------------\n");
			myOutWriter.append(s);
			myOutWriter.close();
			fOut.close();
			
		} catch (IOException ee) {
			ee.printStackTrace();
		}
	}
}
