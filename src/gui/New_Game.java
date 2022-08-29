package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class New_Game extends JFrame {
	public JPanel contentPane;
	private final Action online_action = new SwingAction_online();
	private final Action offline_action = new SwingAction_offline();
	private final Action main_menu_action = new SwingAction_main_menu();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					New_Game frame = new New_Game();
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
	public New_Game() {

		game.Main.reset_values();
		Bord.CustomCursor(this, 0);// set cursor
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, 960, 1030);

		setUndecorated(true);
		getContentPane().setLayout(null);

		JButton online_game_button = new JButton("Online Mode");
		online_game_button.setAction(online_action);
		online_game_button.setContentAreaFilled(false);
		online_game_button.setOpaque(false);
		online_game_button.setBorderPainted(false);
		online_game_button.setForeground(new Color(255, 254, 254));
		online_game_button.setBackground(new Color(40, 30, 30));
		online_game_button.setFocusPainted(false);
		online_game_button.setFont(new Font("Segoe Print", Font.BOLD, 30));
		online_game_button.setBounds(349, 197, 262, 80);
		getContentPane().add(online_game_button);

		JButton offline_game_button = new JButton("Offline Mode");
		offline_game_button.setAction(offline_action);
		offline_game_button.setContentAreaFilled(false);
		offline_game_button.setOpaque(false);
		offline_game_button.setBorderPainted(false);
		offline_game_button.setForeground(new Color(255, 254, 254));
		offline_game_button.setBackground(new Color(40, 30, 30));
		offline_game_button.setFocusPainted(false);
		offline_game_button.setFont(new Font("Segoe Print", Font.BOLD, 30));
		offline_game_button.setBounds(349, 474, 262, 80);
		getContentPane().add(offline_game_button);

		JButton main_menu_button = new JButton("Main Menu");
		main_menu_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		main_menu_button.setAction(main_menu_action);
		main_menu_button.setContentAreaFilled(false);
		main_menu_button.setOpaque(false);
		main_menu_button.setBorderPainted(false);
		main_menu_button.setForeground(new Color(255, 254, 254));
		main_menu_button.setBackground(new Color(25, 20, 20));
		main_menu_button.setFocusPainted(false);
		main_menu_button.setFont(new Font("Segoe Print", Font.BOLD, 30));
		main_menu_button.setBounds(349, 751, 262, 80);
		getContentPane().add(main_menu_button);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(New_Game.class.getResource("/background/menu_background.png")));
		lblNewLabel.setBounds(0, 0, 960, 1030);
		getContentPane().add(lblNewLabel);
		setVisible(true);
	}

	private class SwingAction_online extends AbstractAction {
		public SwingAction_online() {
			putValue(NAME, "Online Mode");
		}

		public void actionPerformed(ActionEvent e) {
			new game.Online_Game();
			dispose();
		}
	}

	private class SwingAction_offline extends AbstractAction {
		public SwingAction_offline() {
			putValue(NAME, "Offline Mode");
		}

		public void actionPerformed(ActionEvent e) {
			new game.Offline_Game();
			dispose();
		}

	}

	private class SwingAction_main_menu extends AbstractAction {
		public SwingAction_main_menu() {
			putValue(NAME, "Main Menu");
		}

		public void actionPerformed(ActionEvent e) {
			new Main_Menu();
			dispose();
		}
	}
}