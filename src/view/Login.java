package view;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login extends JFrame {

	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	Principal principal = new Principal();
	private JPanel contentPane;
	private JTextField txtLogin;
	private JPasswordField txtSenha;
	private JLabel lblStatus;
	private JLabel lblData;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 264);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(10, 30, 68, 33);
		contentPane.add(lblNewLabel);

		txtLogin = new JTextField();
		txtLogin.setBounds(77, 36, 255, 20);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(10, 74, 53, 25);
		contentPane.add(lblNewLabel_1);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(77, 74, 255, 20);
		contentPane.add(txtSenha);

		JButton btnAcessar = new JButton("Acessar");
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(316, 126, 89, 23);
		contentPane.add(btnAcessar);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 255, 0));
		panel.setForeground(new Color(128, 0, 64));
		panel.setBounds(-12, 160, 446, 79);
		contentPane.add(panel);
		panel.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/nuvemoff.png")));
		lblStatus.setBounds(377, 11, 46, 36);
		panel.add(lblStatus);

		lblData = new JLabel("New label");
		lblData.setBounds(29, 22, 276, 25);
		panel.add(lblData);
	}
	
	private void logar() {
		String capturaSenha = new String(txtSenha.getPassword());
		
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {

			
			String read = "select * from usuarios where login =? and senha = md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					
					System.out.println((rs.getString(5)));
					
					String perfil = rs.getString(5);
					if (perfil.equals("admin")) {
						principal.setVisible(true);
						principal.lblUsuario1.setText(rs.getString(2));
						principal.btnRelatorios1.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						principal.panelRodape.setBackground(Color.BLUE);
						this.dispose();
					} else {
						principal.setVisible(true);
						principal.lblUsuario1.setText(rs.getString(2));
						this.dispose();

					}

				} else {
					JOptionPane.showMessageDialog(null, "Usuario e/ou senha invalido(s)");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/nuvemoff/png")));
			} else {
				System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/nuvemon.png")));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	
	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));

	}
}
