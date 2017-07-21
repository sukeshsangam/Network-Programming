// filename : Prog1.java
// Name : Sukesh Sangam  
// Class (CS 555), Fall 2016 
// Program 1 - Java Refresher 
import java.io.*;   //importing java.io(Java IO API) package which is used for reading data from file and writing data to the file. 
import java.util.regex.*; //importing java.util.regex(Java regex API) package which is used for validating the pattern of the String.

public class Prog1 {

    public static void main(String [] args) {

        
        String infileName = "input.txt"; // Input file name stored in String variable.

	String[] tempvar; // String variable used to store address and port
	String d = ":"; // ":" is strored in String Variable d, which is used to split the read input file line into address and port 
	int i=0,j=0; // Two integer Variable i,j are declared and initialized to zero, which are used to count the number of lines read and number of error lines in input file.
        // This will reference one line at a time
        String line = null; // String variable which is used to store each line from input file
	
	//String pattern stores the regular expression pattern which is used to check the input line format
	String pattern= "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])"+
			"\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])"+
			"\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])"+
			"\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])"+
			"\\:([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";
       // String pattern = "^\\d+\\.\\d+\\.\\d+\\.\\d+\\:\\d+$"; without restricting the address range to 255 and port to 65535

        try { // try block
		File file= new File("output.txt"); // creating output file.
            //Object is created for FileReader class which takes input file name as parameter 
            FileReader fileReader = 
                new FileReader(infileName);

            // wrapping FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

             // Object is created for FileWriter class which is used to write to output file 
            FileWriter fileWriter =
                new FileWriter(file);

            // wrapping FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);
		bufferedWriter.write("Name: Sukesh Sangam"); // writes student name into Writer.
		bufferedWriter.newLine();	// newLine() method is used to move the cursor to the next line.
		bufferedWriter.write("UIN: 01060894"); // writing student UIN into the Writer.
		bufferedWriter.newLine(); // moving the cursor to the next line.

            while((line = bufferedReader.readLine()) != null) // condition in the while loop is used to check end of the file
		{
			i++;  // here i increments in each iteration, which is used to count total number of lines read from input file.
			
			if(Pattern.matches(pattern, line))// checking if the input line format matches with the pattern
				{
		bufferedWriter.write(line);//writing the read input line to the writer 
		bufferedWriter.newLine(); // moving the cursor to the newline
		tempvar = line.split(d);// splitting the read input line into two parts using ":" as delimeter
		bufferedWriter.write("Address:"+tempvar[0]+","+"Port:"+tempvar[1]); // after splittting writing it to writer as address and port
		bufferedWriter.newLine(); // moving the cursor to the newline
		bufferedWriter.flush(); // flushes the data to the output file
                //System.out.println(line);
		}
		else
		{
		j++;// here j increments when the input line doesn't match with pattern

		bufferedWriter.write(line);// writing the read input line to the writer
		bufferedWriter.newLine();// moving the cursor to the new line
		bufferedWriter.write("Error in Input Line ");// writing Error in Input Line to the writer 
		//System.out.println("failed");
		//bufferedWriter.write("failed");
		bufferedWriter.newLine();// moving the cursor to new line
		
		bufferedWriter.flush();// flushes the data to the output file
		}
		
        	    }   
		bufferedWriter.newLine(); //moving the cursor to the new line for space between lines
		bufferedWriter.newLine(); //moving the cursor to the new line for space between lines
		bufferedWriter.write("Summary:"); // writing some text ( Summary:) to the writer
		bufferedWriter.newLine(); //moving the cursor to the new line for space between lines
		bufferedWriter.write("Lines Read: "+ i); // writing the total number of lines read to the writer
		bufferedWriter.newLine(); //moving the cursor to the new line
		bufferedWriter.write("Number of Errors: "+j); //writing the total number of error lines to the writer
		bufferedWriter.flush(); // flushes the data to the output file

		
            bufferedReader.close();   // closing the file      
        }
	// catch blocks to catch the exceptions
        catch(FileNotFoundException e) {
                       e.printStackTrace();    
        }
        catch(IOException e) {
                             
            
            e.printStackTrace();
        }
    }
}
