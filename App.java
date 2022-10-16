import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class App {
	public static Scanner kb = new Scanner(System.in);
	public static Random rando = new Random();
	
	public static void main(String[] args) {
		//Welcome to war, this is a simple app to replicate the card game
		
		String[] names = playerNamer(menu());// determines single or 2 player mode and names players
		List<Map<String, Integer>> hands = handBuilder(deckBuilder()); //builds the main deck and then deals it into 2 hands
		Player player1 = new Player(names[0],0, (HashMap<String, Integer>) hands.get(0)); //creates player 1
		Player player2 = new Player(names[1],0, (HashMap<String, Integer>) hands.get(1)); // creates player 2
		
		play(player1,player2); //initiates and carries out gameplay

		System.out.println("Thank you for playing!\n\nWould you like to play again?\n  'y' = play again\n  'n' = exit");
		String again = kb.nextLine();
		//allows players to loop back to beginning for replay
		if (again.contains("y")) {
			App.main(args);
		} else {
			System.out.println("Thank you for playing!\nGood Bye!");
		}
		
		
	}
	
// Methods
	public static boolean menu() {
		//selects number of players
		System.out.println("Welcome to War!\n");
		System.out.println("Please select a mode of play: \n  Enter '1' for Single Player\n  Enter '2' for 2 Players\n  Enter '0' to quit");	
		String initchoice = kb.nextLine();
		int choice = 1;
		try {// attempts to catch improper responses
			choice = Integer.parseInt(initchoice);
		} catch (NumberFormatException e) {
			System.out.println("Please choose a valid option");
			System.out.println("  Enter '1' for Single Player\n  Enter '2' for 2 Players\n  Enter '0' to quit");
			choice = Integer.parseInt(kb.nextLine());
		}
		while (choice > 3 || choice < 0) {
			System.out.println("Please choose a valid option");
			System.out.println("  Enter '1' for Single Player\n  Enter '2' for 2 Players\n  Enter '0' to quit");
			choice = Integer.parseInt(kb.nextLine());
		}
		boolean single = true;
		if (choice == 0) {// exits game
			System.out.println("Good Bye!");
			System.exit(choice);
		}
		switch (choice) {
		case 1: 
			single = true;
			break;
		case 2:
			single = false;
			break;
			}
		return single;
	}
	
	public static String[] playerNamer(boolean menu) {
		//names players
		System.out.println("Please enter name for player 1: ");
		String P1 = kb.nextLine();
		String P2 = "";
		if (menu) {
			P2 = "PC";
		} else { 
			System.out.println("Please enter name for player 2: ");
			P2 = kb.nextLine();
		}
		String[] names = new String[] {P1,P2};
		return names;

		
	}
	
	public static Deck deckBuilder() {
		//creates a HashMap of all faces and values of a regular playing deck minus the jokers
		String[] royals = new String[]{"J","Q","K","A"};
		String[] cases = new String[] {"h","c","s","d"};
		HashMap<String,Integer> stack = new HashMap<String,Integer>();
		for(int i = 2; i < 15; i++) {
			for (int j = 0; j < 4; j++) {
				String temp = "";
				if (i < 11) {
					temp = i+cases[j];
					stack.put(temp, i);
					}
				if (i > 10) {
					temp = royals[i-11]+cases[j];
					stack.put(temp, i);
					}		
				}
				
			}
		//creates the main Deck from which the hands will be drawn
		Deck mainDeck = new Deck();
		mainDeck.setCards(stack);
		return mainDeck;
	}
	
	
	public static List<Map<String, Integer>> handBuilder(Deck mainDeck) {
		//split mainDeck evenly into two random hands
		HashMap<String,Integer> mDeck = mainDeck.getCards();
		HashMap<String,Integer> hand1 = new HashMap<String,Integer>();
		HashMap<String,Integer> hand2 = new HashMap<String,Integer>();
		boolean error = true; // emplaced to prevent errors with the random number
		Object[] arry = mDeck.keySet().toArray();// iterable array of keys
		int counter = arry.length;
		int shuffler =0;		
		while (counter > 0) {
			while (error == true) {
				//pulls a random card from the main deck and places it in hand1
				shuffler = rando.nextInt(counter);
				if (shuffler < mDeck.size()) {
					hand1.put((String) arry[shuffler], mDeck.get(arry[shuffler]));
					mDeck.remove(arry[shuffler]);
					arry = mDeck.keySet().toArray();
					counter --;
					error = false;
				} else{
					error = true;//if random number fails as index, will simply retry until successfull
				}
			}
			while (error == false) {
				shuffler = rando.nextInt(counter);
				if (shuffler < mDeck.size()) {
					// pulls a random card from the main deck and places it in hand2
					hand2.put((String) arry[shuffler], mDeck.get(arry[shuffler]));
					mDeck.remove(arry[shuffler]);
					arry = mDeck.keySet().toArray();
					counter --;
					error = true;
				} else {
					error = false;}
			}
		}
		List<Map<String, Integer>> hands = new ArrayList<Map<String, Integer>>();
		//allows this method to produce two separate hands with no duplicates and no missing cards
		hands.add(hand1);
		hands.add(hand2);
		return hands;

	}
	
	private static void play(Player player1, Player player2) {
		System.out.println(player1.getName() + " vs " + player2.getName());
		System.out.println("\npress enter to begin\n\n or enter 'x' to quit");
		String choice = kb.nextLine();
		if (choice.contains("x")){
			System.out.println("Good Bye!");
			System.exit(0);
		}
		draw(player1,player2);
		win(player1,player2);

	}
	
	private static void draw(Player player1, Player player2) {
		Card p1 = new Card();
		Card p2 = new Card();
		
		int pile = player1.getHand().size();
		HashMap<String, Integer> hand1 = player1.getHand();
		HashMap<String, Integer> hand2 = player2.getHand();
		
		Object[] arry1 = hand1.keySet().toArray();
		Object[] arry2 = hand2.keySet().toArray();
		
		while (pile > 0) {
			//shuffles deck to ensure random outcome
			int shuffler = rando.nextInt(pile); 
			//recreates P1 card for specific battle
			p1.setFace(arry1[shuffler].toString());
			p1.setValue((int) hand1.get(arry1[shuffler].toString()));
			
			//recreates P2 card for specific battle
			p2.setFace(arry2[shuffler].toString());
			p2.setValue((int) hand2.get(arry2[shuffler].toString()));
			
			
			int d1 = p1.getValue();
			int d2 = p2.getValue();
			//displays battle outcome
			System.out.println(player1.getName() + " draws: " + p1.getFace());
			System.out.println(player2.getName() + " draws: " + p2.getFace());

			//determines victory for battle
			if (d1 == d2){
				
				//System.out.println(d1 + "==" + d2); //tested to check if the card builder was accurately valuing cards
				System.out.println("DRAW!");
			} else if (d1 > d2) {
				System.out.println(player1.getName() + " beats " + player2.getName() + "!");
				player1.setScore(player1.getScore()+1);
			} else if (d1 < d2) {
				System.out.println(player2.getName() + " beats " + player1.getName() + "!");
				player2.setScore(player2.getScore()+1);
			} else {
				System.out.println("there is an error");
				break;
			}
			//discards used cards
			hand1.remove(p1.getFace());
			hand2.remove(p2.getFace());
			arry1 = hand1.keySet().toArray();
			arry2 = hand2.keySet().toArray();
			//declares running status
			System.out.println("The score is now: " + player1.getScore() + " to " + player2.getScore());
			pile = pile-1;
		}

	}
	
	private static void win(Player player1, Player player2) {
		//determines victor for game
		System.out.println("The final scores are: \n   " +player1.getName() + ": " + player1.getScore() +"\n   " + player2.getName() + ": " + player2.getScore());
		if ( player1.getScore() == player2.getScore()) {
			System.out.println("This round was a draw. Better luck next time.");
		} else if ( player1.getScore() > player2.getScore()) {
			System.out.println(player1.getName() + " wins!\n");
		} else if (player1.getScore() < player2.getScore()) {
			System.out.println(player2.getName() + " wins!\n");
		}
	}

	public static void printDeck(HashMap<String,Integer> deck) {
		//use for printing deck to test proper shuffling
		for (String key : deck.keySet()) {
			System.out.println(key + " = " + deck.get(key));
		}
	}
}
