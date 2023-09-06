	package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;

public class Principal extends JFrame {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton lblStatus;
	private JLabel lblDate;
	public JLabel lblUsuario1;
	public JLabel lblUsuario;
	public JButton btnUsuarios;
	public JButton btnRelatorios1;
	public JPanel panelRodape;
	
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
				
			}
		});
		setTitle("EletroGelado");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/9990432_snowman_doll_people_holiday_ice_icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnUsuarios = new JButton("");
		btnUsuarios.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/309037_remove_user_users_icon (1).png")));
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Usuarios Usuarios = new Usuarios();
				Usuarios.setVisible(true);

			}
		});
		btnUsuarios.setBounds(84, 106, 147, 125);
		contentPane.add(btnUsuarios);

		JButton btnSobre = new JButton("");
		btnSobre.setContentAreaFilled(false);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre Sobre = new Sobre();
				Sobre.setVisible(true);
			}
		});
		btnSobre.setBorderPainted(false);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/infooo.png")));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setBounds(699, 0, 64, 64);
		contentPane.add(btnSobre);
		
		panelRodape = new JPanel();
		panelRodape.setBackground(new Color(0, 0, 255));
		panelRodape.setBounds(0, 498, 826, 73);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
				
				lblUsuario1 = new JLabel("");
				lblUsuario1.setForeground(new Color(255, 255, 255));
				lblUsuario1.setBounds(333, 23, 85, 14);
				panelRodape.add(lblUsuario1);
		
				lblStatus = new JButton("");
				lblStatus.setBounds(689, 10, 64, 49);
				panelRodape.add(lblStatus);
				lblStatus.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				lblStatus.setContentAreaFilled(false);
				lblStatus.setBorder(null);
				lblStatus.setBorderPainted(false);
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/off.png")));
				lblStatus.setToolTipText("Status off\r\n");
				
				lblDate = new JLabel("New label");
				lblDate.setBounds(10, 10, 221, 35);
				panelRodape.add(lblDate);
				lblDate.setForeground(new Color(255, 255, 255));
				lblDate.setFont(new Font("Tahoma", Font.PLAIN, 13));
				
				JLabel asdasd = new JLabel("Usuario:");
				asdasd.setForeground(new Color(255, 255, 255));
				asdasd.setFont(new Font("Tahoma", Font.BOLD, 12));
				asdasd.setBounds(279, 11, 75, 34);
				panelRodape.add(asdasd);
		
		JLabel lblNewLabel = new JLabel("ELETRO");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 35));
		lblNewLabel.setBounds(239, 54, 174, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("GELADO\r\n");
		lblNewLabel_1.setForeground(new Color(0, 0, 255));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 35));
		lblNewLabel_1.setBounds(376, 54, 141, 31);
		contentPane.add(lblNewLabel_1);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/61816_business_buyers_clients_users_icon.png")));
		btnClientes.setBounds(316, 277, 133, 130);
		contentPane.add(btnClientes);
		
		JButton btnServiços = new JButton("");
		btnServiços.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnServiços.setIcon(new ImageIcon(Principal.class.getResource("/img/9070393_air_conditioning_icon.png")));
		btnServiços.setBounds(84, 277, 147, 130);
		contentPane.add(btnServiços);
		
		btnRelatorios1 = new JButton("");
		btnRelatorios1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios1.setIcon(new ImageIcon(Principal.class.getResource("/img/1622837_analytics_docs_documents_graph_pdf_icon.png")));
		btnRelatorios1.setBounds(316, 106, 133, 125);
		contentPane.add(btnRelatorios1);
		
		JButton btnProduto = new JButton("");
		btnProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setVisible(true);
						
			}
		});
		btnProduto.setIcon(new ImageIcon(Principal.class.getResource("/img/clientesd.png")));
		btnProduto.setBounds(538, 106, 133, 125);
		contentPane.add(btnProduto);
		
		JButton btnFornecedores = new JButton("");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});
		btnFornecedores.setIcon(new ImageIcon(Principal.class.getResource("/img/package.png")));
		btnFornecedores.setBounds(538, 282, 133, 125);
		contentPane.add(btnFornecedores);
		
		JLabel lblNewLabel_2 = new JLabel("USUARIOS");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(114, 229, 147, 24);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("SERVIÇOS");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(114, 402, 147, 24);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("CLIENTES");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(340, 399, 133, 31);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("RELATORIOS");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5.setBounds(326, 223, 133, 37);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("PRODUTOS");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_6.setBounds(560, 406, 133, 24);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("FORNECEDORES");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_7.setBounds(538, 226, 133, 31);
		contentPane.add(lblNewLabel_7);
	}

	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/off/png")));
			} else {
				System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/on.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);}
		}
	    private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblDate.setText(formatador.format(data));
	    
	}
}
