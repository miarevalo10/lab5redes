package cliente;

import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

	public final static int SOCKET_PORT = 8081;      
	public final static String SERVER = "127.0.0.1";  

	private Socket socket;

	private String fromServer;
	private String archivo;
	private String estado;

	private FileOutputStream fos;
	private BufferedOutputStream bos;

	private PrintWriter pw;
	private BufferedReader br;

	private int bytesRead;
	private int current = 0;

	private String[] opciones;

	public Cliente(){

		fromServer = "";
		archivo = "";
		estado = "desconectado";
	}

	public String darEstado() {
		return estado;
	}

	public void conectar() throws Exception{

		socket = new Socket(SERVER, SOCKET_PORT);
		System.out.println("Connecting...");
		pw = new PrintWriter(socket.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		fromServer = br.readLine();
		System.out.println("El servidor dijo " + fromServer);

		if(fromServer.equals("HELLO it's me"));
		{
			pw.println("HELLO");

			estado="conectado";
		}		

		fromServer = br.readLine();
		System.out.println("El servidor dijo " + fromServer);

		String[] temporal = fromServer.split(": ");
		opciones = temporal[1].split(", ");

	}
	public String[] darOpciones(){
		return opciones;
	}

	public void escogerArchivo(String nombreArchivo){
		archivo=nombreArchivo;
	}
	public void iniciarDescarga() throws Exception{
		
		System.out.println(archivo);
		pw.println(archivo);
		
		// receive file
		//Lee el tamaño del archivo enviado or el servidor y crea el array de bytes con un tamaño
		//mayor para poder guardarlo
		int filesize = Integer.parseInt(br.readLine());
		byte [] mybytearray  = new byte [filesize + 1024*1024];
		System.out.println("tamaño " + filesize);
		InputStream is = socket.getInputStream();
		String path = "data/" + archivo + ".pdf";
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
		
		mostrarArchivo();
	}

	public void detenerDescarga(){

	}

	public void mostrarArchivo() throws Exception{
		File file = new File("data/" + archivo + ".pdf");
		Desktop.getDesktop().open(file);
	}

	public void desconectar()throws IOException{
		if (fos != null) fos.close();
		if (bos != null) bos.close();
		if (socket != null) socket.close();
	}


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
