package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Upgrade extends JFrame {
	public JFrame helper_frame = new JFrame();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Upgrade frame = new Upgrade();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Upgrade() {
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

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(275, 275, 400, 300);
		getContentPane().add(panel);
		panel.setLayout(null);
		///////
		///////
		///////

		JButton queen_button = new JButton("");
		queen_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.Bord.upgrade = queen_button.getX() / 100 + 1;
				dispose();
				helper_frame.dispose();
				gui.Bord.upgrade_2(-1, -1);
			}
		});
		queen_button.setOpaque(false);
		queen_button.setContentAreaFilled(false);
		queen_button.setFocusPainted(false);
		queen_button.setBorderPainted(false);
		queen_button.setBounds(0, 150, 100, 100);
		panel.add(queen_button);
		///////
		///////
		///////
		///////
		JButton knight_button = new JButton("");
		knight_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.Bord.upgrade = knight_button.getX() / 100 + 1;
				dispose();
				helper_frame.dispose();

				gui.Bord.upgrade_2(-1, -1);
			}
		});
		knight_button.setOpaque(false);
		knight_button.setFocusPainted(false);
		knight_button.setBounds(100, 150, 100, 100);
		knight_button.setContentAreaFilled(false);
		knight_button.setBorderPainted(false);
		panel.add(knight_button);
		///////
		///////
		///////
		///////
		JButton rook_button = new JButton("");
		rook_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.Bord.upgrade = rook_button.getX() / 100 + 1;
				dispose();
				helper_frame.dispose();

				gui.Bord.upgrade_2(-1, -1);
			}
		});
		rook_button.setOpaque(false);
		rook_button.setBounds(200, 150, 100, 100);
		rook_button.setFocusPainted(false);
		rook_button.setBorderPainted(false);
		rook_button.setContentAreaFilled(false);
		panel.add(rook_button);
		///////
		///////
		///////
		JButton bishop_button = new JButton("");
		bishop_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gui.Bord.upgrade = bishop_button.getX() / 100 + 1;
				dispose();
				helper_frame.dispose();

				gui.Bord.upgrade_2(-1, -1);
			}
		});
		bishop_button.setOpaque(false);
		bishop_button.setBounds(300, 150, 100, 100);
		bishop_button.setBorderPainted(false);
		bishop_button.setContentAreaFilled(false);
		bishop_button.setFocusPainted(false);
		panel.add(bishop_button);
		///////
		///////
		///////
		///////
		JLabel text_label = new JLabel("Pawn's promotion ");
		text_label.setHorizontalAlignment(0);
		text_label.setForeground(Color.WHITE);
		text_label.setFont(new Font("Segoe Print", Font.PLAIN, 23));
		text_label.setBounds(0, 41, 400, 93);
		panel.add(text_label);
		///////
		///////
		///////
		///////
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(Upgrade.class.getResource("/background/upgrade_background.png")));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBounds(0, 0, 400, 300);
		panel.add(lblNewLabel);

		//////
		/////
		///////
		JLabel helper_label = new JLabel("");
		helper_label.setIcon(new ImageIcon(Draw_Offer.class.getResource("/background/upgrade_help.png")));
		helper_label.setBounds(0, 0, 960, 1030);
		helper_frame.getContentPane().add(helper_label);
		///////////
		//////////
		///////

		setVisible(true);
		helper_frame.setVisible(true);
	}
}
