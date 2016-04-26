package Project4;

import java.io.*;

public class FileIn {
//	**************************************************************************
//	 Class FileInput controls reading from a text file by opening the file,
//	 reading one line at a time, keeping track of the end of file, and handling
//	 I/O errors.
//	**************************************************************************
	
	 	BufferedReader fileInput;
	 	private String fileName;
	 	boolean moreData;

	 //**************************************************************************
	  // This constructor opens the file given by the name passed in the parameter.
	  //**************************************************************************

	  public FileIn () {
		  fileName = null;
		  moreData = false;
	  }

	  public void openFile (String name) {

	  	 if (!name.equals(null)) {
	 	    fileName = name;
	 	 try {
			 File inFile = new File(name);
	 	     FileReader fileReader = new FileReader(inFile);
			 fileInput = new BufferedReader(fileReader);
			 moreData = true;
		 } catch (IOException error) {
			System.err.println ("An error occurred reading from file " +
	                     fileName + "!\n");
	        System.exit(1);
	      }

		}
	  }  // end openFile

	  //**************************************************************************
	    // This method reads and returns one line of the text file.  If the file
	    // is empty, an empty string is returned.
	    //**************************************************************************

	  public String readLine ()
	  {
	     String inputLine;

		 if (fileName.equals(null)) {
			 System.err.println("There is no file opened. Please check the file name or open the file");
			 System.exit(1);
		 }

	     try
	     {
	       	inputLine = fileInput.readLine();
	       	if (inputLine == null) moreData = false;
	        return inputLine;
	     }
	     catch (IOException error)
	     {
	        System.err.println ("An error occurred reading from file " +
	                     fileName + "!\n");
	        System.exit(1);
	     }
	     return null;
	  }  // end readLine

	  public boolean hasMoreData() {
		 return moreData;
	  }
	  
	  public void fileClose() {
		try {
			fileInput.close();
		}
		catch (IOException error) {
			System.err.println("An error occurred writing to a file" + fileName+"!\n");
		}
	  }

	
}
