package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.basic.BasicScrollBarUI;

import game.socket;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Bord extends JFrame implements MouseListener {
	public static int i1 = -1, j1 = -1, i2 = -1, j2 = -1; // clicked first and second
	static int ie = -1;// mouse entered
	static int je = -1;
	private static Rectangle first_bounds = null;
	static boolean animation_lock = false;
	public static int xa = -1, ya = -1, xb = -1, yb = -1, ii = -1, jj = -1, Y = 0, X = 0;
	private static JButton[][] tiles = new JButton[8][8];
	public static JLabel[][] pieces_labels = new JLabel[8][8];
	private static JLabel[][] notification_labels = new JLabel[8][8];
	private static JLabel[][] last_and_check_state_labels = new JLabel[8][8];
	private static JLabel[][] eaten_pieces_labels = new JLabel[2][16];
	public static JLabel my_timer_label = new JLabel("00:00");
	private static JLabel other_timer_label = new JLabel("00:00");
	private static JLabel waiting_gif_label = new JLabel("");
	private static JPanel tiles_panel = new JPanel();
	private static JPanel tiles_panel_1;
	private static JPanel notification_panel = new JPanel();
	private static JPanel check_state_panel = new JPanel();
	private static JPanel eated_pieces_panel = new JPanel();
	private static JPanel eated_pieces_panel_1;
	private static Timer time_timer;
	private static Timer animation_timer;
	public static String Type;
	private static JLayeredPane layeredPane = new JLayeredPane();
	public static long remaining = game.Main.my_remaining_time;
	public static int upgrade = -1;
	private static int castling = 0;
	public static String sending_line = "";
	public static String history = "";
	public static NumberFormat format;
	public static int counter = 0;
	JPanel contentPane;
	private final Action offering_draw = new DrawOffer();
	private final Action givving_time = new TimeGivness();
	public static JTextArea history_jta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bord frame = new Bord();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Bord() {
		reset_values();

		game.Main.my_name = "P1";
		game.Main.other_name = "P2";

		if (game.Main.app_mode == 2) {
			game.Main.my_name = "P2";
			game.Main.other_name = "P1";
		}

		game.Main.end_game = new EndGame();
		reset_game_table();// resetting the game table
		Bord.king_position();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				////
				JButton button = new JButton();
				button.setBounds(j * 100, i * 100, 100, 100);
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				button.setFocusPainted(false);
				////
				JLabel label = new JLabel();
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setVerticalAlignment(JLabel.CENTER);
				label.setBounds(j * 100, i * 100, 100, 100);
				label.setOpaque(false);
				////
				JLabel label2 = new JLabel();
				label2.setHorizontalAlignment(JLabel.CENTER);
				label2.setVerticalAlignment(JLabel.CENTER);
				label2.setBounds(j * 100, i * 100, 100, 100);
				label2.setOpaque(false);
				///////
				JLabel label3 = new JLabel();
				label3.setHorizontalAlignment(JLabel.CENTER);
				label3.setVerticalAlignment(JLabel.CENTER);
				label3.setBounds(j * 100, i * 100, 100, 100);
				label3.setOpaque(false);
				///////
				if (game.Main.table_pieces[i][j] != null) {
					label.setIcon(new ImageIcon(game.Main.table_pieces[i][j].image));
				}
				//////
				if (game.Main.my_color == 0) {
					button.setBounds((7 - j) * 100, (7 - i) * 100, 100, 100);
					label.setBounds((7 - j) * 100, (7 - i) * 100, 100, 100);
					label2.setBounds((7 - j) * 100, (7 - i) * 100, 100, 100);
					label3.setBounds((7 - j) * 100, (7 - i) * 100, 100, 100);
				}
				///
				pieces_labels[i][j] = label;
				layeredPane.add(pieces_labels[i][j]);
				///
				tiles[i][j] = button;
				tiles[i][j].addMouseListener(this);
				tiles_panel.add(tiles[i][j]);
				//
				notification_labels[i][j] = label2;
				notification_panel.add(notification_labels[i][j]);
				//
				last_and_check_state_labels[i][j] = label3;
				check_state_panel.add(last_and_check_state_labels[i][j]);
			}
		}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 16; j++) {
				JLabel label3 = new JLabel();
				label3.setHorizontalAlignment(JLabel.CENTER);
				label3.setVerticalAlignment(JLabel.CENTER);
				label3.setOpaque(false);
				if (i == 0)
					label3.setBounds(925, j * 40 + 30, 30, 30);
				else if (i == 1)
					label3.setBounds(5, j * 40 + 30, 30, 30);
				eaten_pieces_labels[i][j] = label3;
				eated_pieces_panel.add(eaten_pieces_labels[i][j]);
			}
		format = NumberFormat.getNumberInstance();
		format.setMinimumIntegerDigits(2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Bord.CustomCursor(this, 1);// set cursor
		setBounds(480, 0, 960, 1030);
		if (game.Main.app_mode == 1)
			setBounds(0, 0, 960, 1030);
		else if (game.Main.app_mode == 2)
			setBounds(960, 0, 960, 1030);
		setUndecorated(true);
		getContentPane().setLayout(null);
		JLabel background = new JLabel("");
		background.setBorder(null);
		background.setIcon(new ImageIcon("img/background/main_board.png"));
		JLabel background_numbs = new JLabel("");
		background_numbs
		.setIcon(new ImageIcon("img/background/board_background_white_player.png"));
		if (game.Main.my_color == 0)
			background_numbs
			.setIcon(new ImageIcon("img/background/board_background_black_player.png"));
		game.Main.other_remaining_time = (long) (game.Main.m * 60 * 1000);
		int minutes2 = (int) (game.Main.other_remaining_time / 60000);
		int seconds2 = (int) ((game.Main.other_remaining_time % 60000) / 1000);
		/////
		game.Main.my_remaining_time = (long) (game.Main.m * 60 * 1000);
		int minutes1 = (int) (game.Main.my_remaining_time / 60000);
		int seconds1 = (int) ((game.Main.my_remaining_time % 60000) / 1000);
		layeredPane.setBounds(80, 75, 800, 800);
		//
		//
		tiles_panel.setOpaque(false);
		tiles_panel.setBorder(null);
		tiles_panel.setBounds(80, 75, 800, 800);
		tiles_panel.setLayout(null);
		//
		//
		notification_panel.setOpaque(false);
		notification_panel.setBorder(null);
		notification_panel.setBounds(80, 75, 800, 800);
		notification_panel.setLayout(null);
		//
		//
		check_state_panel.setOpaque(false);
		check_state_panel.setBorder(null);
		check_state_panel.setBounds(80, 75, 800, 800);
		check_state_panel.setLayout(null);
		//
		//
		eated_pieces_panel.setOpaque(false);
		eated_pieces_panel.setBorder(null);
		eated_pieces_panel.setBounds(0, 0, 960, 1030);
		eated_pieces_panel.setLayout(null);
		////
		/////
		JPanel other_buttons = new JPanel();
		other_buttons.setOpaque(false);
		other_buttons.setBounds(880, 915, 40, 115);
		getContentPane().add(other_buttons);
		other_buttons.setLayout(null);
		///////

		////
		JPanel history_panel = new JPanel();
		history_panel.setBounds(425, 925, 265, 100);
		history_panel.setBorder(null);
		getContentPane().add(history_panel);
		history_panel.setOpaque(false);
		history_panel.setLayout(new BoxLayout(history_panel, BoxLayout.X_AXIS));
		history_panel.setBackground(new Color(0, 0, 0, 0));

		history_jta = new JTextArea("");
		history_jta.setOpaque(false);
		history_panel.add(history_jta);
		history_jta.setEditable(false);
		history_jta.setForeground(new Color(255, 254, 254));
		history_jta.setBorder(null);
		history_jta.setFont(new Font("Segoe Print", Font.PLAIN, 20));
		history_jta.setHighlighter(null);
		///////////
		//////////
		////////
		JScrollPane sp = new JScrollPane(history_jta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBackground(new Color(0, 0, 0, 0));
		sp.setOpaque(false);
		sp.getViewport().setOpaque(false);
		sp.setBorder(null);
		UIManager.put("ScrollBar.thumb", new ColorUIResource(new Color(0, 0, 0)));
		UIManager.put("ScrollBar.track", (new Color(50, 50, 50)));
		UIManager.put("ScrollBar.thumbShadow", (new Color(60, 60, 60)));
		UIManager.put("ScrollBar.thumbDarkShadow", (new Color(40, 40, 40)));
		UIManager.put("ScrollBar.thumbHighlight", (new Color(80, 80, 80)));

		sp.getVerticalScrollBar().setUI(new BasicScrollBarUI());
		history_panel.add(sp);
		/////////
		///
		////
		JButton offer_draw_button = new JButton("");
		offer_draw_button.setAction(offering_draw);
		if (game.Main.app_mode != 0)
			offer_draw_button.setIcon(new ImageIcon("img/background/offer_draw.png"));
		else
			offer_draw_button.setIcon(new ImageIcon("img/background/home.png"));
		offer_draw_button.setBorderPainted(false);
		offer_draw_button.setOpaque(false);
		offer_draw_button.setContentAreaFilled(false);
		offer_draw_button.setBounds(0, 11, 40, 40);
		offer_draw_button.setFont(new Font("Segoe Print", Font.PLAIN, 17));
		offer_draw_button.setForeground(Color.WHITE);
		other_buttons.add(offer_draw_button);
		/////
		/////
		JButton give_time_button = new JButton("");
		give_time_button.setAction(givving_time);
		give_time_button.setIcon(new ImageIcon("img/background/Plus.png"));
		give_time_button.setBorderPainted(false);
		give_time_button.setOpaque(false);
		give_time_button.setContentAreaFilled(false);
		give_time_button.setBounds(0, 65, 40, 40);
		give_time_button.setFont(new Font("Segoe Print", Font.PLAIN, 17));
		give_time_button.setForeground(Color.WHITE);
		other_buttons.add(give_time_button);
		///
		///
		getContentPane().add(tiles_panel);// buttons
		getContentPane().add(layeredPane);// pieces
		getContentPane().add(notification_panel);/// notification
		getContentPane().add(check_state_panel);/// check state
		getContentPane().add(eated_pieces_panel);/// eated
		//////////
		//
		//// waiting gif
		/////
		waiting_gif_label.setIcon(new ImageIcon("img/background/ring.gif"));
		waiting_gif_label.setBounds(44, 925, 40, 40);
		getContentPane().add(waiting_gif_label);
		///////////////
		////// shwoing king as each side color
		///
		JLabel black_king_color = new JLabel("");
		black_king_color.setIcon(new ImageIcon("img/background/Black_King2.png"));
		black_king_color.setBounds(96, 926, 40, 40);
		getContentPane().add(black_king_color);
		JLabel other_name = new JLabel(game.Main.other_name);
		other_name.setFont(new Font("Segoe Print", Font.PLAIN, 19));
		other_name.setForeground(Color.WHITE);
		other_name.setBounds(260, 926, 120, 40);
		getContentPane().add(other_name);
		////
		other_timer_label.setFont(new Font("Segoe Print", Font.PLAIN, 19));
		other_timer_label.setForeground(Color.WHITE);
		if (game.Main.other_remaining_time < 10500)
			other_timer_label.setForeground(Color.red);
		other_timer_label.setBorder(null);
		other_timer_label.setBounds(158, 926, 100, 40);
		other_timer_label.setText(format.format(minutes2) + ":" + format.format(seconds2));
		getContentPane().add(other_timer_label);
		/////
		//////
		////
		JLabel white_king_color = new JLabel("");
		white_king_color.setIcon(new ImageIcon("img/background/White_King2.png"));
		white_king_color.setBounds(96, 980, 40, 40);
		getContentPane().add(white_king_color);
		////
		JLabel my_name = new JLabel(game.Main.my_name);
		my_name.setFont(new Font("Segoe Print", Font.PLAIN, 19));
		my_name.setForeground(Color.WHITE);
		my_name.setBounds(260, 980, 120, 40);
		getContentPane().add(my_name);
		///
		my_timer_label.setFont(new Font("Segoe Print", Font.PLAIN, 19));
		my_timer_label.setForeground(Color.WHITE);
		if (game.Main.my_remaining_time < 10500)
			my_timer_label.setForeground(Color.red);
		my_timer_label.setBorder(null);
		my_timer_label.setBounds(158, 980, 100, 40);
		my_timer_label.setText(format.format(minutes1) + ":" + format.format(seconds1));
		getContentPane().add(my_timer_label);
		////
		background_numbs.setBounds(0, 0, 960, 1030);
		background.setBounds(0, 0, 960, 1030);
		getContentPane().add(background_numbs);
		getContentPane().add(background);
		/////
		///// placing the right color
		if (game.Main.my_color == 1) {
			black_king_color.setBounds(96, 926, 40, 40);
			white_king_color.setBounds(96, 980, 40, 40);
		} else {
			black_king_color.setBounds(96, 980, 40, 40);
			white_king_color.setBounds(96, 926, 40, 40);
		}
		if (game.Main.turn == 1)
			waiting_gif_label.setBounds(44, 980, 40, 40);
		else
			waiting_gif_label.setBounds(44, 926, 40, 40);
		////// setting the frame visible
		setVisible(true);
		/////////////// d////////////////////d//////
		/////////////// /////////////////////////////////////////////////////
		/////////////// /////////////////// /////////////////////////////////
		/////////////// ////////////////////////////////////
		/////////////// ///////////////////////////////////////////////
		///////////////////
		// setpieces();
		/* dfgdg */
		////////////////////////////
		////////////////////
	}

	static void updateDisplay() {
		Timeclass tc = new Timeclass();
		time_timer = new Timer(1000, tc);
		if (game.Main.turn == 1) {
			remaining = game.Main.my_remaining_time;
		} else if (game.Main.turn == 0) {
			remaining = game.Main.other_remaining_time;
		}
		time_timer.start();
	}

	public static void stop_timer() {
		time_timer.stop();
	}

	public static class Timeclass implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			remaining -= 1000;
			if (remaining < 0)
				remaining = (long) 0;
			int minutes = (int) (remaining / 60000);
			int seconds = (int) ((remaining % 60000) / 1000);
			String st = format.format(minutes) + ":" + format.format(seconds);
			///// setting label timers texts
			if (game.Main.turn == 1) {
				my_timer_label.setText(st);
			} else if (game.Main.turn == 0) {
				other_timer_label.setText(st);
			}
			/////// end condition
			///////////////////////////////////////////////// this will also change the
			/////// turns

			if (game.Main.turn == 1) {
				game.Main.my_remaining_time = remaining;
			} else if (game.Main.turn == 0) {
				game.Main.other_remaining_time = remaining;
			}
			if (remaining < 10500) {
				if (game.Main.turn == 1) {
					my_timer_label.setForeground(Color.red);
				} else if (game.Main.turn == 0) {
					other_timer_label.setForeground(Color.red);
				}
			}
			if (remaining <= 0) {
				stop_timer();
			}
		}
	}

	public static void CustomCursor(Component frame, int option) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("img/background/cursor.png");
		if (option == 1) {
			image = toolkit.getImage("img/background/metal_cross_cursor.png");
		}
		Point point = new Point(0, 0);
		Cursor cursor = toolkit.createCustomCursor(image, point, "cursor");
		frame.setCursor(cursor);
	}

	private void reset_game_table() {
		board.pieces.Pieces[][] table = new board.pieces.Pieces[8][8];
		String[][] tableS = { //
				{ "0R00", "0N01", "0B02", "0Q03", "0K04", "0B05", "0N06", "0R07" },
				{ "0P10", "0P11", "0P12", "0P13", "0P14", "0P15", "0P16", "0P17" },
				{ "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    " },
				{ "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    " },
				{ "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    " },
				{ "    ", "    ", "    ", "    ", "    ", "    ", "    ", "    " },
				{ "1P60", "1P61", "1P62", "1P63", "1P64", "1P65", "1P66", "1P67" },
				{ "1R70", "1N71", "1B72", "1Q73", "1K74", "1B75", "1N76", "1R77" } };
		////// ///// used terms
		// K king
		// Q queen
		// R rook, castle
		// B bishop
		// N knight
		// (P) pawn
		// Check
		// Checkmate/Mate
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!tableS[i][j].contains(" ")) {
					table[i][j] = board.pieces.Pieces.create_new_piece(tableS[i][j].substring(1, 2),
							Integer.parseInt(tableS[i][j].substring(0, 1)), tableS[i][j].substring(2, 4));
				}
			}
		}
		game.Main.table_pieces = table;
	}

	@Override
	public void mouseClicked(MouseEvent eve) {
		if (game.Main.my_remaining_time > 10 && game.Main.other_remaining_time > 10 && remaining > 10) {
			if (game.Main.app_mode == 0 || game.Main.app_mode != 0 && game.Main.turn == 1) {
				if (animation_lock == false) {
					boolean done = false;
					if (i1 != -1) {
						for (String x : board.pieces.Pieces.move_posibility(game.Main.table_pieces[i1][j1])) {
							int ii = Integer.parseInt(x.substring(0, x.indexOf(",")));
							int jj = Integer.parseInt(x.substring(x.indexOf(",") + 1, x.length()));
							for (int i = 0; i < 8; i++)
								for (int j = 0; j < 8; j++) {
									if (eve.getSource() == tiles[i][j]) {
										if (i == i1 + ii && j == j1 + jj) {
											i2 = i;
											j2 = j;
											//// the animation
											move(i1, j1, i2, j2);
											done = true;
										}
									}
								}
						}
					}
					for (int i = 0; i < 8; i++)
						for (int j = 0; j < 8; j++) {
							if (done == false && eve.getSource() == tiles[i][j] && (i1 == -1 || (i1 != i || j1 != j))) {
								for (int w = 0; w < 8; w++)
									for (int z = 0; z < 8; z++) {
										notification_labels[w][z].setIcon(null);
									}
								i1 = -1;
								j1 = -1;
								if (game.Main.table_pieces[i][j] != null
										&& !board.pieces.Pieces.move_posibility(game.Main.table_pieces[i][j]).isEmpty()
										&& (game.Main.turn == 1
										&& game.Main.table_pieces[i][j].color == game.Main.my_color
										|| game.Main.turn == 0
										&& game.Main.table_pieces[i][j].color != game.Main.my_color)) {
									i1 = i;
									j1 = j;
									if ((i + j) % 2 == 0)
										notification_labels[i][j].setIcon(
												new ImageIcon("img/background/WClicked.png"));
									else
										notification_labels[i][j].setIcon(
												new ImageIcon("img/background/BClicked.png"));
									for (String x : board.pieces.Pieces
											.move_posibility(game.Main.table_pieces[i1][j1])) {
										int ii = Integer.parseInt(x.substring(0, x.indexOf(",")));
										int jj = Integer.parseInt(x.substring(x.indexOf(",") + 1, x.length()));
										notification_labels[i + ii][j + jj].setIcon(
												new ImageIcon("img/background/Options.png"));
									}
								}
							}
						}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent eve) {
		if (game.Main.my_remaining_time > 10 && game.Main.other_remaining_time > 10 && remaining > 10) {
			if (animation_lock == false)
				if (game.Main.app_mode == 0 || game.Main.app_mode != 0 && game.Main.turn == 1) {
					for (int i = 0; i < 8; i++)
						for (int j = 0; j < 8; j++) {
							if (eve.getSource() == tiles[i][j]) {
								if (game.Main.table_pieces[i][j] != null) {
									if ((game.Main.turn == 1 && game.Main.table_pieces[i][j].color == game.Main.my_color
											|| game.Main.turn == 0
											&& game.Main.table_pieces[i][j].color != game.Main.my_color)
											&& !board.pieces.Pieces.move_posibility(game.Main.table_pieces[i][j])
											.isEmpty()) {
										ie = i;
										je = j;
										if (ie != i1 || je != j1) {
											notification_labels[i][j].setIcon(
													new ImageIcon("img/background/Hover.png"));
										}
									}
								}
							}
						}
				}
		}

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		try {
			if (ie != i1 || je != j1)
				notification_labels[ie][je].setIcon(null);
			ie = -1;
			je = -1;
		} catch (Exception e) {
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	public static void move(int ia, int ja, int ib, int jb)// moving one piece after all
	{

		//
		////
		/////////
		/////////////////
		////////////////////////////////////////
		////////////////////
		/////
		//
		sending_line = "!@~$200#" + String.valueOf(i1) + "," + String.valueOf(j1) + "," + String.valueOf(i2) + ","
				+ String.valueOf(j2) + "#";
		//
		if (game.Main.app_mode != 0 && game.Main.turn == 1 && castling == 0)
			game.socket.send(sending_line);

		//
		////
		/////////
		/////////////////
		////////////////////////////////////////
		////////////////////
		/////
		//

		try {
			stop_timer();
		} catch (Exception e) {
		}
		ii = ia;
		jj = ja;
		upgrade = -1;
		if (game.Main.table_pieces[ia][ja].defenition.substring(1, 2).equals("P") && (ib == 0 || ib == 7))//// upgrade

		{
			upgrade = 0;
		}

		if (game.Main.table_pieces[ia][ja].name == 'K' && Math.abs(jb - ja) == 2) {

			castling = 1;
		}
		if (castling != 2) {
			for (int w = 0; w < 8; w++)
				for (int z = 0; z < 8; z++) {
					notification_labels[w][z].setIcon(null);
					last_and_check_state_labels[w][z].setIcon(null);
				}
		}
		if (castling == 0) {
			if ((i1 + j1) % 2 == 0)
				last_and_check_state_labels[i1][j1]
						.setIcon(new ImageIcon("img/background/bmove.png"));
			else
				last_and_check_state_labels[i1][j1]
						.setIcon(new ImageIcon("img/background/wmove.png"));

			if ((i2 + j2) % 2 == 0)
				last_and_check_state_labels[i2][j2]
						.setIcon(new ImageIcon("img/background/bmove.png"));
			else
				last_and_check_state_labels[i2][j2]
						.setIcon(new ImageIcon("img/background/wmove.png"));
		} else {
			if ((ib + jb) % 2 == 0)
				last_and_check_state_labels[ib][jb]
						.setIcon(new ImageIcon("img/background/bmove.png"));
			else
				last_and_check_state_labels[ib][jb]
						.setIcon(new ImageIcon("img/background/wmove.png"));
		}

		// now moving the piece
		Animation_Listner listner = new Animation_Listner();
		animation_timer = new Timer(3, listner);
		if (game.Main.my_color == 1) {
			xa = ja * 100;
			ya = ia * 100;
			//
			xb = jb * 100;
			yb = ib * 100;
			Y = ib - ia;
			X = jb - ja;
		} else {
			xa = (7 - ja) * 100;
			ya = (7 - ia) * 100;
			//
			xb = (7 - jb) * 100;
			yb = (7 - ib) * 100;
			Y = ia - ib;
			X = ja - jb;
		}
		first_bounds = pieces_labels[ii][jj].getBounds();
		layeredPane.setLayer(pieces_labels[ii][jj], 1);
		animation_timer.start();
		animation_lock = true;
		// try {
		// lock();
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// the check the check / mate
		// if check is true then don't remove the red icon else remove it after the
		// piece is moved totally
	}

	public static void clone(int ia, int ja, int ib, int jb) {
		pieces_labels[i2][j2].setIcon(pieces_labels[ii][jj].getIcon());
		layeredPane.setLayer(pieces_labels[i2][j2], 1);
		layeredPane.setLayer(pieces_labels[ii][jj], 0);
		pieces_labels[ii][jj].setIcon(null);
		layeredPane.setLayer(pieces_labels[i2][j2], 0);
		pieces_labels[ii][jj].setBounds(first_bounds);
		/// cloning the piece
		board.pieces.Pieces piece = board.pieces.Pieces.deep_clone(ia, ja, String.valueOf(ib) + String.valueOf(jb), 1);
		game.Main.table_pieces[ib][jb] = piece;
		game.Main.table_pieces[ia][ja] = null;
	}

	public static void eaten_list_icon() {
		if (castling == 0) {

			String part1 = "1Q06";
			String abc = "abcdefgh";
			part1 = (game.Main.table_pieces[i1][j1].color == 1 ? "W" : "B") + game.Main.table_pieces[i1][j1].name
					+ abc.charAt(j1) + (8 - i1);

			if (game.Main.table_pieces[i2][j2] != null) {
				if (game.Main.turn == 1) {
					game.Main.eaten_me++;
					eaten_pieces_labels[0][game.Main.eaten_me]
							.setIcon(new ImageIcon(game.Main.table_pieces[i2][j2].image30));
					game.Main.eaten_list[0][game.Main.eaten_me] = game.Main.table_pieces[i2][j2].defenition;
				} else {
					game.Main.eaten_other++;
					eaten_pieces_labels[1][game.Main.eaten_other]
							.setIcon(new ImageIcon(game.Main.table_pieces[i2][j2].image30));
					game.Main.eaten_list[1][game.Main.eaten_other] = game.Main.table_pieces[i2][j2].defenition;
				}

				history = part1 + " * " + (game.Main.table_pieces[i2][j2].color == 1 ? "W" : "B")
						+ game.Main.table_pieces[i2][j2].name +abc.charAt(j2) + (8 - i2);
			}

			else {
				history = part1 + " - " + abc.charAt(j2) + (8 - i2);

			}
		}
	}

	public static class Animation_Listner implements ActionListener {
		public static int pps = 4;

		public void actionPerformed(ActionEvent e) {
			boolean NN = false;// not knight
			///////////
			if (Math.abs(X) == Math.abs(Y) || X == 0 || Y == 0) {
				NN = true;
			}
			///////////
			if (NN == true) {
				try {
					xa = xa + pps * X / Math.abs(X);
				} catch (Exception h7e) {
				}
				try {
					ya = ya + pps * Y / Math.abs(Y);
				} catch (Exception h7e) {
				}
			}
			////////////
			else {
				xa = xa + pps * X * 3 / 4;
				ya = ya + pps * Y * 3 / 4;
			}
			//////////

			pieces_labels[ii][jj].setBounds(xa, ya, 100, 100);
			if (Math.abs(xa - xb) <= pps + 1 && Math.abs(ya - yb) <= pps + 1)

			{
				try {
					pieces_labels[ii][jj].setBounds(xb, yb, 100, 100);
					animation_timer.stop();
					counter++;
					if (castling == 0) {
						eaten_list_icon();
						Bord.clone(i1, j1, i2, j2);
						king_position();
						change_turn(i1, j1, i2, j2);
						game.Main.turn = (game.Main.turn + 1) % 2;
						done();
						
						history_jta.setText(history_jta.getText() + counter + ".  " + history + "\n");
						animation_lock = false;

					}

					else if (castling == 1) {
						history = game.Main.table_pieces[i1][j1].color == 1 ? "W" : "B";

						Bord.clone(i1, j1, i2, j2);
						king_position();
						change_turn(i1, j1, i2, j2);
						animation_lock = false;
					}

					else if (castling == 2) {
						Bord.clone(i1, j1, i2, j2);
						castling = 0;
						king_position();
						game.Main.turn = (game.Main.turn + 1) % 2;

						if (j2 < j1)
							history = history + " o - o";

						else
							history = history + " o - o - o";
						done();
						
						history_jta.setText(history_jta.getText() + counter + ".  " + history + "\n");
						animation_lock = false;
					}

				} catch (Exception re) {
					re.printStackTrace();
				}
			}
		}
	}

	public static void king_position() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				try {
					if (game.Main.table_pieces[i][j].name == 'K') {
						if (game.Main.table_pieces[i][j].color == game.Main.my_color)
							game.Main.my_king_position = String.valueOf(i) + String.valueOf(j);
						else
							game.Main.other_king_position = String.valueOf(i) + String.valueOf(j);
					}
				} catch (Exception e) {
				}
	}

	public static void change_turn(int Ia, int Ja, int Ib, int Jb) {

		if (game.Main.move_count > 1) {
			remaining = (long) (remaining + game.Main.n * 1000);
			int minutes = (int) (remaining / 60000);
			int seconds = (int) ((remaining % 60000) / 1000);
			String st = format.format(minutes) + ":" + format.format(seconds);
			///// setting label timers texts
			if (game.Main.turn == 1) {
				my_timer_label.setText(st);
			} else if (game.Main.turn == 0) {
				other_timer_label.setText(st);
			}
		}
		if (remaining > 10500) {
			if (game.Main.turn == 1) {
				my_timer_label.setForeground(Color.white);
			} else if (game.Main.turn == 0) {
				other_timer_label.setForeground(Color.WHITE);
			}
		}

		String k;

		if (game.Main.turn == 1) {
			k = game.Main.my_king_position;
		} else {
			k = game.Main.other_king_position;
		}

		if (game.Main.turn == 1) {
			game.Main.my_remaining_time = remaining;
		} else if (game.Main.turn == 0) {
			game.Main.other_remaining_time = remaining;
		}

		if (castling == 1) {

			i1 = Integer.parseInt(k.substring(0, 1));
			i2 = i1;

			if (j2 - j1 == 2) {

				j1 = 7;
				j2 = 5;
			} else {
				j1 = 0;
				j2 = 3;
			}
			castling++;
			move(i1, j1, i2, j2);
		}

	}

	private static void done() {
		king_position();

		if (game.Main.turn == 1)
			waiting_gif_label.setBounds(44, 980, 40, 40);
		else
			waiting_gif_label.setBounds(44, 926, 40, 40);

		if (upgrade != -1 && ((game.Main.turn == 1 && game.Main.app_mode == 0
				&& game.Main.table_pieces[i2][j2].color != game.Main.my_color) ||

				(game.Main.turn == 0 && game.Main.table_pieces[i2][j2].color == game.Main.my_color)))

			new Upgrade();
		else {
			after();
		}
	}

	private static void after() {
		String statement = end_check();
		if (statement.substring(0, 1).equals("1"))// stalemate
		{
			System.out.println("Stalemate");
			// game.Main.end_game = statement;
		} else if (statement.substring(0, 1).equals("2"))// check
		{
			System.out.println("Check");
		} else if (statement.substring(0, 1).equals("3"))// checkmate
		{
			System.out.println("Checkmate");
			// game.Main.end_game = statement;
		}
		//////////// this is the end of one move
		game.Main.move_count++;
		if ((statement.substring(0, 1).equals("2") || statement.substring(0, 1).equals("3"))) {
			if (statement.substring(1, 2).equals("1"))
				last_and_check_state_labels[Integer.parseInt(game.Main.my_king_position.substring(0, 1))][Integer
				                                                                                          .parseInt(game.Main.my_king_position.substring(1, 2))]
				                                                                                        		  .setIcon(new ImageIcon("img/background/Check.png"));
			else
				last_and_check_state_labels[Integer.parseInt(game.Main.other_king_position.substring(0, 1))][Integer
				                                                                                             .parseInt(game.Main.other_king_position.substring(1, 2))]
				                                                                                            		 .setIcon(new ImageIcon("img/background/Check.png"));
		}
		////// now we start the timer for other person
		if (game.Main.move_count >= 2 && !statement.substring(0, 1).equals("3")
				&& !statement.substring(0, 1).equals("1") && !statement.substring(0, 1).equals("4"))

			if (upgrade == -1)
				updateDisplay();
		if (statement.substring(0, 1).equals("4"))
			System.out.println("not enough sorce");

		i1 = j1 = i2 = j2 = -1;
	
	}

	public static String end_check() {
		String ans = "0";
		int color = 0;
		String k = "";
		boolean check = false;
		boolean stalement = true;
		if (game.Main.turn == 1) {
			color = game.Main.my_color;
			k = game.Main.my_king_position;
		} else {
			color = (game.Main.my_color + 1) % 2;
			k = game.Main.other_king_position;
		}
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				try {
					if (game.Main.table_pieces[i][j].color == color
							&& !board.pieces.Pieces.move_posibility(game.Main.table_pieces[i][j]).isEmpty()) {
						stalement = false;
					}
					if (game.Main.table_pieces[i][j].color == (color + 1) % 2) {
						board.pieces.Pieces.search_run_first_time = false;
						ArrayList<String> otherpossible = board.pieces.Pieces
								.move_posibility(game.Main.table_pieces[i][j]);
						board.pieces.Pieces.search_run_first_time = true;
						for (String x : otherpossible) {
							int ii = Integer.parseInt(x.substring(0, x.indexOf(",")));
							int jj = Integer.parseInt(x.substring(x.indexOf(",") + 1, x.length()));
							//
							int ia = Integer.parseInt(game.Main.table_pieces[i][j].location.substring(0, 1));
							int ja = Integer.parseInt(game.Main.table_pieces[i][j].location.substring(1, 2));
							//
							int ib = ia + ii;
							int jb = ja + jj;
							//
							String p2 = String.valueOf(ib) + String.valueOf(jb);
							if (k.equals(p2))
								check = true;
						}
					}
				} catch (Exception ex) {
				}
			}
		if (check == false && stalement == true)// stalement
			ans = "1";
		else if (check == true && stalement == false)// check
			ans = "2";
		else if (check == true && stalement == true)// mate
			ans = "3";
		// if (check == false && stalement == false)// normal
		// ans=0;
		if (game.Main.turn == 1) {
			game.Main.my_check = check;
			game.Main.my_stalement = stalement;
			ans = ans + "1";
		} else {
			game.Main.other_check = check;
			game.Main.other_stalement = stalement;
			ans = ans + "0";
		}
		/////// ans 31= i'm check mate
		////// ans 20 other's checked
		//
		/////
		///////
		///////
		///////
		////////// in case of // 2king// 2king 1 knight// 2 king one bishop
		////////// the game Draws due to the lack of force for checkmate
		//////
		////
		int N = 0;
		int B = 0;
		int count = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (game.Main.table_pieces[i][j] != null) {
					if (game.Main.table_pieces[i][j].name == 'N')
						N++;
					else if (game.Main.table_pieces[i][j].name == 'B')
						B++;
					count++;
				}
		if (count <= 3)
			if (B == 1 || N == 1 || count == 2) {
				ans = "4";

				game.Main.other_stalement = true;
				game.Main.my_stalement = true;

			}
		//
		//
		/////
		////
		////
		////
		//////
		////////
		//
		return ans;
	}

	public static void reset_values() {
		i1 = -1;
		j1 = -1;
		i2 = -1;
		j2 = -1; // clicked first and second
		ie = -1;
		je = -1;// mouse entered
		first_bounds = null;
		animation_lock = false;
		xa = -1;
		ya = -1;
		xb = -1;
		yb = -1;
		ii = -1;
		jj = -1;
		Y = 0;
		X = 0;
		tiles = new JButton[8][8];
		pieces_labels = new JLabel[8][8];
		notification_labels = new JLabel[8][8];
		last_and_check_state_labels = new JLabel[8][8];
		eaten_pieces_labels = new JLabel[2][16];
		my_timer_label = new JLabel("00:00");
		other_timer_label = new JLabel("00:00");
		waiting_gif_label = new JLabel("");
		tiles_panel_1 = new JPanel();
		notification_panel = new JPanel();
		check_state_panel = new JPanel();
		eated_pieces_panel_1 = new JPanel();
		time_timer = null;
		counter = 0;
		animation_timer = null;
		layeredPane = new JLayeredPane();
		remaining = game.Main.my_remaining_time;
		upgrade = 0;
		sending_line = "";
	}

	private class DrawOffer extends AbstractAction {
		public DrawOffer() {

			if (game.Main.app_mode != 0)
				putValue(SHORT_DESCRIPTION, "Offer Draw");
			else
				putValue(SHORT_DESCRIPTION, "Main Menu");
		}

		public void actionPerformed(ActionEvent e) {
			if (animation_lock == false) {
				if (game.Main.app_mode == 0) {
					game.Main.offer.mode = 0;
					game.Main.offer = new Draw_Offer();
				} else {
					game.Main.offer.mode = 1;
					game.Main.offer = new Draw_Offer();
				}
			}
		}
	}

	private class TimeGivness extends AbstractAction {
		public TimeGivness() {
			putValue(SHORT_DESCRIPTION, "Give 20 seconds");
		}

		public void actionPerformed(ActionEvent e) {
			if (game.Main.app_mode == 0) {
				if (game.Main.turn == 1) {

					game.Main.other_remaining_time += 20000;

					int minutes = (int) (game.Main.other_remaining_time / 60000);
					int seconds = (int) ((game.Main.other_remaining_time % 60000) / 1000);
					String st = "+ " + format.format(minutes) + ":" + format.format(seconds);
					other_timer_label.setText(st);

				} else if (game.Main.turn == 0) {
					game.Main.my_remaining_time += 20000;

					int minutes = (int) (game.Main.my_remaining_time / 60000);
					int seconds = (int) ((game.Main.my_remaining_time % 60000) / 1000);
					String st = "+ " + format.format(minutes) + ":" + format.format(seconds);
					my_timer_label.setText(st);
				}
			} else {

				game.Main.other_remaining_time += 20000;

				int minutes = (int) (game.Main.other_remaining_time / 60000);
				int seconds = (int) ((game.Main.other_remaining_time % 60000) / 1000);

				if (game.Main.turn == 0) {
					remaining += 20000;

					String st = "+ " + other_timer_label.getText();
					other_timer_label.setText(st);

				}

				else if (game.Main.turn == 1) {
					String st = "+ " + format.format(minutes) + ":" + format.format(seconds);
					other_timer_label.setText(st);
				}

				socket.send("!@~$timer#");
			}

		}
	}

	public static void upgrade_2(int Ib, int Jb) {
		if (Ib <= -1) {
			Ib = gui.Bord.i2;
			Jb = gui.Bord.j2;
		}

		if (upgrade == 1)
			Type = "Q";
		else if (upgrade == 2)
			Type = "N";
		else if (upgrade == 3)
			Type = "R";
		else if (upgrade == 4)
			Type = "B";

		game.Main.table_pieces[Ib][Jb] = board.pieces.Pieces.create_new_piece(Type,
				game.Main.table_pieces[Ib][Jb].color, String.valueOf(Ib) + String.valueOf(Jb));

		pieces_labels[Ib][Jb].setIcon(new ImageIcon(game.Main.table_pieces[Ib][Jb].image));

		if (game.Main.app_mode != 0 && game.Main.table_pieces[Ib][Jb].color == game.Main.my_color) {
			sending_line = "!@~$Pawn#" + String.valueOf(Ib) + String.valueOf(Jb) + upgrade;
			socket.send(sending_line);
		}
		upgrade = -1;
		Type = "";
		after();
	}
}