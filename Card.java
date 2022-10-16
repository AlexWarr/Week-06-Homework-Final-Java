
public class Card {
	private String face;
	private int value;
	
	public Card() {
		face = "";
		value = 0;
	}
	
	public Card(String face, int value) {
		this.face = face;
		this.value = value;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	

}
