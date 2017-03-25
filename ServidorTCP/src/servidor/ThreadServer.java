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


	private OutputStream os = null;


	public ThreadServer(Socket pS, int pId) throws IOException {

		s = pS;
		s.setSoTimeout(timeout);  
		id = pId;

		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();

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
			System.out.println ("Server: " + inputLine); 

			File myFile = new File (FILE_TO_SEND);
			byte [] mybytearray  = new byte [(int)myFile.length()];
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);

			System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			System.out.println("Done.");
		}
		catch (FileNotFoundException e) {
			System.out.println("Archivo inexistente");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("finally");
			try {
				if (os != null) os.close();
				if (bis != null) bis.close();
				if (s != null)	s.close();


				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}


	}




}


