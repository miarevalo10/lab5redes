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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfazPrincipal extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField conexion;
	
	
	public InterfazPrincipal(){

		
		setTitle("Conexión TCP");
		getContentPane( ).setLayout( new BorderLayout( ) );
		getContentPane().setBackground( Color.WHITE);
		setSize( 800, 620 );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		
		JPanel con=new JPanel();
		con.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints( 0, 0, 1, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 );
		con.add(new JLabel("Conexion:"),gbc);

		conexion= new JTextField();
		conexion.setEditable(false);

		gbc = new GridBagConstraints( 1, 0, 5, 1, 20, 0, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
		con.add(conexion,gbc);
		
		JButton btnCon= new JButton("conectar");
		btnCon.setActionCommand("conectar");
		btnCon.addActionListener(this);
		
		gbc = new GridBagConstraints( 6, 0, 5, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
		con.add(btnCon,gbc);
		
		add(con,BorderLayout.NORTH);

		JPanel archivos=new JPanel();
		archivos.setLayout(new GridLayout(1,2));
		
		add(archivos,BorderLayout.CENTER);
	}

	public static void main(String[] args) {

		InterfazPrincipal interfaz = new InterfazPrincipal( );
		interfaz.setVisible( true );

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
