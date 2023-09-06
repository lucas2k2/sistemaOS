package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Clientes extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtFone;
	private JTextField txtCpf;
	private JTextField txtEndereco;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnLimparCampos;
	private JButton btnExcluir;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtCidade;
	private JTextField txtCep;
	private JTextField txtBairro;
	private JComboBox cboUF;
	private JButton btnBuscarCEP;
	private JScrollPane scrollPane;
	private JList listUsers;

	public static void main(String[] args) {
		try {
			Clientes dialog = new Clientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Clientes() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/Clientes.png")));
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(189, 100, 202, 50);
		contentPanel.add(scrollPane);

		listUsers = new JList();
		scrollPane.setViewportView(listUsers);
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
				listUsers.setEnabled(true);
			}
		});

		JLabel lblNewLabel = new JLabel("idCliente:");
		lblNewLabel.setBounds(97, 31, 70, 25);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel);

		txtID = new JTextField();
		txtID.setBounds(162, 35, 41, 20);
		txtID.setEnabled(false);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Nome : ");
		lblNewLabel_1.setBounds(118, 95, 133, 25);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_1);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBounds(189, 99, 201, 20);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(40));

		JLabel lblnumero = new JLabel("Contato:");
		lblnumero.setBounds(120, 161, 131, 14);
		lblnumero.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblnumero);

		txtFone = new JTextField();
		txtFone.setBounds(189, 160, 201, 20);
		contentPanel.add(txtFone);
		txtFone.setColumns(10);
		txtFone.setDocument(new Validador(12));

		JLabel lblNewLabel_3 = new JLabel("CPF :");
		lblNewLabel_3.setBounds(138, 210, 65, 14);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_3);

		txtCpf = new JTextField();
		txtCpf.setBounds(189, 209, 207, 20);
		contentPanel.add(txtCpf);
		txtCpf.setColumns(10);
		txtCpf.setDocument(new Validador(11));

		JLabel lblNewLabel_4 = new JLabel("Endereco :");
		lblNewLabel_4.setBounds(107, 243, 78, 14);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_4);

		btnLimparCampos = new JButton("");
		btnLimparCampos.setBounds(97, 484, 70, 66);
		btnLimparCampos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimparCampos.setContentAreaFilled(false);
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimparCampos.setBorderPainted(false);
		btnLimparCampos.setIcon(new ImageIcon(Clientes.class.getResource("/img/vasoura.png")));
		contentPanel.add(btnLimparCampos);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(189, 242, 204, 20);
		contentPanel.add(txtEndereco);
		txtEndereco.setColumns(10);
		txtEndereco.setDocument(new Validador(50));

		btnExcluir = new JButton("");
		btnExcluir.setBounds(213, 489, 78, 61);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/deletar.png")));
		contentPanel.add(btnExcluir);

		btnAdicionar = new JButton("");
		btnAdicionar.setBounds(497, 489, 78, 61);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Adicionar();
			}

		});
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/useradd.png")));
		contentPanel.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setBounds(353, 489, 65, 61);
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditarCliente();
			}
		});
		btnEditar.setBorderPainted(false);
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/editnv.png")));
		contentPanel.add(btnEditar);

		JLabel lblNumero = new JLabel("N° :");
		lblNumero.setBounds(455, 269, 46, 14);
		lblNumero.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBounds(497, 268, 41, 20);
		contentPanel.add(txtNumero);
		txtNumero.setColumns(10);
		txtNumero.setDocument(new Validador(5));

		JLabel lblNewLabel_5 = new JLabel("Complemento :");
		lblNewLabel_5.setBounds(98, 377, 123, 20);
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_5);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(213, 379, 191, 20);
		contentPanel.add(txtComplemento);
		txtComplemento.setColumns(10);
		txtComplemento.setDocument(new Validador(20));

		JLabel lblNewLabel_6 = new JLabel("Cidade :");
		lblNewLabel_6.setBounds(156, 430, 95, 14);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_6);

		txtCidade = new JTextField();
		txtCidade.setBounds(213, 429, 201, 20);
		contentPanel.add(txtCidade);
		txtCidade.setColumns(10);
		txtCidade.setDocument(new Validador(25));

		JLabel lblNewLabel_7 = new JLabel("UF :");
		lblNewLabel_7.setBounds(455, 380, 46, 14);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("CEP :");
		lblNewLabel_8.setBounds(107, 288, 46, 14);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_8);

		txtCep = new JTextField();
		txtCep.setBounds(156, 287, 110, 20);
		contentPanel.add(txtCep);
		txtCep.setColumns(10);
		txtCep.setDocument(new Validador(9));

		btnBuscarCEP = new JButton("BUSCAR CEP");
		btnBuscarCEP.setBounds(285, 286, 111, 23);
		btnBuscarCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		contentPanel.add(btnBuscarCEP);

		cboUF = new JComboBox();
		cboUF.setBounds(497, 378, 46, 22);
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		contentPanel.add(cboUF);

		JLabel lblNewLabel_9 = new JLabel("Bairro :");
		lblNewLabel_9.setBounds(156, 336, 60, 14);
		lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPanel.add(lblNewLabel_9);

		txtBairro = new JTextField();
		txtBairro.setBounds(213, 335, 211, 20);
		contentPanel.add(txtBairro);
		txtBairro.setColumns(10);
		txtBairro.setDocument(new Validador(20));
	}

	private void limparCampos() {
		txtNome.setText(null);
		txtFone.setText(null);
		txtCpf.setText(null);
		txtEndereco.setText(null);
		txtNumero.setText(null);
		txtID.setText(null);
		txtCep.setText(null);
		txtBairro.setText(null);
		txtComplemento.setText(null);
		txtCidade.setText(null);
		cboUF.setSelectedItem("");
	}

	private void EditarCliente() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome Do Cliente");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Numero Do Cliente");
			txtFone.requestFocus();
		} else {
			String update = "Update Clientes set nome=?, cpf=?, contato=?, cep = ?, endereco = ?, numero =?,complemento =?, bairro =?, cidade =?,uf=? where idCli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCpf.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtCep.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(8, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.setString(11, txtID.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados Do Cliente Editados Com Sucesso");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não adicionado. \nEste CPF ja está sendo ultilizado");
				txtCpf.setText(null);
				txtCpf.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}

		}
	}

	private void Adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do  Cliente");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Numero do  Cliente");
			txtFone.requestFocus();
		} else if (txtCpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF do  Cliente");
			txtCpf.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Numero da Casa do Cliente");
			txtNumero.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Cep do  Cliente");
			txtFone.requestFocus();
		} else {
		
			String create = "insert into Clientes (nome,cpf,contato,cep,endereco,numero,complemento,bairro,cidade,uf) values (?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCpf.getText());
				pst.setString(3, txtFone.getText());
				pst.setString(4, txtCep.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(8, txtComplemento.getText());
				pst.setString(7, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUF.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente Adicionado");
				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não adicionado. \nEste CPF ja está sendo ultilizado");
				txtCpf.setText(null);
				txtCpf.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}

		}

	}

	private void Excluir() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Cliente?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from Clientes where idCli=?";
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Cliente excluído");
				
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Cliente nao excluido. \nEste cliente ainda tem um serviço pendente");
			} catch (Exception e2) {
				System.out.println(e2);

			}
		}
	}
	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						System.out.println("OK");
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	private void buscarUsuarioLista() {
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			String readListaUsuario = "select * from Clientes where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtFone.setText(rs.getString(4));
					txtCpf.setText(rs.getString(3));
					txtEndereco.setText(rs.getString(6));
					txtNumero.setText(rs.getString(7));
					txtCep.setText(rs.getString(5));
					txtComplemento.setText(rs.getString(9));
					txtBairro.setText(rs.getString(8));
					txtCidade.setText(rs.getString(10));
					cboUF.setSelectedItem(rs.getString(11));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listUsers.setEnabled(false);
					btnAdicionar.setEnabled(false);

				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPane.setVisible(false);
		}

	}

	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listUsers.setModel(modelo);
		String readLista = "select * from Clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
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

}
