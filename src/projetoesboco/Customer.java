package projetoesboco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import projetoesboco.Admin.GameTable;
import projetoesboco.Admin.MovieTable;

public class Customer {

	public class MovieTable{

		JTable table = new JTable();
		Object[] columns = {"ID","Title", "Price", "Genre","Rating", "Status", "Director"};
		DefaultTableModel model = new DefaultTableModel();
		FilmDbConnection dbConnection = new FilmDbConnection();

		MovieTable(){
			tableSetup();
		}

		void tableSetup(){

			model.setColumnIdentifiers(columns);
			table.setModel(model);
			table.setRowHeight(30);

		}
		
		public void renderTable() {
			ArrayList<Film<Genre>> films = dbConnection.findAll();
			model.setRowCount(0);
			for(int i=0; i<films.size(); i++) {
				Film<Genre> film = films.get(i);
				model.addRow(new Object[]{film.id, film.title, film.price, 
						film.genre.getName(),film.genre.Classification.name(), film.status,film.director});
			}
		}

		public JTable getTable(){
			renderTable();
			return table;
		}
		public int getRowId() {
			int i = table.getSelectedRow();
			String rowId = table.getValueAt(i, 0).toString();
			int id = Integer.parseInt(rowId);
			return id;
		}
	}

	public class GameTable{

		JTable table = new JTable();
		Object[] columns = {"ID","Title", "Price", "Genre","Rating", "Status", "Platform"};
		DefaultTableModel model = new DefaultTableModel();
		GameDbConnection dbConnection = new GameDbConnection();

		GameTable(){
			tableSetup();
		}

		void tableSetup(){

			model.setColumnIdentifiers(columns);
			table.setModel(model);
			table.setRowHeight(30);

		}
		
		public void renderTable() {
			ArrayList<Game<Genre>> games = dbConnection.findAll();
			model.setRowCount(0);
			for(int i=0; i<games.size(); i++) {
				Game<Genre> game = games.get(i);
				model.addRow(new Object[]{game.id, game.title, game.price, 
						game.genre.getName(),game.genre.Classification.name(), game.status,game.platform});
			}
		}

		public JTable getTable(){
			renderTable();
			return table;
		}
		public int getRowId() {
			int i = table.getSelectedRow();
			String rowId = table.getValueAt(i, 0).toString();
			int id = Integer.parseInt(rowId);
			return id;
		}
	}
	
	

	MovieTable mt = new MovieTable();
	GameTable gt = new GameTable();
	JFrame frame = new JFrame();
	JLabel lblSearch = new JLabel("Search");
	private static JTextField search;
	private static JButton btnRent;
	private static JButton btnSearch;
	JComboBox <MediaTypeEnum> comboBox;
	JScrollPane pane;

	Customer(){

		
		comboBox = new JComboBox<MediaTypeEnum>(MediaTypeEnum.values());
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Films) {

					pane.setViewportView(mt.getTable());
				}
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Games) {

					pane.setViewportView(gt.getTable());
				}

			}
		});
		comboBox.setBounds(400, 5, 70, 20);
		frame.getContentPane().add(comboBox);

		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);


		pane = new JScrollPane(mt.getTable());
		pane.setBounds(5, 30, 473, 400);
		frame.getContentPane().add(pane);

		search = new JTextField();
		search.setBounds(5, 5, 300, 20);
		frame.getContentPane().add(search);
		search.setColumns(10);

		btnRent = new JButton("Rent");
		btnRent.setBounds(200, 433, 70, 20);
		frame.getContentPane().add(btnRent);
		btnRent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MediaTypeEnum mediaType = comboBox.getItemAt(comboBox.getSelectedIndex());
				int mediaId = 0;
				if(mediaType==MediaTypeEnum.Films) {
					mediaId = mt.getRowId();
				}if(mediaType==MediaTypeEnum.Games) {
					mediaId = gt.getRowId();
				}
				Details d = new Details(mediaId, mediaType);//receber id da midia selecionada
				frame.dispose();
			}

		});

		btnSearch = new JButton("Search");
		btnSearch.setBounds(305, 5, 80, 20);
		frame.getContentPane().add(btnSearch);

		frame.setVisible(true);

	}
	
	
}
