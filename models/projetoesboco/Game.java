package projetoesboco;

public class Game<T extends Genre> extends Media<T> {

	String platform;

	Game(int id,
			String title, 
			double price, 
			T genre, 
			boolean status, 
			String platform) {

		super(id, title, price, genre, status);
		this.platform = platform;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
