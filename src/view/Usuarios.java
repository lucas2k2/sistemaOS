package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import model.DAO;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;

public class Usuarios extends JDialog {
	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPesquisar;
	private JPasswordField txtSenha;
	@SuppressWarnings("rawtypes")
	private JList listaUsers;
	private JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;
	private JLabel lblEntrar;
	private JLabel lblNewLabel_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/9990432_snowman_doll_people_holiday_ice_icon.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
				
			}
		});
		setTitle("Entrada");
		setModal(true);
		setBounds(350, 150, 450, 357);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		setBounds(350, 150, 800, 600);
		getContentPane().add(scrollPane);
		
				listaUsers = new JList();
				listaUsers.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarUsuarioLista();
					}
				});
				scrollPane.setViewportView(listaUsers);
				listaUsers.setBorder(null);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblNewLabel.setBounds(226, 116, 46, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblNewLabel_1.setBounds(216, 233, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblNewLabel_2.setBounds(216, 178, 46, 16);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 13));
		lblNewLabel_3.setBounds(216, 285, 46, 14);
		getContentPane().add(lblNewLabel_3);

		txtID = new JTextField();
		txtID.setBorder(new LineBorder(new Color(0, 0, 255), 2, true));
		txtID.setEditable(false);
		txtID.setBounds(263, 114, 86, 20);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBorder(new LineBorder(new Color(0, 0, 255), 2, true));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();

			}
		});
		txtNome.setBounds(272, 231, 220, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		txtLogin = new JTextField();
		txtLogin.setBorder(new LineBorder(new Color(0, 0, 255), 2, true));
		txtLogin.setBounds(272, 177, 220, 20);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(15));

		btnPesquisar = new JButton("");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();

			}
		});
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setBorder(null);
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/pesquisar.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setBounds(502, 116, 77, 50);
		getContentPane().add(btnPesquisar);

		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar Campos");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBorder(null);
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/vasoura.png")));
		btnLimpar.setBounds(519, 273, 60, 60);
		getContentPane().add(btnLimpar);
		getRootPane().setDefaultButton(btnPesquisar);

		btnAdicionar = new JButton("");
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBorder(null);
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/useradd.png")));
		btnAdicionar.setBounds(228, 420, 60, 60);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setToolTipText("Editar");
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					editarUsuario();
				} else {
					editarUsuarioExcetoSenha();
				}

			}
		});
		btnEditar.setBorder(null);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/editnv.png")));
		btnEditar.setBounds(362, 420, 60, 60);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnExcluir.setBorder(null);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/deletar.png")));
		btnExcluir.setBounds(502, 420, 60, 60);
		getContentPane().add(btnExcluir);

		txtSenha = new JPasswordField();
		txtSenha.setBorder(new LineBorder(new Color(0, 0, 255)));
		txtSenha.setBounds(272, 283, 220, 20);
		getContentPane().add(txtSenha);
		
		cboPerfil = new JComboBox();
		cboPerfil.setBorder(new LineBorder(new Color(0, 0, 255), 2, true));
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "admin", "user"}));
		cboPerfil.setBounds(359, 113, 95, 22);
		getContentPane().add(cboPerfil);
		
		JLabel lblNewLabel_4 = new JLabel("Perfil");
		lblNewLabel_4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(394, 89, 60, 17);
		getContentPane().add(lblNewLabel_4);
		
		chckSenha = new JCheckBox("Alterar senha");
		chckSenha.setEnabled(false);
		chckSenha.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				
				if (chckSenha.isSelected()) {
					txtSenha.setText(null);
					txtSenha.requestDefaultFocus();
					txtSenha.setBackground(Color.LIGHT_GRAY);
				} else {
					txtSenha.setBackground(Color.white);
				}
				
				
			}
		});
		chckSenha.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		chckSenha.setBounds(337, 310, 117, 23);
		getContentPane().add(chckSenha);
		
		lblEntrar = new JLabel("ENTRAR");
		lblEntrar.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblEntrar.setFont(new Font("Yu Gothic UI Semibold", Font.BOLD, 55));
		lblEntrar.setBounds(234, 17, 220, 61);
		getContentPane().add(lblEntrar);
		
		lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setForeground(new Color(0, 128, 255));
		lblNewLabel_5.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(0, 0, 255), new Color(0, 0, 255), new Color(0, 0, 255), new Color(0, 0, 255)));
		lblNewLabel_5.setBackground(Color.BLUE);
		lblNewLabel_5.setBounds(0, 488, 784, 73);
		getContentPane().add(lblNewLabel_5);

	}

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		txtSenha.setText(null);
		btnAdicionar.setEnabled(false);
		txtSenha.setBackground(Color.white);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		scrollPane.setVisible(false);
		cboPerfil.setSelectedItem("");
		chckSenha.setSelected(false);
		chckSenha.setEnabled(false);
		
	}

	/**
	 * Método para buscar um contato pelo nome
	 */
	private void buscar() {
		String read = "select * from usuarios where login = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3));
				txtSenha.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);
				chckSenha.setEnabled(true);

			} else {
				JOptionPane.showMessageDialog(null, "Usuário inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método para adicionar um novo contato
	 */
	private void adicionar() {
		String capturaSenha = new String(txtSenha.getPassword());
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login do usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha do usuário");
			txtSenha.requestFocus();
		} else {
			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Usuário Adicionado");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	/**
	 * Método para editar um contato (ATENÇÃO!!! Usar o ID)
	 */
	private void editarUsuario() {
		String capturaSenha = new String(txtSenha.getPassword());
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuário");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Digite a senha do usuário");
			txtSenha.requestFocus();
		} else {
			String update = "update usuarios set nome=?,login=?,senha=md5(?),perfil=? where id =?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso.");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
	
	private void editarUsuarioExcetoSenha() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuário");
			txtLogin.requestFocus();
		
		} else {

			String update2 = "update usuarios set nome=?,login=?,perfil=? where id =?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do usuário editado com sucesso.");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Método usado para excluir um contato
	 */
	private void excluirUsuario() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Usuário?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where id=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Usuário excluído");
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public class Validador extends PlainDocument {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int limite;

		public Validador(int limite) {
			super();
			this.limite = limite;
		}

		public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {
			if ((getLength() + str.length()) <= limite) {
				super.insertString(ofs, str, a);
			}
		}

	}

	/**
	 * Método usado para listar o nome dos usuários na lista
	 */
	@SuppressWarnings("unchecked")
	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaUsers.setModel(modelo);
		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
			}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Método que busca o usuário selecionado na lista
	 */
	private void buscarUsuarioLista() {
		int linha = listaUsers.getSelectedIndex();
		if ( linha >= 0) {
		String readBuscaLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome limit " + (linha) + ", 1";
		 try {
			con = dao.conectar();
			pst = con.prepareStatement(readBuscaLista);
			rs = pst.executeQuery();
			if (rs.next()) {
				scrollPane.setVisible(false);
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3));
				txtSenha.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));
				
			} else {
				JOptionPane.showMessageDialog(null,"Usuário Inexistente");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
			
		} else {
			scrollPane.setVisible(false);
			
		}
	}
}
