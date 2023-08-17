package projetoesboco;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class mainPage implements ActionListener {

	JFrame frame = new JFrame();
	JButton button = new JButton("Customer");
	JButton button2 = new JButton("Admin");

	mainPage(){

		try { // not mandatory but checking the drivers
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		} 

		String url = "jdbc:mysql://localhost:3306/blockbuster";
		String username = "root";
		String password = "224791";

		try (Connection connection = DriverManager.getConnection(url, username, password)) { 
			// not mandatory but checking the db connection
			System.out.println("Database connected!");
			connection.close();
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}

		button.setBounds(150, 100, 100, 40);
		button.setFocusable(false);
		button.addActionListener(this);
		button2.setBounds(150, 200, 100, 40);
		button2.setFocusable(false);
		button2.addActionListener(this);

		frame.add(button2);
		frame.add(button);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 420);
		frame.setLayout(null);
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==button) {
			frame.dispose();
			Customer c = new Customer();
		}else if(e.getSource()==button2) {
			frame.dispose();
			Admin a = new Admin();
		}

	}
}

