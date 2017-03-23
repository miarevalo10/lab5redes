package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Servidor extends Thread
{

	public static final int PUERTO = 8081;
	public static final int MAX_CONN = 100;
	public static final int TIMEOUT = 10000;
	
	private static boolean terminado = false;



	private Socket clientSocket;

	private BufferedReader br;

	private PrintWriter pw;

	private static int conexiones = 0;

	public Servidor(Socket s)
	{
		clientSocket = s;
		conexiones++;
		try {
			br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			pw = new PrintWriter(clientSocket.getOutputStream(), true);

		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		start();

	}


	public static void main(String[] args) throws IOException {
		ServerSocket ss = null;

		try 
		{
			ss = new ServerSocket(PUERTO);
			try { 
				while (conexiones < MAX_CONN && !terminado)
				{
					ss.setSoTimeout(TIMEOUT);
					System.out.println ("Esperando la conexion");
					try
					{
						new Servidor (ss.accept()); 
					}
					catch (SocketTimeoutException ste)
					{
						System.out.println ("Timeout");
					}
				}
			}
			catch (IOException e) 
			{ 
				System.err.println("Falló conexión"); 
				System.exit(1); 
			} 
		}
		catch (IOException e)
		{
			System.err.println("No pudo crear socket en el puerto: " + PUERTO);
			System.exit(-1);
		}

		ss.close();
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
		clientSocket.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
