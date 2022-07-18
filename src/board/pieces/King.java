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
public class King extends Pieces {
	public static char name = 'K';

	public static Image white_image = toolkit.getImage("img/pieces/White_King.png");
	public static Image black_image = toolkit.getImage("img/pieces/Black_King.png");
	public static Image white_image30 = toolkit.getImage("img/pieces/30/wk30.png");
	public static Image black_image30 = toolkit.getImage("img/pieces/30/bk30.png");

	public static ArrayList<String> pieces_moves_ = new ArrayList<String>(
			Arrays.asList("0,-1", "1,-1", "1,0", "1,1", "0,1", "-1,1", "-1,0", "-1,-1"));;
			public static ArrayList<String> pieces_eat_ = pieces_moves_;

			///////// 0 color for black and 1 for color white
			///////// r location for right and l location for left
			public King(int color, String location) {
				super(name, color, location, white_image, black_image, white_image30, black_image30, pieces_moves_,
						pieces_eat_);
			}
}
