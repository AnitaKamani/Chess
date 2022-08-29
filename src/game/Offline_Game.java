package game;

import java.util.Random;

import gui.Timer_Request;

public class Offline_Game {
	public Offline_Game() {
		game.Main.reset_values();	
		Random rand = new Random();
		game.Main.turn=game.Main.my_color=rand.nextInt(2);
		
		new Timer_Request();
	}
}
