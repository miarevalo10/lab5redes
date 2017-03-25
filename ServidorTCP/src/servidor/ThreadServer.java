package servidor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ThreadServer extends Thread{

	public final static String FILE_TO_SEND = "data/Lab5.pdf";
	// atributo socket
	private Socket s;

	private int id = 0;

	private int timeout = 100000;

	private BufferedReader br;



	private FileInputStream fis = null;
	private BufferedInputStream bis = null;
    private PrintWriter pw;


	private OutputStream os = null;


	public ThreadServer(Socket pS, int pId) throws IOException {

		s = pS;
		s.setSoTimeout(timeout);  
		id = pId;

		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();
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
		String fromUser="";
		String fromServer="";
		

		try{
			fromServer="HELLO it's me";
			System.out.println ("Server: " + fromServer); 
			pw.println(fromServer);
			fromUser = br.readLine();
			System.out.println("el cliente dijo: " + fromUser );
			if(fromUser.equals("HELLO")){
				System.out.println("antes de dar archivos");
				fromServer= darArchivos();
				pw.println(fromServer);
				fromUser = br.readLine();
				System.out.println("el cliente dijo2: " + fromUser );
				enviarArchivo(fromUser);
			}

			
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("finally");
			try {
				cerrarConn();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}


	}
	
	public void enviarArchivo(String nombre) throws IOException
	{
		String path = buscarArchivo(nombre);
		File myFile = new File (path);
		byte [] mybytearray  = new byte [(int)myFile.length()];
		try 
		{
			fis = new FileInputStream(myFile);
		}
		catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
		}
		bis = new BufferedInputStream(fis);
		bis.read(mybytearray,0,mybytearray.length);

		System.out.println("Sending " + path + "(" + mybytearray.length + " bytes)");
		os.write(mybytearray,0,mybytearray.length);
		os.flush();
		System.out.println("Done.");
	}
	
	public String buscarArchivo(String nombre)
	{
		String path= "";
		switch (nombre) {
		case "Lab5":
			path="data/Lab5.pdf";
			break;
		case "Lab4":
			path="data/Lab4.pdf";
			break;	
		}
		return path;
	}
	
	public void cerrarConn() throws IOException
	{
		if (os != null) os.close();
		if (bis != null) bis.close();
		if (s != null)	s.close();
		if (br != null)	br.close();
	}
	public String darArchivos()
	{
		return "Lab5 , Lab4";
	}




}


