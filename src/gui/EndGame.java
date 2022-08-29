package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EndGame extends Thread {
	public static JFrame f = new JFrame();

	public EndGame() {
		this.start();
	}

	public void run() {
		
		f.setUndecorated(true);
		f.setBackground(new Color(0, 0, 0, 0));

		f.setOpacity(0.8f);
		f.setAlwaysOnTop(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(480, 0, 960, 1030);
		if (game.Main.app_mode == 1) {
			f.setBounds(new Rectangle(0, 0, 960, 1030));
		} else if (game.Main.app_mode == 2) {
			f.setBounds(new Rectangle(960, 0, 960, 1030));
		}
		f.getContentPane().setLayout(null);

		JButton main_menu_button = new JButton("Menu");
		main_menu_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.Main.bord.dispose();
				f.dispose();
				new gui.Main_Menu();
			}
		});
		//
		///
		//

		JLabel result_label = new JLabel("CheckMate");
		result_label.setForeground(Color.WHITE);
		result_label.setHorizontalAlignment(0);
		result_label.setFont(new Font("Segoe Print", Font.BOLD, 50));
		result_label.setBounds(0, 165, 960, 300);
		f.getContentPane().add(result_label);
		//
		//
		//

		main_menu_button.setBounds(228, 490, 138, 50);
		main_menu_button.setContentAreaFilled(false);
		main_menu_button.setOpaque(false);
		main_menu_button.setBorderPainted(false);
		main_menu_button.setForeground(new Color(255, 254, 254));

		main_menu_button.setFocusPainted(false);
		main_menu_button.setFont(new Font("Segoe Print", Font.BOLD, 30));

		f.getContentPane().add(main_menu_button);
		////

		////
		////

		////
		JButton exit_button = new JButton("Exit");
		exit_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}
		});
		exit_button.setBounds(594, 490, 138, 50);
		exit_button.setContentAreaFilled(false);
		exit_button.setOpaque(false);
		exit_button.setBorderPainted(false);
		exit_button.setForeground(new Color(255, 254, 254));
		exit_button.setFocusPainted(false);
		exit_button.setFont(new Font("Segoe Print", Font.BOLD, 30));
		f.getContentPane().add(exit_button);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(EndGame.class.getResource("/background/hole.png")));
		label.setBounds(0, 0, 960, 1030);
		f.getContentPane().add(label);

		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (game.Main.my_stalement == false && game.Main.other_stalement == false
				&& game.Main.my_remaining_time > 500 && game.Main.other_remaining_time > 500 && gui.Bord.remaining > 500
				&& game.Main.draw_offer == false) {

			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		if (game.Main.my_stalement == true && game.Main.my_check == true)
			result_label.setText(game.Main.my_name + " was checkmated");

		else if (game.Main.my_stalement == true && game.Main.my_check == false)
			result_label.setText(game.Main.my_name + " was stalemented");

		else if (game.Main.other_stalement == true && game.Main.other_check == true)
			result_label.setText(game.Main.other_name + " was checkmated");

		else if (game.Main.other_stalement == true && game.Main.other_check == false)
			result_label.setText(game.Main.other_name + " was stalemented");

		else if (game.Main.other_stalement == true && game.Main.my_stalement == true)
			result_label.setText("Not enough force");

		else if (game.Main.my_remaining_time < 500)
			result_label.setText(game.Main.my_name + "'s time is over");
		else if (game.Main.other_remaining_time < 500)
			result_label.setText(game.Main.other_name + "'s time is over");

		else if (game.Main.draw_offer == true) {
			result_label.setText("this game is over due to draw offer");
			if (game.Main.app_mode == 0)

				f.dispose();

		}

		try {
			f.setVisible(true);
		} catch (Exception c) {

			f.dispose();
		}
	}

}
