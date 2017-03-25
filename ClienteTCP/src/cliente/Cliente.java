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

	public final static int SOCKET_PORT = 8081;      // you may change this
	public final static String SERVER = "127.0.0.1";  // localhost
	public final static String
	FILE_TO_RECEIVED = "data/ejempl9.pdf";  // you may change this, I give a
	// different name because i don't want to
	// overwrite the one used by server...

	public final static int FILE_SIZE = 6022386; // file size temporary hard coded
	// should bigger than the file to be downloaded

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
			byte [] mybytearray  = new byte [FILE_SIZE];
			InputStream is = sock.getInputStream();
			fos = new FileOutputStream(FILE_TO_RECEIVED);
			bos = new BufferedOutputStream(fos);

			int bytes = 2048*16;

			bytesRead = is.read(mybytearray, current, mybytearray.length);

			current = bytesRead;

			while (bytesRead>-1) {
				bytesRead = is.read(mybytearray, current, bytes);
				if(bytesRead >= 0) current += bytesRead;
				System.out.println("Recibiendo " + current + "KB");
			}
			//          System.out.println("current" + current);

			bos.write(mybytearray, 0 , current);
			bos.flush();
			System.out.println("File " + FILE_TO_RECEIVED
					+ " downloaded (" + current + " bytes read)");
		}
		finally {
			if (fos != null) fos.close();
			if (bos != null) bos.close();
			if (sock != null) sock.close();
		}
	}

}
