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
	private ServerSocket serverSocket;

	// defina un atributo identificador local de tipo int
	private int id = 0;
	
	private double timeout = 1.5;
	
	public ThreadServer() throws IOException {
		// asigne el valor a los atributos correspondientes
		serverSocket = new ServerSocket(Servidor.PUERTO);
	    serverSocket.setSoTimeout(10000);    
	    
	    serverSocket.bind(InetSocketAddress.createUnresolved("java2s.com", 8080), 1000);
	}

	public void run() {

		while (true) {
		      try {
		        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
		        Socket client = serverSocket.accept();

		        System.out.println("Just connected to " + client.getRemoteSocketAddress());
		        client.close();
		      } catch (SocketTimeoutException s) {
		        System.out.println("Socket timed out!");
		        break;
		      } catch (IOException e) {
		        e.printStackTrace();
		        break;
		      }
		    }
	}
	
	public static void main(String[] args) {
	    try {
	      Thread t = new ThreadServer() ;
	      t.start();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }
}


