package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.Font;
import java.awt.Toolkit;

public class Servicos extends JDialog {

	/**
	 * 
	 */
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtModelo;
	private JTextField txtDefeito;
	private JTextField txtValor;
	private JTextField txtID;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnBuscar;
	private JButton btnExcluir;
	private JTextField txtCliente;
	private JList listaClientes;
	private JScrollPane scrollPane;
	private JButton btnImprimir;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Servicos.class.getResource("/img/9990432_snowman_doll_people_holiday_ice_icon.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(385, 62, 218, 176);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("ID do Cliente");
		lblNewLabel_5.setBounds(9, 111, 74, 14);
		panel.add(lblNewLabel_5);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(74, 108, 96, 20);
		panel.add(txtID);
		txtID.setColumns(10);

		txtCliente = new JTextField();
		txtCliente.setBounds(9, 12, 186, 20);
		panel.add(txtCliente);
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}
		});
		txtCliente.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(9, 14, 186, 85);
		panel.add(scrollPane);

		listaClientes = new JList();
		scrollPane.setViewportView(listaClientes);
		listaClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
				listaClientes.setEnabled(true);
			}
		});
		scrollPane.setVisible(false);

		JLabel lblNewLabel = new JLabel("OS");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(74, 122, 46, 20);
		getContentPane().add(lblNewLabel);

		txtOS = new JTextField();
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtOS.setEditable(false);
		txtOS.setBounds(114, 122, 103, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Data OS");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(22, 191, 118, 20);
		getContentPane().add(lblNewLabel_1);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setBounds(109, 195, 204, 20);
		getContentPane().add(txtData);
		txtData.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Modelo");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_2.setBounds(22, 287, 78, 20);
		getContentPane().add(lblNewLabel_2);

		txtModelo = new JTextField();
		txtModelo.setBounds(98, 291, 204, 20);
		getContentPane().add(txtModelo);
		txtModelo.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Defeito");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_3.setBounds(22, 343, 89, 20);
		getContentPane().add(lblNewLabel_3);

		txtDefeito = new JTextField();
		txtDefeito.setBounds(98, 347, 204, 20);
		getContentPane().add(txtDefeito);
		txtDefeito.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Valor");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_4.setBounds(34, 414, 120, 25);
		getContentPane().add(lblNewLabel_4);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		txtValor.setBounds(93, 420, 103, 20);
		getContentPane().add(txtValor);
		txtValor.setColumns(10);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/useradd.png")));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(307, 481, 75, 57);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editnv.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(438, 481, 53, 57);
		getContentPane().add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/deletar.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirServico();
			}
		});
		btnExcluir.setBounds(550, 481, 53, 57);
		getContentPane().add(btnExcluir);

		btnBuscar = new JButton("");
		btnBuscar.setBorderPainted(false);
		btnBuscar.setBorder(null);
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/pesquisar.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(249, 115, 64, 57);
		getContentPane().add(btnBuscar);

		JButton btnNewButton = new JButton("");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setToolTipText("Limpar Campos");
		btnNewButton.setIcon(new ImageIcon(Servicos.class.getResource("/img/borracha.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}

		});
		btnNewButton.setBounds(194, 481, 64, 57);
		getContentPane().add(btnNewButton);
		getRootPane().setDefaultButton(btnBuscar);

		btnImprimir = new JButton("");
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimir();
			}
		});
		btnImprimir.setIcon(new ImageIcon(Servicos.class.getResource("/img/4243338_basic_app_print_ux_icon.png")));
		btnImprimir.setContentAreaFilled(false);
		btnImprimir.setBounds(680, 481, 71, 57);
		getContentPane().add(btnImprimir);

	}

	private void limparCampos() {
		txtCliente.setText(null);
		txtID.setText(null);
		txtModelo.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		txtData.setText(null);
		txtOS.setText(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnBuscar.setEnabled(true);

	}

	private void adicionar() {

		String comando = "insert into servicos (idcli,Modelo,defeito,valor) value (?,?,?,?)";
		String numOs = "SELECT OS FROM servicos WHERE OS = (SELECT MAX(OS) FROM servicos)";
		String info = "";
		if (txtID.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo ID não pode estar vazio.");
			txtID.requestFocus();
		} else if (txtID.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo OS não pode estar vazio.");
			txtModelo.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo Modelo não pode estar vazio.");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo Defeito não pode estar vazio.");
			txtDefeito.requestFocus();
		} else {
			try {
				con = dao.conectar();

				pst = con.prepareStatement(comando);
				pst.setString(1, txtID.getText());
				pst.setString(2, txtModelo.getText());
				pst.setString(3, txtDefeito.getText());
				pst.setString(4, txtValor.getText());

				pst.executeUpdate();
				con.close();

				con = dao.conectar();
				pst = con.prepareStatement(numOs);
				rs = pst.executeQuery();

				if (rs.next()) {
					info = rs.getString(1);
				}
				con.close();
				limparCampos();
				JOptionPane.showInternalMessageDialog(null, "Adicionado com sucesso");
				JOptionPane.showInternalMessageDialog(null, "Sua OS é: " + info);
				btnEditar.setEnabled(true);
			} catch (SQLException se) {
				se.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void editar() {
		if (txtModelo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Modelo do Ar Condicionado");
			txtModelo.requestFocus();

		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Defeito do Ar Condicionado");
			txtDefeito.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Valor desse serviço");
			txtValor.requestFocus();

		} else {
			String update = "update servicos set os=?, dataOS=?, Modelo=?, Defeito=?, Valor=? where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);

				pst.setString(1, txtOS.getText());
				pst.setString(2, txtData.getText());
				pst.setString(3, txtModelo.getText());
				pst.setString(4, txtDefeito.getText());
				pst.setString(5, txtValor.getText());
				pst.setString(6, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso.");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void buscar() {
		String numOS = JOptionPane.showInputDialog("Número da OS");
		String read = "select * from servicos where os = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, numOS);
			rs = pst.executeQuery();
			if (rs.next()) {

				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtModelo.setText(rs.getString(3));
				txtDefeito.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtID.setText(rs.getString(6));
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnBuscar.setEnabled(false);

			} else {
				JOptionPane.showMessageDialog(null, "OS inexistente");
				btnAdicionar.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void excluirServico() {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste serviço?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from servicos where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				pst.executeUpdate();
				limparCampos();
				JOptionPane.showMessageDialog(null, "Serviço excluído");
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listaClientes.setModel(modelo);
		String readLista = "select * from Clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtCliente.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarClienteLista() {
		int linha = listaClientes.getSelectedIndex();
		if (linha >= 0) {
			String readBuscaLista = "select * from Clientes where nome like '" + txtCliente.getText() + "%'"
					+ "order by nome limit " + (linha) + ", 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readBuscaLista);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					listaClientes.setEnabled(false);
					btnAdicionar.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Cliente Inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}

		} else {
			scrollPane.setVisible(false);

		}
	}

	private void imprimir() {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			document.open();
			String readOS = "select * from servicos where os = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readOS);
				pst.setString(1, txtOS.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					Paragraph os = new Paragraph("OS: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_RIGHT);
					document.add(os);

					Paragraph Modelo = new Paragraph("Modelo: " + rs.getString(3));
					Modelo.setAlignment(Element.ALIGN_LEFT);
					document.add(Modelo);

					Paragraph Defeito = new Paragraph("Defeito: " + rs.getString(4));
					Defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(Defeito);

					Paragraph Data = new Paragraph("Data: " + rs.getString(2));
					Data.setAlignment(Element.ALIGN_LEFT);
					document.add(Data);

					Paragraph Valor = new Paragraph("Valor: " + rs.getString(5));
					Valor.setAlignment(Element.ALIGN_LEFT);
					document.add(Valor);

					Image imagem = Image.getInstance(Servicos.class.getResource("/img/airgelado.png"));
					imagem.scaleToFit(200, 200);
					imagem.setAbsolutePosition(200, 530);
					document.add(imagem);
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
