package game;

import java.io.IOException;

import gui.Draw_Offer;
import gui.New_Game;

public class ReadLine extends Thread {
	public static boolean online = true;

	ReadLine() {
		start();
	}

	public void run() {

		while (online == true) {
			try {

				socket.line = socket.in.readUTF();

				if (socket.line.startsWith("!@~$1#")) {
					Online_Game.P1(socket.line);
				}

				else if (socket.line.startsWith("!@~$2#")) {
					Online_Game.P2(socket.line);
				}

				else if (socket.line.startsWith("!@~$3#")) {
					Online_Game.P2(socket.line);
					// TODO
				}

				else if (socket.line.startsWith("!@~$200#")) {
					/////// everything's fine and this is contains the move
					String line = socket.line;

					int a = line.charAt(8) - '0';
					int b = line.charAt(10) - '0';
					int c = line.charAt(12) - '0';
					int d = line.charAt(14) - '0';

					gui.Bord.i1 = a;
					gui.Bord.j1 = b;
					gui.Bord.i2 = c;
					gui.Bord.j2 = d;

					gui.Bord.move(a, b, c, d);
				} else if (socket.line.startsWith("!@~$timer#")) {

					game.Main.my_remaining_time += 20000;

					int minutes = (int) (game.Main.my_remaining_time / 60000);
					int seconds = (int) ((game.Main.my_remaining_time % 60000) / 1000);

					if (game.Main.turn == 1) {
						gui.Bord.remaining += 20000;

						String st = "+ " + gui.Bord.my_timer_label.getText();
						gui.Bord.my_timer_label.setText(st);
					}

					else if (game.Main.turn == 0) {
						String st = "+ " + gui.Bord.format.format(minutes) + ":" + gui.Bord.format.format(seconds);
						gui.Bord.my_timer_label.setText(st);
					}

				} else if (socket.line.startsWith("!@~$Pawn#")) {
					///////////// Pawn had an upgrade
					// !@~$Pawn#03Q the white pawn which is in the 03 square had been upgraded to a
					// white queen in 03
					int Ib = socket.line.charAt(9) - '0';
					int Jb = socket.line.charAt(10) - '0';
					int Type = socket.line.charAt(11) - '0';
					gui.Bord.upgrade = Type;
					gui.Bord.upgrade_2(Ib, Jb);
					socket.line = "";
				}

				else if (socket.line.startsWith("!@~$draw#")) {
					//// 1 request// 2 request accepted//0 request declined

					if (socket.line.charAt(9) - '0' == 1) {
						Draw_Offer.mode=2;
						game.Main.offer = new Draw_Offer();
					}

					else if (socket.line.charAt(9) - '0' == 2) {
						game.Main.offer.helper_frame.dispose();
						game.Main.offer.dispose();
				
						game.Main.draw_offer=true;
						/// TODO The_End();
						// new The_End();
					} else if (socket.line.charAt(9) - '0' == 0) {

						game.Main.offer.helper_frame.dispose();
						game.Main.offer.dispose();
					
					}

				}

				else if (socket.line.startsWith("!@~$404#")) {
					/////// timeout or <<anything>> gone wrong

				}

			} catch (IOException e) {
				online = false;
				new New_Game();

			}

		}

	}

}