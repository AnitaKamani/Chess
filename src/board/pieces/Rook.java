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
public class Rook extends Pieces {
	public static char name = 'R';

	public static Image white_image = toolkit.getImage("img/pieces/White_Rook.png");
	public static Image black_image = toolkit.getImage("img/pieces/Black_Rook.png");
	public static Image white_image30 = toolkit.getImage("img/pieces/30/wr30.png");
	public static Image black_image30 = toolkit.getImage("img/pieces/30/br30.png");

	public static ArrayList<String> pieces_moves_ = new ArrayList<String>();
	public static ArrayList<String> pieces_eat_ = new ArrayList<String>();

	///////// 0 color for black and 1 for color white
	///////// r location for right and l location for left
	public Rook(int color, String location) {
		super(name, color, location, white_image, black_image, white_image30, black_image30, pieces_moves_,
				pieces_eat_);
		pieces_moves_ = new ArrayList<String>();
		for (int j = 1; j <= 7; j++)
			this.pieces_moves.addAll(Arrays.asList("0,-" + j, j + ",0", "0," + j, -j + ",0"));
		this.pieces_eat = this.pieces_moves;

	}

}
