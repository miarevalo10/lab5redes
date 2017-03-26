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

	private int timeout = 1000*60;

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
				
				//Tamaño del buffer
				s.setReceiveBufferSize(2048);

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
		System.out.println(myFile.length());
		byte [] mybytearray  = new byte [(int)myFile.length()];
		int tamaño = mybytearray.length;
		pw.println(tamaño);
		System.out.println(tamaño);
		
		//Define el tamaño de los mensajes
		byte[] bytes = new byte[ 2048*16];
		
		int current=0;

		try 
		{
			fis = new FileInputStream(myFile);
		}
		
		catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
		}

		int bytesRead=fis.read(bytes);

		while (bytesRead  > -1) {
			os.write(bytes, 0, bytesRead);
			current += bytesRead;
			System.out.println("Sending " + nombre +" "+current/1024+" KB of "+ tamaño/1024 + "KB"  );
			bytesRead=fis.read(bytes);
		}
		os.flush();

		System.out.println("Sending " + path + "(" + mybytearray.length + " bytes)");
		System.out.println("Done.");
	}

	public String buscarArchivo(String nombre)
	{
		String path= "";
		switch (nombre) {
		case "libro8MB":
			path="data/libro8MB.pdf";
			break;
		case "fisica25MB":
			path="data/fisica25MB.pdf";
			break;
		case "redes4MB":
			path="data/redes4MB.pdf";
			break;
		case "infra20MB":
			path="data/infra20MB.pdf";
			break;
		case "vecto50MB":
			path="data/vecto50MB.pdf";
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
		return "Archivos disponibles: libro8MB, fisica25MB, redes4MB, infra20MB, vecto50MB";
	}




}


