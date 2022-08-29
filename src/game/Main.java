package game;

import java.net.Socket;

public class Main {
	public static int port=8931;
	public static socket socket;
	public static gui.Bord bord=null;
	public static gui.Draw_Offer offer=null;
	
	
	public static Socket s;
	////
	public static int app_mode=0; // 1 as first player and 2 for 2nd player
	public static gui.New_Game ng = null;
	public static int my_color; // me// 0 for black and 1 for white // white player will choose m/n times and
	// starts
	public static String my_name="";// me//first player in offline mode board will be on his/her side
	public static String other_name="";// other
	public static board.pieces.Pieces[][] table_pieces;
	public static String[][] eaten_list;
	///
	////////// timers
	public static double m; // Minutes per side
	public static double n; // Increment in seconds
	// Remaining times
	public static long my_remaining_time;// in milliseconds
	public static long other_remaining_time;// in milliseconds
	////////
	///
	public static int turn;///// 1=me//0=other//first turn is for white player
	public static int move_count;
	public static int eaten_me;
	public static int eaten_other;
	/////////////////////// our king is a vital piece for us so we need to know it's
	/////////////////////// exact position
	public static String my_king_position;
	public static String other_king_position;
	////
	/////////// Useful for checking the state of the game
	public static boolean my_check;
	public static boolean other_check;
	public static boolean my_stalement;
	public static boolean other_stalement;
	public static boolean draw_offer;
	//
	public static boolean first_time_connection = true;
	public static gui.EndGame end_game;

	public static void main(String[] args) {
		new gui.Main_Menu();
	}

	public static void reset_values() {
		socket = null;
		s=null;
		app_mode = 0; // 1 as first player and 2 for 2nd player
		my_color = 1; // me// 0 for black and 1 for white // white player will choose m/n times and
		// starts
		table_pieces = new board.pieces.Pieces[8][8];
		eaten_list = new String[2][16];
		///
		////////// timers
		m = 5; // Minutes per side
		n = 8; // Increment in seconds
		// Remaining times
		my_remaining_time = (long) (m * 60 * 1000);// in milliseconds
		other_remaining_time = (long) (m * 60 * 1000);// in milliseconds
		draw_offer=false;
		////////
		///
		turn = 1;///// 1=me//0=other//first turn is for white player
		move_count = 0;
		eaten_me = -1;
		eaten_other = -1;
		/////////////////////// our king is a vital piece for us so we need to know it's
		/////////////////////// exact position
		my_king_position = "74";
		other_king_position = "04";
		////
		/////////// Useful for checking the state of the game
		my_check = false;
		other_check = false;
		my_stalement = false;
		other_stalement = false;
		//
		first_time_connection = true;
		try {
		end_game.stop();
		end_game.f.dispose();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		gui.Bord.reset_values();
	}
}