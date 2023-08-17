package projetoesboco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Details {

	JFrame frame;
	JLabel lblFilmTitle;
	JLabel lblFilmDirector;
	JLabel lblGamePlatform;
	JLabel lblGenre;
	JLabel lblName;
	JTextField txtName;
	JLabel lblAddress;
	JTextField txtAddress;
	JLabel lblDaysRented;
	JTextField txtDaysRented;
	JLabel lblPrice;
	JLabel lblTotal;
	JButton btnConfirm;
	Media<Genre> selectedMedia = null;

	Details(int id, MediaTypeEnum mediaType){ 

		if(mediaType == MediaTypeEnum.Films) {

			FilmDbConnection dbConnection = new FilmDbConnection();
			selectedMedia = dbConnection.findOne(id);

		}

		if(mediaType == MediaTypeEnum.Games) {
			GameDbConnection dbConnection = new GameDbConnection();
			selectedMedia = dbConnection.findOne(id);
		}

		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setVisible(true);

		lblFilmTitle = new JLabel(selectedMedia.title);
		lblFilmTitle.setBounds(210, 15, 70, 20);
		frame.getContentPane().add(lblFilmTitle);

		lblFilmDirector = new JLabel();
		lblFilmDirector.setBounds(50, 70, 140, 20);
		frame.getContentPane().add(lblFilmDirector);
		directorVisibility(mediaType==MediaTypeEnum.Films);

		lblGamePlatform = new JLabel();
		lblGamePlatform.setBounds(50, 70, 140, 20);
		frame.getContentPane().add(lblGamePlatform);
		platformVisibility(mediaType==MediaTypeEnum.Games);


		lblGenre = new JLabel("Genre: "+selectedMedia.genre.getName());
		lblGenre.setBounds(350, 70, 140, 20);
		frame.getContentPane().add(lblGenre);

		lblName = new JLabel("Name:");
		lblName.setBounds(50, 130, 70, 20);
		frame.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setBounds(100, 130, 250, 20);
		frame.getContentPane().add(txtName);

		lblAddress = new JLabel("Address:");
		lblAddress.setBounds(50, 200, 70, 20);
		frame.getContentPane().add(lblAddress);

		txtAddress = new JTextField();
		txtAddress.setBounds(110, 200, 250, 20);
		frame.getContentPane().add(txtAddress);

		lblDaysRented = new JLabel("Days:");
		lblDaysRented.setBounds(50, 270, 70, 20);
		frame.getContentPane().add(lblDaysRented);

		txtDaysRented = new JTextField();
		txtDaysRented.setBounds(100, 270, 250, 20);
		frame.getContentPane().add(txtDaysRented);
		txtDaysRented.getDocument().addDocumentListener(new DocumentListener() {
			public void calculateTotal() {
				try {
					Double daysRented = Double.parseDouble(txtDaysRented.getText());
					Double price = selectedMedia.price;
					Double total = daysRented * price; 
					lblTotal.setText("Total: $"+total.toString());
				}catch (Exception ex){
					lblTotal.setText("Total: -");
				}
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				calculateTotal();

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				calculateTotal();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				calculateTotal();

			}

		});

		lblPrice = new JLabel("Price: $" +selectedMedia.price);
		lblPrice.setBounds(50, 360, 70, 20);
		frame.getContentPane().add(lblPrice);

		lblTotal = new JLabel("Total: ");
		lblTotal.setBounds(350, 360, 150, 20);
		frame.getContentPane().add(lblTotal);

		btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(170, 390, 100, 30);
		frame.getContentPane().add(btnConfirm);
		btnConfirm.addActionListener((ActionEvent e) -> { //lambda
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("receipt.txt");
				PrintWriter pw = new PrintWriter(fos); 
				if(mediaType == MediaTypeEnum.Films) {
					Film<Genre> selectedFilm = (Film<Genre>)selectedMedia;
					pw.print("RECEIPT"+
							"\n================================"+
							"\nFilm Title: "+ selectedMedia.title +
							"\nDirector: "+selectedFilm.director +
							"\nCustomer Name: "+txtName.getText()+
							"\n"+lblTotal.getText()); 
					JOptionPane.showMessageDialog(frame, "Success!");
					frame.dispose();
				}

				if(mediaType == MediaTypeEnum.Games) {
					Game<Genre> selectedGame = (Game<Genre>)selectedMedia;
					pw.print("RECEIPT"+
							"\n================================"+
							"\nGame Title: "+ selectedMedia.title +
							"\nPlatform: "+selectedGame.platform +
							"\nCustomer Name: "+txtName.getText()+
							"\n"+lblTotal.getText()); 
					JOptionPane.showMessageDialog(frame, "Success!");
					frame.dispose();
				}

				pw.close();

			} catch (FileNotFoundException e1) {

				e1.printStackTrace();
			} 

		});
		
		if(mediaType==MediaTypeEnum.Films) {
			lblFilmDirector.setText("Director: "+((Film<Genre>)selectedMedia).director);
		} if(mediaType==MediaTypeEnum.Games) {
			lblGamePlatform.setText("Platform: "+((Game<Genre>)selectedMedia).platform);
		}
	}

	void directorVisibility(boolean b){
		lblFilmDirector.setVisible(b);
		frame.revalidate();
		frame.repaint();
	}

	void platformVisibility(boolean p){
		lblGamePlatform.setVisible(p);
		frame.revalidate();
		frame.repaint();
	}

}
