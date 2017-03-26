package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

public class Servidor 
{

	public static final int PUERTO = 8081;
	public static final int MAX_CONN = 3;
	public static final int TIMEOUT = 10000;
	
	private static boolean terminado = false;
	
	private static int conexiones = 0;
	
	private static ServerSocket ss;

	public static void main(String[] args) throws IOException {
		try 
		{
			ss = new ServerSocket(PUERTO);
			try { 
				while (conexiones < MAX_CONN && !terminado)
				{
					System.out.println ("Esperando la conexion");
					try
					{
						conexiones++;
						ThreadServer ts = new ThreadServer (ss.accept(), conexiones); 
						ts.start();
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
		System.out.println("aquí 1");
		ss.close();
	}



}
