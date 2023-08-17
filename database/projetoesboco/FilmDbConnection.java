package projetoesboco;

import java.util.ArrayList;
import java.sql.*;

public class FilmDbConnection extends DatabaseConnection<Film <Genre>> {

	public Film<Genre> convertToFilm(ResultSet result) throws SQLException {
		int film_id = result.getInt("film_id");
		String title = result.getString("title");
		double price = result.getDouble("price");
		String genre = result.getString("genre");
		String rating = result.getString("rating");
		Boolean status = result.getBoolean("status");
		String director = result.getString("director");

		Genre ratedGenre = getRatedGenre(rating, genre);

		Film<Genre> film = new Film<Genre>(film_id, title, price, ratedGenre, status, director);
		return film; 
	}

	@Override
	public ArrayList<Film<Genre>> findAll() {


		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "SELECT * FROM films";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ArrayList<Film<Genre>> films = new ArrayList<Film<Genre>>();

			while(result.next()){

				Film film = convertToFilm(result);
				films.add(film);

			}

			return films;

		} catch (SQLException e) {
			System.out.println("Connection error");
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Film<Genre> findOne(int id) { 

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "SELECT * FROM films WHERE film_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				Film<Genre> film = convertToFilm(result);
				return film;
			}else {
				return null;
			}


		} catch (SQLException e) {
			System.out.println("Connection error");
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public boolean insert(Film<Genre> entity) {

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "INSERT INTO films (title, price, genre, rating, status, director) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.title);
			statement.setString(2, Double.toString(entity.price));
			statement.setString(3, entity.genre.getName());
			statement.setString(4, entity.genre.Classification.name());
			statement.setBoolean(5, entity.status);
			statement.setString(6, entity.director);

			int rows = statement.executeUpdate();
			return rows > 0;

		} catch(SQLException e) {
			System.out.println("Connection error");
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean update(Film<Genre> entity) {

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "UPDATE films SET title = ?, price = ?, genre = ?, rating = ?, status = ?, director = ?"
					+ "WHERE film_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.title);
			statement.setDouble(2, entity.price);
			statement.setString(3, entity.genre.getName());
			statement.setString(4, entity.genre.Classification.name());
			statement.setBoolean(5, entity.status);
			statement.setString(6, entity.director);
			statement.setInt(7, entity.id);

			int rows = statement.executeUpdate();
			return rows > 0;

		} catch(SQLException e) {
			System.out.println("Connection error");
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean delete(int id) {

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "DELETE FROM films WHERE film_id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, id);

			int rows = statement.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			System.out.println("Connection error");
			e.printStackTrace();
			return false;
		}

	}

}
