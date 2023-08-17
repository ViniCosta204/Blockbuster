package projetoesboco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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


public class Admin implements ActionListener {

	//inner class
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

		public JTable getTable(){
			renderTable();
			return table;
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

		public void addRow(Film<Genre> film) {
			boolean result = dbConnection.insert(film);
			if(result) {
				renderTable();
			}
		}

		public int getRowId() {
			int i = table.getSelectedRow();
			String rowId = table.getValueAt(i, 0).toString();
			int id = Integer.parseInt(rowId);
			return id;
		}

		public void updateRow(Film<Genre> film) {
			int id = getRowId();
			film.id = id;
			boolean result = dbConnection.update(film);
			if(result) {
				renderTable();
			}

		}

		public void deleteRow() {
			int id = getRowId();
			boolean result = dbConnection.delete(id);
			if(result) {
				renderTable();
				JOptionPane.showMessageDialog(frame, "Deleted successfully");
			} else {
				JOptionPane.showMessageDialog(frame, "Delete error");
			}
		}

	}
	//fim da inner class

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

		public JTable getTable(){
			renderTable();
			return table;
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

		public void addRow(Game<Genre> game) {
			boolean result = dbConnection.insert(game);
			if(result) {
				renderTable();
			}
		}

		public int getRowId() {
			int i = table.getSelectedRow();
			String rowId = table.getValueAt(i, 0).toString();
			int id = Integer.parseInt(rowId);
			return id;
		}

		public void updateRow(Game<Genre> game) {
			int id = getRowId();
			game.id = id;
			boolean result = dbConnection.update(game);
			if(result) {
				renderTable();
			}
		}

		public void deleteRow() {
			int id = getRowId();
			boolean result = dbConnection.delete(id);
			if(result) {
				renderTable();
				JOptionPane.showMessageDialog(frame, "Deleted successfully");
			} else {
				JOptionPane.showMessageDialog(frame, "Delete error");
			}
		}
	}
	
	//test

	JFrame frame = new JFrame();
	MovieTable mt = new MovieTable();
	GameTable gt = new GameTable();
	JLabel lblTitle = new JLabel("Title:");
	JLabel lblPrice = new JLabel("Price:");
	JLabel lblGenre = new JLabel("Genre:");
	JLabel lblRating = new JLabel("Rating:");
	JLabel lblDirector = new JLabel("Director:");
	JLabel lblPlatform = new JLabel("Platform:");
	private static JTextField title;
	private static JTextField price;
	private static JTextField director;
	private static JTextField platform;
	JComboBox comboBox;
	JComboBox<GenreEnum> genreComboBox;
	JComboBox<RatingEnum> ratingComboBox;
	PreparedStatement pst;
	JScrollPane pane;

	void directorVisibility(boolean b){
		director.setVisible(b);
		lblDirector.setVisible(b);
		frame.revalidate();
		frame.repaint();
	}

	void platformVisibility(boolean p){
		platform.setVisible(p);
		lblPlatform.setVisible(p);
		frame.revalidate();
		frame.repaint();
	}

	Admin(){

		comboBox = new JComboBox<MediaTypeEnum>(MediaTypeEnum.values());
		comboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Films) {

					pane.setViewportView(mt.getTable());
					directorVisibility(true);
					platformVisibility(false);
				}
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Games) {

					pane.setViewportView(gt.getTable());
					platformVisibility(true);
					directorVisibility(false);
				}
			}
		});

		comboBox.setBounds(5, 20, 70, 20);

		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		pane = new JScrollPane(mt.getTable());
		pane.setBounds(120, 10, 350, 400);
		frame.getContentPane().add(pane);

		frame.getContentPane().add(comboBox);

		title = new JTextField();
		title.setBounds(50, 50, 65, 20);
		frame.getContentPane().add(title);
		title.setColumns(10);

		price = new JTextField();
		price.setBounds(50, 80, 65, 20);
		frame.getContentPane().add(price);
		price.setColumns(10);

		genreComboBox = new JComboBox<GenreEnum>(GenreEnum.values());
		genreComboBox.setBounds(50, 110, 65, 20);
		frame.getContentPane().add(genreComboBox);

		ratingComboBox = new JComboBox<RatingEnum>(RatingEnum.values());
		ratingComboBox.setBounds(50, 140, 65, 20);
		frame.getContentPane().add(ratingComboBox);

		director = new JTextField();
		director.setBounds(55, 170, 65, 20);
		frame.getContentPane().add(director);
		director.setColumns(10);

		platform = new JTextField();
		platform.setBounds(60, 170, 60, 20);
		frame.getContentPane().add(platform);
		platform.setColumns(10);

		lblTitle.setBounds(5, 50, 90, 20);
		frame.getContentPane().add(lblTitle);

		lblPrice.setBounds(5, 80, 90, 20);
		frame.getContentPane().add(lblPrice);

		lblGenre.setBounds(5, 110, 90, 20);
		frame.getContentPane().add(lblGenre);

		lblRating.setBounds(5, 140, 90, 20);
		frame.getContentPane().add(lblRating);

		lblDirector.setBounds(5, 170, 90, 20);
		frame.getContentPane().add(lblDirector);

		lblPlatform.setBounds(5, 170, 90, 20);
		frame.getContentPane().add(lblPlatform);

		platformVisibility(false);

		//ADD BUTTON
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(5, 250, 60, 20);
		frame.getContentPane().add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Double priceDouble = Double.parseDouble(price.getText());
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Films) {
					Film<Genre> f = new Film<Genre>(0,title.getText(),priceDouble,
							getRatedGenre(),true,director.getText());
					assert priceDouble != null: "price not null";  //making sure everything has a price
																//it's very important
					mt.addRow(f);
				} if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Games) {

					Game<Genre> g = new Game<Genre>(0,title.getText(),priceDouble,
							getRatedGenre(),true,platform.getText());
					assert priceDouble != null: "price not null"; //making sure everything has a price
																//it's very important
					gt.addRow(g);
				}
			}
		});

		//UPDATE BUTTON
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(5, 300, 100, 20);
		frame.getContentPane().add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Double priceDouble = Double.parseDouble(price.getText());
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Films) {
					Film<Genre> f = new Film<Genre>(0,title.getText(),priceDouble,
							getRatedGenre(),true,director.getText());
					assert priceDouble != null: "price not null"; //making sure everything has a price
					//it's very important
					mt.updateRow(f);
				}
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Games) {
					Game<Genre> g = new Game<Genre>(0,title.getText(),priceDouble,
							getRatedGenre(),true,platform.getText());
					assert priceDouble != null: "price not null"; //making sure everything has a price
					//it's very important
					gt.updateRow(g);
				}
			}
		});

		//DELETE BUTTON
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(5, 350, 100, 20);
		frame.getContentPane().add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Films) {
					mt.deleteRow();
				}
				if(comboBox.getItemAt(comboBox.getSelectedIndex())==MediaTypeEnum.Games) {
					gt.deleteRow();
				}
			}

		});

		frame.revalidate(); //it's like a refresher
		frame.setVisible(true);	

	}

	public Genre getRatedGenre() {
		RatingEnum selectedRating = ratingComboBox.getItemAt(ratingComboBox.getSelectedIndex());

		Genre selectedGenre;
		switch(genreComboBox.getItemAt(genreComboBox.getSelectedIndex())) {
		case Action : 
			selectedGenre = new Action(selectedRating);
			break;
		case Comedy :
			selectedGenre = new Comedy(selectedRating);
			break;
		case Fantasy :
			selectedGenre = new Fantasy(selectedRating);
			break;
		case Horror :
			selectedGenre = new Horror(selectedRating);
			break;
		case SciFi :
			selectedGenre = new SciFi(selectedRating);
			break;
		default : 
			selectedGenre = null;
		}
		return selectedGenre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
