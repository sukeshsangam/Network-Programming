import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;
import java.net.*;
import java.util.regex.*;
import java.util.Scanner;

public class HTTPClient {

    public static void main(String[] args) throws IOException {
		String hostname="";
        int port = 80;
		String result="";
		String path="";
		String filename="";
		String LengthGlobal="";
		String LastModifiedGlobal="";
		int i=0,j=0,code=0;
		int read = 0;
		String file="";
		byte[] buffer = new byte[1024];	

        Socket socket = null;
        PrintWriter writer = null;
		PrintWriter writer1 = null;
        DataOutputStream dOutStream;
		BufferedReader reader = null;
		BufferedReader reader2 = null;
		BufferedReader reader3 = null;
		BufferedReader br =null;
		String y="";
			if (args.length < 1) 
			{
	    System.out.println ("Usage: java HTTPClient URL or java HTTPClient PUT URL path/<filename>\n");
	    System.exit (-1);
		}
	
		if(args[0].equals("PUT"))
		{
			//System.out.println("Sukku");
			
			//System.out.println("Sukku");
					if(args.length<3)
					{
					System.out.println("Usage: java HTTPClient PUT URL path/<filename>\n");
						System.exit(-1);
					}
						String url1=args[1];
					String regex1="^http://[-a-z0-9+&@#/%?=~_|!:,.:]*[1]{1}(([0]{1}[0-9]{3})|([1]{1}[0]{3}))";
					Pattern r1= Pattern.compile(regex1);
					Matcher m1 = r1.matcher(url1);
					if(m1.matches())
					{
				// takin indexes
					int doubleslash1=url1.indexOf("//");
					doubleslash1 +=2;
					int portindex= url1.indexOf(':',doubleslash1);
					//int pathindex= url1.length();
					String hostname1=url1.substring(doubleslash1,portindex);
					int port1=Integer.parseInt(url1.substring(portindex+1,url1.length()));
					System.out.println(hostname1);
					System.out.println(port1);
					String path1=args[2];  
					try{
					//checking if it contains hostname1
						if(hostname1.contains(":"))
					{
					int x=hostname1.indexOf(":");
					String final_hostname=hostname1.substring(0,x);
				//System.out.println(final_hostname);
				//System.out.println(port);
			//	socket = new Socket("localhost",3248 );
					socket = new Socket(final_hostname, port1);}
					else{
					socket = new Socket(hostname1,port1 );
					}
					}
					catch(Exception e)
					{
				
					System.exit(-1);
					}
			
			
            
				//http put request
				
					String httprequest1="PUT"+"  "+path1+"  "+"HTTP/1.0"+"\n"+"Host: "+hostname1+"\n"+"User-Agent: ODU-CS455/555"+"\n"+"";
					writer1 = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
					writer1.println(httprequest1);
					//writer1.println("{\"on\":false}");
					//dOutStream=new DataOutputStream(socket.getOutputStream());
			
					//dOutStream.writeBytes(httprequest1);
					
					System.out.println(httprequest1);
					if(path1.contains("/"))
					{
					String fileparts[]=path1.split("/");
					int a=fileparts.length;
					file=fileparts[a-1];
					}
					else 
					{
						file=path1;
					}
					byte[] bytBuffer = new byte[(int) file.length()];
		/*	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			bis.read(bytBuffer, 0, bytBuffer.length);
			FileInputStream fileInputStream = new FileInputStream(file);
			String a="";
			while ((read = fileInputStream.read(buffer)) != -1) 
			{
				//dOutStream.write(buffer, 0, read);
				a=a.concat(Character.toString((char)read));
				System.out.println(a);
			}
			//writer1.write(bytBuffer,0,bytBuffer.length);
			writer1.println(a);*/try{
			Scanner input=new Scanner(new File(file));}
			catch(Exception e)
			{
				System.out.println("file not found");
			}
			
    try {
		//bufferReader.
		 br = new BufferedReader(new FileReader(file));
			
			
			StringBuilder sb = new StringBuilder();
        
        String line_f = br.readLine();

       while (line_f != null) {
		  // while(input.hasNextLine()){
           sb.append(line_f);
            sb.append("\n");
            line_f = br.readLine();
			//System.out.println(line_f);
			//line_f=input.nextLine();
			
        }
        //return sb.toString();
    
 y=sb.toString();
	   }
	   catch(Exception c)
	   {
		   System.out.println("filenotfound");
	   }
	   finally {
       // br.close();
    }
			writer1.println(y);
			writer1.flush();
			
		//	System.out.println(x);
					
				InputStream data1= socket.getInputStream();
				reader3 = new BufferedReader(new InputStreamReader(data1));
				String line1;
				//System.out.println("1");
				while((line1 = reader3.readLine()) != null) {
				//System.out.println("1");	
				System.out.println(line1);
				
				
				}
				}
				else
				{
				System.out.println("it should start with http://");
		        System.exit(-1);
			}
		}
	else
	{
	 String url = args[0];
	 String regex="^http://[-a-z0-9+&@#/%?=~_|!:,.:]*[-a-z0-9+&@#/%=~_|]";
	 Pattern r = Pattern.compile(regex);
	 Matcher m = r.matcher(url);
	 if(m.matches())
		{
		 
		}
		else
		{
		 System.out.println("Usage: java HTTPClient PUT URL path/<filename> or java HTTPClient URL \n");
		 System.exit(-1);
		}
	 int doubleslash=url.indexOf("//");
	 doubleslash +=2;
	 int end=url.indexOf('/',doubleslash);
	 
	 if(end==-1)
		{
		 result=url.substring(doubleslash,url.length());
		 int portindex= url.indexOf(':',doubleslash);
		 int pathindex= url.length();
	
	 if(portindex==-1)
		{
		 
		}
	 else
		{
		 portindex+=1;
		 port=Integer.parseInt(url.substring(portindex,url.length()));
		 //System.out.println(port);
		}
		 
		}
	 else
		{
		result=url.substring(doubleslash,end);
		//System.out.println(result);
		int portindex= url.indexOf(':',doubleslash);
		int pathindex= url.length();
		if(end+1==pathindex)
		{
		 path="/";
		}
		else{
		path=url.substring(end,pathindex);
		}	 //System.out.println(path);
	 
	 if(portindex==-1)
		{
		 
		}
		else
		{
		 portindex+=1;
		 port=Integer.parseInt(url.substring(portindex,end));
		 //System.out.println(port);
		}
		 
		 
		}

		 String httprequest="";
		 
		 hostname=result;
		 //System.out.println(hostname);
		 if(path==""||path=="/"||path==null)
		 {
			httprequest="GET"+"  "+"/"+"  "+"HTTP/1.0"+"\n"+"Host: "+hostname+"\n"+"User-Agent: ODU-CS455/555"+"\n"+""+"\0";
		 }
		 else
		 {
		 httprequest="GET"+"  "+path+"  "+"HTTP/1.0"+"\n"+"Host: "+hostname+"\n"+"User-Agent: ODU-CS455/555"+"\n"+"";
		 }
		 
		 
		 
		 //System.out.println(httprequest);
		 
		 
		
		try {
			try{
				if(hostname.contains(":"))
				{
				int x=hostname.indexOf(":");
				String final_hostname=hostname.substring(0,x);
				//System.out.println(final_hostname);
				//System.out.println(port);
			//	socket = new Socket("localhost",3248 );
				socket = new Socket(final_hostname, port);}
				else{
					socket = new Socket(hostname,port );
				}
				}
			catch(Exception e)
			{
				System.out.println("Unknown source");
				System.exit(-1);
			}
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.println(httprequest);
			
            writer.flush();

            
			reader2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			
			if(path==""||path=="/")
			{
				filename="index.html";
			}
			else{
			int index = path.indexOf("/");
			int temp=0;
			while(index>=0)
			{
				//System.out.println(index);
				temp=index+1;
				index=path.indexOf("/",index+1);
			}
			filename = path.substring(temp,path.length());
			//System.out.println(filename);
			}
		
			
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filename)));
		
		
			int inByte;
			String TempString="";
			while((line = reader2.readLine()) != null) {
				//while(1){
				//line = reader2.readLine();
				//System.out.println("loop entered");
				
                if (line.isEmpty())
				{
					break; 
				}
				
				else
				{
                //System.out.println(line);
				TempString=TempString+"\n"+line;
				}
            }
			//System.out.println(TempString);
			try{
			code=Integer.parseInt(TempString.substring(10,13));
			System.out.println(code);
			}
			catch(Exception e)
			{}
			/*if(TempString.contains("Server"))
				{
					
					String serverText="\n";
					int serverIndex= TempString.indexOf("Server");
					//System.out.println(serverIndex);
					serverIndex=serverIndex+8;
					int dateindex=TempString.indexOf("Date");
					serverText=TempString.substring(serverIndex,dateindex-1);
					System.out.println(serverText);
					/*while(a)
					{
					
					serverText=TempString.substring(serverIndex,serverIndex+2);
					serverIndex=serverIndex+1;
					System.out.print(serverText);
					if(serverText=="")
						a=false;
					
				}}*/
			
				String[] sp=TempString.split("\n");
				
				//System.out.println(sp.length);
				for(int x=0;x<sp.length;x++)
				{
					//System.out.println(sp[x]);
					//System.out.print(x);
			    
				if(code>=200&&code<=299)
				{
				if(sp[x].contains("Server:"))
				{
					
					int serverIndex=sp[x].indexOf(":");
					String server=sp[x].substring(serverIndex+2,sp[x].length());
					System.out.println(server);
				}
				if(sp[x].contains("Last-Modified:"))
				{
					i=1;
					int lastIndex=sp[x].indexOf(":");
					String Last=sp[x].substring(lastIndex+2,sp[x].length());
					//System.out.println(Last);	
					//System.out.println(Length);
					LastModifiedGlobal=Last;
				}
				if(sp[x].contains("Content-Length:"))
				{
					j=1;
					int conIndex=sp[x].indexOf(":");
					//int ConnIndex=TempString.indexOf("Connection");
					String Length=sp[x].substring(conIndex+2,sp[x].length());
					LengthGlobal=Length;
					//System.out.println(Length);
				}
				
			}
			
			
			if(code>=300&&code<=399)
			{
				if(sp[x].contains("Location:"))
				{
					int LocIndex=sp[x].indexOf(":");
					String location=sp[x].substring(LocIndex+2,sp[x].length());
					System.out.println(location);
				}
			}
		}
				if(i==1&&j==1)
			{
				System.out.println(LastModifiedGlobal);
				System.out.println(LengthGlobal);
			}
			if(i==1&&j==0)
			{
				System.out.println(LastModifiedGlobal);
			}
			if(i==0&&j==1)
			{
				System.out.println(LengthGlobal);
			}
			System.out.println(TempString);
			
		//byte[] theByteArray = TempString.getBytes();
			//bos.write(theByteArray);
			
			while((inByte=reader2.read())!=-1)
			{
				
					//while((inByte=reader2.read())!=-1)
					bos.write(inByte);
				
			
            }
			bos.flush();
			
        }
catch(ConnectException e)
			{
				System.out.println("port not reached");
			}	
catch(FileNotFoundException e)
		{
			//System.out.println("File not found");
			int inByte;
			String TempString="";
			String line;
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File("home.html")));
			
			while((line = reader2.readLine()) != null) {
				
				
				
                if (line.isEmpty())
				{
					break; 
				}
				
				else
				{
                //System.out.println(line);
				TempString=TempString+"\n"+line;
			}
			}
			try{
			code=Integer.parseInt(TempString.substring(10,13));
			System.out.println(code);
			}
			catch(Exception f)
			{
				
			}
			/*if(TempString.contains("Server"))
				{
					
					String serverText="\n";
					int serverIndex= TempString.indexOf("Server");
					//System.out.println(serverIndex);
					serverIndex=serverIndex+8;
					int dateindex=TempString.indexOf("Date");
					serverText=TempString.substring(serverIndex,dateindex-1);
					System.out.println(serverText);
					/*while(a)
					{
					
					serverText=TempString.substring(serverIndex,serverIndex+2);
					serverIndex=serverIndex+1;
					System.out.print(serverText);
					if(serverText=="")
						a=false;
					
				}}*/
			
				String[] sp=TempString.split("\n");
				
				//System.out.println(sp.length);
				for(int x=0;x<sp.length;x++)
				{
					//System.out.println(sp[x]);
					//System.out.print(x);
			    
			if(code>=200&&code<=299)
			{
				if(sp[x].contains("Server:"))
				{
					
					int serverIndex=sp[x].indexOf(":");
					String server=sp[x].substring(serverIndex+2,sp[x].length());
					System.out.println(server);
				}
				if(sp[x].contains("Last-Modified:"))
				{
					i=1;
					int lastIndex=sp[x].indexOf(":");
					String Last=sp[x].substring(lastIndex+2,sp[x].length());
					//System.out.println(Last);	
					//System.out.println(Length);
					LastModifiedGlobal=Last;
				}
				if(sp[x].contains("Content-Length:"))
				{
					j=1;
					int conIndex=sp[x].indexOf(":");
					//int ConnIndex=TempString.indexOf("Connection");
					String Length=sp[x].substring(conIndex+2,sp[x].length());
					LengthGlobal=Length;
					//System.out.println(Length);
				}
				
			}
			
			
			if(code>=300&&code<=399)
			{
				if(sp[x].contains("Location:"))
				{
					int LocIndex=sp[x].indexOf(":");
					String location=sp[x].substring(LocIndex+2,sp[x].length());
					System.out.println(location);
				}
			}
		}
				if(i==1&&j==1)
			{
				System.out.println(LastModifiedGlobal);
				System.out.println(LengthGlobal);
			}
			if(i==1&&j==0)
			{
				System.out.println(LastModifiedGlobal);
			}
			if(i==0&&j==1)
			{
				System.out.println(LengthGlobal);
			}
			
			
			
			
			
			
			
			
			System.out.println(TempString);
			byte[] theByteArray = TempString.getBytes();
			//bos.write(theByteArray);
			
			while((inByte=reader2.read())!=-1)
			bos.write(inByte);
            
			bos.flush();
				
            
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
			finally {
            if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {} 
            if (writer != null) { writer.close(); }
            if (socket != null) try { socket.close(); } catch (IOException logOrIgnore) {} 
		}
	}
	
    }

}