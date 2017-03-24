package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ThreadServer extends Thread{

	// atributo socket
	private Socket s;

	// defina un atributo identificador local de tipo int
	private int id = 0;
	
	private double timeout = 1.5;
	
	private BufferedReader br;

	private PrintWriter pw;
	
	private boolean terminado;
		
	public ThreadServer(Socket pS, int pId) throws IOException {
		// asigne el valor a los atributos correspondientes
		s = pS;
	    s.setSoTimeout(10000);  
	    id = pId;
	    
	    try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			pw = new PrintWriter(s.getOutputStream(), true);
			
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		start();


	}
	
	public void run()
	{
		System.out.println("Comunicación iniciada");
//		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromUser="";
		String fromServidor="";
		String inputLine="";
		try{
		while ((fromUser=br.readLine() )!= null) 
		{ 
			System.out.println ("Server: " + inputLine); 

			if (inputLine.equals("?")) 
				inputLine = new String ("\"Bye.\" ends Client, " +
						"\"End Server.\" ends Server");

			pw.println(inputLine); 

			if (inputLine.equals("Bye.")) 
				break; 

			if (inputLine.equals("End Server.")) 
				terminado = true; 
		} 

		pw.close(); 
		
		br.close(); 
		s.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}


}


