package projetoesboco;

public class Film<T extends Genre> extends Media<T> {

	String director;

	Film(int id,
			String title,
			double price,
			T genre,
			boolean status,
			String director){

		super(id, title, price, genre, status);
		this.director = director;

	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Override
	public String toString(){
		return title;
		
	}
}
