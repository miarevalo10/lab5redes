package cliente;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

	public final static int SOCKET_PORT = 8081;      
	public final static String SERVER = "127.0.0.1";  
//	public final static String 	FILE_TO_RECEIVED = "data/modelado.pdf";  
//
//	public final static int FILE_SIZE = 1024*50000; 

	public static void main (String [] args ) throws IOException {
		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		Socket sock = null;
		String fromServer = "";
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromUser = "";

		try {
			sock = new Socket(SERVER, SOCKET_PORT);
			System.out.println("Connecting...");
			PrintWriter pw = new PrintWriter(sock.getOutputStream(), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			fromServer = br.readLine();
			System.out.println("El servidor dijo " + fromServer);


			if(fromServer.equals("HELLO it's me"));
			{
				pw.println("HELLO");
				fromServer = br.readLine();
				System.out.println("El servidor dijo " + fromServer);
				fromUser = stdIn.readLine();
				System.out.println(fromUser);
				pw.println(fromUser);
			}

			// receive file
			//Lee el tamaño del archivo enviado por el servidor y crea el array de bytes con un tamaño
			//mayor para poder guardarlo
			int filesize = Integer.parseInt(br.readLine());
			byte [] mybytearray  = new byte [filesize + 1024*1024];
			System.out.println("tamaño " + filesize);
			InputStream is = sock.getInputStream();
			String path = "data/" + fromUser + ".pdf";
			fos = new FileOutputStream(path);
			bos = new BufferedOutputStream(fos);

//			byte[] bytes = new byte[ 2048*16];
			int bytes =2048*16;

			bytesRead = is.read(mybytearray, current, mybytearray.length);
			current = bytesRead;

			while (bytesRead>-1) {
				//Lee los bytes enviados y los va guardando en mybytearray, con un offset de current y
				//de a paquetes de tamaño bytes
				bytesRead = is.read(mybytearray, current, bytes);
				if(bytesRead >= 0) current += bytesRead;
				System.out.println("Recibiendo " + current/1024 + "KB de " + filesize/1024 + "KB");
			}
			
			//Escribe el array de bytes en el path definido en fileoutputstream
			bos.write(mybytearray, 0 , current);
			bos.flush();
			System.out.println("File " + path
					+ " downloaded (" + current + " bytes read)");
		}
		finally {
			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (sock != null) sock.close();
		}
	}

}
