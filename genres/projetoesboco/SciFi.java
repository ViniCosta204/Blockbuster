package projetoesboco;

public class SciFi extends Genre {

	public SciFi(RatingEnum classification) {
		super(classification);
	}

	@Override
	public String getName() {
		return "SciFi";
	}

}
