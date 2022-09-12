package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.socket;

public class Draw_Offer extends JFrame {
	public static int mode = 4;
	private final Action right = new right_action();
	private final Action left = new left_action();
	public JFrame helper_frame = new JFrame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Draw_Offer frame = new Draw_Offer();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Draw_Offer() {
		
		helper_frame.setBounds(new Rectangle(480, 0, 960, 1030));

		helper_frame.setUndecorated(true);
		helper_frame.setAlwaysOnTop(true);
		helper_frame.getContentPane().setLayout(null);
		helper_frame.setOpacity(0.4f);
		helper_frame.setBackground(new Color(0, 0, 0, 0));
		helper_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Bord.CustomCursor(helper_frame, 1);

		///////////////////
		setBounds(new Rectangle(480, 0, 960, 1030));
		setAlwaysOnTop(true);
		setUndecorated(true);
		getContentPane().setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		Bord.CustomCursor(this, 1);// set cursor
	
		if (game.Main.app_mode == 1) {
			setBounds(new Rectangle(0, 0, 900, 1030));
			helper_frame.setBounds(new Rectangle(0, 0, 960, 1030));
		} else if (game.Main.app_mode == 2) {
			setBounds(new Rectangle(960, 0, 900, 1030));
			helper_frame.setBounds(new Rectangle(960, 0, 960, 1030));
		}
		////////////
		//////
		JPanel offer_draw = new JPanel();
		offer_draw.setBounds(180, 275, 600, 200);
		offer_draw.setLayout(null);
		getContentPane().add(offer_draw);
		////////

		//////
		////////
		JLabel message = new JLabel();
		message.setBorder(null);
		message.setForeground(new Color(255, 254, 254));
		if (mode == 2)
			message.setText(game.Main.other_name + " has offered you a draw");
		else if (mode == 1 && game.Main.app_mode != 0)
			message.setText("are you sure you want to offer " + game.Main.other_name + " a draw ?");
		else
			message.setText("are you sure you want end this game ?");

		message.setFont(new Font("Segoe Print", Font.BOLD, 21));
		message.setBounds(0, 50, 600, 50);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		offer_draw.add(message);
		message.setOpaque(false);

		JButton left_button = new JButton("left");
		left_button.setMargin(new Insets(0, 0, 0, 0));
		left_button.setBounds(190, 110, 100, 60);
		offer_draw.add(left_button);
		left_button.setAction(left);
		left_button.setContentAreaFilled(false);
		left_button.setOpaque(false);
		left_button.setBorderPainted(false);
		left_button.setForeground(new Color(255, 254, 254));
		left_button.setBackground(new Color(40, 30, 30));
		left_button.setFocusPainted(false);
		left_button.setFont(new Font("Segoe Print", Font.BOLD, 19));

		JButton right_button = new JButton("right");
		right_button.setMargin(new Insets(0, 0, 0, 0));
		right_button.setBounds(310, 110, 100, 60);
		offer_draw.add(right_button);
		right_button.setAction(right);
		right_button.setContentAreaFilled(false);
		right_button.setOpaque(false);
		right_button.setBorderPainted(false);
		right_button.setForeground(new Color(255, 254, 254));
		right_button.setBackground(new Color(40, 30, 30));
		right_button.setFocusPainted(false);
		right_button.setFont(new Font("Segoe Print", Font.BOLD, 19));
		//////
		/////
		///////
		JLabel helper_label = new JLabel("");
		helper_label.setIcon(new ImageIcon("img/background/offer_help.png"));
		helper_label.setBounds(0, 0, 960, 1030);
		helper_frame.getContentPane().add(helper_label);
		///////////
		//////////
		///////
		JLabel back_ground_label = new JLabel("");
		back_ground_label.setIcon(new ImageIcon("img/background/offer_background.png"));
		back_ground_label.setBounds(0, 0, 600, 200);
		offer_draw.add(back_ground_label);

		setVisible(true);
		helper_frame.setVisible(true);
	}

	private class left_action extends AbstractAction {
		public left_action() {
			if (mode == 2)
				putValue(NAME, "Accept");
			else
				putValue(NAME, "YES");

		}

		public void actionPerformed(ActionEvent e) {

			if (game.Main.app_mode != 0) {
				try {
					if (mode == 1) {
						socket.send("!@~$draw#" + "1");
					} else if (mode == 2) {
						socket.send("!@~$draw#" + "2");
						dispose();
						helper_frame.dispose();
		
						game.Main.draw_offer=true;
						///TODO
					}
				}

				catch (Exception e1) {
					dispose();
					helper_frame.dispose();
					game.Main.bord.dispose();
					///TODO
				}
			}

			else {
				dispose();

				helper_frame.dispose();
				game.Main.bord.dispose();
				new Main_Menu();
				///TODO
			}

		}
	}

	private class right_action extends AbstractAction {
		public right_action() {
			if (mode == 2)
				putValue(NAME, "Decline");
			else
				putValue(NAME, "NO");

		}

		public void actionPerformed(ActionEvent e) {
			try {
				if (game.Main.app_mode != 0)
					if (mode == 2)
						socket.send("!@~$draw#" + "0");
			} catch (Exception e1) {
			}
			dispose();
			helper_frame.dispose();
		}

	}
}
