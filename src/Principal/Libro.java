package Principal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.Conectar;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class Libro extends JFrame {

	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtTitulo;
	private JTextField txtEditorial;
	private JTextField txtArea;
	private JTable table;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnModificar;
	private JButton btnEliminar;

	
	DefaultTableModel modelo;
	private JLabel lblNewLabel_1;
	private JTextField txtBuscar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Libro frame = new Libro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Libro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Registro de libros");
		lblNewLabel.setBounds(191, 11, 107, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(31, 58, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblTitulo = new JLabel("Titulo");
		lblTitulo.setBounds(31, 87, 46, 14);
		contentPane.add(lblTitulo);
		
		JLabel lblEditorial = new JLabel("Editorial");
		lblEditorial.setBounds(31, 127, 46, 14);
		contentPane.add(lblEditorial);
		
		JLabel lblArea = new JLabel("Area");
		lblArea.setBounds(31, 158, 46, 14);
		contentPane.add(lblArea);
		
		txtId = new JTextField();
		txtId.setEnabled(false);
		txtId.setBounds(85, 55, 86, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		txtTitulo = new JTextField();
		txtTitulo.setBounds(85, 83, 164, 20);
		contentPane.add(txtTitulo);
		txtTitulo.setColumns(10);
		
		txtEditorial = new JTextField();
		txtEditorial.setBounds(87, 114, 164, 20);
		contentPane.add(txtEditorial);
		txtEditorial.setColumns(10);
		
		txtArea = new JTextField();
		txtArea.setBounds(87, 155, 150, 20);
		contentPane.add(txtArea);
		txtArea.setColumns(10);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(259, 54, 89, 23);
		contentPane.add(btnNuevo);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{	insertar();
			}
		});
		btnGuardar.setBounds(259, 87, 89, 23);
		contentPane.add(btnGuardar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{ modificar();
			}
		});
		btnModificar.setBounds(261, 123, 89, 23);
		contentPane.add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
					try
					{	eliminar();
					}
					catch (ArrayIndexOutOfBoundsException u)
					{
						JOptionPane.showMessageDialog(null,"No seleccionó nada");
					}
								
			
			
			}
		});
		btnEliminar.setBounds(259, 154, 89, 23);
		contentPane.add(btnEliminar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 229, 360, 178);
		contentPane.add(scrollPane);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(scrollPane, popupMenu);
		
		JMenuItem mntmEliminar = new JMenuItem("Eliminar");
		mntmEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{	try
				{
				eliminar();
				}
				catch (ArrayIndexOutOfBoundsException u)
				{
				JOptionPane.showMessageDialog(null,"No seleccionó nada");
				}
			}
		});
		popupMenu.add(mntmEliminar);
		
		JMenuItem mntmModificar = new JMenuItem("Modificar");
		mntmModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{	int fila = table.getSelectedRow();
				
				if(fila>=0)
				{	txtId.setText(table.getValueAt(fila, 0).toString());
					txtTitulo.setText(table.getValueAt(fila, 1).toString());
					txtEditorial.setText(table.getValueAt(fila, 2).toString());
					txtArea.setText(table.getValueAt(fila, 3).toString());	
				}
				else
					JOptionPane.showMessageDialog(null, "No seleccion nada");
				
			}
		});
		popupMenu.add(mntmModificar);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{ setVisible(false);
			}
		});
		btnSalir.setBounds(259, 188, 89, 23);
		contentPane.add(btnSalir);
		
		lblNewLabel_1 = new JLabel("Buscar");
		lblNewLabel_1.setBounds(31, 204, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		txtBuscar = new JTextField();
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) 
			{
				cargar(txtBuscar.getText()) ;
			}
		});
		txtBuscar.setBounds(85, 198, 86, 20);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		///***************//
		table.setComponentPopupMenu(popupMenu);
		//***************//
		cargar(""); 
	}
	
	public void insertar() 
	{	try 
		{	Conectar cc= new Conectar();
			Connection cn= cc.conexion();
			String sql=null;
			String tit,edit, are;
			
			tit= txtTitulo.getText();
			edit= txtEditorial.getText();
			are= txtArea.getText();
						
			sql= "INSERT INTO libro (titulo, editorial, area) values (?,?,?)";
			
			PreparedStatement pst= cn.prepareStatement(sql);
			pst.setString(1, tit);
			pst.setString(2, edit);
			pst.setString(3, are);
			
			
			int n= pst.executeUpdate();
			if(n>0)
			{	JOptionPane.showMessageDialog(null, "Registro guardado con exito");									
			}
			
			pst.close();
			cn.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	
	cargar("");
	}
	public void cargar( String valor) 
	{	String [] titulos= {"Codigo", "Titulos", "Editorial", "Area"};
		String [] filas= new String[4];
		String sql= "Select *from libro where titulo like '%" + valor + "%'";
		
		modelo= new DefaultTableModel (null, titulos);
		
		Conectar cc= new Conectar();
		
		Connection cn;
		
			
		
		Statement st;
		try 
		{	cn = cc.conexion();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next())
			{	
				filas[0] = rs.getString("idlibro");
				filas[1] = rs.getString("titulo");
				filas[2] = rs.getString("editorial");
				filas[3] = rs.getString("area");
				
				modelo.addRow(filas);
			}
			table.setModel(modelo);
			st.close();
			cn.close();
		} 
		catch (SQLException e) 
		{	e.printStackTrace();
		}
				
	}
	
	public void modificar()
	{	String sql= "UPDATE LIBRO SET titulo='"+ txtTitulo.getText()
					 + "',editorial='"+txtEditorial.getText()
					 + "',area='" + txtArea.getText() + "' where idLibro='"
					 + txtId.getText() + "'";
	
		try
		{	Conectar cc = new Conectar();
			Connection cn = cc.conexion();
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Registro actualizado");
			cargar("");
			pst.close();
			cn.close();
		}
		catch (SQLException e) 
		{	e.printStackTrace();
		}
		
	}
	
	public void eliminar() 
	{
		int fila = table.getSelectedRow();
		int cod = Integer.parseInt(table.getValueAt(fila, 0).toString());
		String sql = "DELETE from libro where idLibro='"
						+ cod + "'";						
		try
		{
			Conectar cc= new Conectar();
			Connection cn=cc.conexion();
			PreparedStatement pst = cn.prepareStatement(sql);
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null,"Registro eliminado");
			cargar("");
			pst.close();
			cn.close();
		}
		
		catch (SQLException e) 
		{	e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
