package game;

import gui.New_Game;

public class Online_Game {
	public static boolean accept = false;
	public static boolean conneciton = true;

	public Online_Game() {
		game.Main.socket = new socket();

		if (game.Main.s == null) {
			new New_Game();

		}
	}

	public static void P1(String p1) {
		if (p1.length() != 0) {
			// ex!@~$1#11
			if (p1.charAt(6) == '1') {
				Main.ng.setBounds(0, 0, 960, 1030);
				Main.app_mode = 1;
			}

			else if (p1.charAt(6) == '2') {
				Main.ng.setBounds(960, 0, 960, 1030);
				Main.app_mode = 2;
			}

			Main.turn = Main.my_color = p1.charAt(7) - '0';

			if (Main.my_color == 1)
				new gui.Timer_Request();

		}
	}

	public static void P2(String p2) {
		// !@~$2#m%n

		Main.m = Double.parseDouble(p2.substring(p2.indexOf("#") + 1, p2.indexOf("%")));
		Main.n = Double.parseDouble(p2.substring(p2.indexOf("%") + 1, p2.length()));

		Main.my_remaining_time = (long) (Main.m * 60 * 1000);// in milliseconds
		Main.other_remaining_time = (long) (Main.m * 60 * 1000);// in milliseco
		game.Main.bord = new gui.Bord();

	}
}
