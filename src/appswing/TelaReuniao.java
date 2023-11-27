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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import jakarta.persistence.EntityManager;
import models.Pessoa;
import models.Reuniao;
import regras_negocio.Fachada;

public class TelaReuniao {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField textField;
	private JButton button;
	private JButton btnProximoPasso;
	private JButton button_2;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_4;

	private EntityManager manager;
	private JButton btnExibirPessoas;
	private JComboBox comboBox_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaReuniao tela = new TelaReuniao();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaReuniao() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);

		frame.setResizable(false);
		frame.setTitle("Reuniao");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
				listagem();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);
		
		JButton btnNewButton_2 = new JButton("Voltar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_2.setBounds(0, 0, 85, 21);
		frame.getContentPane().add(btnNewButton_2);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label_4.setText("selecionado=" + (int) table.getValueAt(table.getSelectedRow(), 0));
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.YELLOW);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(31, 327, 664, 14);
		frame.getContentPane().add(label);
		label.setText("Obs: selecione alguma linha.");

		label_4 = new JLabel("resultados:");
		label_4.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_4);

		label_2 = new JLabel("data:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(31, 267, 43, 14);
		frame.getContentPane().add(label_2);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(68, 264, 195, 20);
		frame.getContentPane().add(textField);

		btnProximoPasso = new JButton("Próximo Passo");
	    btnProximoPasso.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    btnProximoPasso.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            proximoPasso();
	        }
	    });
	    btnProximoPasso.setBounds(97, 294, 120, 23);
	    frame.getContentPane().add(btnProximoPasso);

		button = new JButton("Listar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setBounds(308, 11, 89, 23);
		frame.getContentPane().add(button);

		button_2 = new JButton("Apagar selecionado");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						int id = (int) table.getValueAt(table.getSelectedRow(), 0);
						Fachada.excluirReuniao(id);
						label.setText("reuniao apagada");
						listagem();
					} else
						label.setText("nao selecionado");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(279, 215, 146, 23);
		frame.getContentPane().add(button_2);

		btnExibirPessoas = new JButton("exibir pessoas");
		btnExibirPessoas.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnExibirPessoas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.getSelectedRow() >= 0) {
						int idreuniao = (int) table.getValueAt(table.getSelectedRow(), 0);
						Reuniao reuniao = Fachada.localizarReuniao(idreuniao);

						if (reuniao != null) {
							String texto = "";
							if (reuniao.getListaDePessoas().isEmpty())
								texto = "nao possui membros";
							else
								for (Pessoa a : reuniao.getListaDePessoas()) {
									texto = texto + a.getNome() + "\n";
								}
							JOptionPane.showMessageDialog(frame, texto, "pessoas", 1);
						}
					}
				} catch (Exception erro) {
					label.setText(erro.getMessage());
				}
			}
		});
		btnExibirPessoas.setBounds(279, 287, 146, 23);
		frame.getContentPane().add(btnExibirPessoas);

		JButton btnNewButton = new JButton("Adicionar membro");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {

				JComboBox comboBox = new JComboBox<Object>();
				comboBox.setBounds(486, 216, 161, 43);
				frame.getContentPane().add(comboBox);
				List<Pessoa> pessoas = Fachada.listarPessoas();

				for (Pessoa pessoa : pessoas) {
					comboBox.addItem(pessoa.getNome());
				}

				JButton btnNewButton_1 = new JButton("Confirmar");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {

							if (table.getSelectedRow() >= 0) {
								int idreuniao = (int) table.getValueAt(table.getSelectedRow(), 0);
								Fachada.localizarReuniao(idreuniao);
								int selectedIndex = comboBox.getSelectedIndex();
								if (selectedIndex != -1) {
									String selectedValue = (String) comboBox.getItemAt(selectedIndex);
									Fachada.agendarReuniao(selectedValue, idreuniao);
									label.setText("Pessoa adicionada com sucesso!");
								}
							}

						} catch (Exception erro) {
							label.setText(erro.getMessage());
						}
					}
				});
				btnNewButton_1.setBounds(520, 287, 100, 23);
				frame.getContentPane().add(btnNewButton_1);
			}

		});
		btnNewButton.setBounds(279, 249, 146, 23);
		frame.getContentPane().add(btnNewButton);

		JLabel lblAgendeUmaReunio = new JLabel("Agende uma reunião");
		lblAgendeUmaReunio.setHorizontalAlignment(SwingConstants.LEFT);
		lblAgendeUmaReunio.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAgendeUmaReunio.setBounds(97, 240, 134, 14);
		frame.getContentPane().add(lblAgendeUmaReunio);
	}

	public void listagem() {
		try {
			List<Reuniao> lista = Fachada.listarReunioes();

			// model armazena todas as linhas e colunas do table
			DefaultTableModel model = new DefaultTableModel();

			// adicionar colunas no model
			model.addColumn("ID");
			model.addColumn("Data");

			// adicionar linhas no model
			for (Reuniao reuniao : lista) {
				model.addRow(new Object[] { reuniao.getId(), reuniao.getData() });
			}

			// atualizar model no table (visualizacao)
			table.setModel(model);

			label_4.setText("resultados: " + lista.size() + " objetos");
		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
	
	private void proximoPasso() {
	    try {
	        if (textField.getText().isEmpty()) {
	            label.setText("Campo de data vazio");
	            return;
	        }

	        String data = textField.getText();

	        int idReuniao = Fachada.cadastrarReuniao(data).getId();
	        frame.dispose();
	        adicionarPessoasAReuniao(idReuniao);
	    } catch (Exception ex) {
	        label.setText(ex.getMessage());
	    }
	}

	private void adicionarPessoasAReuniao(int idReuniao) {
	    JDialog novaJanela = new JDialog();
	    novaJanela.setTitle("Adicionar Pessoas à Reunião");
	    novaJanela.setSize(400, 300);
	    novaJanela.getContentPane().setLayout(null);

	    JComboBox<String> comboBox = new JComboBox<>();
	    comboBox.setBounds(50, 50, 200, 30);
	    List<Pessoa> pessoas = Fachada.listarPessoas();

	    for (Pessoa pessoa : pessoas) {
	        comboBox.addItem(pessoa.getNome());
	    }
	    novaJanela.getContentPane().add(comboBox);

	    JButton btnConfirmar = new JButton("Confirmar");
	    btnConfirmar.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                int selectedIndex = comboBox.getSelectedIndex();
	                if (selectedIndex != -1) {
	                    String selectedValue = (String) comboBox.getItemAt(selectedIndex);
	                    Reuniao reuniao = Fachada.localizarReuniao(idReuniao);
	                    while (reuniao.getListaDePessoas().size() < 2) {
	                        Fachada.agendarReuniao(selectedValue, idReuniao);
	                        selectedValue = (String) comboBox.getItemAt(selectedIndex);
	                        
	                        int option = JOptionPane.showConfirmDialog(novaJanela, "Deseja adicionar mais pessoas à reunião?",
	                        		"Confirmação", JOptionPane.YES_NO_OPTION);
	                     
	                        if (option == JOptionPane.NO_OPTION) {
	                            break;
	                        }
	                    }
	                    novaJanela.dispose();
	                    frame.setVisible(true);
	                }
	            } catch (Exception ex) {
	                label.setText(ex.getMessage());
	            }
	        }
	    });
	    btnConfirmar.setBounds(260, 50, 120, 30);
	    novaJanela.getContentPane().add(btnConfirmar);
	    novaJanela.setVisible(true);
	}
}
