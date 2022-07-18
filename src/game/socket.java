package game;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class socket {
	public static DataInputStream in = null;
	static DataOutputStream out = null;
	public static String line = "";

	public socket() {
		try {
			game.Main.s = new Socket("127.0.0.1", Main.port);

			in = new DataInputStream(new BufferedInputStream(game.Main.s.getInputStream()));
			out = new DataOutputStream(game.Main.s.getOutputStream());

			// game.Main.s.setSoTimeout(Main.timeout);
			new ReadLine();

		} catch (Exception ee) {
			ee.printStackTrace();
			game.Main.s = null;
		}
	}

	public static int online_check() {
		return 0;

	}

	public static void send(String input) {
		try {
			out.writeUTF(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}