package Principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

import dao.Conectar;
import VO.usuarioVO;

import javax.swing.JComboBox;

public class Logeo extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtContraseña;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Logeo frame = new Logeo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Logeo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setBounds(176, 33, 46, 14);
		contentPane.add(lblLogin);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(50, 81, 46, 14);
		contentPane.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(120, 78, 102, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(50, 125, 46, 14);
		contentPane.add(lblPassword);
		
		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(120, 122, 112, 20);
		contentPane.add(txtContraseña);
		
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	logino();
			}
		});
		btnIngresar.setBounds(250, 77, 89, 23);
		contentPane.add(btnIngresar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(242, 121, 89, 23);
		contentPane.add(btnSalir);
		
		comboBox = new JComboBox();
		comboBox.setBounds(50, 169, 182, 43);
		contentPane.add(comboBox);
		
		comboBox.addItem("Usuarios registrados");
		cargarUusuarios();
	}
		
	public void logino()
	{	String usu= txtUsuario.getText();
		String pas= txtContraseña.getText();
		String sql = "Select *from usuario where usu='" 
					+ usu + "' and pass='" + pas + "'";
		Conectar cc = new Conectar();
		try
		{	Connection cn = cc.conexion();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			if (rs!=null)
			{	if(rs.next())
				{	switch (rs.getString("tipo"))
					{
					case "administrador" : dispose();
											Administrador ad = new Administrador();
											ad.setVisible(true);
											break;
					case "usuario" : dispose();
									 Usuarios u = new Usuarios();
									 u.setVisible(true);
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Usuario no registrado");				
			}
			st.close();
			cn.close();
			
		}
		catch (SQLException e) 
		{ e.printStackTrace();
		}			
	}
	
	private void cargarUusuarios()
	{	Conectar cc= new Conectar();
		String sql ="Select *from usuario";
		try 
		{	Connection cn = cc.conexion();
			PreparedStatement pst = cn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			
			while (rs.next())
			{	usuarioVO u = new usuarioVO();
				u.setUs(rs.getString("usu"));
				u.setNombresC(rs.getString("nombres"));
				comboBox.addItem(u);
			}
		} 
		catch (SQLException e) {
				e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
