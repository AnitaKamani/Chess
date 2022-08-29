/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package board.pieces;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 *
 * @author Anita
 */
public class Pieces {

	public ArrayList<String> pieces_moves = new ArrayList<String>();
	public ArrayList<String> pieces_eat = new ArrayList<String>();

	public static Toolkit toolkit = Toolkit.getDefaultToolkit();
	// public static int move_cocunt = 0;
	public char name;
	public int color;
	public String location;
	public Image image;
	public Image image30;

	public Image white_image;
	public Image white_image30;
	public Image black_image;
	public Image black_image30;

	public String defenition = "";
	public int move_count = 0;

	//////////////////////////////////
	public static boolean search_run_first_time = true;

	public Pieces(char nam, int col, String loc, Image w_i, Image b_i, Image w_i30, Image b_i30,
			ArrayList<String> moves, ArrayList<String> eats) {
		name = nam;
		color = col;
		location = loc;
		white_image = w_i;
		white_image30 = w_i30;
		black_image = b_i;
		black_image30 = b_i30;

		image = (col == 1 ? w_i : b_i);
		image30 = (col == 1 ? w_i30 : b_i30);

		pieces_moves = moves;
		pieces_eat = eats;
		defenition = (color) + String.valueOf(nam) + (loc);

	}

	public static Pieces create_new_piece(String type, int col, String loc) {
		///////////// check the tests
		if (type.equals("K"))
			return new King(col, loc);
		else if (type.equals("Q"))
			return new Queen(col, loc);
		else if (type.equals("R"))
			return new Rook(col, loc);
		else if (type.equals("N"))
			return new Knight(col, loc);
		else if (type.equals("B"))
			return new Bishop(col, loc);
		else
			return new Pawn(col, loc);
	}

	public static ArrayList<String> move_posibility(Pieces piece) {
		ArrayList<String> possible_moves = new ArrayList<String>();
		///// we will do this for each piece but Knight and Pawn
		//////////////////////////// is this piece able to move or not
		//////////////////// 1st normal situation only check the learned moves
		/////////////// 2nd king-rook
		////////// 3rd upgrade
		//// 4th check-/mate
		//
		//
		//
		//
		//
		//

		int ii = Integer.parseInt(piece.location.substring(0, 1));
		int jj = Integer.parseInt(piece.location.substring(1, 2));
		if (piece.name != 'N') {
			for (String x : piece.pieces_moves) {

				boolean ok = true;
				int ii2 = Integer.parseInt(x.substring(0, x.indexOf(",")));
				int jj2 = Integer.parseInt(x.substring(x.indexOf(",") + 1, x.length()));
				if (ii2 == 0) {
					for (int j = 1; j <= Math.abs(jj2); j++) {

						int j3 = jj + j * jj2 / Math.abs(jj2);
						if (0 <= j3 && j3 < 8) {
							if (j != Math.abs(jj2) && game.Main.table_pieces[ii][j3] != null) {
								ok = false;
							}

							if (j == Math.abs(jj2)) {
								if (game.Main.table_pieces[ii][j3] != null
										&& game.Main.table_pieces[ii][j3].color == piece.color) {
									ok = false;

								}
							}

						} else {
							ok = false;
						}
					}
				} else if (jj2 == 0) {
					for (int i = 1; i <= Math.abs(ii2); i++) {

						int i3 = ii + i * ii2 / Math.abs(ii2);
						if (0 <= i3 && i3 < 8) {
							if (i != Math.abs(ii2) && game.Main.table_pieces[i3][jj] != null) {
								ok = false;
							}

							if (i == Math.abs(ii2)) {
								if (game.Main.table_pieces[i3][jj] != null
										&& game.Main.table_pieces[i3][jj].color == piece.color
										|| piece.name == 'P' && game.Main.table_pieces[i3][jj] != null) {
									ok = false;

								}
							}
						} else {
							ok = false;
						}

					}
				}

				else {
					for (int i = 1, j = 1; i <= Math.abs(ii2); i++, j++) {

						int i3 = ii + i * ii2 / Math.abs(ii2);
						int j3 = jj + j * jj2 / Math.abs(jj2);

						if (0 <= i3 && i3 < 8 && 0 <= j3 && j3 < 8) {
							if (i != Math.abs(ii2) && game.Main.table_pieces[i3][j3] != null) {
								ok = false;
							}

							if (i == Math.abs(ii2)) {
								if (game.Main.table_pieces[i3][j3] != null
										&& game.Main.table_pieces[i3][j3].color == piece.color) {
									ok = false;

								}
							}
						} else {
							ok = false;
						}

					}
				}

				if (ok == true)
					possible_moves.add(x);
			}
		}

		else if (piece.name == 'N')

		{
			for (String x : piece.pieces_moves) {

				boolean ok = false;
				int ii2 = Integer.parseInt(x.substring(0, x.indexOf(",")));
				int jj2 = Integer.parseInt(x.substring(x.indexOf(",") + 1, x.length()));

				int i3 = ii + ii2;
				int j3 = jj + jj2;
				if (0 <= i3 && i3 < 8 && 0 <= j3 && j3 < 8) {
					if (game.Main.table_pieces[i3][j3] == null) {
						ok = true;
					} else if (game.Main.table_pieces[i3][j3].color != piece.color) {
						ok = true;
					}

				} else {
					ok = false;
				}
				if (ok == true)
					possible_moves.add(x);
			}

		}

		/// now we will get to pawn eating another piece
		if (piece.name == 'P')

		{
			for (String x : piece.pieces_eat) {

				boolean ok = false;
				int ii2 = Integer.parseInt(x.substring(0, x.indexOf(",")));
				int jj2 = Integer.parseInt(x.substring(x.indexOf(",") + 1, x.length()));

				int i3 = ii + ii2;
				int j3 = jj + jj2;
				if (0 <= i3 && i3 < 8 && 0 <= j3 && j3 < 8) {
					if (game.Main.table_pieces[i3][j3] == null) {
						ok = false;
					} else if (game.Main.table_pieces[i3][j3].color != piece.color) {
						ok = true;
					}

				} else {
					ok = false;
				}
				if (ok == true)
					possible_moves.add(x);
			}

			////////// here we remove the ability to move for 2 squares for a pawn who has
			////////// moved at least once
			if (piece.move_count >= 1) {
				try {
					possible_moves.remove("-2,0");

				} catch (Exception e) {
				}
				try {
					possible_moves.remove("2,0");
				} catch (Exception e) {
				}
			}

		}

		if (piece.name == 'K') {
			int iking = Integer.parseInt(piece.location.substring(0, 1));
			int jking = Integer.parseInt(piece.location.substring(1, 2));

			{

				if (piece.move_count == 0 && (game.Main.turn == 1 && game.Main.my_check == false
						|| game.Main.turn == 0 && game.Main.other_check == false))

				{

					if (game.Main.table_pieces[iking][jking + 1] == null
							&& game.Main.table_pieces[iking][jking + 2] == null
							&& game.Main.table_pieces[iking][jking + 3] != null
							&& game.Main.table_pieces[iking][jking + 3].name == 'R'
							&& game.Main.table_pieces[iking][jking + 3].move_count == 0) {
						possible_moves.add("0,2");
					}

					if (game.Main.table_pieces[iking][jking - 1] == null
							&& game.Main.table_pieces[iking][jking - 2] == null
							&& game.Main.table_pieces[iking][jking - 3] == null
							&& game.Main.table_pieces[iking][jking - 4] != null
							&& game.Main.table_pieces[iking][jking - 4].name == 'R'
							&& game.Main.table_pieces[iking][jking - 4].move_count == 0) {
						possible_moves.add("0,-2");
					}
				}

			}

		}

		if (search_run_first_time == true) {
			search_run_first_time = false;

			int ia = Integer.parseInt(piece.location.substring(0, 1));
			int ja = Integer.parseInt(piece.location.substring(1, 2));

			for (int q = 0; q < possible_moves.size();) {
				String x = possible_moves.get(q);
				boolean may = true;
				int di = Integer.parseInt(x.substring(0, x.indexOf(",")));
				int dj = Integer.parseInt(x.substring(x.indexOf(",") + 1, x.length()));

				// do the move to see the result and keep the move
				Pieces eaten_piece = move_for_test(game.Main.table_pieces[ia][ja], ia, ja, di, dj);

				for (int w = 0; w < 8 && may == true; w++)
					for (int z = 0; z < 8 && may == true; z++) {

						if (game.Main.table_pieces[w][z] != null && may == true) {
							if ((game.Main.turn == 0 && game.Main.table_pieces[w][z].color == game.Main.my_color
									|| game.Main.turn == 1 && game.Main.table_pieces[w][z].color != game.Main.my_color)
									&& may == true)

							{
								Pieces piece2 = game.Main.table_pieces[w][z];
								ArrayList<String> possible_moves2 = move_posibility(piece2);

								for (String y : possible_moves2) {
									if (may == true) {
										int ii2 = Integer.parseInt(piece2.location.substring(0, 1));
										int jj2 = Integer.parseInt(piece2.location.substring(1, 2));

										int dii2 = Integer.parseInt(y.substring(0, y.indexOf(",")));
										int djj2 = Integer.parseInt(y.substring(y.indexOf(",") + 1, y.length()));

										ii2 = ii2 + dii2;
										jj2 = jj2 + djj2;

										String l2 = String.valueOf(ii2) + String.valueOf(jj2);

										if (game.Main.turn == 1 && l2.equals(game.Main.my_king_position)
												|| game.Main.turn == 0 && l2.equals(game.Main.other_king_position)) {
											may = false;
											possible_moves.remove(x);
										}

									}
								}
							}

						}
					}

				//// reverse the test move

				reverse_move_for_test(game.Main.table_pieces[ia + di][ja + dj], eaten_piece, ia + di, ja + dj, di, dj);

				if (may)
					q++;
			}
			search_run_first_time = true;
		}
		return possible_moves;

	}

	private static Pieces move_for_test(Pieces piece, int ia, int ja, int di, int dj) {

		int ib = ia + di;
		int jb = ja + dj;
		String p2 = String.valueOf(ib) + String.valueOf(jb);

		/// cloning the first piece
		board.pieces.Pieces piece_a = deep_clone(ia, ja, p2, 1);

		if (piece_a.name == 'K')
			if (game.Main.turn == 1)
				game.Main.my_king_position = p2;
			else if (game.Main.turn == 0)
				game.Main.other_king_position = p2;
		try {
			board.pieces.Pieces piece_b = deep_clone(ib, jb, game.Main.table_pieces[ib][jb].location, 0);
			game.Main.table_pieces[ib][jb] = piece_a;
			game.Main.table_pieces[ia][ja] = null;
			return piece_b;

		} catch (Exception e) {
			game.Main.table_pieces[ib][jb] = piece_a;
			game.Main.table_pieces[ia][ja] = null;
			return null;
		}

	}

	private static void reverse_move_for_test(Pieces piece, Pieces eaten, int ia, int ja, int di, int dj) {
		int ib = ia - di;
		int jb = ja - dj;

		String p2 = String.valueOf(ib) + String.valueOf(jb);

		/// cloning the piece
		board.pieces.Pieces piece_a = deep_clone(ia, ja, p2, -1);

		game.Main.table_pieces[ib][jb] = piece_a;
		game.Main.table_pieces[ia][ja] = null;
		game.Main.table_pieces[ia][ja] = eaten;

		if (piece_a.name == 'K')
			if (game.Main.turn == 1)
				game.Main.my_king_position = p2;
			else if (game.Main.turn == 0)
				game.Main.other_king_position = p2;

	}

	public static Pieces deep_clone(int ia, int ja, String new_loc, int count_increasment) {

		Pieces piece_a = new Pieces(game.Main.table_pieces[ia][ja].name, game.Main.table_pieces[ia][ja].color, new_loc,
				game.Main.table_pieces[ia][ja].white_image, game.Main.table_pieces[ia][ja].black_image,
				game.Main.table_pieces[ia][ja].white_image30, game.Main.table_pieces[ia][ja].black_image30,
				game.Main.table_pieces[ia][ja].pieces_moves, game.Main.table_pieces[ia][ja].pieces_eat);
		piece_a.move_count = game.Main.table_pieces[ia][ja].move_count;

		piece_a.move_count += count_increasment;

		return piece_a;
	}
}