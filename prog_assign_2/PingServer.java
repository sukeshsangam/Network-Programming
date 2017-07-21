
// importing the required packages
import java.io.*;
import java.net.*;
import java.util.*;

class PingServer {
	private static final double LOSS_RATE = 0.25; // Declaring and initializing the LOSS_RATE
    private static final int AVERAGE_DELAY = 150; // milliseconds
	
    public static void main(String argv[]) throws Exception
    {
		int seed=0;    // declaring seed to zero 
	
	// socket variables
	DatagramSocket serverSocket; // Declaring DatagramSocket
	byte[] receiveData = new byte[1024]; // Declaring receiveData as byte
	byte[] sendData = new byte[1024]; // Declaring sendData as byte
	InetAddress IPAddress;   // IPAddress is used to store the  ip address of the client
	String IPADDRESS,IP;  // Declaring the string variables to store ipaddresses
	int clientPort; // This variable is used to store the clientPort
	int delay; // delay variable is used to store the delay for each packet
	String delays; // delays variable is used to store the delay as string, Which is used further to print.

	// server variables
	String serverSentence; // serverSentence is used to print the 

	// command-line arguments
	int port=0;

	// process command-line arguments
	if (argv.length < 1 || argv.length >2) { 
	   
	   System.out.println("Usage: java PingServer port [seed] "); // Printing the error of arguments, if it is not given through command line argument 
	    System.exit (-1); // exit from the program
	}
	
	Random random; // Declaring the random class
	
	try
	{
	
	port = Integer.parseInt(argv[0]); //Taking port number from the command line arguments 
	//seed = Integer.parseInt(argv[1]);
	if(port<10001 || port >11000) // Checking the port range
	{
		System.out.println("ERR - arg 1"); //printing the error for argument one
		System.exit(0); // exiting from the program
	}
	}
    catch(NumberFormatException e) // if the given command line argument is not a number ,then it catches the exception here
	{
		System.out.println("ERR - arg 1"); //printing the error for argument two
		System.exit(0);// exiting from the program
	}
	catch(Exception e) // Other than numberformat execption ,if there is any other error, it catches here for argument two 
	{
		System.out.println("ERR - arg 1");
		System.exit(0);
	}
	if (argv.length==2)// If the length of the argument is eual to two ,then it the seed value is stored here by checking 
	{
	try
	{
	//port = Integer.parseInt(argv[0]);
	seed = Integer.parseInt(argv[1]); // Storing the seed value, which is given through command prompt  
	}
    catch(NumberFormatException e) // through this try and catch block we are vaidating the seed number format and printing error if it is not a number.
	{
		System.out.println("ERR - arg 2"); //printing error for argument two if it is not a number
		System.exit(0);
	}
	}
	if (seed == 0) {   
		random = new Random(); // if seed is not given then it automatically generates a randome number without seed
		} else {
			
		random = new Random(seed);// if seed is given , then it takes seed to generate random number
		}
	
	serverSocket = new DatagramSocket(port); // Create welcoming socket using given port

	

	// here While loop is used to run the server continuosly, waiting for a server request  
	while (true) {

	    // Waits for some client to send a packet
	    DatagramPacket receivePacket = new DatagramPacket 
		(receiveData,receiveData.length);
	    serverSocket.receive(receivePacket); // receiving the packets from the server
	    
    	    String clientSentence = new String(receivePacket.getData(), 0,
    					       receivePacket.getLength());  // storing the received data into a string variable
	  
	    IPAddress = receivePacket.getAddress(); // getting the ipaaddress of the client and storing it into a variable
		IPADDRESS= IPAddress.toString(); // Converting the ipaddress to string and storing it into a variable 
		IP= IPADDRESS.replace("/", ""); // replacing the "/" by empty space in the ipaddress 
	    clientPort = receivePacket.getPort(); // Getting the port of the client
	    sendData = clientSentence.getBytes(); // storing the bytes into a variable
	    DatagramPacket sendPacket = new DatagramPacket(sendData, 
							   sendData.length, 
							   IPAddress, 
							   clientPort); // declaring and initializing the DatagramPacket
							   if (random.nextDouble() < LOSS_RATE) // checking condition for the packet loss
							   {
								  
								  System.out.println (IP+":"+clientPort+">"+clientSentence + " ACTION: not sent");// here the packet is not sent to the client, so here we are displaying the action equal to not sent
							   }else
							   {
								   delay = (int) (random.nextDouble() * 2 * AVERAGE_DELAY); // if it is not dropped here we are generating the delay
								   delays= Integer.toString(delay);// converting the delay time to string
                                   Thread.sleep(delay);// here the message is delayed by calling Thread.sleep function
								 
	    serverSocket.send(sendPacket); // after delayed the message is sent back to server
		System.out.println (IP+":"+clientPort+">"+clientSentence+" ACTION: delayed "+delays+" ms"); // here the client ip,port,message, delay and action is displayed
							   }
	} //  end while; loop back to accept a new client connection

    } // end main

} // end class