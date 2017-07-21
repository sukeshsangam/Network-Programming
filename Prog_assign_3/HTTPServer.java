import java.io.*;
import java.net.*;

public class HTTPServer 
{
	public static void main(String argv[]) throws Exception {
		// TCP server socket variables
		Socket Socket_c;
		InetAddress IP_address;
		ServerSocket Socket_s = null;		
		
		//file writer
		FileWriter fw = null;
		String request = "", line;		
		int port_no = 0;
		//integer variables
		int[] a=new int[20];
		int[] b=new int[20];
		
		int read = 0;
		int j=0;
		//buffer
		byte[] buffer = new byte[1024];
		
		// processing  command-line arguments
		if (argv.length != 1) {
			System.out.println("Usage : java HTTPServer <port>");
			System.exit(-1);
		}

		try {
			//taking port
			port_no = Integer.parseInt(argv[0]);
			//checking port
			if (port_no < 1 || port_no > 65535) {
				System.out.println("ERROR : invalid port number");
				System.out.println("Usage : java HTTPServer <port>");
				System.exit(-1);
			}		
		} catch (Exception e) {
			System.out.println("ERROR : Port number invalid.");
			System.out.println("Usage : java HTTPServer <port>");
			System.exit(-1);
		}
//System.out.println("Test1");
		// Create server socket
		try {
			//socket variables
			Socket_s = new ServerSocket(port_no);
			//System.out.println("Test2");
		} catch (BindException e) {
			System.out.println("Port number " + port_no + " not available");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("Exception creating server socket.");
			System.exit(-1);
		}

		for(;;)
		{//System.out.println("Test3");
			//Wait for client connection
			//accept socket
			Socket_c = Socket_s.accept();
			request = "";
			
			try 
			{
				//buffer reader
				BufferedReader clientRequest = new BufferedReader(
						new InputStreamReader(Socket_c.getInputStream()));
				Socket_c.setKeepAlive(true);

				IP_address = Socket_c.getInetAddress();

				//Read data from client
				//System.out.println("Test");
				line = clientRequest.readLine();
				
				//System.out.println(line);
				//System.out.println(line);
				//System.out.println("Test");
				/*if (line == null)
				{
					Socket_c.close();
					System.out.println("Error reading client data");
					continue;
				}
				*/
//System.out.println(line.length());
				while (line.length() > 0) {
					//System.out.println(line.length());
					request += line + '\n';
					//System.out.println("check");
					
					line = clientRequest.readLine();
					//System.out.println("hi:"+j);
				}
				//System.out.println(line);
				if(request.contains("GET")||request.contains("PUT"))
					{
					//	System.out.println("check2");
						
						
						for (int index = request.indexOf("/");
						index >= 0;
								index = request.indexOf("/", index + 1))
							{
								a[j]=index;
								j++;
								}
								int k=0;
								for (int index = request.indexOf("");
						index >= 0;
								index = request.indexOf("", index + 1))
							{
								
								b[k]=index;
								//j++;
								k++;
								//System.out.println(k);
								if(k==3)
								{
									break;
								}
								}
								//System.out.println("check2");
						//	System.out.println("hi:"+j);
					}
					//System.out.println("hi:"+j);

				int pos = request.indexOf("/");
				//System.out.println(pos);
				//pos = request.indexOf("/");
				//System.out.println(pos);
				
				//System.out.println("Type: "+ request.substring(0, pos-1));
				System.out.println(request);
				int i=0;				if (request.contains("GET")) {
					System.out.println(IP_address.getHostAddress() + ":" + Socket_c.getPort() + ": GET");
				} else if (request.contains("PUT")) {
					System.out.println(IP_address.getHostAddress() + ":" + Socket_c.getPort() + ": PUT");
					i=1;
				}

				if (i==0) 
				{
					//System.out.println("hi");
					int pos1 = request.indexOf("HTTP", pos);
					String path = request.substring(pos + 1, pos1 - 1);
					//System.out.println("Path: " + path);
					File file = new File(path);					
					
					if(file.exists()) 
					{
						//System.out.println("file present");
						DataOutputStream clientResponse = new DataOutputStream(
								Socket_c.getOutputStream());
						//request="sukesh";
						request += "HTTP/1.0 " + " 200 OK" + '\n';
						request += "Host: " + Socket_c.getLocalAddress() + '\n';						
						request += "Last-Modified: " + file.lastModified() + '\n';
						request += "Content-Length: " + file.length() + '\n';
						request += "User-agent: ODU-CS455/555" + '\n';
						request += "Content-Type: text/html" + '\n';
						//request += "Server: Linux" + '\n';
						clientResponse.writeBytes(request + '\n');
						//System.out.println("bro try it");
						byte[] bytBuffer = new byte[(int) file.length()];
						BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
						bis.read(bytBuffer, 0, bytBuffer.length);
						
						
						FileInputStream fileInputStream = new FileInputStream(file);
						while ((read = fileInputStream.read(buffer)) != -1) {
							clientResponse.write(buffer, 0, read);
						}
						
						clientResponse.close();						
                        fileInputStream.close();                        
						Socket_c.close();
					}
					else {
						
						// Create output stream attached to socket
						DataOutputStream clientResponse = new DataOutputStream(
								Socket_c.getOutputStream());

						request = "HTTP/1.0" + " 404 file not found" + '\n';
						request += "User-agent: ODU-CS455/555" + '\n';
						request += "Content-Type: text/html" + '\n';
						request += "Host: "	+ Socket_c.getLocalAddress() + '\n';						
						clientResponse.writeBytes(request + '\n');
						Socket_c.close();
						//continue;
					} 
				}
				else 
				{			
			//System.out.println("put entered");
					DataOutputStream clientResponse = new DataOutputStream(
								Socket_c.getOutputStream());

					//read file name first
					//System.out.println("12");
					String filename1 = request.substring(a[j-3]+1);
					String file_ex[]=filename1.split(" ");
					String filename=file_ex[0];
                     //System.out.println("13");
					//System.out.println(filename);					 
					File file1 = null;
					file1 = new File(filename);
					//System.out.println(filename);

					if (file1.exists()) {
						file1.delete();
					}
					file1.createNewFile();					
					
					fw = new FileWriter(file1.getAbsoluteFile(), true);
					BufferedWriter bw = new BufferedWriter(fw);						

					try {
						//Read file content and store on server
						
						line = clientRequest.readLine();
						//System.out.println("1"+line);
						bw.append(line + "\n");
						//while (line.length() > 0) {
							//while(line!=null){
								int out=0;
								while(true){
									if(filename.contains(".html")){
								if(line.contains("</html>")){
									bw.append(line + "\n");
									break;
									}}
									else
									{ int z=0;
								bw.append(line + "\n");
										while(z<30)
										{
										
										bw.append(line + "\n");
										
										z++;
										}
										break;
									//	if(out==100)
										//{
											//break;
										//}
									}
							request = request + line + '\n';
							line = clientRequest.readLine();
							bw.append(line + "\n");
							//System.out.println(line+"1");
							//out++;
						}
						
						request = "HTTP/1.0" + " 200 OK  File Created" + '\n';
						//System.out.println("File stored successfully !!!");						
					} catch (Exception e) {
						request = "HTTP/1.0" + " 606 FAILED File not created" + '\n';											
						e.printStackTrace();
					}
					
					bw.close();					
					clientResponse.writeBytes(request + '\n');
				}
				Socket_c.close();
			} catch (Exception e) {
				Socket_c.close();
			}
		}

	}
}
