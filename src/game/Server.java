package game;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {
	public static ServerSocket ss;
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
	public static ArrayList<DataInputStream> input_stream = new ArrayList<DataInputStream>();
	public static ArrayList<DataOutputStream> output_stream = new ArrayList<DataOutputStream>();
	public static ArrayList<ArrayList<String>> history = new ArrayList<ArrayList<String>>();
	public static int turn = 0;

	public static void main(String[] args) {
		try {
			ss = new ServerSocket(game.Main.port);
			while (true) {
				new Connect(ss.accept()).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// class barqarari ertebat
	private static class Connect extends Thread {
		private Socket s;
		private DataInputStream in;
		private DataOutputStream out;

		public Connect(Socket socket) throws IOException {
			this.s = socket;
			// in = new DataInputStream (s.getInputStream()));
			in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
			out = new DataOutputStream(s.getOutputStream());
			if (Server.sockets.size() <= 1) {

				// TODO
				Server.sockets.add(s);
				Server.input_stream.add(in);
				Server.output_stream.add(out);
				if (Server.sockets.size() == 2) {
					start_game();
				}
			} else {
				// TODO
			}
		}

		public void run() {
			try {
				String line = in.readUTF();
				while (!line.startsWith("!@~$404#")) {
					send((sockets.indexOf(this.s) + 1) % 2, line);
					line = in.readUTF();
				}
			} catch (IOException e) {
				send_404();
			}
		}

		public static void start_game() {
			int counter = 1;
			Random rand = new Random();
			int random_white_black = rand.nextInt(2);
			for (int i = 0; i < 2; i++) {
				random_white_black = (random_white_black + 1) % 2;
				// TODO
				send(i, "!@~$1#" + Integer.toString((i + 1)) + random_white_black);
			}
		}

		public static void send(int index, String input) {
			try {
				output_stream.get(index).writeUTF(input);
			} catch (IOException e) {
				// e.printStackTrace();
			}
		}

		public static void send_404() {
			try {

				for (int i = 0; i < 2; i++) {
					send(i, "!@~$404#");
					try {
						sockets.get(i).close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
			}
			output_stream.clear();
			input_stream.clear();
			sockets.clear();
		}
	}
}