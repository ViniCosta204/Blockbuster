package projetoesboco;

public class Media<T extends Genre> {
	
	int id;
	String title;
	double price;
	T genre;
	boolean status;
	
	Media(int id,
			String title,
			double price,
			T genre,
			boolean status){
				
				this.id = id;
				this.title = title;
				this.price = price;
				this.genre = genre;
				this.status = status;
			
			}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public T getGenre() {
		return genre;
	}
	public void setGenre(T genre) {
		this.genre = genre;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	

}
