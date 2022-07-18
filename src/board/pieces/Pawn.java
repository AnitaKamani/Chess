/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package board.pieces;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Anita
 */
public class Pawn extends Pieces {
	public static char name = 'P';
	/// we will delete "0,2 " and "0,-2" after first move
	public static Image white_image = toolkit.getImage("img/pieces/White_Pawn.png");
	public static Image black_image = toolkit.getImage("img/pieces/Black_Pawn.png");
	public static Image white_image30 = toolkit.getImage("img/pieces/30/wp30.png");
	public static Image black_image30 = toolkit.getImage("img/pieces/30/bp30.png");

	public static ArrayList<String> pieces_moves_W = new ArrayList<String>(Arrays.asList("-1,0", "-2,0"));
	public static ArrayList<String> pieces_eat_W = new ArrayList<String>(Arrays.asList("-1,1", "-1,-1"));
	public static ArrayList<String> pieces_moves_B = new ArrayList<String>(Arrays.asList("1,0", "2,0"));
	public static ArrayList<String> pieces_eat_B = new ArrayList<String>(Arrays.asList("1,1", "1,-1"));

	public Pawn(int color, String location) {
		super(name, color, location, white_image, black_image, white_image30, black_image30,
				color == 1 ? pieces_moves_W : pieces_moves_B, color == 1 ? pieces_eat_W : pieces_eat_B);
	}

}
