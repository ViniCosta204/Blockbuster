package projetoesboco;

public abstract class Genre {
	
	public RatingEnum Classification;
	
	public Genre(RatingEnum classification) {
        Classification = classification;
    }

	protected abstract String getName();

}