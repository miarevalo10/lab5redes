package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfazPrincipal extends JFrame {

	private JTextField conexion;
	
	
	public InterfazPrincipal(){

		
		setTitle("Conexión TCP");
		getContentPane( ).setLayout( new BorderLayout( ) );
		getContentPane().setBackground( Color.WHITE);
		setSize( 800, 620 );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		
		JPanel p=new JPanel();
		
		conexion= new JTextField();
		p.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints( 0, 0, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 );
		p.add(new JLabel("Conexión"), gbc);
		
		
		p.add(conexion, BorderLayout.SOUTH);
		
		add(p,BorderLayout.NORTH);

	}

	public static void main(String[] args) {

		InterfazPrincipal interfaz = new InterfazPrincipal( );
		interfaz.setVisible( true );

	}

}
