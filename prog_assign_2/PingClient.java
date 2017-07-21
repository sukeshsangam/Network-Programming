//importing the required packages
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DecimalFormat;

class PingClient {
	// getMin function is used for calculating the minimum value of the RTT's ,by passing the input RTT values as a array
	public static long getMin(long[] inputArray){ 
    long minValue = inputArray[0]; 
    for(int i=1;i<inputArray.length;i++) //iterating through the array
	{ 
      if(inputArray[i] < minValue){  // finds minimum value
        minValue = inputArray[i]; 
      } 
    } 
    return minValue; // returns the minimum value
  } 
  
  // getMax function is used for calculating the maximum value of the RTT's , by passing the input RTT values as a array
   public static long getMax(long[] inputArray){ 
    long maxValue = inputArray[0]; 
    for(int i=1;i < inputArray.length;i++) // iterating through the array
	{ 
      if(inputArray[i] > maxValue){ // finds the maximum value
         maxValue = inputArray[i]; 
      } 
    } 
    return maxValue; // returns the maximum value
  }
  
  // getAvgValue function is used for calculating the average value of the RTT's , by passing the input RTT values as array
  public static String getAvgValue(long[] inputArray){
	  long sum=0; // declaring the sum variable and initializing it to zero
	  double summ; // declaring the summ variable as double
	  for(int i=0;i<inputArray.length;i++)// iterating through the array
	  {
		  sum=sum+inputArray[i]; // summing up all the values in the array
	  }
	  summ=(double)sum; // converting the sum value to double
	  double length=(double)inputArray.length; // converting the input array length to double
	  double avgValue = summ/length; // finding the average value
	  DecimalFormat df = new DecimalFormat("0.00"); //used for rounding off the decimal value upto two digits
	  //String avg=avgValue.toString();
	  //String d= df.format(avgValue);
	  //double d= (double)Math.round((avgValue*100.0)/100.0);
	 //double f=(double)d;
	 String output=df.format(avgValue); // rounding off the decimal value upto two digits
	  return output; // returning the average value
  }
 
    public static void main(String argv[]) throws Exception // msin class
    {
	// socket variables
	DatagramSocket clientSocket; // declaring the datagram socket
	// declaring the datagram packets
	DatagramPacket sendPacket; 
	DatagramPacket receivePacket;
	// declaring the Byte variables
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	// declaring the IPaddres variable
		InetAddress IPAddress;
	
	// declaring start,a[] array, end 
	long start=0;
	long a[]=new long[10];
	long end=0;
	// client variables
	String clientSentence="";  // declaring the string variables
	String	serverSentence=""; // declaring the string variable
	BufferedReader inFromUser; // declaring the inFromUser as bufferedReader

	// command-line arguments
	int port=0; 
	String server;
	int i=0,j=0,k=0,dev=0; // declaring and initiazing the integers variable which are used in the program , here dev is used as flag , 

	// process command-line arguments and printing the errors if it not given in right format
	 // It should accept two command line arguments so if the user does not give two arguments it checks and prints te errors
		if(argv.length==1) 
		{
			System.out.println("Usage: java PingClient hostname port"); // error in argument 2
			System.exit(0);
		}
		if(argv.length==0)
		{
			System.out.println("Usage: java PingClient hostname port "); // error in argument 1 and 2
			System.exit(0);
		}
		if(argv.length>2)
		{
			System.out.println("Usage: java PingClient hostname port "); // error in argument 1 and 2
			System.exit(0);
		}
	    

	
	server = argv[0];// storing the argument one in the server variable
	try // checking the argument ,whether it is given in the write format
	{
	port = Integer.parseInt(argv[1]);// storing the argument two in port variable
	if(port<10001 || port >11000) // Checking the port range
	{
		System.out.println("ERR - arg 2"); //printing the error for argument two
		System.exit(0); // exiting from the program
	}
	}
	catch(NumberFormatException e)
	{
		System.out.println("ERR - arg 2"); // printing the error for argument two,if it is not number
		System.exit(0);// exting from the program
	}
	
        inFromUser = new BufferedReader(new InputStreamReader(System.in));  // Create (buffered) input stream using standard input
      
	
	clientSocket = new DatagramSocket();// Create client socket to destination
	
	IPAddress = InetAddress.getByName (server); // getting the ip address of the server through the name of the server
	
	
	
	
	
		
			
			Date now = new Date(); // declaring the date class object
			

		while(i<10) // while loop is used to sent the 10 packets continuously as we took 10 to compare with i
		{
			
			String str= Integer.toString(i); // converting the i to string
	
	long msSend = now.getTime();// getting the current system time 
		clientSentence = "PING "+str+" "+Long.toString(msSend) ; // storing the specipied format message in the string variable                     
	sendData = clientSentence.getBytes(); // getting the message data into bytes and storing it into byte variable
		
		start= System.currentTimeMillis(); // getting the system current time in milliseconds
	// Create packet and send to server
	sendPacket = new DatagramPacket(sendData, sendData.length,  
					IPAddress, port);
					
	clientSocket.send(sendPacket); // sending the packet to the server
	try
	{
		dev=0; // setting the flat to zero
		clientSocket.setSoTimeout(2000); // timeout is set to 2secs
	// Create receiving packet and receive from server
	receivePacket = new DatagramPacket(receiveData,
					   receiveData.length); 
					   now = new Date();
				long msReceived = now.getTime(); // getting the system current time
	clientSocket.receive(receivePacket); // receive the packets
	end=System.currentTimeMillis();// getting the system current time in milliseconds
	serverSentence = new String(receivePacket.getData(), 0,
				    receivePacket.getLength()); // storing the received data into the string variable
					if(dev==0) // checking the flag variable
					{
						
    a[k]=end-start; // calculating the RTT and storing it into an array
	
					
	
	System.out.println(serverSentence + " RTT :" + Long.toString(a[k])+ "ms"); // printing the message in the specifiend format
	k++; // incrementing the k
					}
		}
		catch(IOException e) // if the packet is not received it goes to catch block
		{
			System.out.println(clientSentence + " RTT : " +"*"); // prints the output in the specified format
			j++; // incrementing the j to calculate the no. of packets lossed
			
			dev=1; // setting the flag to one
		}
		i++;// incrementing the i
		
		
		}
		long b[]=new long[i-j]; // declaring an array to store the RTTS which is further used to calculate the min/avg/max
		for(int s=0;s<i-j;s++) 
		{
			b[s]=a[s]; // storing the RTT values into a array
			
		}
		float percentage= (float)j/i*100; // calculating the percentage of packet loss
		System.out.println("---- PING Statistics ----"); // printing the ping statistics
		System.out.println("10 packets transmitted, "+Integer.toString(i-j)+" packets received, "+Integer.toString((int)percentage)+ "% packet loss");// printing the ping statistics in specified format
		System.out.println("round-trip (ms) min/avg/max = "+ getMin(b)+"/"+getAvgValue(b)+"/"+getMax(b)); //printing the min/avg/max values
		
	// close the socket
	clientSocket.close();

    } // end main

} // end class