package projetoesboco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GameDbConnection extends DatabaseConnection<Game<Genre>> {

	public Game<Genre> convertToGame(ResultSet result) throws SQLException {
		int game_id = result.getInt("game_id");
		String title = result.getString("title");
		double price = result.getDouble("price");
		String genre = result.getString("genre");
		String rating = result.getString("rating");
		Boolean status = result.getBoolean("status");
		String platform = result.getString("platform");


		Genre ratedGenre = getRatedGenre(rating, genre);

		Game<Genre> game = new Game<Genre>(game_id, title, price, ratedGenre, status, platform);
		return game; 
	}

	@Override
	public ArrayList<Game<Genre>> findAll() {

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "SELECT * FROM games";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			ArrayList<Game<Genre>> games = new ArrayList<Game<Genre>>();

			while(result.next()){
				Game game = convertToGame(result);
				games.add(game);
			}

			return games;

		} catch (SQLException e) {
			System.out.println("Connection error");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Game<Genre> findOne(int id) {

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "SELECT * FROM games WHERE game_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			if(result.next()) {
				Game<Genre> game = convertToGame(result);
				return game;
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
	public boolean insert(Game<Genre> entity) {

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "INSERT INTO games (title, price, genre, rating, status, platform) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.title);
			statement.setString(2, Double.toString(entity.price));
			statement.setString(3, entity.genre.getName());
			statement.setString(4, entity.genre.Classification.name());
			statement.setBoolean(5, entity.status);
			statement.setString(6, entity.platform);

			int rows = statement.executeUpdate();
			return rows > 0;

		} catch(SQLException e) {
			System.out.println("Connection error");
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean update(Game<Genre> entity) {

		try { // important to check if the communication with the db is working
			Connection connection = connect();
			String sql = "UPDATE games SET title = ?, price = ?, genre = ?, rating = ?, status = ?, platform = ?"
					+ "WHERE game_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, entity.title);
			statement.setDouble(2, entity.price);
			statement.setString(3, entity.genre.getName());
			statement.setString(4, entity.genre.Classification.name());
			statement.setBoolean(5, entity.status);
			statement.setString(6, entity.platform);
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
			String sql = "DELETE FROM games WHERE game_id = ?";

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
