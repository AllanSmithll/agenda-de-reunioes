/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */
package appswing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import models.Pessoa;
import models.Reuniao;
import regras_negocio.Fachada;

public class TelaConsulta {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JLabel label;
	private JLabel label_4;

	private JComboBox<Object> comboBox;
	private JButton button_1;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsulta tela = new TelaConsulta();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaConsulta() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);

		frame.setResizable(false);
		frame.setTitle("Consulta");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_4.setText("selecionado=" + (String) table.getValueAt(table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.LIGHT_GRAY);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel(""); // label de mensagem
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_4);

		button = new JButton("Consultar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if (index < 0)
					label_4.setText("consulta nao selecionada");
				else {
					switch (index) {
					case 0:
						String data = JOptionPane.showInputDialog("digite a data");
						List<Reuniao> resultado1 = Fachada.listarReunioesNaData(data);
						listagemReuniao(resultado1);
						break;
					case 1:
						String nome = JOptionPane.showInputDialog("digite o nome");
						List<Reuniao> resultado2 = Fachada.listarReunioesComAPessoa(nome);
						listagemReuniao(resultado2);
						break;
					case 2:
						String n = JOptionPane.showInputDialog("digite N");
						int numero = Integer.parseInt(n);
						List<Pessoa> resultado3 = Fachada.pessoasEmMaisDeNReunioes(numero);
						listagemPessoa(resultado3);
						break;
					}
				}
			}
		});
		button.setBounds(606, 10, 89, 23);
		frame.getContentPane().add(button);

		comboBox = new JComboBox<Object>();
		comboBox.setToolTipText("selecione a consulta");
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "Reunioes em determinada data",
				"Reunioes que tenham determinada pessoa", "Pessoas que estao em mais de N reunioes" }));
		comboBox.setBounds(21, 10, 513, 22);
		frame.getContentPane().add(comboBox);
		
		button_1 = new JButton("New button");
		button_1.setBounds(620, 321, 45, -17);
		frame.getContentPane().add(button_1);
		
		btnNewButton = new JButton("Voltar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton.setBounds(610, 314, 85, 21);
		frame.getContentPane().add(btnNewButton);
	}

	public void listagemReuniao(List<Reuniao> lista) {
		try {
			// o model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			model.addColumn("id");
			model.addColumn("data");
			model.addColumn("Lista de pessoas");
			for (Reuniao reu : lista) {
				if (!reu.getListaDePessoas().isEmpty()) {
					List<Pessoa> pessoasDaReuniao = reu.getListaDePessoas();
					StringBuilder sb = new StringBuilder();
					for (Pessoa p : pessoasDaReuniao) {
						sb.append(p.getNome() + " ");
					}
					model.addRow(new Object[] { reu.getId(), reu.getData(), sb });
				}
			}
			table.setModel(model);
			label_4.setText("resultados: " + lista.size() + " objetos");
		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}

	public void listagemPessoa(List<Pessoa> lista) {
		try {
			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			model.addColumn("nome");
			model.addColumn("reunioes");
			for (Pessoa p : lista) {
				if (!p.getReunioes().isEmpty()) {
					List<Reuniao> reunioes = p.getReunioes();
					StringBuilder sb = new StringBuilder();
					for (Reuniao r : reunioes) {
						sb.append(r.getId()+" ");
					}
					model.addRow(new Object[] { p.getNome(), sb.toString() });
				}
			}
			table.setModel(model);
			label_4.setText("resultados: " + lista.size() + " objetos");
		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
