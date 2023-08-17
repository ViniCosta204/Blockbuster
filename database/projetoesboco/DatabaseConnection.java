package projetoesboco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DatabaseConnection <T> {

	String url = "jdbc:mysql://localhost:3306/blockbuster";
	String username = "root";
	String password = "224791";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public Genre getRatedGenre(String rating, String genre) {

		RatingEnum selectedRating;
		switch(rating) {
		case "General" : 
			selectedRating = RatingEnum.General;
			break;
		case "PG" :
			selectedRating = RatingEnum.PG;
			break;
		case "Fourteen" :
			selectedRating = RatingEnum.Fourteen;
			break;
		case "Eighteen" :
			selectedRating = RatingEnum.Eighteen;
			break;
		case "R" :
			selectedRating = RatingEnum.R;
			break;
		default : 
			selectedRating = null;
			
		}


		Genre selectedGenre;
		switch(genre) {
		case "Action" : 
			selectedGenre = new Action(selectedRating);
			break;
		case "Comedy" :
			selectedGenre = new Comedy(selectedRating);
			break;
		case "Fantasy" :
			selectedGenre = new Fantasy(selectedRating);
			break;
		case "Horror" :
			selectedGenre = new Horror(selectedRating);
			break;
		case "SciFi" :
			selectedGenre = new SciFi(selectedRating);
			break;
		default : 
			selectedGenre = null;
		}

		return selectedGenre;
	}

	public abstract ArrayList<T> findAll();
	public abstract T findOne(int id);
	public abstract boolean insert(T entity);
	public abstract boolean update(T entity);
	public abstract boolean delete(int id);

}
