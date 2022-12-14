import java.util.HashMap;

public class Player {
	private String name;
	private int score;
	private HashMap<String,Integer> hand;
	
	public Player() {
		name = "";
		score = 0;
		hand = null;
		
	}
	
	public Player(String name, int score, HashMap<String,Integer> hand) {
		this.name = name;
		this.score = score;
		this.hand = hand;
	}
	
	public String playerDisplay() {
		return "Name: " + getName() + " , Score: " + getScore();
	}
	
	public void win(int point) {
		setScore(getScore() + point);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public HashMap<String,Integer> getHand() {
		return hand;
	}

	public void sethand(HashMap<String,Integer> hand) {
		this.hand = hand;
	}

	public static void describe(String name, int score, HashMap<String,Integer> hand) {
		System.out.println(name + " currently has " + score + " points");
		System.out.println(name + " has the following cards in their hand:");
		Card temp = new Card();
		for (String key : hand.keySet()) {
			temp.setFace(key);
			temp.setValue(hand.get(key));
			Card.describe(temp.getFace(),temp.getValue());
		}
	}


}
