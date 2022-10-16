import java.util.HashMap;

public class Deck {
	private HashMap<String,Integer> cards;
	
	public Deck() {
		cards = null;

	}
	
	public Deck(HashMap<String,Integer> cards) {
		this.cards = cards;
	}

	public HashMap<String,Integer> getCards() {
		return cards;
	}

	public void setCards(HashMap<String,Integer> cards) {
		this.cards = cards;
	}

}
