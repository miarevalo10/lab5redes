package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import cliente.Cliente;

public class InterfazPrincipal extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel temporal2;
	private JTextField conexion;
	private JComboBox archivos;

	private Cliente mundo;

	private JButton iniciar;
	private JButton detener;
	private JButton btnConexion;


	public InterfazPrincipal(){

		mundo = new Cliente();

		setTitle("Conexión TCP");
		getContentPane( ).setLayout( new BorderLayout( ) );
		setSize( 650, 200 );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );


		JPanel con=new JPanel();
		con.setLayout(new GridBagLayout());
		con.setBackground( Color.WHITE);

		GridBagConstraints gbc = new GridBagConstraints( 0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 );
		con.add(new JLabel("Conexion:"),gbc);

		conexion= new JTextField(Cliente.SERVER + ":" + Cliente.SOCKET_PORT );
		conexion.setBackground(Color.WHITE);
		conexion.setBorder(new LineBorder(Color.RED));
		conexion.setEditable(false);

		gbc = new GridBagConstraints( 1, 0, 5, 1, 20, 0, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
		con.add(conexion,gbc);

		btnConexion= new JButton("Conectar");
		btnConexion.setActionCommand("Conectar");
		btnConexion.addActionListener(this);

		gbc = new GridBagConstraints( 6, 0, 5, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
		con.add(btnConexion,gbc);

		add(con,BorderLayout.NORTH);

		temporal2 = new JPanel();
		temporal2.setLayout(new BorderLayout());
		temporal2.setBorder(new TitledBorder(mundo.darEstado()));
		temporal2.setBackground( Color.WHITE);

		JPanel arch=new JPanel();
		arch.setBackground( Color.WHITE);
		arch.setLayout(new GridLayout(3,2));

		arch.add(new JLabel(""));

		archivos= new JComboBox();
		archivos.addActionListener(this);

		archivos.setEnabled(false);
		archivos.setBackground(Color.WHITE);
		arch.add(archivos);

		for(int i =0;i<4;i++){
			arch.add(new JLabel(""));
		}

		temporal2.add(arch,BorderLayout.CENTER);

		JPanel botones = new JPanel();
		GridLayout l = new GridLayout(1,4);
		l.setHgap(20);
		botones.setLayout(l);
		botones.setBackground( Color.WHITE);

		iniciar = new JButton("Iniciar");
		iniciar.setActionCommand("Iniciar");
		iniciar.addActionListener(this);

		detener = new JButton("Detener");
		detener.setActionCommand("Detener");
		detener.addActionListener(this);

		botones.add(new JLabel(""));
		botones.add(iniciar);
		botones.add(detener);
		botones.add(new JLabel(""));

		temporal2.add(botones, BorderLayout.SOUTH);


		add(temporal2,BorderLayout.CENTER);
	}

	public void darOpciones(){

	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("Conectar")){
			if(mundo.darEstado().equals("desconectado")){
				try{
					mundo.conectar();
					conexion.setBorder(new LineBorder(Color.GREEN));
					temporal2.setBorder(new TitledBorder(mundo.darEstado()));
					btnConexion.setText("Desconectar");

					for(int i=0; i<mundo.darOpciones().length;i++){
						archivos.addItem(mundo.darOpciones()[i]);
					}
					archivos.setEnabled(true);

				}
				catch(Exception e){
					JOptionPane.showMessageDialog( null, "Se detecto un error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
				}
			}
			else if(mundo.darEstado().equals("conectado")){
				try{
					mundo.desconectar();
					conexion.setBorder(new LineBorder(Color.RED));
					temporal2.setBorder(new TitledBorder(mundo.darEstado()));
					btnConexion.setText("Conectar");;

					archivos.setEnabled(false);
				}
				catch(Exception e){		
				}
			}	
		}
		else if(event.getActionCommand().equals("Iniciar")){
			try{
				mundo.escogerArchivo((String) archivos.getSelectedItem());
				mundo.iniciarDescarga();
			}
			catch(Exception e){
				JOptionPane.showMessageDialog( null, "Se detecto un error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
			}
		}
		else if(event.getActionCommand().equals("Detener")){
			mundo.detenerDescarga();
		}	
	}

	public static void main(String[] args) {

		InterfazPrincipal interfaz = new InterfazPrincipal( );
		interfaz.setVisible( true );

	}
}
